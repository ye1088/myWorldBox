package com.MCWorld.login;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.login.b.a;

/* compiled from: Login */
public class d {
    private static d MI = new d();
    public static final String MM = "version";
    private static final String TAG = "LoginService";
    private static final int VERSION = 1;
    private Object LOCK = new Object();
    private c MH;
    private HandlerThread MJ;
    private Handler MK;
    private k ML;
    private ServiceConnection MN = new 1(this);
    private a MO = new 2(this);
    private Context mContext;
    boolean mv = false;
    private String signature;

    public static d pR() {
        return MI;
    }

    public void aK(Context context) {
        this.mContext = context.getApplicationContext();
        this.MJ = new HandlerThread("login-thread");
        this.MJ.start();
        this.MK = new Handler(this.MJ.getLooper());
        Intent intent = new Intent(context, LoginService.class);
        context.startService(intent);
        context.bindService(intent, this.MN, 1);
    }

    public void stop() {
        if (this.mv) {
            this.mContext.unbindService(this.MN);
            this.mContext.stopService(new Intent("com.huluxia.login.LoginService"));
            this.mv = false;
        }
    }

    public k pS() {
        return this.ML;
    }

    public boolean ey() {
        return (this.ML == null || this.ML.userID <= 0 || UtilsFunction.empty(this.ML.key)) ? false : true;
    }

    public void a(Context context, String email, String encrytPwd, String nick, int gender, long birthday, String avatarFid, String openid, String accessToken) {
        this.MK.post(new 3(this, context, email, encrytPwd, nick, gender, birthday, avatarFid, openid, accessToken));
    }

    public void b(Context context, String email, String encryptPwd, String openid) {
        this.MK.post(new 4(this, context, email, encryptPwd, openid));
    }

    public void logout(Context context) {
        this.ML = null;
        this.MK.post(new 5(this, context));
    }

    public void aL(Context context) {
        this.MK.post(new 6(this, context));
    }

    private String aM(Context context) throws Exception {
        Context app = context.getApplicationContext();
        if (app == null || app.getPackageName() == null) {
            return "floor";
        }
        return app.getPackageName();
    }
}
