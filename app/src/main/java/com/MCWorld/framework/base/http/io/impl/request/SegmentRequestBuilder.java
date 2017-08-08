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

public class SegmentRequestBuilder extends Request$RequestBuilder<SegmentRequestBuilder, DownloadRequestParam, String> {
    private static final String TAG = "DownloadRequestBuilder";
    private final WeakReference<RequestQueue> mQ;

    public static class DownloadRequestParam extends Request$RequestParam<String> {
        public CancelListener cancelListener;
        public Object context;
        public String dir;
        public int encodeType;
        public String filename;
        public String id;
        public Request$PrepareListener prepareListener;
        public ProgressListener progressListener;
        public Range<Long> range;
        int rate;
        public boolean rename;
        public int total;
        public int weight;
        public int weightTotal;

        public DownloadRequestParam() {
            this.encodeType = 1;
            this.timeoutMs = 10000;
            this.retryCount = 4;
            this.cache = false;
        }
    }

    public SegmentRequestBuilder(RequestQueue queue) {
        this.param = new DownloadRequestParam();
        this.mQ = new WeakReference(queue);
    }

    public void execute() {
        if (UtilsFunction.empty(((DownloadRequestParam) this.param).url)) {
            HLog.error(TAG, "gson obj request param invalid", new Object[0]);
            return;
        }
        SegmentRequest request = new SegmentRequest(((DownloadRequestParam) this.param).method, ((DownloadRequestParam) this.param).id, UtilsText.getUrlWithParam(((DownloadRequestParam) this.param).url, ((DownloadRequestParam) this.param).params), ((DownloadRequestParam) this.param).dir, ((DownloadRequestParam) this.param).filename, ((DownloadRequestParam) this.param).encodeType, ((DownloadRequestParam) this.param).range, ((DownloadRequestParam) this.param).weight, ((DownloadRequestParam) this.param).weightTotal, ((DownloadRequestParam) this.param).rate, new Listener<String>() {
            public void onResponse(String response) {
                if (((DownloadRequestParam) SegmentRequestBuilder.this.param).succListener != null) {
                    ((DownloadRequestParam) SegmentRequestBuilder.this.param).succListener.onResponse(response);
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 256, new Object[]{((DownloadRequestParam) SegmentRequestBuilder.this.param).url, SegmentRequestBuilder.this.getDownloadPath()});
                HLog.verbose(SegmentRequestBuilder.TAG, "notify download succ", new Object[0]);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                HLog.error(SegmentRequestBuilder.TAG, "download error url %s , error %s", new Object[]{((DownloadRequestParam) SegmentRequestBuilder.this.param).url, error});
                if (((DownloadRequestParam) SegmentRequestBuilder.this.param).errListener != null) {
                    ((DownloadRequestParam) SegmentRequestBuilder.this.param).errListener.onErrorResponse(error);
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 257, new Object[]{((DownloadRequestParam) SegmentRequestBuilder.this.param).url, SegmentRequestBuilder.this.getDownloadPath(), ((DownloadRequestParam) SegmentRequestBuilder.this.param).context});
            }
        }, new ProgressListener() {
            public void onProgress(String url, long length, long progress, float speed) {
                if (((DownloadRequestParam) SegmentRequestBuilder.this.param).progressListener != null) {
                    ((DownloadRequestParam) SegmentRequestBuilder.this.param).progressListener.onProgress(url, length, progress, speed);
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 258, new Object[]{url, SegmentRequestBuilder.this.getDownloadPath(), new ProgressInfo(length, progress, speed)});
            }
        }, new CancelListener() {
            public void onCancel() {
                HLog.debug(SegmentRequestBuilder.TAG, "cancel listener recv notification", new Object[0]);
                if (((DownloadRequestParam) SegmentRequestBuilder.this.param).cancelListener != null) {
                    ((DownloadRequestParam) SegmentRequestBuilder.this.param).cancelListener.onCancel();
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 259, new Object[]{((DownloadRequestParam) SegmentRequestBuilder.this.param).url, SegmentRequestBuilder.this.getDownloadPath()});
            }
        }, new Request$PrepareListener() {
            public void onPrepare() {
                if (((DownloadRequestParam) SegmentRequestBuilder.this.param).prepareListener != null) {
                    ((DownloadRequestParam) SegmentRequestBuilder.this.param).prepareListener.onPrepare();
                }
                EventNotifyCenter.notifyEventUiThread(BaseEvent.class, 266, new Object[]{((DownloadRequestParam) SegmentRequestBuilder.this.param).url, SegmentRequestBuilder.this.getDownloadPath()});
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

    public SegmentRequestBuilder setId(String id) {
        ((DownloadRequestParam) this.param).id = id;
        return getThis();
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

    public SegmentRequestBuilder getThis() {
        return this;
    }

    public SegmentRequestBuilder setDir(String dir) {
        ((DownloadRequestParam) this.param).dir = dir;
        return this;
    }

    public SegmentRequestBuilder setFilename(String filename) {
        ((DownloadRequestParam) this.param).filename = filename;
        return this;
    }

    public SegmentRequestBuilder setContext(Object context) {
        ((DownloadRequestParam) this.param).context = context;
        return this;
    }

    public SegmentRequestBuilder setEncodeType(int encodeType) {
        ((DownloadRequestParam) this.param).encodeType = encodeType;
        return this;
    }

    public SegmentRequestBuilder setProgressListener(ProgressListener progressListener) {
        ((DownloadRequestParam) this.param).progressListener = progressListener;
        return this;
    }

    public SegmentRequestBuilder setCancelListener(CancelListener cancelListener) {
        ((DownloadRequestParam) this.param).cancelListener = cancelListener;
        return this;
    }

    public SegmentRequestBuilder setPrepareListener(Request$PrepareListener prepareListener) {
        ((DownloadRequestParam) this.param).prepareListener = prepareListener;
        return this;
    }

    public SegmentRequestBuilder setSuccListener(Listener<String> succListener) {
        return (SegmentRequestBuilder) super.setSuccListener(succListener);
    }

    public SegmentRequestBuilder setErrListener(ErrorListener errListener) {
        return (SegmentRequestBuilder) super.setErrListener(errListener);
    }

    public SegmentRequestBuilder setRange(Range<Long> range) {
        ((DownloadRequestParam) this.param).range = range;
        return this;
    }

    public SegmentRequestBuilder setWeight(int weight, int weightTotal) {
        ((DownloadRequestParam) this.param).weight = weight;
        ((DownloadRequestParam) this.param).weightTotal = weightTotal;
        return this;
    }

    public SegmentRequestBuilder setRate(int rate) {
        ((DownloadRequestParam) this.param).rate = rate;
        return this;
    }

    public SegmentRequestBuilder setRename(boolean rename) {
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
