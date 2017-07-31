package com.huluxia.framework.base.http.io.impl.request;

import com.huluxia.framework.base.http.dispatcher.RequestQueue;
import com.huluxia.framework.base.http.io.Request$RequestBuilder;
import com.huluxia.framework.base.http.io.Request$RequestParam;
import com.huluxia.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsText;
import java.lang.ref.WeakReference;

public class GsonObjRequestBuilder<P> extends Request$RequestBuilder<GsonObjRequestBuilder, GsonObjRequestParam<P>, P> {
    private final WeakReference<RequestQueue> mQ;

    public static class GsonObjRequestParam<P> extends Request$RequestParam<P> {
        private final Class<P> clz;

        public GsonObjRequestParam(Class<P> clz) {
            this.clz = clz;
        }
    }

    public static <T> GsonObjRequestBuilder<T> newBuilder(RequestQueue queue, Class<T> clz) {
        return new GsonObjRequestBuilder(queue, clz);
    }

    public GsonObjRequestBuilder(RequestQueue queue, Class<P> clz) {
        this.param = new GsonObjRequestParam(clz);
        this.mQ = new WeakReference(queue);
    }

    public void execute() {
        if (UtilsFunction.empty(((GsonObjRequestParam) this.param).url)) {
            HLog.error(this, "gson obj request param invalid", new Object[0]);
            return;
        }
        GsonObjectRequest<P> request = new GsonObjectRequest(((GsonObjRequestParam) this.param).clz, UtilsFunction.empty(((GsonObjRequestParam) this.param).postParam) ? ((GsonObjRequestParam) this.param).method : 1, UtilsText.getUrlWithParam(((GsonObjRequestParam) this.param).url, ((GsonObjRequestParam) this.param).params), ((GsonObjRequestParam) this.param).succListener, ((GsonObjRequestParam) this.param).errListener);
        request.setShouldCache(((GsonObjRequestParam) this.param).cache).addHeader(((GsonObjRequestParam) this.param).additionalHeaders);
        if (!UtilsFunction.empty(((GsonObjRequestParam) this.param).postParam)) {
            request.setPostParam(((GsonObjRequestParam) this.param).postParam);
        }
        DefaultRetryPolicy policy = new DefaultRetryPolicy();
        if (((GsonObjRequestParam) this.param).timeoutMs != policy.getCurrentTimeout()) {
            policy.setTimeoutMs(((GsonObjRequestParam) this.param).timeoutMs);
        }
        if (((GsonObjRequestParam) this.param).retryCount != policy.getCurrentRetryCount()) {
            policy.setMaxNumRetries(((GsonObjRequestParam) this.param).retryCount);
        }
        request.setRetryPolicy(policy);
        request.setTag(((GsonObjRequestParam) this.param).url);
        if (this.mQ.get() != null) {
            ((RequestQueue) this.mQ.get()).add(request);
        }
    }

    public void cancel() {
        if (this.mQ.get() != null && this.param != null) {
            ((RequestQueue) this.mQ.get()).cancelAll(((GsonObjRequestParam) this.param).url);
        }
    }

    public GsonObjRequestBuilder<P> getThis() {
        return this;
    }
}
