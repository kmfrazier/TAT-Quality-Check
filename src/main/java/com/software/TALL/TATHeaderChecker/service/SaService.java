package com.software.TALL.TATHeaderChecker.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.paging.Page;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static com.software.TALL.TATHeaderChecker.utils.Globals.SA_GET_METHOD;
import static com.software.TALL.TATHeaderChecker.utils.Globals.GOOGLE_APPLICATION_CREDENTIALS;

public class SaService {
    public static void run() throws IOException, GeneralSecurityException {
        System.out.println("SaService.run()");

        // TODO call authExplicit(GOOGLE_APPLICATION_CREDENTIALS) here
        // The resource name of the service account key in the following format:
        // `projects/{PROJECT_ID}/serviceAccounts/{ACCOUNT}/keys/{key}`.
        // Using `-` as a wildcard for the `PROJECT_ID` will infer the project from
        // the account. The `ACCOUNT` value can be the `email` address or the
        // `unique_id` of the service account.
        String name = SA_GET_METHOD;
        // placeholder value.

        Iam iamService = createIamService();
        Iam.Projects.ServiceAccounts.Keys.Get request =
                iamService.projects().serviceAccounts().keys().get(name);

        ServiceAccountKey response = request.execute();

        // TODO: Change code below to process the `response` object:
        System.out.println(response);
    }

    public static Iam createIamService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleCredential credential = GoogleCredential.getApplicationDefault(); // TODO declare Application Default Credentials
        if (credential.createScopedRequired()) {
            credential =
                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        }

        return new Iam.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("TAT-Header-Checker") //TODO rename this
                .build();
    }

    //todo finish implementing this function
    static void authExplicit(String jsonPath) throws IOException {
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }
}
