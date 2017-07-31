package com.huluxia.framework.base.http.toolbox.reader;

import android.os.SystemClock;
import com.huluxia.framework.base.log.HLog;

public class RateLimiter {
    private static final String TAG = "RateLimiter";
    private int mBytePerSecond = Integer.MAX_VALUE;
    private int mBytesReadSinceSleep = 0;
    private long mLastSleepTime;

    public RateLimiter(int bytesPerSecond) {
        this.mBytePerSecond = bytesPerSecond;
    }

    public void acquire(int bytesRead) {
        this.mBytesReadSinceSleep += bytesRead;
        if (this.mBytesReadSinceSleep >= this.mBytePerSecond) {
            long time = Math.max(1000 - (SystemClock.elapsedRealtime() - this.mLastSleepTime), 0);
            try {
                HLog.debug(TAG, "rate limiter sleep " + time + ", per sec limit " + this.mBytePerSecond, new Object[0]);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                HLog.error(TAG, "limiter rate thead err " + e, new Object[0]);
            }
            this.mBytesReadSinceSleep = 0;
            this.mLastSleepTime = SystemClock.elapsedRealtime();
        }
    }

    public static RateLimiter create(int bytesPerSecond) {
        return new RateLimiter(bytesPerSecond);
    }
}
