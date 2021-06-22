package com.spy686.google.helper.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AddSheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SheetsServiceUtil {

    private static Sheets sheetsService;

    public static Sheets getSheetsService() {
        if (sheetsService == null) {
            Credential credential = GoogleAuthorizeUtil.authorize();
            try {
                sheetsService = new Sheets.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credential)
                        .build();

            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Can not be to connect to 'Google sheet service'.");
            }
        }
        return sheetsService;
    }

    public static UpdateValuesResponse writeToSheet(List<String[]> data, String googleSheetId) {
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
            e.printStackTrace();
            throw new RuntimeException("Can not be to connect to Google Sheet.");
        }
    }

    public static UpdateValuesResponse writeToSheet(List<String[]> data, String googleSheetId, String sheetName) {
        List<List<Object>> records = new ArrayList<>();
        data.forEach(row -> records.add(Arrays.asList(row)));
        ValueRange body = new ValueRange().setValues(records);

        try {
            return getSheetsService().spreadsheets()
                    .values()
                    .update(googleSheetId, sheetName + "!A1", body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not be to connect to Google Sheet.");
        }
    }

    /**
     * Notes: https://stackoverflow.com/a/37658641
     */
    public static BatchUpdateSpreadsheetResponse createNewList(String listName, String googleSheetId) {
        AddSheetRequest addSheetRequest = new AddSheetRequest();
        addSheetRequest.setProperties(new SheetProperties());
        addSheetRequest.getProperties().setTitle(listName);

        BatchUpdateSpreadsheetRequest batchUpdateSpreadsheetRequest = new BatchUpdateSpreadsheetRequest();
        batchUpdateSpreadsheetRequest.setRequests(new ArrayList<>());
        Request qw = new Request().setAddSheet(addSheetRequest);
        batchUpdateSpreadsheetRequest.getRequests().add(0, qw);

        try {
            return getSheetsService().spreadsheets()
                    .batchUpdate(googleSheetId, batchUpdateSpreadsheetRequest)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not be to add new Sheet to Google Sheet.");
        }
    }

}
