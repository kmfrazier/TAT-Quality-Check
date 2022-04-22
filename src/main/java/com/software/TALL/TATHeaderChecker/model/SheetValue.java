package com.software.TALL.TATHeaderChecker.model;

import com.google.api.services.sheets.v4.model.ValueRange;

public class SheetValue {

    public SheetValue(String docUrl, 
                      String languageName, 
                      String headerToCheck,
                      String rangeToCheck,
                      String extractedUrl) {
        this.docUrl = docUrl;
        this.languageName = languageName;
        this.headerToCheck = headerToCheck;
        this.rangeToCheck = rangeToCheck;
        this.extractedUrl = extractedUrl;
    }

    public ValueRange getHeaderContents() {
        return headerContents;
    }

    public void setHeaderContents(ValueRange headerContents) {
        this.headerContents = headerContents;
    }

    public boolean isDoesTabExist() {
        return doesTabExist;
    }

    public void setDoesTabExist(boolean doesTabExist) {
        this.doesTabExist = doesTabExist;
    }

    public boolean isDoesHeaderPass() {
        return doesHeaderPass;
    }

    public void setDoesHeaderPass(boolean doesHeaderPass) {
        this.doesHeaderPass = doesHeaderPass;
    }

    public boolean isHeaderEmpty() {
        return isHeaderEmpty;
    }

    public void setHeaderEmpty(boolean headerEmpty) {
        isHeaderEmpty = headerEmpty;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getRangeToCheck() {
        return rangeToCheck;
    }

    public void setRangesToCheck(String rangeToCheck) {
        this.rangeToCheck = rangeToCheck;
    }


    public String getHeaderToCheck() {
        return headerToCheck;
    }

    public void setHeaderToCheck(String rangeToCheck) {
        this.headerToCheck = rangeToCheck;
    }
    
    public String getExtractedUrl() {
        return extractedUrl;
    }

    public void setExtractedUrl(String extractedUrl) {
        this.extractedUrl = extractedUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private ValueRange headerContents;
    private boolean doesTabExist = false;
    private boolean doesHeaderPass = false;
    private boolean isHeaderEmpty = true;
    private String docUrl;
    private String languageName;
    private String rangeToCheck;
    private String headerToCheck;
    private String extractedUrl;
    private String message;

}
