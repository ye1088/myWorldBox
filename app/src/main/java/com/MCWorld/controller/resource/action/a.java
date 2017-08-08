package com.MCWorld.controller.resource.action;

import android.os.SystemClock;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.handler.impl.d;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.io.impl.request.DownloadRequestBuilder;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsApkPackage;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.UtilsAndroid;
import com.MCWorld.jni.UtilsEncrypt;
import com.MCWorld.jni.UtilsEncrypt$EncryptItem;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/* compiled from: DownloadAction */
public class a implements b {
    private static final String TAG = "DownloadAction";
    private WeakReference<d> mE;

    public a(d handler) {
        this.mE = new WeakReference(handler);
    }

    public boolean run() {
        final ResTaskInfo info = (ResTaskInfo) ((d) this.mE.get()).getInfo();
        Map<String, String> headers = new HashMap();
        headers.put("Cookie", info.mW);
        headers.putAll(ah(info.url));
        headers.putAll(dL());
        ((DownloadRequestBuilder) com.MCWorld.controller.resource.http.a.dX().getDownloadReqBuilder(info.url, info.dir, info.filename).setRename(info.mU).setEncodeType(UtilsFunction.empty(info.dataDownUrl) ? -1 : 4).setAdditionalHeaders(headers)).setContext(info).setSuccListener(new Listener<String>(this) {
            final /* synthetic */ a mF;

            public void onResponse(String response) {
                HLog.info(a.TAG, "download action on response %s, info %s, handler %s", new Object[]{response, info, this.mF.mE.get()});
                if (this.mF.mE.get() != null) {
                    try {
                        ((d) this.mF.mE.get()).onResponse(response);
                    } catch (Exception ex) {
                        ((d) this.mF.mE.get()).destroy();
                        HLog.error(a.TAG, "download action onresponse error %s", ex, new Object[0]);
                    }
                }
            }
        }).setProgressListener(new ProgressListener(this) {
            final /* synthetic */ a mF;

            {
                this.mF = this$0;
            }

            public void onProgress(String url, long length, long progress, float speed) {
                if (this.mF.mE.get() != null) {
                    ((d) this.mF.mE.get()).onProgress(url, length, progress, speed);
                }
            }
        }).setErrListener(new ErrorListener(this) {
            final /* synthetic */ a mF;

            public void onErrorResponse(VolleyError error) {
                HLog.info(a.TAG, "download action on err %s, info %s , handler %s", new Object[]{error, info, this.mF.mE.get()});
                if (this.mF.mE.get() != null) {
                    ((d) this.mF.mE.get()).onErrorResponse(error);
                }
            }
        }).setCancelListener(new CancelListener(this) {
            final /* synthetic */ a mF;

            public void onCancel() {
                HLog.info(a.TAG, "download action cancel response %s, handler %s", new Object[]{info, this.mF.mE.get()});
                if (this.mF.mE.get() != null) {
                    ((d) this.mF.mE.get()).onCancel();
                }
            }
        }).execute();
        return false;
    }

    private Map<String, String> ah(String url) {
        Map<String, String> headers = new HashMap();
        try {
            UtilsEncrypt$EncryptItem item = UtilsEncrypt.encrpytLogin(url, UtilsAndroid.fetchDeviceId(), UtilsApkPackage.getAppPackageName(AppConfig.getInstance().getAppContext()), String.valueOf(SystemClock.elapsedRealtime()));
            int randomFactor = UtilsEncrypt.radomInt();
            int assist_02 = randomFactor + 65;
            int assist_01 = item.len ^ randomFactor;
            HLog.debug(TAG, "download encode %s", new Object[]{String.format("%s_%d_%d", new Object[]{item.code, Integer.valueOf(assist_01), Integer.valueOf(assist_02)})});
            headers.put("Referer", URLEncoder.encode(downloadCode, "UTF-8"));
        } catch (Exception e) {
            HLog.error(TAG, "download encode err %s, url %s", new Object[]{e.getMessage(), url});
        }
        return headers;
    }

    private Map<String, String> dL() {
        Map<String, String> headers = new HashMap();
        headers.put("User-Agent", "huluxiadown");
        return headers;
    }
}
