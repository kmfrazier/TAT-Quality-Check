package com.software.TALL.TATChecker.utils;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.iam.v1.model.ServiceAccountKey;
import com.software.TALL.TATChecker.service.SaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.lang.invoke.MethodHandles;

public class SaUtils {

    /** Application name. */
    private static final String APPLICATION_NAME = "TAT Header Checker";

    /** Directory to store user credentials for this application. */
    public static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com-tall-alphabet");

    /** Global instance of the {@link FileDataStoreFactory}. */
    public static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static ServiceAccountKey SaKey = null;

    /** Global instance of the HTTP transport. */
    public static HttpTransport HTTP_TRANSPORT;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        }
        catch (Throwable t) {
            log.error("Error initializing.", t);
            System.exit(1);
        }
    }

    public static void runSaUtils() {
        try {
            SaKey = SaService.run();
        } catch (Throwable t) {
            log.error("general security exception.",t);
        }

    }

}
