package com.software.TALL.TATChecker.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_EMPTY)
public class Language {

    private String id;
    private String locale;
    private String language_id;
    private String name;
    private String doc_url;
    private String archive_batch;
    private String alphabet_batch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getArchive_batch() {
        return archive_batch;
    }

    public void setArchive_batch(String archive_batch) {
        this.archive_batch = archive_batch;
    }

    public String getAlphabet_batch() {
        return alphabet_batch;
    }

    public void setAlphabet_batch(String alphabet_batch) {
        this.alphabet_batch = alphabet_batch;
    }

}
