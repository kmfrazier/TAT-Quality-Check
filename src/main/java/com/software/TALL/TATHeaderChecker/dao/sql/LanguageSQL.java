package com.software.TALL.TATHeaderChecker.dao.sql;

public class LanguageSQL {

//    public static final String SELECT_LANGUAGES_BY_APP_ID = "" +
//            "SELECT " +
//            "  l.lang_num_id AS id, " + // may need to use lang_id if the app has been using that.. not unique, though
//            "  l.lang_num_id AS prefix, " +
//            "  l.name, " +
//            "  COALESCE(l.native_name, l.name) AS endonym, " +
//            "  l.locale, " +
//            "  cd.display_value AS download_size, " +
//            "  IF (cb.module = 1, 'aws', 'mtc') AS cdn, " +
//            "  l.is_tonal_language, " +
//            "  l.is_ignore_tone_order_language, " +
//            "  l.has_multiple_alphabets, " +
//            "  l.is_spaceless_language, " +
//            "  l.is_right_to_left_language, " +
//            "  l.is_resource_only_language " +
//            "FROM " +
//            "  standards.locales l " +
//            "  LEFT JOIN tallcourse.app_locale al ON l.locale = al.locale " +
//            "  LEFT JOIN tallcourse.content_download_size cd ON l.locale = cd.locale " +
//            "  LEFT JOIN tall_beta.course_beta cb ON l.locale = cb.locale AND cb.module = 1 " +
//            "WHERE al.app_id = ? " +
//            "  AND al.status = 'COMPLETE' " +
//            "  AND IF (cb.module = 1, 'aws', 'mtc') = cd.content_cdn";

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
            "   tall_tools.locale " +
            "ORDER BY id ASC ";
//            "SELECT " +
//            "  lang_id as id, " +
//            "  name, " +
//            "  native_name as endonym," +
//            "  locale, " +
//            "  lang_num_id as prefix" +
//            "FROM " +
//            "  standards.locales " +
//            "ORDER BY name ASC ";
}

