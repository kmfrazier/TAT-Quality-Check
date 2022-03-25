package com.software.TALL.TATHeaderChecker.processes;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static com.software.TALL.TATHeaderChecker.utils.Globals.*;


public class GetSa {

    public static void run() throws IOException, GeneralSecurityException {
        System.out.println("runGetSa.run()");
//        String name =
//                SA_GET_METHOD;
//
//        Iam iamService = createIamService();
//        Iam.Projects.ServiceAccounts.Keys.Get request =
//                iamService.projects().serviceAccounts().keys().get(name);
//
//        ServiceAccountKey response = request.execute();
//
//        System.out.println(response);
    }

//    public static Iam createIamService() throws IOException, GeneralSecurityException {
//        System.out.println("runGetSa.createIamService()");
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//
//        GoogleCredential credential = GoogleCredential.getApplicationDefault();
//        if (credential.createScopedRequired()) {
//            credential =
//                    credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
//        }
//
//        return new Iam.Builder(httpTransport, jsonFactory, credential)
//                .setApplicationName("Google-iamSample/0.1")
//                .build();
//    }
}
