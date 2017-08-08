package com.MCWorld.framework.base.http.toolbox.error;

public class ErrorCode {
    public static final int ERROR_AUTHOR_FAILED = 16;
    public static final int ERROR_CANCELED = 33;
    public static final int ERROR_CANCELED_EXCEEDED = 36;
    public static final int ERROR_CANNOT_RESUME = 29;
    public static final int ERROR_CANNOT_RESUME_2 = 30;
    public static final int ERROR_CLIENT = 24;
    public static final int ERROR_CLOSE_SOCKET_BEFORE_END = 31;
    public static final int ERROR_CREATE_DIR = 34;
    public static final int ERROR_IMG_NOT_FOUND = 38;
    public static final int ERROR_IMG_OOM = 37;
    public static final int ERROR_INTERCEPT = 25;
    public static final int ERROR_INVALID_PARAM = 19;
    public static final int ERROR_LOCAL_FILE = 39;
    public static final int ERROR_NETWORK = 18;
    public static final int ERROR_NO = -1;
    public static final int ERROR_NO_AVAILABLE_SPACE = 21;
    public static final int ERROR_NO_CONNECTION = 22;
    public static final int ERROR_PARSE = 20;
    public static final int ERROR_PREPARE = 27;
    public static final int ERROR_SEGMENT = 4096;
    public static final int ERROR_SEGMENT_MASK = 273;
    public static final int ERROR_SERVER = 17;
    public static final int ERROR_TIMEOUT = 23;
    public static final int ERROR_UNKNOWN = 255;
    public static final int ERROR_UNKNOWN_SIZE = 28;
    public static final int ERROR_UNZIP_INTERCEPT = 26;
    public static final int ERROR_XOR = 35;

    public static boolean isRestart(int error) {
        return error == 255 || error == 29 || error == 30 || error == 34;
    }

    public static boolean isResume(int error) {
        if (isSegmentErr(error)) {
            error = getSegmentErr(error);
        }
        return error == 16 || error == 17 || error == 18 || error == 19 || error == 20 || error == 21 || error == 22 || error == 23 || error == 24 || error == 25 || error == 26 || error == 27 || error == 28 || error == 31 || error == 4096 || error == 39;
    }

    public static int generateSegmentErr(int error) {
        if (error == -1) {
            return -1;
        }
        return error | 4096;
    }

    public static boolean isSegmentErr(int segErr) {
        if (segErr != -1 && (segErr & 4096) == 4096) {
            return true;
        }
        return false;
    }

    public static int getSegmentErr(int segErr) {
        if (segErr == -1) {
            return -1;
        }
        return segErr & 273;
    }
}
