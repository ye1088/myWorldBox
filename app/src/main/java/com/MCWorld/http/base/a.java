package com.MCWorld.http.base;

import android.net.Uri;
import android.net.Uri.Builder;
import com.MCWorld.HTApplication;
import com.MCWorld.data.j;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.BaseHttpMgr;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.module.ab;
import com.MCWorld.utils.o;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.CMPVersion;

/* compiled from: AsyncBBSHttpRequest */
public abstract class a extends c {
    public static String rN = (HTApplication.DEBUG ? "http://test.bbs.huluxia.net" : ab.axS);
    public static String rO = CMPVersion.CMP2_0;
    public static String rP = "/ANDROID/2.0";
    public static String rQ = UtilsVersion.getVersionString(AppConfig.getInstance().getAppContext());
    public static int versioncode = UtilsVersion.getVersionCode(AppConfig.getInstance().getAppContext());

    protected String aE(String url) {
        String key = j.ep().getKey();
        String marketID = HTApplication.bJ_mctool_huluxia_string();
        Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(BaseHttpMgr.PARAM_APP_VERSION, rQ);
        builder.appendQueryParameter("platform", "2");
        builder.appendQueryParameter("_key", key);
        builder.appendQueryParameter("marketID", marketID);
        builder.appendQueryParameter(BaseHttpMgr.PARAM_DEVICE_CODE, o.getDeviceId());
        builder.appendQueryParameter("versioncode", String.valueOf(versioncode));
        return builder.toString();
    }
}
