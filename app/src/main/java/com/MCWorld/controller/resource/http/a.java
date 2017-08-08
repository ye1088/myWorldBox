package com.MCWorld.controller.resource.http;

import android.content.Context;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.AppConstant;
import com.MCWorld.framework.BaseHttpMgr;

import java.io.File;

/* compiled from: ResourceHttp */
public class a extends BaseHttpMgr {
    private static a oG;

    private a() {
        if (AppConfig.getInstance().getAppContext() == null) {
            throw new IllegalStateException("app config has not been initialized");
        }
    }

    public static synchronized a dX() {
        a aVar;
        synchronized (a.class) {
            if (oG == null) {
                oG = new a();
            }
            aVar = oG;
        }
        return aVar;
    }

    public void init(Context context) {
        super.init(context, new Config(false));
        super.initDownloadRequest(context);
    }

    public String getStringReqCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_CACHE;
    }

    public String getDownloadCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_DOWNLOAD_CACHE;
    }

    public String getVoiceCachePath() {
        return AppConstant.getHlxName() + File.separator + AppConstant.getAppName() + File.separator + AppConstant.HTTP_VOICE_CACHE;
    }
}
