package com.huluxia.framework.base.http.toolbox;

import android.annotation.TargetApi;
import android.os.SystemClock;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsVersion;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HttpLog {
    public static String TAG = "HttpTag";

    public static class MarkerLog {
        public static final boolean ENABLED = true;
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;
        private boolean mFinished = false;
        private final List<Marker> mMarkers = new ArrayList();

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String name, long thread, long time) {
                this.name = name;
                this.thread = thread;
                this.time = time;
            }
        }

        public synchronized void add(String name, long threadId) {
            if (this.mFinished) {
                HLog.error(this, "Marker added to finished log %s", new Object[]{name});
            } else {
                this.mMarkers.add(new Marker(name, threadId, SystemClock.elapsedRealtime()));
            }
        }

        public synchronized void finish(String header) {
            this.mFinished = true;
            if (getTotalDuration() > 0) {
                long prevTime = ((Marker) this.mMarkers.get(0)).time;
                HLog.info(this, "(%-4d ms) %s", new Object[]{Long.valueOf(duration), header});
                for (Marker marker : this.mMarkers) {
                    HLog.info(this, "(+%-4d) [%2d] %s", new Object[]{Long.valueOf(marker.time - prevTime), Long.valueOf(marker.thread), marker.name});
                    prevTime = marker.time;
                }
            }
        }

        protected void finalize() throws Throwable {
            if (!this.mFinished) {
                finish("Request on the loose");
                HttpLog.e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        private long getTotalDuration() {
            if (this.mMarkers.size() == 0) {
                return 0;
            }
            return ((Marker) this.mMarkers.get(this.mMarkers.size() - 1)).time - ((Marker) this.mMarkers.get(0)).time;
        }
    }

    public static void setTag(String tag) {
        d("Changing log tag to %s", tag);
        TAG = tag;
    }

    public static void v(String format, Object... args) {
        HLog.verbose(TAG, buildMessage(format, args), new Object[0]);
    }

    public static void d(String format, Object... args) {
        HLog.debug(TAG, buildMessage(format, args), new Object[0]);
    }

    public static void e(String format, Object... args) {
        HLog.error(TAG, format, args);
    }

    public static void e(Throwable tr, String format, Object... args) {
        HLog.error(TAG, format, args);
    }

    @TargetApi(8)
    public static void wtf(String format, Object... args) {
        if (UtilsVersion.hasFroyo()) {
            HLog.warn(TAG, buildMessage(format, args), new Object[0]);
        } else {
            HLog.error(TAG, buildMessage(format, args), new Object[0]);
        }
    }

    @TargetApi(8)
    public static void wtf(Throwable tr, String format, Object... args) {
        if (UtilsVersion.hasFroyo()) {
            HLog.warn(TAG, buildMessage(format, args), new Object[]{tr});
            return;
        }
        HLog.error(TAG, buildMessage(format, args), tr, new Object[0]);
    }

    private static String buildMessage(String format, Object... args) {
        String msg = args == null ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = 2; i < trace.length; i++) {
            if (!trace[i].getClass().equals(HttpLog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf(46) + 1);
                caller = callingClass.substring(callingClass.lastIndexOf(36) + 1) + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), caller, msg});
    }
}
