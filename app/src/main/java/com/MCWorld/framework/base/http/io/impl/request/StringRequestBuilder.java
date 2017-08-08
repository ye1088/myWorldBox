package com.MCWorld.framework.base.http.io.impl.request;

import com.MCWorld.framework.base.http.dispatcher.RequestQueue;
import com.MCWorld.framework.base.http.io.Request$RequestBuilder;
import com.MCWorld.framework.base.http.io.Request$RequestParam;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsText;
import java.lang.ref.WeakReference;

public class StringRequestBuilder extends Request$RequestBuilder<StringRequestBuilder, StringRequestParam, String> {
    private final WeakReference<RequestQueue> mQ;

    public static class StringRequestParam extends Request$RequestParam<String> {
    }

    public StringRequestBuilder(RequestQueue queue) {
        this.param = new StringRequestParam();
        this.mQ = new WeakReference(queue);
    }

    public StringRequestBuilder getThis() {
        return this;
    }

    public void execute() {
        if (UtilsFunction.empty(((StringRequestParam) this.param).url)) {
            HLog.error(this, "string request param invalid", new Object[0]);
            return;
        }
        StringRequest request = new StringRequest(UtilsFunction.empty(((StringRequestParam) this.param).postParam) ? ((StringRequestParam) this.param).method : 1, UtilsText.getUrlWithParam(((StringRequestParam) this.param).url, ((StringRequestParam) this.param).params), ((StringRequestParam) this.param).succListener, ((StringRequestParam) this.param).errListener);
        if (!UtilsFunction.empty(((StringRequestParam) this.param).postParam)) {
            request.setPostParam(((StringRequestParam) this.param).postParam);
        }
        request.setShouldCache(((StringRequestParam) this.param).cache).addHeader(((StringRequestParam) this.param).additionalHeaders);
        DefaultRetryPolicy policy = new DefaultRetryPolicy();
        if (((StringRequestParam) this.param).timeoutMs != policy.getCurrentTimeout()) {
            policy.setTimeoutMs(((StringRequestParam) this.param).timeoutMs);
        }
        if (((StringRequestParam) this.param).retryCount != policy.getCurrentRetryCount()) {
            policy.setMaxNumRetries(((StringRequestParam) this.param).retryCount);
        }
        request.setTag(((StringRequestParam) this.param).url);
        request.setRetryPolicy(policy);
        if (this.mQ.get() != null) {
            ((RequestQueue) this.mQ.get()).add(request);
        }
    }

    public void cancel() {
        if (this.mQ.get() != null && this.param != null) {
            ((RequestQueue) this.mQ.get()).cancelAll(((StringRequestParam) this.param).url);
        }
    }
}
