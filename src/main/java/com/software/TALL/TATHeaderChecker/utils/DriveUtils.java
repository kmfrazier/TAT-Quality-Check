package com.software.TALL.TATHeaderChecker.utils;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.software.TALL.TATHeaderChecker.model.Language;
import com.software.TALL.TATHeaderChecker.model.SheetValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import javax.ws.rs.core.Response;

import static com.software.TALL.TATHeaderChecker.utils.Globals.*;
import static com.software.TALL.TATHeaderChecker.utils.SaUtils.*;

public class DriveUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    public static Response run(ServiceAccountKey saResponse, ArrayList<Language> languages) {

        try {
            // Instantiate Sheets using the service account info
            Sheets sheets = getSheetsService(saResponse);

            // Declare sheet tab names
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

//            String[] badSheets =
//                    {
//                            "https://docs.google.com/spreadsheets/d/12zwe-R33F1PM1AEjs57bCDn3fu7Xx5pLtG1tRjVWcOg/edit",
//                            "https://docs.google.com/spreadsheets/d/16Jb1hh03TML5Etrb-EsqqcMhMfhWJB7gDMdYzV5--XE/edit#gid=1411803589",
//                            "https://docs.google.com/spreadsheets/d/1q32umGA0SFvUDawZWMgvbIy0BZ1mgRnnlmr3kEClDSQ/edit#gid=1354481109",
//                            "https://docs.google.com/spreadsheets/d/1-KXGXlSspHd3Sy7SAl7OC7zhAIcIvLpICBL-N8I3l_E/edit#gid=420552297",
//                            "https://docs.google.com/spreadsheets/d/195UUDuj5N-brFHmqBpM-C-uJOMWz1B9ns0WlL1KWPhA/edit",
//                            "https://docs.google.com/spreadsheets/d/1MuU1Q14wSNBF2XXAY8oCrmbixXl0dpzaxaqKSr19LdA/edit#gid=845640874"
//            };

            for (int i = 0; i < languages.size(); i++) {

                String sheetUrl = extractUrl(languages.get(i).getDoc_url());
                System.out.println(languages.get(i).getDoc_url());

                // TODO iterate over each header/range pair,
                //  for each iteration pass both into a refactored fetch function

                for (int j = 0; j < ranges.size(); j++) {

                    SheetValue sv = new SheetValue(
                            languages.get(i).getDoc_url(),
                            languages.get(i).getName(),
                            headers.get(j),
                            ranges.get(j),
                            sheetUrl
                    );

                    sheetValues.add(fetch(sheets, sv));
                }


                // if GET fails, then return "{locale} {which tab} Tab does not exist"
                // else
                    // if vr is null, then return "{locale} {which tab} Tab exists, header is empty"
                    // if vr has duplicate, then return "{locale} {which tab} Tab exists, duplicate headers {list header}"
                    // if vr does not have duplicate, then return "{locale} {which tab} Tab header passes"
//
//                try {
//                    ValueRange response1 = sheets.spreadsheets().values()
//                            .get(sheetUrl, range1)
//                            .execute();
//                    System.out.println(response1.getValues());
//                } catch(IOException e) {
//                    System.out.println("get Master List failed for " + languages.get(i).getLocale());
//                }
//
//                try {
//                    ValueRange response2 = sheets.spreadsheets().values()
//                            .get(sheetUrl, range2)
//                            .execute();
//                    System.out.println(response2.getValues());
//                } catch(IOException e) {
//                    System.out.println("get Alphabet Embark failed for " + languages.get(i).getLocale());
//                }

            }

            ArrayList<String> results = new ArrayList<String>();
            for (int i = 0; i < sheetValues.size(); i++) {
                results.add(sheetValues.get(i).getMessage());
            }
            return Response.ok(results).build();

        } catch(IOException e) {
            String failure = "DriveUtils.run() failed";
            System.out.println(failure);
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

        HttpRequestInitializer httpri = credentialAuthorize();

        Sheets sheetService = new Sheets.Builder(HTTP_TRANSPORT,JSON_FACTORY, setTimeout(httpri,60000))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return sheetService;
    }


