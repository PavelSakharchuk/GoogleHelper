package com.spy686.google.helper;

public abstract class PropertiesValue {
    // General Config
    // File can get from: https://console.cloud.google.com/apis/credentials?authuser=0&project=perfluencetoolid&supportedpurview=project
    public static final String GOOGLE_HELPER_TEMPLATE_JSON= "googleHelper.templateJson";

    // Auth data according google-sheets-client-secret.json
    // File can get from: https://console.cloud.google.com/apis/credentials?authuser=0&project=perfluencetoolid&supportedpurview=project
    public static final String GOOGLE_HELPER_AUTH_CLIENT_ID = "googleHelper.auth.clientId";
    public static final String GOOGLE_HELPER_AUTH_CLIENT_SECRET = "googleHelper.auth.clientSecret";
    public static final String GOOGLE_HELPER_AUTH_CLIENT_REFRESH_TOKEN = "googleHelper.auth.refreshToken";

    private PropertiesValue() {
        throw new IllegalStateException("Utility class");
    }
}