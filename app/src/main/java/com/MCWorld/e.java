package com.MCWorld;

import com.MCWorld.framework.base.async.AsyncTaskCenter;
import com.MCWorld.framework.base.crash.CrashReporter;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.http.base.c;
import com.MCWorld.utils.o;
import com.xiaomi.mipush.sdk.MiPushClient;

/* compiled from: CrashCollector */
public class e implements CrashReporter {
    private static final String TAG = "CrashCollector";
    private static final String eI = "http://upload.huluxia.net/upload/file";

    public e() {
        CharSequence latestCrash = j.bZ().ca();
        if (UtilsFunction.empty(latestCrash)) {
            HLog.info(TAG, "no latest crash...", new Object[0]);
        } else {
            sendReport(latestCrash);
        }
    }

    public void sendReport(String crash) {
        HLog.info(TAG, "report crash " + crash, new Object[0]);
        AsyncTaskCenter.getInstance().execute(new 1(this, crash));
    }

    public void onCrash(String crash) {
        HLog.info(TAG, "crash collect " + crash, new Object[0]);
        j.bZ().B(crash);
    }

    private String s(String contact) {
        StringBuffer sb = new StringBuffer();
        sb.append(o.getModel());
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(o.getVersion());
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(contact);
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(c.ff());
        return sb.toString();
    }
}
