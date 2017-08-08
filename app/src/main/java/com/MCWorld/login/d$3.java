package com.MCWorld.login;

import android.content.Context;
import android.os.RemoteException;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.login.LoginError.LoginErrCode;

/* compiled from: Login */
class d$3 implements Runnable {
    final /* synthetic */ d MP;
    final /* synthetic */ String MS;
    final /* synthetic */ String MT;
    final /* synthetic */ String MU;
    final /* synthetic */ int MV;
    final /* synthetic */ long MW;
    final /* synthetic */ String MY;
    final /* synthetic */ String MZ;
    final /* synthetic */ String Na;
    final /* synthetic */ Context val$context;

    d$3(d this$0, Context context, String str, String str2, String str3, int i, long j, String str4, String str5, String str6) {
        this.MP = this$0;
        this.val$context = context;
        this.MS = str;
        this.MT = str2;
        this.MU = str3;
        this.MV = i;
        this.MW = j;
        this.MY = str4;
        this.MZ = str5;
        this.Na = str6;
    }

    public void run() {
        String signature = null;
        try {
            signature = d.a(this.MP, this.val$context.getApplicationContext());
            if (UtilsFunction.empty(this.MS) || UtilsFunction.empty(signature) || UtilsFunction.empty(this.MT)) {
                EventNotifyCenter.notifyEventUiThread(e.class, 1024, new Object[]{Boolean.valueOf(false), signature, this.MS, this.MT, "注册时客户端错误，请重启[2]", LoginErrCode.REGISTER_ARGUEMENT_ERROR});
                return;
            }
            if (!this.MP.mv) {
                synchronized (d.d(this.MP)) {
                    try {
                        d.d(this.MP).wait();
                    } catch (InterruptedException e) {
                        HLog.error("LoginService", "register lock wait ex %s", e, new Object[0]);
                    }
                }
            }
            if (this.MP.mv) {
                try {
                    d.b(this.MP).a(signature, this.MS, this.MT, this.MU, this.MV, this.MW, this.MY, this.MZ, this.Na);
                    return;
                } catch (RemoteException e2) {
                    EventNotifyCenter.notifyEventUiThread(e.class, 1024, new Object[]{Boolean.valueOf(false), this.MS, Integer.valueOf(104), "客户端错误，请重启[2]", LoginErrCode.REGISTER_REMOTE_EX});
                    return;
                }
            }
            EventNotifyCenter.notifyEventUiThread(e.class, 1024, new Object[]{Boolean.valueOf(false), this.MS, Integer.valueOf(104), "客户端错误，请重启[1]", LoginErrCode.REGISTER_UNBOUND});
        } catch (Exception e3) {
            EventNotifyCenter.notifyEventUiThread(e.class, 1024, new Object[]{Boolean.valueOf(false), signature, this.MS, this.MT, "注册时客户端错误，请重启[1]", LoginErrCode.REGISTER_CLIENTID_ERROR});
        }
    }
}
