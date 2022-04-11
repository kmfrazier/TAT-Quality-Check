package com.software.TALL.TATHeaderChecker.service;

import com.google.api.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import com.software.TALL.TATHeaderChecker.utils.DriveUtils;

import java.io.*;
import java.security.GeneralSecurityException;

import static com.software.TALL.TATHeaderChecker.utils.Globals.*;
//import static com.software.TALL.TATHeaderChecker.utils.Globals.GOOGLE_APPLICATION_CREDENTIALS;


public class SaService {
//    public static void run() throws IOException, GeneralSecurityException {
//        System.out.println("SaService.run()");
//
//        // TODO call authExplicit(GOOGLE_APPLICATION_CREDENTIALS) here
//        // authExplicit(GOOGLE_APPLICATION_CREDENTIALS)
//
//        String name = SA_GET_METHOD;
//
//        Iam iamService = createIamService();
//        Iam.Projects.ServiceAccounts.Keys.Get request =
//                iamService.projects().serviceAccounts().keys().get(name);
//
//        ServiceAccountKey response = request.execute();
//
//        // TODO: Change code below to process the `response` object:
//        System.out.println(response);
//    }
//
//    public static Iam createIamService() throws IOException, GeneralSecurityException {
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//
//        InputStream in = SaService.class.getResourceAsStream("/client_secret.json");
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        //GoogleCredential credential = GoogleCredential.getApplicationDefault(); // TODO declare Application Default Credentials
//        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//        if (credential.createScopedRequired()) {
//            credential =
//                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
//        }
//
//        return new Iam.Builder(httpTransport, jsonFactory, credential)
//                .setApplicationName("TAT-Header-Checker") //TODO rename this
//                .build();
//    }
//
//    //todo finish implementing this function
//    static void authExplicit(String jsonPath) throws IOException {
//        // You can specify a credential file by providing a path to GoogleCredentials.
//        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
//        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
//                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
//        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//
//        System.out.println("Buckets:");
//        Page<Bucket> buckets = storage.list();
//        for (Bucket bucket : buckets.iterateAll()) {
//            System.out.println(bucket.toString());
//        }
//    }

    //private static String CLIENT_SECRET_PATH = "src/main/resources/client_secret.json";

    public static ServiceAccountKey run() throws IOException, GeneralSecurityException {
        System.out.println("SaService.run()");

        Iam iamService = createIamService();
        Iam.Projects.ServiceAccounts.Keys.Get request =
                iamService.projects().serviceAccounts().keys().get(SA_GET_METHOD);

        System.out.println(request);

        ServiceAccountKey response = request.execute();

        System.out.println(response);

        return response;
    }

    public static Iam createIamService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleCredentials credential = GoogleCredentials.fromStream(new FileInputStream(CLIENT_SECRET_PATH))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        //is storing necessary?
        //Storage storage = StorageOptions.newBuilder().setCredentials(credential).build().getService();

        // it seems this constructor may be deprecated, and the spot credential would take is
        // instead an HttpRequestInitializer
        Iam iam = new Iam.Builder(httpTransport,jsonFactory, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return iam;
    }


}
