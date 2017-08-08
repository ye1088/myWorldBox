package com.MCWorld.framework.base.utils;

import android.os.SystemClock;
import com.MCWorld.framework.base.log.HLog;
import java.util.ArrayList;
import java.util.List;

@DoNotStrip
public class MarkerLog {
    public static final boolean ENABLED = true;
    private static final long MIN_DURATION_FOR_LOGGING_MS = 0;
    private static final String TAG = "MarkerLog";
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
            HLog.error(TAG, "Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
        }
    }

    private long getTotalDuration() {
        if (this.mMarkers.size() == 0) {
            return 0;
        }
        return ((Marker) this.mMarkers.get(this.mMarkers.size() - 1)).time - ((Marker) this.mMarkers.get(0)).time;
    }

    public static void addMarker(MarkerLog marker, String tag) {
        if (marker != null) {
            marker.add(tag, Thread.currentThread().getId());
        }
    }
}
