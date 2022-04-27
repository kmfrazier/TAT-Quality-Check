package com.software.TALL.TATChecker.utils;

public class Globals {

    public static String APPLICATION_NAME = "tat-header-checker";
    public static String DATABASE_TABLE_ID = "tall_tools.locale";
    public static String KEY_ID = "b751dec42378956e18c66d19099cf24f166df696";
    public static String KEY_TYPE = "TYPE_RAW_PUBLIC_KEY";
    public static String PROJECT_ID = "mtc-tall-spreadsheet-import";
    public static String SA_EMAIL = "tall-service-account@mtc-tall-spreadsheet-import.iam.gserviceaccount.com";
    public static String SA_NAME = "113896262364677014749";
    public static String SA_SECRET_PATH = "src/main/resources/mtc-tall-spreadsheet-import-e4f1574bd5af.json";

    public static String SA_GET_METHOD = "projects/"+PROJECT_ID+"/serviceAccounts/"+SA_NAME+"/keys/"+KEY_ID+"?publicKeyType="+KEY_TYPE;

}
