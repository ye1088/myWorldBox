package com.MCWorld.login;

import android.content.SharedPreferences;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.utils.SharedPref;

/* compiled from: LoginPref */
public class g extends SharedPref {
    private static final String Nj = "account-pref";
    private static g Nk;

    private g(SharedPreferences preferences) {
        super(preferences);
    }

    public static synchronized g pU() {
        g gVar;
        synchronized (g.class) {
            if (Nk == null) {
                Nk = new g(AppConfig.getInstance().getAppContext().getSharedPreferences(Nj, 0));
            }
            gVar = Nk;
        }
        return gVar;
    }
}
