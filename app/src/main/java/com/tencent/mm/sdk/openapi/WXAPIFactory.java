package com.tencent.mm.sdk.openapi;

import android.content.Context;

public class WXAPIFactory {
    private static WXAPIFactory p = null;

    private WXAPIFactory() {
    }

    public static IWXAPI createWXAPI(Context context, String str) {
        if (p == null) {
            p = new WXAPIFactory();
        }
        return new WXApiImplV10(context, str);
    }

    public static IWXAPI createWXAPI(Context context, String str, boolean z) {
        if (p == null) {
            p = new WXAPIFactory();
        }
        return new WXApiImplV10(context, str, z);
    }
}
