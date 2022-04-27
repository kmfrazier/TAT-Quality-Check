package com.software.TALL.TATChecker.dao.sql;

import static com.software.TALL.TATChecker.utils.Globals.DATABASE_TABLE_ID;

public class LanguageSQL {

    public static final String SELECT_LANGUAGES = "" +
            "SELECT " +
            " lang_id as id, " +
            "locale, " +
            "lang_text_id AS language_id, " +
            "name, " +
            "sheet_url as doc_url, " +
            "archive_batch, " +
            "alphabet_batch " +
            "FROM " +
            "   " + DATABASE_TABLE_ID + " " +
            "ORDER BY id ASC ";
}

