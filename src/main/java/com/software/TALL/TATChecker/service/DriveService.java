package com.software.TALL.TATChecker.service;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.software.TALL.TATChecker.model.SheetValue;

import java.io.IOException;
import java.util.ArrayList;

public class DriveService {



    public static String extractUrl(String docId) {
        // Example:
        // https://docs.google.com/spreadsheets/d/1Gfmom7BEvzIvyB6Ht3Sb5Q50rz8nytuEJJ7kzVs7y1I/edit#gid=420552297
        // becomes:
        // 1Gfmom7BEvzIvyB6Ht3Sb5Q50rz8nytuEJJ7kzVs7y1I/
        int firstCutoff = docId.indexOf("/d/") + 3;
        String firstString = docId.substring(firstCutoff);
        int secondCutoff = firstString.indexOf("/edit");
        String finalString = firstString.substring(0, secondCutoff);
        return finalString;
    }

    public static SheetValue throttledFetch(Sheets.Spreadsheets.Values.Get gr, SheetValue sv) throws IOException {

        try {
            // pull headers from sheet
            ValueRange response = gr.execute();

            // response object will not contain "values" key if there are no headers on the sheet
            if (!response.containsKey("values")) {
                String m = sv.getLanguageName()
                        + " - "
                        + sv.getHeaderToCheck()
                        + " - fail: tab does not exist";

                sv.setMessage(m);
                return sv;
            }

            sv.setDoesTabExist(true);
            ArrayList<String> dup = checkForDuplicates(response);

            // if dup contains any duplicate strings found while checking for duplicates
            if (dup.size() > 0) {
                String m = sv.getLanguageName()
                        + " - "
                        + sv.getHeaderToCheck()
                        + " - fail: duplicates found";
                sv.setMessage(m);
            } else {
                sv.setDoesHeaderPass(true);
                String m = sv.getLanguageName()
                        + " - "
                        + sv.getHeaderToCheck()
                        + " - pass";
                sv.setMessage(m);
            }

        } catch(IOException e) {
            String m = sv.getLanguageName()
                    + " - "
                    + sv.getHeaderToCheck()
                    + " - fail: no headers found";
            sv.setMessage(m);
        }

        return sv;
    }

    private static ArrayList<String> checkForDuplicates(ValueRange vr) {
        ArrayList<String> indices = new ArrayList<String>();
        for (int i = 0; i < vr.getValues().size(); i++) {
            for (int j = 0; j < vr.getValues().get(0).size(); j++) {
                String val1 = vr.getValues().get(0).get(i).toString();
                String val2 = vr.getValues().get(0).get(j).toString();
                if (i == j || val1.isEmpty() || val2.isEmpty()) {
                    continue;
                } else if (val1.equalsIgnoreCase(val2)) {
                    if ( !indices.contains(val1)) {
                        indices.add(val1);
                    }
                }
            }
        }
        return indices;
    }

}
