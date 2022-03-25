package com.software.TALL.TATHeaderChecker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_EMPTY)
public class Language {

    private String id;
    private String name;
    private String endonym;
    private String locale;
    private String downloadSize;
    private Integer isTonalLanguage;
    private Integer isIgnoreToneOrderLanguage;
    private Integer hasMultipleAlphabets;
    private Integer isSpacelessLanguage;
    private Integer isRightToLeftLanguage;
    private Integer isResourceOnlyLanguage;

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

    public Integer getPrefix() {
        return prefix;
    }

    public void setPrefix(Integer prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndonym() {
        return endonym;
    }

    public void setEndonym(String endonym) {
        this.endonym = endonym;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(String downloadSize) {
        this.downloadSize = downloadSize;
    }

    public Integer getIsTonalLanguage() {
        return isTonalLanguage;
    }

    public void setIsTonalLanguage(Integer isTonalLanguage) {
        this.isTonalLanguage = isTonalLanguage;
    }

    public Integer getIsIgnoreToneOrderLanguage() {
        return isIgnoreToneOrderLanguage;
    }

    public void setIsIgnoreToneOrderLanguage(Integer isIgnoreToneOrderLanguage) {
        this.isIgnoreToneOrderLanguage = isIgnoreToneOrderLanguage;
    }

    public Integer getHasMultipleAlphabets() {
        return hasMultipleAlphabets;
    }

    public void setHasMultipleAlphabets(Integer hasMultipleAlphabets) {
        this.hasMultipleAlphabets = hasMultipleAlphabets;
    }

    public Integer getIsSpacelessLanguage() {
        return isSpacelessLanguage;
    }

    public void setIsSpacelessLanguage(Integer isSpacelessLanguage) {
        this.isSpacelessLanguage = isSpacelessLanguage;
    }

    public Integer getIsRightToLeftLanguage() {
        return isRightToLeftLanguage;
    }

    public void setIsRightToLeftLanguage(Integer isRightToLeftLanguage) {
        this.isRightToLeftLanguage = isRightToLeftLanguage;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public Integer getIsResourceOnlyLanguage() {
        return isResourceOnlyLanguage;
    }

    public void setIsResourceOnlyLanguage(Integer isResourceOnlyLanguage) {
        this.isResourceOnlyLanguage = isResourceOnlyLanguage;
    }
}
