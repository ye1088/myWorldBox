package com.tencent.mm.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.mm.sdk.plugin.IMMPluginAPI;
import com.tencent.mm.sdk.plugin.MMPluginUtil;

public final class MMAppMgr {
    private MMAppMgr() {
    }

    public static void activate(boolean z) {
    }

    public static IMMPluginAPI getPluginMgr(Context context) {
        return MMPluginUtil.queryPluginMgr(context);
    }

    public static SharedPreferences getSharedPreferences(Context context, int i) {
        return new MMSharedPreferences(context);
    }
}
