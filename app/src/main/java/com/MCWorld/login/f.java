package com.MCWorld.login;

import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.AppConstant;
import com.MCWorld.framework.BaseHttpMgr;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.login.utils.b;
import java.io.File;
import java.util.Map;

/* compiled from: LoginHttp */
public class f extends BaseHttpMgr {
    private static f Nh;
    public static String Ni;

    private f() {
        if (AppConfig.getInstance().getAppContext() == null) {
            throw new IllegalStateException("app config has not been initialized");
        }
    }

    public static synchronized f pT() {
        f fVar;
        synchronized (f.class) {
            if (Nh == null) {
                Nh = new f();
            }
            fVar = Nh;
        }
        return fVar;
    }

    protected String getStringReqCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_CACHE;
    }

    protected void fillCommonParam(Map<String, String> params) {
        if (!params.containsKey(BaseHttpMgr.PARAM_APP_VERSION)) {
            params.put(BaseHttpMgr.PARAM_APP_VERSION, UtilsVersion.getVersionString(AppConfig.getInstance().getAppContext()));
        }
        if (!params.containsKey("platform")) {
            params.put("platform", "2");
        }
        if (!params.containsKey(BaseHttpMgr.PARAM_MARKET_ID)) {
            params.put(BaseHttpMgr.PARAM_MARKET_ID, Ni);
        }
        if (!params.containsKey(BaseHttpMgr.PARAM_DEVICE_CODE)) {
            params.put(BaseHttpMgr.PARAM_DEVICE_CODE, b.getDeviceId());
        }
    }

    public static void bQ(String id) {
        Ni = id;
    }

    protected String getDownloadCachePath() {
        return null;
    }

    protected String getVoiceCachePath() {
        return null;
    }
}
