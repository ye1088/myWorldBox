package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MTimerHandler extends Handler {
    private static int ac;
    private final int ad;
    private final boolean av;
    private long aw = 0;
    private final CallBack ax;

    public interface CallBack {
        boolean onTimerExpired();
    }

    public MTimerHandler(Looper looper, CallBack callBack, boolean z) {
        super(looper);
        this.ax = callBack;
        this.ad = d();
        this.av = z;
    }

    public MTimerHandler(CallBack callBack, boolean z) {
        this.ax = callBack;
        this.ad = d();
        this.av = z;
    }

    private static int d() {
        if (ac >= 8192) {
            ac = 0;
        }
        int i = ac + 1;
        ac = i;
        return i;
    }

    protected void finalize() {
        stopTimer();
        super.finalize();
    }

    public void handleMessage(Message message) {
        if (message.what == this.ad && this.ax != null && this.ax.onTimerExpired() && this.av) {
            sendEmptyMessageDelayed(this.ad, this.aw);
        }
    }

    public void startTimer(long j) {
        this.aw = j;
        stopTimer();
        sendEmptyMessageDelayed(this.ad, j);
    }

    public void stopTimer() {
        removeMessages(this.ad);
    }

    public boolean stopped() {
        return !hasMessages(this.ad);
    }
}
