package com.huluxia.controller.resource;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.huluxia.controller.resource.DownloadService.a;
import com.huluxia.framework.base.log.HLog;

class DownloadService$a$1 implements ServiceConnection {
    final /* synthetic */ a my;

    DownloadService$a$1(a this$0) {
        this.my = this$0;
    }

    public void onServiceConnected(ComponentName name, IBinder iBinder) {
        Log.d(a.TAG, "Service Connected");
        if (a.a(this.my)) {
            DownloadService service = DownloadService.a(iBinder);
            if (service != null) {
                a.b(this.my).onConnected(service);
            } else {
                HLog.error(a.TAG, "service connected get service is NULL, binder is " + iBinder, new Object[0]);
            }
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        Log.d(a.TAG, "Service Disconnected");
        a.b(this.my).onDisconnected();
    }
}
