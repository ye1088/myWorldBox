package com.huluxia.framework.base.crash;

public interface CrashReporter extends CrashListener {
    void sendReport(String str);
}
