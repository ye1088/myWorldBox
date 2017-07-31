package com.huluxia.controller.resource;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.w;
import com.huluxia.controller.c;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private final IBinder mu = new b();

    public static class a {
        public static final String TAG = "DownloadService.Client";
        private final Context mContext;
        private boolean mv = false;
        private final a mw;
        private final ServiceConnection mx = new 1(this);

        @w
        public interface a {
            void onConnected(DownloadService downloadService);

            void onDisconnected();
        }

        private static Intent ay(Context context) {
            return new Intent(context, DownloadService.class);
        }

        private static void az(Context context) {
            context.startService(ay(context));
        }

        private static void aA(Context context) {
            context.stopService(ay(context));
        }

        public a(Context context, a callback) {
            if (context == null || callback == null) {
                throw new IllegalArgumentException("Context and callback can't be null");
            }
            this.mContext = context;
            this.mw = callback;
        }

        @w
        public void connect() {
            if (this.mv) {
                throw new IllegalStateException("already connected");
            }
            az(this.mContext);
            this.mv = this.mContext.bindService(ay(this.mContext), this.mx, 1);
        }

        @w
        public void disconnect() {
            if (this.mv) {
                this.mv = false;
                this.mContext.unbindService(this.mx);
            }
        }

        public static void aB(Context context) {
            aA(context);
            az(context);
        }
    }

    private class b extends Binder {
        final /* synthetic */ DownloadService mz;

        private b(DownloadService downloadService) {
            this.mz = downloadService;
        }

        DownloadService getService() {
            return this.mz;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mu;
    }

    public void onCreate() {
        HLog.debug(TAG, "download service create", new Object[0]);
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        HLog.debug(TAG, "download service start %s", intent);
        if (intent == null) {
            EventNotifyCenter.notifyEvent(c.class, 266, new Object[0]);
        }
        return 1;
    }

    public void onDestroy() {
        HLog.debug(TAG, "download service destroy", new Object[0]);
        super.onDestroy();
    }

    public static DownloadService a(IBinder iBinder) {
        if (iBinder instanceof b) {
            return ((b) iBinder).getService();
        }
        return null;
    }
}
