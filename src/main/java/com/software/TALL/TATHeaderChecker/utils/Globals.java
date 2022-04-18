package com.software.TALL.TATHeaderChecker.utils;

public class Globals {

    public static String SA_EMAIL = "tall-service-account@mtc-tall-spreadsheet-import.iam.gserviceaccount.com";
    public static String PROJECT_ID = "mtc-tall-spreadsheet-import";
    public static String SA_NAME = "113896262364677014749";
    public static String KEY_ID = "b751dec42378956e18c66d19099cf24f166df696";
    public static String KEY_TYPE = "TYPE_RAW_PUBLIC_KEY";
    public static String SA_GET_METHOD = "projects/"+PROJECT_ID+"/serviceAccounts/"+SA_NAME+"/keys/"+KEY_ID+"?publicKeyType="+KEY_TYPE;
    public static String APPLICATION_NAME = "tat-header-checker";
    public static String SA_SECRET_PATH = "src/main/resources/mtc-tall-spreadsheet-import-e4f1574bd5af.json";
    public static String CLIENT_SECRET_PATH = "src/main/resources/client_secret.json";
    public static String DATABASE_TABLE_ID = "tall_tools.locale";

    //public static String GOOGLE_APPLICATION_CREDENTIALS = "";
    //projects/mtc-tall-spreadsheet-import/serviceAccounts/tall-service-account@mtc-tall-spreadsheet-import.iam.gserviceaccount.com/keys/b751dec42378956e18c66d19099cf24f166df696
    // https://cloud.google.com/iam/docs/creating-managing-service-accounts#updating
    // GET https://iam.googleapis.com/v1/projects/mtc-tall-spreadsheet-import/serviceAccounts/tall-service-account/keys/b751dec42378956e18c66d19099cf24f166df696?publicKeyType=TYPE_RAW_PUBLIC_KEY&key=[YOUR_API_KEY] HTTP/1.1

}
