package com.huluxia;

import android.content.SharedPreferences;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.utils.SharedPref;

/* compiled from: McPref */
public class j extends SharedPref {
    private static j fX;

    private j(SharedPreferences pref) {
        super(pref);
    }

    public static synchronized j bZ() {
        j jVar;
        synchronized (j.class) {
            if (fX == null) {
                fX = new j(AppConfig.getInstance().getAppContext().getSharedPreferences("mc-pref", 0));
            }
            jVar = fX;
        }
        return jVar;
    }

    public void B(String crash) {
        putString("latest-crash", crash);
    }

    public String ca() {
        String crash = getString("latest-crash");
        remove("latest-crash");
        return crash;
    }
}
