package com.baeldung.libraries.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;


public class GoogleAuthorizeUtil {
    private static FileDataStoreFactory dataStoreFactory;


    public static Credential authorize() {
        Credential credential = null;

        boolean tt = false;
//        boolean tt = true;
        if(tt){
            try {
                credential = login();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }else{
            try {
                credential = getCredential();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
        return credential;
    }


    public static Credential login() throws IOException, GeneralSecurityException {
        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream("/google-sheets-client-secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));
        System.out.println(new File(""));
        dataStoreFactory = new FileDataStoreFactory(new File("credentials"));

        // token: ya29.a0AfH6SMA48fMBFx0QnFnvMH5PqsM3fVE-GsJcd_b3RsBo80gtCvecdVyloyei2wCca2ktCn3Jxlt0poFwporHR3e4PyBMh1o8AMGwKawcKPLmAu7gR8MkVvIbr5wsNGq1lu0xlsMIoSwBUXQou9tEVxQVtdFR

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                clientSecrets,
                scopes)
                .setDataStoreFactory(new MemoryDataStoreFactory())
//                .setDataStoreFactory(dataStoreFactory)
                .setAccessType("offline")
                .setApprovalPrompt("auto")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        System.out.println("token: " + credential.getAccessToken());
        credential.refreshToken() ;
        System.out.println("token: " + credential.getAccessToken());
        System.out.println("refreshToken: " + credential.getRefreshToken());

        return credential;
    }

    // https://ru.stackoverflow.com/questions/509701/%D0%90%D0%B2%D1%82%D0%BE%D1%80%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F-%D0%B2-google-%D1%81-%D0%BF%D0%BE%D0%BC%D0%BE%D1%89%D1%8C%D1%8E-oauth-2-%D0%B1%D0%B5%D0%B7-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F-%D0%B1%D1%80%D0%B0%D1%83%D0%B7%D0%B5%D1%80%D0%B0
    public static Credential getCredential() throws IOException, GeneralSecurityException {
        String CLIENT_ID = "949927302490-b56behq5dl5fdf7jg6oc1nv47u1lmbaf.apps.googleusercontent.com";
        String CLIENT_SECRET = "HohJvG9GRQB9sQTkaucGYOcm";
        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        String REFRESH_TOKEN = "1//0ciJcIQc1Xs4WCgYIARAAGAwSNwF-L9IrpxnEcoql2NH-lUAQlegf4FTSWtqb0DryYYvpCDVUSOzkuYiFmqX2JVahvpT3avmStc8";

//        String CLIENT_ID = "130678710689-omosgtqn47nce9la59hls7erhdi066b5.apps.googleusercontent.com";
//        String CLIENT_SECRET = "5oGhBFc2yDRGZnWgd4HC10OD";
//        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        String REFRESH_TOKEN = "1//0ckEGmubGVCXICgYIARAAGAwSNwF-L9Irj_OKCuFNkmNa163HrsY9mzjt9c-pfRgAVD18WQej5FDm8L5YPsPEE-usyWwimffNJTY";

//        String CLIENT_ID = "1057009694119-e2mg2og6rmj9ukui5ci7puof43ii3c99.apps.googleusercontent.com";
//        String CLIENT_SECRET = "_6vOpHw8aicgqAxscRD-5Z1v";
//        JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
//        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        String REFRESH_TOKEN = "1//0c4PdCDGFvdSxCgYIARAAGAwSNwF-L9IrYOmSvAsZb1lu8jaVLqhEyWPMnCJz6V1BPNRdqHtj2V__i3EV9T0_a7C5IOSi20Ttl2s";


        Credential credential = new GoogleCredential.Builder()
                .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
                .setJsonFactory(JSON_FACTORY)
                .setTransport(HTTP_TRANSPORT)
                .build()
                .setRefreshToken(REFRESH_TOKEN);
        credential.refreshToken();

        return credential;
    }

}
