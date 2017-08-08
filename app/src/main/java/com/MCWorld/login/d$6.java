package com.MCWorld.login;

import android.content.Context;
import android.os.RemoteException;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.login.LoginError.LoginErrCode;

/* compiled from: Login */
class d$6 implements Runnable {
    final /* synthetic */ d MP;
    final /* synthetic */ Context val$context;

    d$6(d this$0, Context context) {
        this.MP = this$0;
        this.val$context = context;
    }

    public void run() {
        String signature = null;
        try {
            signature = d.a(this.MP, this.val$context.getApplicationContext());
            if (UtilsFunction.empty(signature)) {
                EventNotifyCenter.notifyEventUiThread(e.class, 1026, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.AUTO_LOGIN_ARGUEMENT_ERROR});
                return;
            }
            if (!this.MP.mv) {
                synchronized (d.d(this.MP)) {
                    try {
                        d.d(this.MP).wait();
                    } catch (InterruptedException e) {
                        HLog.error("LoginService", "auto login lock wait ex %s", e, new Object[0]);
                    }
                }
            }
            if (this.MP.mv) {
                try {
                    d.b(this.MP).bO(signature);
                    return;
                } catch (RemoteException e2) {
                    EventNotifyCenter.notifyEventUiThread(e.class, 1026, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.AUTO_LOGIN_REMOTE_EX});
                    return;
                }
            }
            EventNotifyCenter.notifyEventUiThread(e.class, 1026, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.AUTO_LOGIN_UNBOUND});
        } catch (Exception e3) {
            EventNotifyCenter.notifyEventUiThread(e.class, 1026, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.AUTO_LOGIN_CLIENTID_ERROR});
        }
    }
}
