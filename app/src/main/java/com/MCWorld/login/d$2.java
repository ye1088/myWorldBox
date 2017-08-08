package com.MCWorld.login;

import android.os.RemoteException;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsJson;
import com.MCWorld.login.LoginError.LoginErrCode;
import com.MCWorld.login.b.a;

/* compiled from: Login */
class d$2 extends a {
    final /* synthetic */ d MP;

    d$2(d this$0) {
        this.MP = this$0;
    }

    public void a(boolean succ, String email, int code, String msg, int error) throws RemoteException {
        HLog.info("LoginService", "onregister succ %b, email %s, error %d", new Object[]{Boolean.valueOf(succ), email, Integer.valueOf(error)});
        LoginErrCode errCode = LoginErrCode.values()[error];
        EventNotifyCenter.notifyEventUiThread(e.class, 1024, new Object[]{Boolean.valueOf(succ), email, Integer.valueOf(code), msg, errCode});
    }

    public void a(boolean succ, String clientid, String email, String encryptPwd, String openid, String session, int code, String msg, int error) throws RemoteException {
        HLog.info("LoginService", "onlogin succ %b, email %s, pwd %s, error %d, session %s", new Object[]{Boolean.valueOf(succ), email, encryptPwd, Integer.valueOf(error), session});
        HLog.verbose("TAG", "DTPrint IAsyncInterface sessionInfo onLogin ===", new Object[0]);
        if (succ) {
            d.a(this.MP, (k) UtilsJson.toObjectNoExp(session, k.class));
            g.pU().put(email, openid);
        } else {
            d.a(this.MP, null);
        }
        LoginErrCode errCode = LoginErrCode.values()[error];
        EventNotifyCenter.notifyEventUiThread(e.class, 1025, new Object[]{Boolean.valueOf(succ), clientid, email, encryptPwd, openid, Integer.valueOf(code), msg, errCode});
    }

    public void a(boolean succ, String client, String session, int error) throws RemoteException {
        HLog.info("LoginService", "onAutoLogin succ %b, error %d, session %s", new Object[]{Boolean.valueOf(succ), Integer.valueOf(error), session});
        HLog.verbose("TAG", "DTPrint IAsyncInterface sessionInfo onAutoLogin ===", new Object[0]);
        if (succ) {
            d.a(this.MP, (k) UtilsJson.toObjectNoExp(session, k.class));
        } else {
            d.a(this.MP, null);
        }
        LoginErrCode errCode = LoginErrCode.values()[error];
        EventNotifyCenter.notifyEventUiThread(e.class, 1026, new Object[]{Boolean.valueOf(succ), client, errCode});
    }

    public void cP(int version) throws RemoteException {
    }
}
