package com.huluxia.version;

import com.huluxia.framework.base.http.io.impl.request.GsonObjRequestBuilder;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.widget.UtilsAndroid;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.utils.ah;
import java.util.HashMap;

/* compiled from: VersionModule */
public class h {
    private static h boA;

    public static synchronized h MU() {
        h hVar;
        synchronized (h.class) {
            if (boA == null) {
                boA = new h();
            }
            hVar = boA;
        }
        return hVar;
    }

    public void MV() {
        ((GsonObjRequestBuilder) HttpMgr.getInstance().getGsonObjReqBuilder(i.boH, a.class).setSuccListener(new 2(this))).setErrListener(new 1(this)).execute();
    }

    public void r(String app_name, String channel, String tag) {
        HLog.info("requestVersionInfo", "app_name:" + app_name + " channel:" + channel, new Object[0]);
        HashMap<String, String> param = new HashMap();
        param.put("app_name", app_name);
        param.put("channel", channel);
        HttpMgr.getInstance().performStringRequest(i.boF, param, new 3(this, tag), new 4(this, tag));
    }

    public void ax(String app_name, String channel) {
        long versioncode = (long) UtilsAndroid.fetchVersionCode();
        if (versioncode > 0 && versioncode > ah.KZ().LY()) {
            HashMap<String, String> param = new HashMap();
            param.put("app_name", app_name);
            param.put("channel", channel);
            HttpMgr.getInstance().performStringRequest(i.boG, param, new 5(this, versioncode), new 6(this));
        }
    }
}
