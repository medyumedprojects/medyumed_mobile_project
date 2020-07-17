package com.medyumed.medyumedmobile.data;

public final class Constants {
    public static final String APP_MAIN_VERSION = "1";
    public static final String APP_PATCH_VERSION = "1";
    public static final String APP_VERSION = APP_MAIN_VERSION + "." + APP_PATCH_VERSION;
    public static final String IP_SERVER_ADDRESS = "10.9.8.139:5050";
    public static final String URL_BASE = "http://" + IP_SERVER_ADDRESS + "/api/v"
            + APP_MAIN_VERSION + "/";

    public static interface Controllers {
        public static final String LOG_IN = "LogInSystem";
    }
}
