package com.software.TALL.TATHeaderChecker.dao;

class DbImportSQL {

    public static final String SELECT_DB_LANGUAGE_LIST = " " +
            "SELECT " +
            "   locale, " +
            "   language_id, " +
            "   name, " +
            "   source_document_id " +
            "FROM " +
            "   language_import.locales ";

}
