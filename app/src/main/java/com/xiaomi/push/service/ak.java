package com.xiaomi.push.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.channel.commonutils.logger.b;

class ak implements ServiceConnection {
    final /* synthetic */ XMPushService a;

    ak(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        b.b("onServiceConnected " + iBinder);
        Service a = XMJobService.a();
        if (a != null) {
            this.a.startForeground(XMPushService.f, XMPushService.a(this.a));
            a.startForeground(XMPushService.f, XMPushService.a(this.a));
            a.stopForeground(true);
            this.a.unbindService(this);
            return;
        }
        b.a("XMService connected but innerService is null " + iBinder);
    }

    public void onServiceDisconnected(ComponentName componentName) {
    }
}
