package com.MCWorld.framework;

import android.content.SharedPreferences;
import com.MCWorld.framework.base.utils.SharedPref;

public class FrameworkPref extends SharedPref {

    private static class Singleton {
        public static FrameworkPref instance = new FrameworkPref(AppConfig.getInstance().getAppContext().getSharedPreferences("framework-common-pref", 0));

        private Singleton() {
        }
    }

    public static SharedPref getInstance() {
        return Singleton.instance;
    }

    private FrameworkPref(SharedPreferences pref) {
        super(pref);
    }
}
