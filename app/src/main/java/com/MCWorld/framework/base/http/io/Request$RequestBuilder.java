package com.MCWorld.framework.base.http.io;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.util.Map;

public abstract class Request$RequestBuilder<V extends Request$RequestBuilder, T extends Request$RequestParam<P>, P> {
    protected T param;

    public abstract void cancel();

    public abstract void execute();

    public abstract V getThis();

    public V setMethod(int method) {
        this.param.method = method;
        return getThis();
    }

    public V setUrl(String url) {
        this.param.url = url;
        return getThis();
    }

    public V setCache(boolean cache) {
        this.param.cache = cache;
        return getThis();
    }

    public V setTimeoutMs(int timeoutMs) {
        this.param.timeoutMs = timeoutMs;
        return getThis();
    }

    public V setRetryCount(int retryCount) {
        this.param.retryCount = retryCount;
        return getThis();
    }

    public V setParams(Map<String, String> params) {
        if (!UtilsFunction.empty(params)) {
            this.param.params.putAll(params);
        }
        return getThis();
    }

    public V setPostParam(Map<String, String> params) {
        if (!UtilsFunction.empty(params)) {
            this.param.postParam.putAll(params);
        }
        return getThis();
    }

    public V setAdditionalHeaders(Map<String, String> headers) {
        if (!UtilsFunction.empty(headers)) {
            this.param.additionalHeaders.putAll(headers);
        }
        return getThis();
    }

    public V setSuccListener(Listener<P> succListener) {
        this.param.succListener = succListener;
        return getThis();
    }

    public V setErrListener(ErrorListener errListener) {
        this.param.errListener = errListener;
        return getThis();
    }
}
