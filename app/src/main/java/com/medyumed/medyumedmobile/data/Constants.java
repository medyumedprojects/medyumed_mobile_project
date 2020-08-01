package com.medyumed.medyumedmobile.data;

public final class Constants {
    public static final String APP_MAIN_VERSION = "1";
    public static final String DOMAIN = "api.yclients.com";
    public static final String URL_BASE = "https://" + DOMAIN + "/api/v" + APP_MAIN_VERSION + "/";
    public static final String COMPANY_ID = "114454";

    public static final String API_KEY = "btdkh97mpd49hw5n5p9p";

    public static interface Controllers {
        public static final String REQUEST_PASSWORD = "book_code/" + COMPANY_ID;
        public static final String LOGIN = "auth";
    }

    public static interface SharedPreferences{
        public static final String APP_PREFERENCES = "APP_PREFERENCE";
        public static final String PHONE_NUMBER = "PHONE_NUMBER";
        public static final String USER_TOKEN = "USER_TOKEN";
    }
}
