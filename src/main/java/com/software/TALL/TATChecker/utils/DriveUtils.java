package com.software.TALL.TATChecker.utils;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.util.concurrent.RateLimiter;
import com.software.TALL.TATChecker.model.Language;
import com.software.TALL.TATChecker.model.SheetValue;
import com.software.TALL.TATChecker.service.DriveService;
import com.software.TALL.TATChecker.service.SaService;

import java.io.*;
import java.util.*;
import javax.ws.rs.core.Response;

import static com.software.TALL.TATChecker.utils.Globals.*;
import static com.software.TALL.TATChecker.utils.SaUtils.*;

public class DriveUtils {

    // space out the requests at 'rate' per second. 0.50 is optimal
    private static double rate = 0.50;
    final static RateLimiter rateLimiter = RateLimiter.create(rate);

    public static Response run(ServiceAccountKey saResponse, ArrayList<Language> languages) {

        try {
            // Instantiate Sheets using the service account info
            Sheets sheets = getSheetsService(saResponse);
            ArrayList<Sheets.Spreadsheets.Values.Get> getRequests = new ArrayList<Sheets.Spreadsheets.Values.Get>();

            // Declare sheet tabs to check
            ArrayList<SheetValue> sheetValues = new ArrayList<SheetValue>();

            String range1 = "Master List!1:1";
            String range2 = "Alphabet Embark!1:1";
            ArrayList<String> ranges = new ArrayList<String>();
            ranges.add(range1);
            ranges.add(range2);

            String header1 = "Master List";
            String header2 = "Alphabet Embark";
            ArrayList<String> headers = new ArrayList<String>();
            headers.add(header1);
            headers.add(header2);

            for (int i = 0; i < languages.size(); i++) {
                String sheetUrl = DriveService.extractUrl(languages.get(i).getDoc_url());
                for (int j = 0; j < ranges.size(); j++) {
                    SheetValue sv = new SheetValue(
                            languages.get(i).getDoc_url(),
                            languages.get(i).getName(),
                            headers.get(j),
                            ranges.get(j),
                            sheetUrl
                    );
                    Sheets.Spreadsheets.Values.Get getRequest = sheets.spreadsheets().values()
                            .get(sv.getExtractedUrl(), sv.getRangeToCheck());
                    getRequests.add(getRequest);
                    sheetValues.add(sv);


                }
            }

            for (int i = 0; i < getRequests.size(); i++) {
                rateLimiter.acquire();
                sheetValues.set(i, DriveService.throttledFetch(getRequests.get(i), sheetValues.get(i)));
            }

            ArrayList<String> results = new ArrayList<String>();
            for (int i = 0; i < sheetValues.size(); i++) {
                results.add(sheetValues.get(i).getMessage());
            }
            return Response.ok(results).build();

        } catch(IOException e) {
            String failure = "DriveUtils.run() failed";
            return Response.ok(e + " " + failure).build();
        }
    }

    /**
     * Build and return an authorized Sheets API client service.
     *
     * @return an authorized Sheets API client service
     * @throws IOException
     */

    public static Sheets getSheetsService(ServiceAccountKey saResponse) throws IOException {
        HttpRequestInitializer httpri = SaService.credentialAuthorize();
        Sheets sheetService = new Sheets.Builder(HTTP_TRANSPORT,JSON_FACTORY, setTimeout(httpri,60000))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return sheetService;
    }

    private static HttpRequestInitializer setTimeout(final HttpRequestInitializer initializer, final int timeout) {
        return request -> {
            initializer.initialize(request);
            request.setReadTimeout(timeout);
        };
    }

}