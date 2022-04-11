package com.software.TALL.TATHeaderChecker.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.common.collect.Lists;
import com.software.TALL.TATHeaderChecker.model.Language;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.software.TALL.TATHeaderChecker.utils.Globals.APPLICATION_NAME;
import static com.software.TALL.TATHeaderChecker.utils.Globals.CLIENT_SECRET_PATH;
import static com.software.TALL.TATHeaderChecker.utils.SaUtils.*;
import static org.apache.http.conn.params.ConnManagerParams.setTimeout;

public class DriveUtils {

    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String SA_EMAIL = "tat-import@tall-sheet-impor-1614610764322.iam.gserviceaccount.com";

    public static void run(ServiceAccountKey saResponse, ArrayList<Language> languages) {

        //TODO use this file as example
            // getSheetsService()
            // getSchema(String spreadsheetId)

        // declare drive or spreadsheet service object to target
        try {
            Sheets sheets = getSheetsService(saResponse);

            // TODO parse
            for (int i = 0; i < languages.size(); i++) {
                String range = "Alphabet Embark!A1:Z";
                ValueRange response = sheets.spreadsheets().values()
                        .get(languages.get(i).getDoc_url(), range)
                        .execute();
                System.out.println(response);
            }

        } catch(IOException e) {
            System.out.println("getSheetsService failed");
        }

        // loop through languages
            // for each language
            // load spreadsheet url
            // for each tab in spreadsheet
                // declare set of column names
                // for each column
                    // if column name is in set
                    //

    }

    /**
     * Build and return an authorized Sheets API client service.
     *
     * @return an authorized Sheets API client service
     * @throws IOException
     */

    public static Sheets getSheetsService(ServiceAccountKey saResponse) throws IOException {

//        Credential credential = new GoogleCredential.Builder()
//                .setTransport(HTTP_TRANSPORT)
//                .setJsonFactory(JSON_FACTORY)
//                .setServiceAccountId(saResponse.getName())
//                .build();

//        GoogleCredentials credential = GoogleCredentials
//                .fromStream(new FileInputStream(CLIENT_SECRET_PATH))
//                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        //Credential credential = GoogleCredential.fromStream(new )

        Credential credential = authorize();

        // TODO test saResponse vs tall import as the input
//        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(ServiceAccountCredentials.fromStream(new FileInputStream(CLIENT_SECRET_PATH))
//                .createScoped(SCOPES)
//                .createDelegated(SA_EMAIL)); // TODO what is this?
//        Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer)
//                .setApplicationName(APPLICATION_NAME)
//                .build();

        Sheets sheetService = Sheets.Builder(HTTP_TRANSPORT,JSON_FACTORY, setTimeout(credential, 60000))
                .setApplicationName(APPLICATION_NAME)
                .build();
/*
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, setTimeout(credential, 60000))
                .setApplicationName(APPLICATION_NAME)
                .build();

 */
        return sheetService;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = DriveUtils.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");

        return credential;
    }

}
