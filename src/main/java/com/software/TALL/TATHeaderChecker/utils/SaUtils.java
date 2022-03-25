package com.software.TALL.TATHeaderChecker.utils;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.software.TALL.TATHeaderChecker.processes.GetSa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.lang.invoke.MethodHandles;

public class SaUtils {

    /** Application name. */
    private static final String APPLICATION_NAME = "TALL Header Checker";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com-tall-alphabet");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;


    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        }
        catch (Throwable t) {
            log.error("Error initializing.", t);
            //t.printStackTrace();
            System.exit(1);
        }
    }

    public static void runSaUtils() {
        try {
            System.out.println("runSaUtils");
            GetSa.run();
        } catch (Throwable t) {
            log.error("general security exception.",t);
        }

    }



}
