package com.spy686.google.helper.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SheetsServiceUtil {

    public static Sheets getSheetsService(){
        Credential credential = GoogleAuthorizeUtil.authorize();
        try {
            return new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Can not be to connect to 'Google sheet service'.");
        }
    }

    public static UpdateValuesResponse writeToSheet(List<String[]> data, String googleSheetId){
        List<List<Object>> records = new ArrayList<>();
        data.forEach(row -> records.add(Arrays.asList(row)));
        ValueRange body = new ValueRange().setValues(records);

        try {
            return getSheetsService().spreadsheets()
                    .values()
                    .update(googleSheetId, "A1", body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException("Can not be to connect to Google Sheet.");
        }
    }

}