    private static HttpRequestInitializer credentialAuthorize() throws IOException {

        String url = "mtc-tall-spreadsheet-import-e4f1574bd5af.json";
        ServiceAccountCredentials serviceAccountCredentials =
                ServiceAccountCredentials.fromStream(Objects.requireNonNull(DriveUtils.class.getClassLoader().getResourceAsStream(url)));

        GoogleCredentials googleCredentials = serviceAccountCredentials.createScoped(SCOPES);

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(googleCredentials);

        return requestInitializer;
    }


    private static GoogleCredentials googleCredentialsAuthorize() throws IOException{

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(SA_SECRET_PATH))
                .createScoped(DriveScopes.all());

        return credentials;
    }


    private static HttpRequestInitializer setTimeout(final HttpRequestInitializer initializer, final int timeout) {
        return request -> {
            initializer.initialize(request);
            request.setReadTimeout(timeout);
        };
    }

    private static String extractUrl(String docId) {
        // Example:
            // https://docs.google.com/spreadsheets/d/1Gfmom7BEvzIvyB6Ht3Sb5Q50rz8nytuEJJ7kzVs7y1I/edit#gid=420552297
            // becomes:
            // 1Gfmom7BEvzIvyB6Ht3Sb5Q50rz8nytuEJJ7kzVs7y1I/
        int firstCutoff = docId.indexOf("/d/") + 3;
        String firstString = docId.substring(firstCutoff);
        int secondCutoff = firstString.indexOf("/edit");
        String finalString = firstString.substring(0, secondCutoff);
        return finalString;
    }

    private static SheetValue fetch(Sheets sheets, SheetValue sv) throws IOException {
        ArrayList<String> messages = new ArrayList<String>();
            try {
                // pull headers from sheet
                ValueRange response = sheets.spreadsheets().values()
                        .get(sv.getExtractedUrl(), sv.getRangeToCheck())
                        .execute();
                System.out.println(response.getValues());

                // response object will not contain "values" key if there are no headers on the sheet
                if (!response.containsKey("values")) {
                    String m = sv.getLanguageName()
                            + " - "
                            + sv.getHeaderToCheck()
                            + " - fail: no headers found";

                    sv.setMessage(m);
                    return sv;
                }

                sv.setDoesTabExist(true);
                ArrayList<String> dup = checkForDuplicates(response);

                // if dup contains any duplicate strings found while checking for duplicates
                if (dup.size() > 0) {
                    String m = sv.getLanguageName()
                            + " - "
                            + sv.getHeaderToCheck()
                            + " fail: duplicates found";
                    sv.setMessage(m);
                } else {
                    sv.setDoesHeaderPass(true);
                    String m = sv.getLanguageName()
                            + " - "
                            + sv.getHeaderToCheck()
                            + " pass";
                    sv.setMessage(m);
                }

            } catch(IOException e) {
                String m = sv.getLanguageName()
                        + " - "
                        + sv.getHeaderToCheck()
                        + " - fail: no headers found";
                sv.setMessage(m);
            }

        return sv;
    }

    private static ArrayList<String> checkForDuplicates(ValueRange vr) {
        ArrayList<String> indices = new ArrayList<String>();
        for (int i = 0; i < vr.getValues().size(); i++) {
            for (int j = 0; j < vr.getValues().get(0).size(); j++) {
                String val1 = vr.getValues().get(0).get(i).toString();
                String val2 = vr.getValues().get(0).get(j).toString();
                if (i == j || val1.isEmpty() || val2.isEmpty()) {
                    continue;
                } else if (val1.equalsIgnoreCase(val2)) {
                    if ( !indices.contains(val1)) {
                        indices.add(val1);
                    }
                }
            }
        }
        return indices;
    }

}
