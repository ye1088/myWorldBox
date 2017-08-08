package com.MCWorld.mcsdk.log;

import com.MCWorld.framework.AppConstant;

/* compiled from: DTDebug */
public class a$a {
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_WARN = 4;
    public int backUpLogLimitInMB = 4;
    public int buffSizeInBytes = 32768;
    public boolean honorVerbose = false;
    public String logFileName = AppConstant.LOG_FILE;
    public int logLevel = 1;
    public String stackTraceFilterKeyword;
    public String uniformTag;
}
