package com.MCWorld.framework.base.http.toolbox.statis;

import android.os.SystemClock;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.impl.request.DownloadRequest;
import com.MCWorld.framework.base.http.io.impl.request.StringRequestBuilder;
import com.MCWorld.framework.base.http.toolbox.error.AuthFailureError;
import com.MCWorld.framework.base.http.toolbox.error.ClientError;
import com.MCWorld.framework.base.http.toolbox.error.NetworkError;
import com.MCWorld.framework.base.http.toolbox.error.ServerError;
import com.MCWorld.framework.base.http.toolbox.error.TimeoutError;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsMD5;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CdnQualityTracker implements IDownloadTracker {
    private static final String TAG = "CdnQualityTracker";
    private static final String URL_ERROR_STATIS = "http://stat.huluxia.com/stat/networkerror";
    private static final String URL_SPEED_STATIS = "http://stat.huluxia.com/stat/networkspeed";
    private static long sBytesSoFar;
    private static Set<String> sDownloads = new HashSet();
    private static long sProgress;
    private static long sStart;
    private final int DEFAULT_TRACKER_INTERVAL = 300000;

    public void trackSectionSpeed(DownloadRequest request, long increaseBytes, boolean complete) {
        if (increaseBytes < 0) {
            HLog.error(TAG, "track section speed increase %d", new Object[]{Long.valueOf(increaseBytes)});
        } else if (sStart == 0) {
            sStart = SystemClock.elapsedRealtime();
        } else {
            sBytesSoFar += increaseBytes;
            long now = SystemClock.elapsedRealtime();
            long period = now - sStart;
            long intervalProgress = sBytesSoFar - sProgress;
            sDownloads.add(request.getUrl());
            long speed = 0;
            if (period > 300000 || complete) {
                try {
                    speed = (1000 * intervalProgress) / period;
                } catch (ArithmeticException e) {
                    HLog.error(TAG, "divide error " + e, new Object[0]);
                }
                sProgress = sBytesSoFar;
                sStart = now;
                if (complete) {
                    sDownloads.remove(request.getUrl());
                }
                reset();
                HLog.info(this, "speed tracker %d, period %d, complete %b, progress %d, size %d", new Object[]{Long.valueOf(speed), Long.valueOf(period), Boolean.valueOf(complete), Long.valueOf(sProgress), Integer.valueOf(sDownloads.size())});
                ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) AppConfig.getInstance().getHttpMgr().getStringReqBuilder(URL_SPEED_STATIS, false).setParams(getParam(String.valueOf(speed), request.getUrl(), request.getIp()))).setCache(false)).setSuccListener(new Listener<String>() {
                    public void onResponse(String response) {
                        HLog.debug(CdnQualityTracker.TAG, "statis speed response " + response, new Object[0]);
                    }
                })).execute();
            }
        }
    }

    public void trackError(DownloadRequest request, VolleyError error) {
        if (error != null) {
            if ((error instanceof ClientError) || (error instanceof ServerError) || (error instanceof TimeoutError) || (error instanceof AuthFailureError) || (error instanceof NetworkError)) {
                int statusCode;
                HLog.error(TAG, "upload cdn error " + error, new Object[0]);
                int errorCode = VolleyError.getErrorId(error);
                String desc = error.getMessage();
                if (error.networkResponse != null) {
                    statusCode = error.networkResponse.statusCode;
                } else {
                    statusCode = 0;
                }
                ((StringRequestBuilder) ((StringRequestBuilder) ((StringRequestBuilder) AppConfig.getInstance().getHttpMgr().getStringReqBuilder(URL_ERROR_STATIS, false).setParams(getParam(String.valueOf(errorCode), desc, String.valueOf(statusCode), request.getUrl(), request.getIp()))).setCache(false)).setSuccListener(new Listener<String>() {
                    public void onResponse(String response) {
                        HLog.debug(CdnQualityTracker.TAG, "statis error response " + response, new Object[0]);
                    }
                })).execute();
            }
        }
    }

    private void reset() {
        if (sDownloads.size() == 0) {
            sBytesSoFar = 0;
            sProgress = 0;
            sStart = 0;
        }
    }

    private static Map<String, String> getParam(String... contents) {
        if (contents == null || contents.length == 0) {
            return null;
        }
        Map<String, String> param = new HashMap();
        String ts = String.valueOf(System.currentTimeMillis());
        String key = UtilsMD5.getMD5String(ts + "hlxsystem");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < contents.length; i++) {
            sb.append(contents[i]);
            if (i != contents.length - 1) {
                sb.append("@");
            }
        }
        param.put("time", ts);
        param.put("key", key);
        param.put("content", sb.toString());
        return param;
    }
}
