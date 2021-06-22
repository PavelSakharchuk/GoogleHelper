package com.spy686.google.helper.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.spy686.google.helper.PropertiesName;
import com.spy686.google.helper.PropertiesValue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Documentation: https://www.baeldung.com/google-sheets-java-client
 * Github: https://github.com/eugenp/tutorials/tree/master/libraries-data-io
 */
public class GoogleAuthorizeUtil {
    private static final ResourceBundle applicationConfigBundle = ResourceBundle
            .getBundle(PropertiesName.APPLICATION_CONFIG);

    private static final String GOOGLE_SHEETS_CLIENT_SECRET_TEMPLATE_JSON_FILE_PATH = applicationConfigBundle
            .getString(PropertiesValue.GOOGLE_HELPER_TEMPLATE_JSON);
    private static final String GOOGLE_SHEETS_SERVICE_CLIENT_ID = applicationConfigBundle
            .getString(PropertiesValue.GOOGLE_HELPER_AUTH_CLIENT_ID);
    private static final String GOOGLE_SHEETS_SERVICE_CLIENT_SECRET = applicationConfigBundle
            .getString(PropertiesValue.GOOGLE_HELPER_AUTH_CLIENT_SECRET);
    private static String googleSheetsServiceClientRefreshToken = applicationConfigBundle
            .getString(PropertiesValue.GOOGLE_HELPER_AUTH_CLIENT_REFRESH_TOKEN);

    public static Credential authorize() {
        if (null == googleSheetsServiceClientRefreshToken || googleSheetsServiceClientRefreshToken.isEmpty()) {
            // Need for manual getting refreshToken
            return login();
        } else {
            return getCredential();
        }
    }

    public static Credential login() {
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        try {
            InputStream googleSheetsClientSecretTemplateJson = GoogleAuthorizeUtil.class
                    .getResourceAsStream(GOOGLE_SHEETS_CLIENT_SECRET_TEMPLATE_JSON_FILE_PATH);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JacksonFactory.getDefaultInstance(), new InputStreamReader(googleSheetsClientSecretTemplateJson));
            clientSecrets.getInstalled().setClientId(GOOGLE_SHEETS_SERVICE_CLIENT_ID);
            clientSecrets.getInstalled().setClientSecret(GOOGLE_SHEETS_SERVICE_CLIENT_SECRET);

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    clientSecrets,
                    scopes)
                    .setDataStoreFactory(new MemoryDataStoreFactory())
                    .setAccessType("offline")
                    .setApprovalPrompt("auto")
                    .build();

            Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
            System.out.println("refreshToken: " + credential.getRefreshToken());
            googleSheetsServiceClientRefreshToken = credential.getRefreshToken();
            return credential;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    String.format("'%s' file can not be read.", GOOGLE_SHEETS_CLIENT_SECRET_TEMPLATE_JSON_FILE_PATH));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    String.format("Can not be authorisation with '%s.properties' data.", PropertiesName.APPLICATION_CONFIG));
        }
    }

    /**
     * Notes: https://ru.stackoverflow.com/a/510182
     */
    public static Credential getCredential() {
        try {
            Credential credential = new GoogleCredential.Builder()
                    .setClientSecrets(GOOGLE_SHEETS_SERVICE_CLIENT_ID, GOOGLE_SHEETS_SERVICE_CLIENT_SECRET)
                    .setJsonFactory(JacksonFactory.getDefaultInstance())
                    .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                    .build()
                    .setRefreshToken(googleSheetsServiceClientRefreshToken);
            credential.refreshToken();
            return credential;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    String.format("Can not be to refresh token for authorisation data from '%s.properties'.",
                            PropertiesName.APPLICATION_CONFIG));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    String.format("Can not be generate credential with '%s.properties' data.", PropertiesName.APPLICATION_CONFIG));
        }
    }

}
