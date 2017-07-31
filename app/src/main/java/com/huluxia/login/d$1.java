package com.huluxia.login;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.login.c.a;

/* compiled from: Login */
class d$1 implements ServiceConnection {
    final /* synthetic */ d MP;

    d$1(d this$0) {
        this.MP = this$0;
    }

    public void onServiceConnected(ComponentName name, final IBinder service) {
        d.e(this.MP).post(new Runnable(this) {
            final /* synthetic */ d$1 MR;

            public void run() {
                int i = 0;
                while (true) {
                    int retry = i + 1;
                    if (i >= 5) {
                        break;
                    }
                    try {
                        HLog.info("LoginService", "service connected retry %d", new Object[]{Integer.valueOf(retry)});
                        d.a(this.MR.MP, a.c(service));
                        d.b(this.MR.MP).a(d.a(this.MR.MP, AppConfig.getInstance().getAppContext()), d.a(this.MR.MP));
                        this.MR.MP.mv = true;
                        break;
                    } catch (RemoteException e) {
                        this.MR.MP.mv = false;
                        this.MR.MP.aK(d.c(this.MR.MP));
                        HLog.error("LoginService", "connect service remote ex %s", e, new Object[0]);
                        i = retry;
                    } catch (Exception e2) {
                        this.MR.MP.mv = false;
                        this.MR.MP.aK(d.c(this.MR.MP));
                        HLog.error("LoginService", "connect service ex %s", e2, new Object[0]);
                        i = retry;
                    }
                }
                synchronized (d.d(this.MR.MP)) {
                    d.d(this.MR.MP).notifyAll();
                }
            }
        });
    }

    public void onServiceDisconnected(ComponentName name) {
        HLog.info("LoginService", "service unconnected", new Object[0]);
        d.a(this.MP, null);
        this.MP.mv = false;
    }
}
