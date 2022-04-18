package com.software.TALL.TATHeaderChecker.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.*;
import java.security.GeneralSecurityException;

import static com.software.TALL.TATHeaderChecker.utils.Globals.*;


public class SaService {

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

        GoogleCredentials credential = GoogleCredentials.fromStream(new FileInputStream(SA_SECRET_PATH))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

        Iam iam = new Iam.Builder(httpTransport,jsonFactory, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return iam;
    }


}
