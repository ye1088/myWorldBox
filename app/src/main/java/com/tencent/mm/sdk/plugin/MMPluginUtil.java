package com.tencent.mm.sdk.plugin;

import android.content.Context;

public class MMPluginUtil {
    public static IMMPluginAPI queryPluginMgr(Context context) {
        return new MMPluginAPIImpl(context);
    }
}
