package com.MCWorld.http.other;

import com.MCWorld.HTApplication;
import com.MCWorld.data.n;
import com.MCWorld.http.base.c;
import com.MCWorld.http.base.d;
import com.MCWorld.utils.u;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: VersionCheckRequest */
public class j extends c {
    private static final String ti = "http://version.check.huluxia.com/hlx_mc";
    private static final String tj = "http://version.check.huluxia.com/test";

    public String eU() {
        String szUrl = String.format(Locale.getDefault(), "%s/hlx_mctool_update.txt", new Object[]{ti});
        String channel = u.ca("UMENG_CHANNEL");
        if (channel != null && "mctool_tencent".equals(channel)) {
            szUrl = String.format(Locale.getDefault(), "%s/hlx_mctool_tencent_update.txt", new Object[]{ti});
        } else if (channel != null && "mctool_tiebax".equals(channel)) {
            szUrl = String.format(Locale.getDefault(), "%s/hlx_mctool_tiebax_update.txt", new Object[]{ti});
        } else if (channel != null && "mctool_bbs".equals(channel)) {
            szUrl = String.format(Locale.getDefault(), "%s/hlx_mctool_bbs_update.txt", new Object[]{ti});
        }
        if (!HTApplication.DEBUG) {
            return szUrl;
        }
        return String.format(Locale.getDefault(), "%s/test_hlx_mctool_update.txt", new Object[]{tj});
    }

    public void g(List<NameValuePair> list) {
    }

    public void a(d response, JSONObject json) throws JSONException {
        response.setData(new n(json));
    }
}
