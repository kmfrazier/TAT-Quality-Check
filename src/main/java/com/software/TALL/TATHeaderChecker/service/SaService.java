package com.software.TALL.TATHeaderChecker.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.common.collect.Lists;
import com.software.TALL.TATHeaderChecker.utils.DriveUtils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.software.TALL.TATHeaderChecker.utils.Globals.*;

public class SaService {

    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    public static ServiceAccountKey run() throws IOException, GeneralSecurityException {
        Iam iamService = createIamService();
        Iam.Projects.ServiceAccounts.Keys.Get request =
                iamService.projects().serviceAccounts().keys().get(SA_GET_METHOD);

        ServiceAccountKey response = request.execute();

        return response;
    }

    public static Iam createIamService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleCredentials credential = GoogleCredentials.fromStream(new FileInputStream(SA_SECRET_PATH))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        Iam iam = new Iam.Builder(httpTransport,jsonFactory, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return iam;
    }

    public static HttpRequestInitializer credentialAuthorize() throws IOException {

        String url = "mtc-tall-spreadsheet-import-e4f1574bd5af.json";
        ServiceAccountCredentials serviceAccountCredentials =
                ServiceAccountCredentials.fromStream(Objects.requireNonNull(DriveUtils.class.getClassLoader().getResourceAsStream(url)));

        GoogleCredentials googleCredentials = serviceAccountCredentials.createScoped(SCOPES);

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(googleCredentials);

        return requestInitializer;
    }

}
