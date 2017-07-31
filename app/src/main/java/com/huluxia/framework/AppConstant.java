package com.huluxia.framework;

public class AppConstant {
    private static String APP_NAME = "tool";
    public static final String CRASH_FILE = "crash.txt";
    private static String HLX_NAME = "huluxia";
    public static final String HTTP_CACHE = "http-cache";
    public static final String HTTP_DOWNLOAD_CACHE = "http-download-cache";
    public static final String HTTP_IMAGE_CACHE = "http-image-cache";
    public static final String HTTP_VOICE_CACHE = "http-voice-cache";
    public static final String LOG = "logs";
    public static final String LOG_FILE = "logs.txt";
    public static final String WAP_DOWNLOAD_RECORD_SEPARATOR = "---";

    static void setAppName(String appName) {
        APP_NAME = appName;
    }

    static void setHlxName(String root_name) {
        HLX_NAME = root_name;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getHlxName() {
        return HLX_NAME;
    }
}
