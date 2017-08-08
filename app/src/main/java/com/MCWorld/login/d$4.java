package com.MCWorld.login;

import android.content.Context;
import android.os.RemoteException;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.login.LoginError.LoginErrCode;

/* compiled from: Login */
class d$4 implements Runnable {
    final /* synthetic */ d MP;
    final /* synthetic */ String MS;
    final /* synthetic */ String MZ;
    final /* synthetic */ String Nb;
    final /* synthetic */ Context val$context;

    d$4(d this$0, Context context, String str, String str2, String str3) {
        this.MP = this$0;
        this.val$context = context;
        this.MS = str;
        this.Nb = str2;
        this.MZ = str3;
    }

    public void run() {
        String signature = null;
        try {
            signature = d.a(this.MP, this.val$context.getApplicationContext());
            if (UtilsFunction.empty(this.MS) || UtilsFunction.empty(signature) || UtilsFunction.empty(this.Nb)) {
                EventNotifyCenter.notifyEventUiThread(e.class, 1025, new Object[]{Boolean.valueOf(false), signature, this.MS, this.Nb, "客户端错误，请重启[2]", LoginErrCode.LOGIN_ARGUEMENT_ERROR});
                return;
            }
            if (!this.MP.mv) {
                synchronized (d.d(this.MP)) {
                    try {
                        d.d(this.MP).wait();
                    } catch (InterruptedException e) {
                        HLog.error("LoginService", "login lock wait ex %s", e, new Object[0]);
                    }
                }
            }
            if (this.MP.mv) {
                try {
                    d.b(this.MP).a(signature, this.MS, this.Nb, this.MZ);
                    return;
                } catch (RemoteException e2) {
                    EventNotifyCenter.notifyEventUiThread(e.class, 1025, new Object[]{Boolean.valueOf(false), signature, this.MS, this.Nb, "客户端错误，请重启[4]", LoginErrCode.LOGIN_REMOTE_EX});
                    return;
                }
            }
            EventNotifyCenter.notifyEventUiThread(e.class, 1025, new Object[]{Boolean.valueOf(false), signature, this.MS, this.Nb, "客户端错误，请重启[3]", LoginErrCode.LOGIN_UNBOUND});
        } catch (Exception e3) {
            EventNotifyCenter.notifyEventUiThread(e.class, 1025, new Object[]{Boolean.valueOf(false), signature, this.MS, this.Nb, "客户端错误，请重启[1]", LoginErrCode.LOGIN_CLIENTID_ERROR});
        }
    }
}
