package com.xiaomi.network;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.huluxia.video.recorder.b;
import com.xiaomi.channel.commonutils.network.a;
import com.xiaomi.channel.commonutils.network.c;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.network.HostManager.HttpGet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.tools.ant.taskdefs.optional.j2ee.HotDeploymentTool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HostManagerV2 extends HostManager {
    private final int a;
    private final int b;
    private int c;

    protected HostManagerV2(Context context, HostFilter hostFilter, HttpGet httpGet, String str) {
        this(context, hostFilter, httpGet, str, null, null);
    }

    protected HostManagerV2(Context context, HostFilter hostFilter, HttpGet httpGet, String str, String str2, String str3) {
        super(context, hostFilter, httpGet, str, str2, str3);
        this.a = 80;
        this.b = 5222;
        this.c = 80;
        addReservedHost("resolver.msg.xiaomi.net", "resolver.msg.xiaomi.net:5222");
    }

    static String a(String str) {
        try {
            int length = str.length();
            byte[] bytes = str.getBytes("UTF-8");
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                if ((b & b.bpd) != b.bpd) {
                    bytes[i] = (byte) (((b & 15) ^ ((byte) (((b >> 4) + length) & 15))) | (b & b.bpd));
                }
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    protected JSONObject a() {
        JSONObject jSONObject;
        synchronized (this.mHostsMapping) {
            jSONObject = new JSONObject();
            jSONObject.put("ver", 2);
            jSONObject.put("data", toJSON());
        }
        return jSONObject;
    }

    protected void b(String str) {
        synchronized (this.mHostsMapping) {
            this.mHostsMapping.clear();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("ver") != 2) {
                throw new JSONException("Bad version");
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("data");
            for (int i = 0; i < optJSONArray.length(); i++) {
                Fallbacks fromJSON = new Fallbacks().fromJSON(optJSONArray.getJSONObject(i));
                this.mHostsMapping.put(fromJSON.getHost(), fromJSON);
            }
        }
    }

    protected boolean checkHostMapping() {
        synchronized (this.mHostsMapping) {
            if (hostLoaded) {
                return true;
            }
            hostLoaded = true;
            this.mHostsMapping.clear();
            try {
                Object loadHosts = loadHosts();
                if (!TextUtils.isEmpty(loadHosts)) {
                    b(loadHosts);
                    com.xiaomi.channel.commonutils.logger.b.b("loading the new hosts succeed");
                    return true;
                }
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a("load bucket failure: " + th.getMessage());
            }
        }
        return false;
    }

    protected String getHost() {
        return "resolver.msg.xiaomi.net";
    }

    protected String getRemoteFallbackJSON(ArrayList<String> arrayList, String str, String str2) {
        Iterator it;
        ArrayList arrayList2;
        ArrayList arrayList3 = new ArrayList();
        List<c> arrayList4 = new ArrayList();
        arrayList4.add(new a("type", str));
        if (str.equals("wap")) {
            arrayList4.add(new a("conpt", a(d.f(this.sAppContext))));
        }
        arrayList4.add(new a("uuid", str2));
        arrayList4.add(new a(HotDeploymentTool.ACTION_LIST, join(arrayList, MiPushClient.ACCEPT_TIME_SEPARATOR)));
        Fallback localFallback = getLocalFallback("resolver.msg.xiaomi.net");
        String format = String.format(Locale.US, "http://%1$s/gslb/?ver=3.0", new Object[]{"resolver.msg.xiaomi.net:" + this.c});
        if (localFallback == null) {
            arrayList3.add(format);
            synchronized (mReservedHosts) {
                it = ((ArrayList) mReservedHosts.get("resolver.msg.xiaomi.net")).iterator();
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    arrayList3.add(String.format(Locale.US, "http://%1$s/gslb/?ver=3.0", new Object[]{str3}));
                }
            }
            arrayList2 = arrayList3;
        } else {
            arrayList2 = localFallback.a(format);
        }
        Iterator it2 = arrayList2.iterator();
        IOException iOException = null;
        while (it2.hasNext()) {
            Builder buildUpon = Uri.parse((String) it2.next()).buildUpon();
            for (c cVar : arrayList4) {
                buildUpon.appendQueryParameter(cVar.a(), cVar.b());
            }
            try {
                return this.sHttpGetter == null ? d.a(this.sAppContext, new URL(buildUpon.toString())) : this.sHttpGetter.a(buildUpon.toString());
            } catch (IOException e) {
                iOException = e;
            }
        }
        return iOException != null ? super.getRemoteFallbackJSON(arrayList, str, str2) : null;
    }

    public void persist() {
        synchronized (this.mHostsMapping) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.sAppContext.openFileOutput(getProcessName(), 0)));
                Object jSONObject = a().toString();
                if (!TextUtils.isEmpty(jSONObject)) {
                    bufferedWriter.write(jSONObject);
                }
                bufferedWriter.close();
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a("persist bucket failure: " + e.getMessage());
            }
        }
    }

    public void purge() {
        synchronized (this.mHostsMapping) {
            for (Fallbacks purge : this.mHostsMapping.values()) {
                purge.purge(true);
            }
            Object obj = null;
            while (obj == null) {
                for (String str : this.mHostsMapping.keySet()) {
                    if (((Fallbacks) this.mHostsMapping.get(str)).getFallbacks().isEmpty()) {
                        this.mHostsMapping.remove(str);
                        obj = null;
                        break;
                    }
                }
                obj = 1;
            }
        }
    }
}
