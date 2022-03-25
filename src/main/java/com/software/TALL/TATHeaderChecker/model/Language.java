package com.software.TALL.TATHeaderChecker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_EMPTY)
public class Language {

//    private String id;
//    private String name;
//    private String endonym;
//    private String locale;
//    private String downloadSize;
//    private Integer isTonalLanguage;
//    private Integer isIgnoreToneOrderLanguage;
//    private Integer hasMultipleAlphabets;
//    private Integer isSpacelessLanguage;
//    private Integer isRightToLeftLanguage;
//    private Integer isResourceOnlyLanguage;

    private String id;
    private String locale;
    private String language_id;
    private String name;
    private String doc_url;
    private String archive_batch;
    private String alphabet_batch;

    @JsonIgnore
    private Integer prefix;

    @JsonIgnore
    private String cdn;


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

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
    public Integer getPrefix() {
        return prefix;
    }

    public void setPrefix(Integer prefix) {
        this.prefix = prefix;
    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEndonym() {
//        return endonym;
//    }
//
//    public void setEndonym(String endonym) {
//        this.endonym = endonym;
//    }
//
//    public String getLocale() {
//        return locale;
//    }
//
//    public void setLocale(String locale) {
//        this.locale = locale;
//    }
//
//    public String getDownloadSize() {
//        return downloadSize;
//    }
//
//    public void setDownloadSize(String downloadSize) {
//        this.downloadSize = downloadSize;
//    }
//
//    public Integer getIsTonalLanguage() {
//        return isTonalLanguage;
//    }
//
//    public void setIsTonalLanguage(Integer isTonalLanguage) {
//        this.isTonalLanguage = isTonalLanguage;
//    }
//
//    public Integer getIsIgnoreToneOrderLanguage() {
//        return isIgnoreToneOrderLanguage;
//    }
//
//    public void setIsIgnoreToneOrderLanguage(Integer isIgnoreToneOrderLanguage) {
//        this.isIgnoreToneOrderLanguage = isIgnoreToneOrderLanguage;
//    }
//
//    public Integer getHasMultipleAlphabets() {
//        return hasMultipleAlphabets;
//    }
//
//    public void setHasMultipleAlphabets(Integer hasMultipleAlphabets) {
//        this.hasMultipleAlphabets = hasMultipleAlphabets;
//    }
//
//    public Integer getIsSpacelessLanguage() {
//        return isSpacelessLanguage;
//    }
//
//    public void setIsSpacelessLanguage(Integer isSpacelessLanguage) {
//        this.isSpacelessLanguage = isSpacelessLanguage;
//    }
//
//    public Integer getIsRightToLeftLanguage() {
//        return isRightToLeftLanguage;
//    }
//
//    public void setIsRightToLeftLanguage(Integer isRightToLeftLanguage) {
//        this.isRightToLeftLanguage = isRightToLeftLanguage;
//    }
//
    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }
//
//    public Integer getIsResourceOnlyLanguage() {
//        return isResourceOnlyLanguage;
//    }
//
//    public void setIsResourceOnlyLanguage(Integer isResourceOnlyLanguage) {
//        this.isResourceOnlyLanguage = isResourceOnlyLanguage;
//    }
}
