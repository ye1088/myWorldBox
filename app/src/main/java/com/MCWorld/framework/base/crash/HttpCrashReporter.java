package com.MCWorld.framework.base.crash;

public class HttpCrashReporter implements CrashReporter {
    public void sendReport(String crash) {
    }

    public void onCrash(String crash) {
        sendReport(crash);
    }
}
