package com.huluxia.login;

import android.content.Context;
import android.os.RemoteException;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.login.LoginError.LoginErrCode;

/* compiled from: Login */
class d$5 implements Runnable {
    final /* synthetic */ d MP;
    final /* synthetic */ Context val$context;

    d$5(d this$0, Context context) {
        this.MP = this$0;
        this.val$context = context;
    }

    public void run() {
        String signature = null;
        try {
            signature = d.a(this.MP, this.val$context.getApplicationContext());
            if (UtilsFunction.empty(signature)) {
                EventNotifyCenter.notifyEventUiThread(e.class, 1027, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.LOGOUT_ARGUEMENT_ERROR});
                return;
            }
            try {
                d.b(this.MP).bP(signature);
            } catch (RemoteException e) {
                EventNotifyCenter.notifyEventUiThread(e.class, 1027, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.LOGOUT_REMOTE_EX});
            }
        } catch (Exception e2) {
            EventNotifyCenter.notifyEventUiThread(e.class, 1027, new Object[]{Boolean.valueOf(false), signature, LoginErrCode.LOGOUT_CLIENTID_ERROR});
        }
    }
}
