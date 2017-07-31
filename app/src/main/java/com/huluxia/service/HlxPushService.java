package com.huluxia.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import java.util.Timer;
import java.util.TimerTask;

public class HlxPushService extends Service {
    private TimerTask aDo;
    private Timer aDp;

    public void onCreate() {
        super.onCreate();
        this.aDp = new Timer();
        this.aDo = new TimerTask(this) {
            final /* synthetic */ HlxPushService aDq;

            {
                this.aDq = this$0;
            }

            public void run() {
                try {
                    this.aDq.Ev();
                } catch (Throwable e) {
                    Log.d("[checkAndReconnect]", DownloadRecord.COLUMN_ERROR, e);
                }
            }
        };
        this.aDp.schedule(this.aDo, 120000, 120000);
    }

    public void onDestroy() {
        super.onDestroy();
        this.aDp.cancel();
    }

    private long Ev() {
        i.EL();
        return 10000;
    }

    public void onStart(Intent intent, int startId) {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return 3;
    }

    private static Intent bb(Context context) {
        return new Intent(context, HlxPushService.class);
    }

    public static void az(Context context) {
        Log.i("pushService", "start");
        context.startService(bb(context));
    }

    public static void aA(Context context) {
        Log.i("pushService", "stop");
        context.stopService(bb(context));
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
