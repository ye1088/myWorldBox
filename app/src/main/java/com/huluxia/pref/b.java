package com.huluxia.pref;

import android.content.SharedPreferences;
import com.huluxia.framework.AppConfig;
import com.huluxia.utils.n;

/* compiled from: ConfigPref */
public class b extends n {
    private static final String aCT = "config";
    private static b aCU;

    private b(SharedPreferences preferences) {
        super(preferences);
    }

    public static synchronized b Em() {
        b bVar;
        synchronized (b.class) {
            if (aCU == null) {
                aCU = new b(AppConfig.getInstance().getAppContext().getSharedPreferences(aCT, 0));
            }
            bVar = aCU;
        }
        return bVar;
    }
}
