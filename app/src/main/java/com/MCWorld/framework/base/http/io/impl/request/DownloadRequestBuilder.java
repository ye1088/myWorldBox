package com.MCWorld.framework.base.http.io.impl.request;

import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.base.http.dispatcher.RequestQueue;
import com.MCWorld.framework.base.http.io.Request$PrepareListener;
import com.MCWorld.framework.base.http.io.Request$RequestBuilder;
import com.MCWorld.framework.base.http.io.Request$RequestParam;
import com.MCWorld.framework.base.http.io.Response.CancelListener;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsString;
import com.MCWorld.framework.base.utils.UtilsText;
import java.io.File;
import java.lang.ref.WeakReference;

public class DownloadRequestBuilder extends Request$RequestBuilder<DownloadRequestBuilder, DownloadRequestParam, String> {
    private static final String TAG = "DownloadRequestBuilder";
    private final WeakReference<RequestQueue> mQ;

    public static class DownloadRequestParam extends Request$RequestParam<String> {
        public CancelListener cancelListener;
        public Object context;
        public String dir;
        public int encodeType;
        public String filename;
        public Request$PrepareListener prepareListener;
        public ProgressListener progressListener;
        public boolean rename;

        public DownloadRequestParam() {
            this.timeoutMs = 10000;
            this.retryCount = 4;
            this.cache = false;
        }
    }

    public DownloadRequestBuilder(RequestQueue queue) {
        this.param = new DownloadRequestParam();
        this.mQ = new WeakReference(queue);
    }

    public void execute() {
        if (UtilsFunction.empty(((DownloadRequestParam) this.param).url)) {
            HLog.error(TAG, "gson obj request param invalid", new Object[0]);
            return;
        }
        DownloadRequest request = new DownloadRequest(((DownloadRequestParam) this.param).method, UtilsText.getUrlWithParam(((DownloadRequestParam) this.param).url, ((DownloadRequestParam) this.param).params), ((DownloadRequestParam) this.param).dir, ((DownloadRequestParam) this.param).filename, ((DownloadRequestParam) this.param).encodeType, new Listener<String>() {
            public void onResponse(String response) {
                if (((DownloadRequestParam) DownloadRequestBuilder.this.param).succListener != null) {
                    ((DownloadRequestParam) DownloadRequestBuilder.this.param).succListener.onResponse(response);
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 256, new Object[]{((DownloadRequestParam) DownloadRequestBuilder.this.param).url, DownloadRequestBuilder.this.getDownloadPath()});
                HLog.verbose(DownloadRequestBuilder.TAG, "notify download succ", new Object[0]);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                HLog.error(DownloadRequestBuilder.TAG, "download error url %s , error %s", new Object[]{((DownloadRequestParam) DownloadRequestBuilder.this.param).url, error});
                if (((DownloadRequestParam) DownloadRequestBuilder.this.param).errListener != null) {
                    ((DownloadRequestParam) DownloadRequestBuilder.this.param).errListener.onErrorResponse(error);
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 257, new Object[]{((DownloadRequestParam) DownloadRequestBuilder.this.param).url, DownloadRequestBuilder.this.getDownloadPath(), ((DownloadRequestParam) DownloadRequestBuilder.this.param).context});
            }
        }, new ProgressListener() {
            public void onProgress(String url, long length, long progress, float speed) {
                HLog.verbose(DownloadRequestBuilder.TAG, "recv progress url %s progress %d", new Object[]{url, Long.valueOf(progress)});
                if (((DownloadRequestParam) DownloadRequestBuilder.this.param).progressListener != null) {
                    ((DownloadRequestParam) DownloadRequestBuilder.this.param).progressListener.onProgress(url, length, progress, speed);
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 258, new Object[]{url, DownloadRequestBuilder.this.getDownloadPath(), new ProgressInfo(length, progress, speed)});
            }
        }, new CancelListener() {
            public void onCancel() {
                HLog.debug(DownloadRequestBuilder.TAG, "cancel listener recv notification", new Object[0]);
                if (((DownloadRequestParam) DownloadRequestBuilder.this.param).cancelListener != null) {
                    ((DownloadRequestParam) DownloadRequestBuilder.this.param).cancelListener.onCancel();
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 259, new Object[]{((DownloadRequestParam) DownloadRequestBuilder.this.param).url, DownloadRequestBuilder.this.getDownloadPath()});
            }
        }, new Request$PrepareListener() {
            public void onPrepare() {
                if (((DownloadRequestParam) DownloadRequestBuilder.this.param).prepareListener != null) {
                    ((DownloadRequestParam) DownloadRequestBuilder.this.param).prepareListener.onPrepare();
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 266, new Object[]{((DownloadRequestParam) DownloadRequestBuilder.this.param).url, DownloadRequestBuilder.this.getDownloadPath()});
            }
        });
        request.setShouldCache(((DownloadRequestParam) this.param).cache).addHeader(((DownloadRequestParam) this.param).additionalHeaders);
        DefaultRetryPolicy policy = new DefaultRetryPolicy();
        if (((DownloadRequestParam) this.param).timeoutMs != policy.getCurrentTimeout()) {
            policy.setTimeoutMs(((DownloadRequestParam) this.param).timeoutMs);
        }
        if (((DownloadRequestParam) this.param).retryCount != policy.getCurrentRetryCount()) {
            policy.setMaxNumRetries(((DownloadRequestParam) this.param).retryCount);
        }
        request.setRetryPolicy(policy);
        if (this.mQ.get() != null) {
            ((RequestQueue) this.mQ.get()).add(request);
        }
    }

