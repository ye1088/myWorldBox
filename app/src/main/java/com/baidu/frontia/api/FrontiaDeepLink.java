package com.baidu.frontia.api;

import android.content.Context;
import com.baidu.frontia.framework.IModule;
import com.baidu.frontia.module.deeplink.FrontiaDeepLinkImpl;

public class FrontiaDeepLink implements IModule {
    private static final String a = "FrontiaDeepLink";
    private static FrontiaDeepLink c;
    private FrontiaDeepLinkImpl b;

    private FrontiaDeepLink(Context context) {
        this.b = new FrontiaDeepLinkImpl(context);
    }

    public static FrontiaDeepLink newInstance(Context context) {
        if (context == null) {
            return null;
        }
        if (c == null) {
            synchronized (a) {
                if (c == null) {
                    c = new FrontiaDeepLink(context);
                }
            }
        }
        return c;
    }

    public void init(String str) {
        this.b.init(str);
    }

    public void launch(String str) {
        this.b.launch(str);
    }
}
