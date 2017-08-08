package com.MCWorld.module;

import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.toolbox.entity.ContentType;
import com.MCWorld.framework.base.http.toolbox.entity.mime.content.StringBody;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.http.HttpMgr;
import java.io.File;
import java.util.HashMap;

/* compiled from: UploadModule */
class aa$6 implements Runnable {
    final /* synthetic */ aa axK;
    final /* synthetic */ String oX;
    final /* synthetic */ Object rB;
    final /* synthetic */ int val$index;

    aa$6(aa this$0, String str, int i, Object obj) {
        this.axK = this$0;
        this.oX = str;
        this.val$index = i;
        this.rB = obj;
    }

    public void run() {
        new HashMap().put("_key", new StringBody("key_10", ContentType.MULTIPART_FORM_DATA).toString());
        if (UtilsFunction.empty(this.oX)) {
            EventNotifyCenter.notifyEventUiThread(h.class, 771, new Object[]{"", Integer.valueOf(this.val$index), this.rB});
            return;
        }
        HttpMgr.getInstance().performUpload(ab.ayD, new File(this.oX).getAbsolutePath(), null, new Listener<String>(this) {
            final /* synthetic */ aa$6 axQ;

            {
                this.axQ = this$1;
            }

            public void onResponse(String response) {
                HLog.info("UploadModule", "response %s", new Object[]{response});
                EventNotifyCenter.notifyEventUiThread(h.class, 771, new Object[]{response, Integer.valueOf(this.axQ.val$index), this.axQ.rB});
            }
        }, new ErrorListener(this) {
            final /* synthetic */ aa$6 axQ;

            {
                this.axQ = this$1;
            }

            public void onErrorResponse(VolleyError error) {
                HLog.info("UploadModule", "onErrorResponse %s", new Object[]{error});
                EventNotifyCenter.notifyEventUiThread(h.class, 771, new Object[]{"", Integer.valueOf(this.axQ.val$index), this.axQ.rB});
            }
        }, new ProgressListener(this) {
            final /* synthetic */ aa$6 axQ;

            {
                this.axQ = this$1;
            }

            public void onProgress(String url, long length, long progress, float speed) {
            }
        }, new CancelListener(this) {
            final /* synthetic */ aa$6 axQ;

            {
                this.axQ = this$1;
            }

            public void onCancel() {
                EventNotifyCenter.notifyEventUiThread(h.class, 771, new Object[]{"", Integer.valueOf(this.axQ.val$index), this.axQ.rB});
            }
        }, false);
    }
}
