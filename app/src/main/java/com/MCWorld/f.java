package com.MCWorld;

import com.MCWorld.framework.AppConfig;

/* compiled from: DownloadDbManager */
public class f {
    public static final String eM = "mcworld";

    public static void init() {
        AppConfig.getInstance().initDbManager(eM, new 1(), 5);
    }
}
