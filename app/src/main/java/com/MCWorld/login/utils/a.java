package com.MCWorld.login.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.baidu.android.pushservice.PushConstants;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.utils.UtilsJson;
import com.MCWorld.login.k;
import java.util.UUID;

/* compiled from: SessionStorage */
public class a {
    private static a Nx = new a();
    private Context context = AppConfig.getInstance().getAppContext();

    private a() {
    }

    public static a pW() {
        return Nx;
    }

    private SharedPreferences pX() {
        return this.context.getSharedPreferences("login-session", 0);
    }

    public void bT(String account) {
        Editor editor = pX().edit();
        editor.putString("account", account);
        editor.commit();
    }

    public void pY() {
        Editor editor = pX().edit();
        editor.remove("account");
        editor.commit();
    }

    public String pZ() {
        return pX().getString("account", "");
    }

    public void setPassword(String pwd) {
        Editor editor = pX().edit();
        editor.putString("pwd", pwd);
        editor.commit();
    }

    public void qa() {
        Editor editor = pX().edit();
        editor.remove("pwd");
        editor.commit();
    }

    public String getPassword() {
        return pX().getString("pwd", "");
    }

    public void a(String clientid, k info) {
        Editor editor = pX().edit();
        editor.putString(clientid, UtilsJson.toJsonString(info));
        editor.commit();
    }

    public void bU(String clientid) {
        Editor editor = pX().edit();
        editor.remove(clientid);
        editor.commit();
    }

    public k bS(String clientid) {
        return (k) UtilsJson.toObjectNoExp(pX().getString(clientid, ""), k.class);
    }

    public String qb() {
        SharedPreferences preferences = this.context.getSharedPreferences(PushConstants.EXTRA_APP, 0);
        String duuid = preferences.getString("DeviceUUID", null);
        if (duuid != null) {
            return duuid;
        }
        Editor editor = preferences.edit();
        duuid = UUID.randomUUID().toString();
        editor.putString("DeviceUUID", duuid);
        editor.commit();
        return duuid;
    }

    public String qc() {
        SharedPreferences preferences = this.context.getSharedPreferences(PushConstants.EXTRA_APP, 0);
        String duuid = preferences.getString("ComDeviceUUID", null);
        if (duuid != null) {
            return duuid;
        }
        Editor editor = preferences.edit();
        duuid = UUID.randomUUID().toString();
        editor.putString("ComDeviceUUID", duuid);
        editor.commit();
        return duuid;
    }
}
