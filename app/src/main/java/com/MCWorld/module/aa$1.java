package com.MCWorld.module;

import com.MCWorld.data.j;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.http.HttpMgr;
import java.util.HashMap;
import java.util.Map;
import net.lingala.zip4j.core.c;
import net.lingala.zip4j.exception.ZipException;

/* compiled from: UploadModule */
class aa$1 implements Runnable {
    final /* synthetic */ String axJ;
    final /* synthetic */ aa axK;
    final /* synthetic */ String oX;

    aa$1(aa this$0, String str, String str2) {
        this.axK = this$0;
        this.oX = str;
        this.axJ = str2;
    }

    public void run() {
        final String retPath = aa.a(this.axK, this.oX, this.axJ);
        if (retPath != null) {
            try {
                if (!new c(retPath).Wq()) {
                    EventNotifyCenter.notifyEventUiThread(n.class, n.awx, new Object[]{Boolean.valueOf(false), "压缩文件不合法,可能被损坏", "", Integer.valueOf(0), null});
                    return;
                }
            } catch (ZipException e) {
            }
            final long size = aa.a(this.axK, retPath);
            if (size != -1) {
                final String mapMd5 = aa.b(this.axK, retPath);
                if (mapMd5 != null) {
                    Map<String, String> postparams = new HashMap();
                    postparams.put("_key", j.ep().getKey());
                    HttpMgr.getInstance().performUpload(ab.ayE, retPath, null, postparams, new Listener<String>(this) {
                        final /* synthetic */ aa$1 axO;

                        public void onResponse(String response) {
                            try {
                                t info = (t) Json.parseJsonObject(response, t.class);
                                if (info == null || !info.isSucc()) {
                                    HLog.debug("ETPrint", "sendfile fail " + (info == null ? "上传资源文件失败" : info.msg), new Object[0]);
                                    EventNotifyCenter.notifyEventUiThread(n.class, n.awx, new Object[]{Boolean.valueOf(false), msg, "", Long.valueOf(size), null});
                                    if (!this.axO.oX.toLowerCase().endsWith(".zip")) {
                                        UtilsFile.deleteFile(retPath);
                                    }
                                }
                                HLog.debug("ETPrint", "sendfile succ " + info.msg, new Object[0]);
                                EventNotifyCenter.notifyEventUiThread(n.class, n.awx, new Object[]{Boolean.valueOf(true), info.msg, mapMd5, Long.valueOf(size), info.url});
                                if (!this.axO.oX.toLowerCase().endsWith(".zip")) {
                                    UtilsFile.deleteFile(retPath);
                                }
                            } catch (Exception e) {
                                HLog.debug("ETPrint", "sendfile exception occur " + e.toString(), new Object[0]);
                                EventNotifyCenter.notifyEventUiThread(n.class, n.awx, new Object[]{Boolean.valueOf(true), "数据解析错误", "", Integer.valueOf(0), ""});
                            }
                        }
                    }, new ErrorListener(this) {
                        final /* synthetic */ aa$1 axO;

                        public void onErrorResponse(VolleyError error) {
                            HLog.debug("ETPrint", "sendfile on error " + error.toString(), new Object[0]);
                            EventNotifyCenter.notifyEventUiThread(n.class, n.awx, new Object[]{Boolean.valueOf(false), "上传资源文件错误,网络出现状况", "", Long.valueOf(size), null});
                        }
                    }, new ProgressListener(this) {
                        final /* synthetic */ aa$1 axO;

                        {
                            this.axO = this$1;
                        }

                        public void onProgress(String url, long length, long progress, float speed) {
                            boolean flag = false;
                            float percent = 0.0f;
                            if (length != 0) {
                                percent = (float) ((100 * progress) / length);
                                flag = true;
                            }
                            HLog.debug("ETPrint", "sendfile on progress " + progress, new Object[0]);
                            EventNotifyCenter.notifyEventUiThread(n.class, n.awE, new Object[]{Boolean.valueOf(flag), "上传文件:", Float.valueOf(percent)});
                        }
                    }, new CancelListener(this) {
                        final /* synthetic */ aa$1 axO;

                        public void onCancel() {
                            HLog.debug("ETPrint", "sendfile on cancel ", new Object[0]);
                            EventNotifyCenter.notifyEventUiThread(n.class, n.awx, new Object[]{Boolean.valueOf(false), "上传取消", "", Long.valueOf(size), null});
                        }
                    }, true);
                }
            }
        }
    }
}
