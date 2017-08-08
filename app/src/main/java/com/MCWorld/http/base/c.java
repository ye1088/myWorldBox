package com.MCWorld.http.base;

import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;
import com.MCWorld.HTApplication;
import com.MCWorld.data.j;
import com.MCWorld.framework.BaseHttpMgr;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsVersion;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.utils.o;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* compiled from: AsyncHttpRequest */
public abstract class c implements ErrorListener, Listener<String> {
    public static String HOST = (HTApplication.DEBUG ? "http://test.tools.huluxia.net" : "http://tools.huluxia.net");
    public static final int STATUS_OK = 1;
    private static String appVersion = UtilsVersion.getVersionString(HTApplication.getAppContext());
    public static String rU = "http://upload.huluxia.net";
    private int rV;
    private boolean rW = true;
    private f rX;
    private boolean rY = false;
    private int rZ = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;

    public abstract void a(d dVar, JSONObject jSONObject) throws JSONException;

    public abstract String eU();

    public abstract void g(List<NameValuePair> list);

    public boolean eV() {
        return this.rY;
    }

    public void E(boolean showProgress) {
        this.rY = showProgress;
    }

    protected String aE(String url) {
        String key = j.ep().getKey();
        String marketID = HTApplication.bJ_mctool_huluxia_string();
        Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(BaseHttpMgr.PARAM_APP_VERSION, appVersion);
        builder.appendQueryParameter("platform", "2");
        builder.appendQueryParameter("_key", key);
        builder.appendQueryParameter("marketID", marketID);
        builder.appendQueryParameter(BaseHttpMgr.PARAM_DEVICE_CODE, o.getDeviceId());
        return builder.toString();
    }

    public void execute() {
        b(false, true);
    }

    public void eW() {
        b(false, false);
    }

    public void ba(int ver) {
    }

    public void eX() {
        String url = eU();
        this.rY = false;
        fa();
        HttpMgr.getInstance().performStringRequest(aE(url), null, this, this, false, false);
    }

    public void b(boolean showProgress, boolean needStatus) {
        String url = eU();
        this.rY = showProgress;
        fa();
        HttpMgr.getInstance().performStringRequest(aE(url), null, this, this, false, false);
    }

    public void eY() {
        c(true, true);
    }

    public void eZ() {
        c(true, false);
    }

    public void c(boolean showProgress, boolean needStatus) {
        this.rY = showProgress;
        String url = eU();
        List<NameValuePair> nvps = new ArrayList();
        g(nvps);
        if (!HTApplication.DEBUG) {
            h(nvps);
        }
        Map<String, String> params = new HashMap();
        for (NameValuePair param : nvps) {
            params.put(param.getName(), param.getValue() == null ? "" : param.getValue());
        }
        fa();
        HttpMgr.getInstance().performPostStringRequest(aE(url), null, params, this, this, false, false, this.rW, this.rZ);
    }

    public void onResponse(String response) {
        aG(response);
    }

    public void onErrorResponse(VolleyError error) {
        fb();
    }

    protected void fa() {
        d response = new d();
        response.bb(this.rV);
        response.E(this.rY);
        if (this.rX != null) {
            this.rX.a(response);
        }
    }

    protected void fb() {
        d response = new d();
        response.E(this.rY);
        response.bb(this.rV);
        Log.i("sendFailureMessage", "DEBUG requestError");
        if (this.rX != null) {
            this.rX.b(response);
        }
    }

    protected void aG(String JSON) {
        d response = new d();
        response.E(this.rY);
        Log.i("sendSuccessMessage", "BaseAdapter : requestFinished");
        try {
            Log.i("sendSuccessMessage", "Response: " + JSON);
            JSONObject json = (JSONObject) new JSONTokener(JSON).nextValue();
            int status = json.optInt("status", 0);
            response.setStatus(status);
            if (status != 1) {
                response.bd(json.optInt("code", 0));
                response.aH(json.optString("msg", ""));
            }
            response.aI(JSON);
            response.bb(this.rV);
            a(response, json);
            if (this.rX != null) {
                this.rX.c(response);
            }
        } catch (Exception e) {
            if (this.rX != null) {
                this.rX.b(response);
            }
            Log.w("e", e);
        }
    }

    public f fc() {
        return this.rX;
    }

    public void a(f delegate) {
        this.rX = delegate;
    }

    public int fe() {
        return this.rV;
    }

    public void bb(int requestType) {
        this.rV = requestType;
    }

    public static void setDebug(boolean debug) {
        if (debug) {
            HOST = "http://hlx.iweju.com";
        } else {
            HOST = "http://floor.huluxia.net";
        }
    }

    public static boolean getDebug() {
        return HTApplication.DEBUG;
    }

    public static void setHost(String ip) {
        HOST = "http://" + ip + ":8061";
    }

    public static String ff() {
        return appVersion;
    }

    public static String b(String url, List<NameValuePair> nvps) {
        StringBuffer sb = new StringBuffer();
        sb.append(url).append("?");
        int i = 0;
        for (NameValuePair nvp : nvps) {
            String para;
            int i2 = i + 1;
            if (i != 0) {
                sb.append("&");
            }
            try {
                para = URLEncoder.encode(nvp.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                para = nvp.getValue();
            } catch (Exception e2) {
                HLog.error("encodeUrl", "Exception(%s) url(%s)", e2.getMessage(), url);
                para = "";
            }
            sb.append(nvp.getName()).append(SimpleComparison.EQUAL_TO_OPERATION).append(para);
            i = i2;
        }
        return sb.toString();
    }

    public static void h(List<NameValuePair> nvps) {
        String key = j.ep().getKey();
        String marketID = HTApplication.bJ_mctool_huluxia_string();
        nvps.add(new BasicNameValuePair("_key", key));
        nvps.add(new BasicNameValuePair("marketID", marketID));
        nvps.add(new BasicNameValuePair(BaseHttpMgr.PARAM_DEVICE_CODE, o.getDeviceId()));
    }

    public void F(boolean retry) {
        this.rW = retry;
    }

    public void bc(int timeOut) {
        this.rZ = timeOut;
    }
}
