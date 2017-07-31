package com.huluxia.module.feedback;

import com.huluxia.framework.base.http.io.Response.CancelListener;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.io.Response.ProgressListener;
import com.huluxia.framework.base.http.toolbox.entity.ContentType;
import com.huluxia.framework.base.http.toolbox.entity.mime.content.StringBody;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.log.LogToES;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.module.h;
import java.io.File;
import java.util.HashMap;

/* compiled from: FeedbackModule */
class a$1 implements Runnable {
    final /* synthetic */ a aBT;
    final /* synthetic */ Object rB;

    a$1(a this$0, Object obj) {
        this.aBT = this$0;
        this.rB = obj;
    }

    public void run() {
        String logPath = a.a(this.aBT);
        new HashMap().put("_key", new StringBody("key_10", ContentType.MULTIPART_FORM_DATA).toString());
        if (UtilsFunction.empty(logPath)) {
            EventNotifyCenter.notifyEventUiThread(h.class, h.aqX, new Object[]{Boolean.valueOf(false), "", this.rB});
            return;
        }
        File file = new File(logPath);
        LogToES.flush();
        HttpMgr.getInstance().performUpload("http://upload.huluxia.net/upload/file", file.getAbsolutePath(), null, new Listener<String>(this) {
            final /* synthetic */ a$1 aBU;

            {
                this.aBU = this$1;
            }

            public void onResponse(String response) {
                HLog.info("FeedbackModule", "response %s", new Object[]{response});
                EventNotifyCenter.notifyEventUiThread(h.class, h.aqX, new Object[]{Boolean.valueOf(true), response, this.aBU.rB});
            }
        }, new ErrorListener(this) {
            final /* synthetic */ a$1 aBU;

            {
                this.aBU = this$1;
            }

            public void onErrorResponse(VolleyError error) {
                HLog.info("FeedbackModule", "onErrorResponse %s", new Object[]{error});
                EventNotifyCenter.notifyEventUiThread(h.class, h.aqX, new Object[]{Boolean.valueOf(false), "", this.aBU.rB});
            }
        }, new ProgressListener(this) {
            final /* synthetic */ a$1 aBU;

            {
                this.aBU = this$1;
            }

            public void onProgress(String url, long length, long progress, float speed) {
            }
        }, new CancelListener(this) {
            final /* synthetic */ a$1 aBU;

            {
                this.aBU = this$1;
            }

            public void onCancel() {
                EventNotifyCenter.notifyEventUiThread(h.class, h.aqX, new Object[]{Boolean.valueOf(false), "", this.aBU.rB});
            }
        }, false);
    }
}