    public void cancel() {
        if (this.mQ.get() != null && this.param != null) {
            ((RequestQueue) this.mQ.get()).cancelAll(UtilsText.getUrlWithParam(((DownloadRequestParam) this.param).url, ((DownloadRequestParam) this.param).params));
            ((DownloadRequestParam) this.param).prepareListener = null;
            ((DownloadRequestParam) this.param).progressListener = null;
            ((DownloadRequestParam) this.param).errListener = null;
            ((DownloadRequestParam) this.param).succListener = null;
        }
    }

    public DownloadRequestBuilder getThis() {
        return this;
    }

    public DownloadRequestBuilder setDir(String dir) {
        ((DownloadRequestParam) this.param).dir = dir;
        return this;
    }

    public DownloadRequestBuilder setFilename(String filename) {
        ((DownloadRequestParam) this.param).filename = filename;
        return this;
    }

    public DownloadRequestBuilder setContext(Object context) {
        ((DownloadRequestParam) this.param).context = context;
        return this;
    }

    public DownloadRequestBuilder setEncodeType(int encodeType) {
        ((DownloadRequestParam) this.param).encodeType = encodeType;
        return this;
    }

    public DownloadRequestBuilder setProgressListener(ProgressListener progressListener) {
        ((DownloadRequestParam) this.param).progressListener = progressListener;
        return this;
    }

    public DownloadRequestBuilder setCancelListener(CancelListener cancelListener) {
        ((DownloadRequestParam) this.param).cancelListener = cancelListener;
        return this;
    }

    public DownloadRequestBuilder setPrepareListener(Request$PrepareListener prepareListener) {
        ((DownloadRequestParam) this.param).prepareListener = prepareListener;
        return this;
    }

    public DownloadRequestBuilder setSuccListener(Listener<String> succListener) {
        return (DownloadRequestBuilder) super.setSuccListener(succListener);
    }

    public DownloadRequestBuilder setErrListener(ErrorListener errListener) {
        return (DownloadRequestBuilder) super.setErrListener(errListener);
    }

    public DownloadRequestBuilder setRename(boolean rename) {
        if (((DownloadRequestParam) this.param).dir == null || ((DownloadRequestParam) this.param).filename == null || ((DownloadRequestParam) this.param).url == null) {
            throw new IllegalArgumentException("must rename after set url, dir and filename");
        }
        ((DownloadRequestParam) this.param).rename = rename;
        if (rename) {
            DownloadRequestParam downloadRequestParam = (DownloadRequestParam) this.param;
            downloadRequestParam.filename = new File(getDownloadFileAbsPath(((DownloadRequestParam) this.param).url, ((DownloadRequestParam) this.param).dir, ((DownloadRequestParam) this.param).filename)).getName();
        }
        return this;
    }

    public String getDownloadPath() {
        if (((DownloadRequestParam) this.param).dir != null && ((DownloadRequestParam) this.param).filename != null) {
            return new File(((DownloadRequestParam) this.param).dir, ((DownloadRequestParam) this.param).filename).getAbsolutePath();
        }
        throw new IllegalArgumentException("must getpath after set dir and filename");
    }

    public static String getDownloadFileAbsPath(String url, String path, String fileName) {
        return path + File.separator + generateFileName(url, fileName);
    }

    private static String generateFileName(String url, String fileName) {
        String file = fileName.replaceAll("[^0-9a-zA-Z一-龥]+", "");
        String hash = String.valueOf(UtilsString.hashCode(url));
        return file + "-" + hash + "-" + String.valueOf(System.currentTimeMillis());
    }
}
