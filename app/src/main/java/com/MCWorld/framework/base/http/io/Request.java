package com.MCWorld.framework.base.http.io;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.MCWorld.framework.base.http.datasource.cache.Cache.Entry;
import com.MCWorld.framework.base.http.dispatcher.RequestQueue;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.ProgressListener;
import com.MCWorld.framework.base.http.toolbox.HttpLog;
import com.MCWorld.framework.base.http.toolbox.HttpLog.MarkerLog;
import com.MCWorld.framework.base.http.toolbox.error.AuthFailureError;
import com.MCWorld.framework.base.http.toolbox.error.InvalidParamError;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import com.MCWorld.framework.base.http.toolbox.retrypolicy.RetryPolicy;
import com.MCWorld.framework.base.log.HLog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

public abstract class Request<T> implements Comparable<Request<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static final long SLOW_REQUEST_THRESHOLD_MS = 3000;
    private static final String TAG = "Request";
    private boolean enableMarker;
    private boolean isRunning;
    protected Map<String, String> mAdditionalHeaders;
    private Entry mCacheEntry;
    private volatile boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    private final ErrorListener mErrorListener;
    private MarkerLog mEventLog;
    private String mIp;
    private final int mMethod;
    protected final ProgressListener mProgressListener;
    private long mRequestBirthTime;
    private RequestQueue mRequestQueue;
    private boolean mResponseDelivered;
    private RetryPolicy mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private Object mTag;
    private final String mUrl;

    public interface Method {
        public static final int DELETE = 3;
        public static final int DEPRECATED_GET_OR_POST = -1;
        public static final int GET = 0;
        public static final int HEAD = 4;
        public static final int OPTIONS = 5;
        public static final int PATCH = 7;
        public static final int POST = 1;
        public static final int PUT = 2;
        public static final int TRACE = 6;
    }

    public abstract void deliverResponse(T t);

    public abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);

    @Deprecated
    public Request(String url, ErrorListener listener) {
        this(-1, url, listener);
    }

    public Request(int method, String url, ErrorListener listener) {
        this(method, url, listener, null);
    }

    public Request(int method, String url, ErrorListener listener, ProgressListener progressListener) {
        this.enableMarker = false;
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mRequestBirthTime = 0;
        this.mCacheEntry = null;
        this.mAdditionalHeaders = new HashMap();
        this.isRunning = false;
        this.mMethod = method;
        this.mUrl = url;
        this.mErrorListener = listener;
        this.mProgressListener = progressListener;
        setRetryPolicy(new DefaultRetryPolicy());
        this.mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(url);
    }

    public int getMethod() {
        return this.mMethod;
    }

    public Request<?> setTag(Object tag) {
        this.mTag = tag;
        return this;
    }

    public Object getTag() {
        return this.mTag;
    }

    public ErrorListener getErrorListener() {
        return this.mErrorListener;
    }

    public ProgressListener getProgressListener() {
        return this.mProgressListener;
    }

    public int getTrafficStatsTag() {
        return this.mDefaultTrafficStatsTag;
    }

    private static int findDefaultTrafficStatsTag(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                String host = uri.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    public void setEnableMarker(boolean enableMarker) {
        this.enableMarker = enableMarker;
        if (enableMarker) {
            this.mEventLog = new MarkerLog();
        }
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        this.mRetryPolicy = retryPolicy;
        return this;
    }

    public void addMarker(String tag) {
        if (this.enableMarker) {
            this.mEventLog.add(tag, Thread.currentThread().getId());
        } else if (this.mRequestBirthTime == 0) {
            this.mRequestBirthTime = SystemClock.elapsedRealtime();
        }
    }

    public void addMarker(String formatTag, Object... param) {
        try {
            String.format(formatTag, param);
        } catch (Exception e) {
            HLog.error(this, "add marker e %s", e, new Object[0]);
        }
    }

    public Request<?> addHeader(Map<String, String> headers) {
        if (headers != null) {
            this.mAdditionalHeaders.putAll(headers);
        }
        return this;
    }

    public Request<?> addHeader(String headerKey, String headerValue) {
        if (headerKey != null) {
            this.mAdditionalHeaders.put(headerKey, headerValue);
        }
        return this;
    }

    public void finish(String tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.finish(this);
        }
        if (this.enableMarker) {
            long threadId = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new 1(this, tag, threadId));
                return;
            }
            this.mEventLog.add(tag, threadId);
            this.mEventLog.finish(toString());
            return;
        }
        long requestTime = SystemClock.elapsedRealtime() - this.mRequestBirthTime;
        HttpLog.d("request %s[CLZ : %s ] cacel reason : %s", new Object[]{toString(), getClass().toString(), tag});
        if (requestTime >= SLOW_REQUEST_THRESHOLD_MS) {
            HttpLog.d("%d ms: %s", new Object[]{Long.valueOf(requestTime), toString()});
        }
    }

    public void setIp(String ip) {
        addMarker(String.format("mark-ip-%s", new Object[]{ip}));
        this.mIp = ip;
    }

    public String getIp() {
        return this.mIp;
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
        return this;
    }

    public final Request<?> setSequence(int sequence) {
        this.mSequence = Integer.valueOf(sequence);
        return this;
    }

    public final int getSequence() {
        if (this.mSequence != null) {
            return this.mSequence.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getCacheKey() {
        return getUrl();
    }

    public Request<?> setCacheEntry(Entry entry) {
        this.mCacheEntry = entry;
        return this;
    }

    public Entry getCacheEntry() {
        return this.mCacheEntry;
    }

    public void cancel() {
        cancel(false);
    }

    public void cancel(boolean mayInterruptIfRunning) {
        HLog.debug(TAG, "cancel request " + this, new Object[0]);
        addMarker(String.format("cancel-called-mayInterruptIfRunning-%b", new Object[]{Boolean.valueOf(mayInterruptIfRunning)}));
        this.mCanceled = true;
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public Map<String, String> getHeaders() throws AuthFailureError, InvalidParamError {
        return this.mAdditionalHeaders;
    }

    @Deprecated
    protected Map<String, String> getPostParams() throws AuthFailureError {
        return getParams();
    }

    @Deprecated
    protected String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    public HttpEntity getPostBody() throws AuthFailureError {
        Map<String, String> postParams = getPostParams();
        if (postParams == null || postParams.size() <= 0) {
            return null;
        }
        return new ByteArrayEntity(encodeParameters(postParams, getPostParamsEncoding()));
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    protected String getParamsEncoding() {
        return "UTF-8";
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public HttpEntity getBody() throws AuthFailureError {
        Map<String, String> params = getParams();
        if (params == null || params.size() <= 0) {
            return null;
        }
        return new ByteArrayEntity(encodeParameters(params, getParamsEncoding()));
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() != null && ((String) entry.getKey()).length() > 0) {
                    encodedParams.append(URLEncoder.encode((String) entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    if (entry.getValue() == null || ((String) entry.getValue()).length() <= 0) {
                        encodedParams.append("");
                    } else {
                        encodedParams.append(URLEncoder.encode((String) entry.getValue(), paramsEncoding));
                    }
                    encodedParams.append('&');
                }
            }
            HttpLog.v("post param %s", new Object[]{encodedParams.toString()});
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public final Request<?> setShouldCache(boolean shouldCache) {
        this.mShouldCache = shouldCache;
        return this;
    }

    public final boolean shouldCache() {
        return this.mShouldCache;
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public final int getTimeoutMs() {
        return this.mRetryPolicy.getCurrentTimeout();
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy;
    }

    public void markDelivered() {
        this.mResponseDelivered = true;
    }

    public boolean hasHadResponseDelivered() {
        return this.mResponseDelivered;
    }

    public VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    public void deliverError(VolleyError error) {
        if (this.mErrorListener != null) {
            this.mErrorListener.onErrorResponse(error);
        }
    }

    public void deliverCancel(VolleyError error) {
    }

    public void prepare() throws VolleyError {
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setRunning(boolean running) {
        addMarker("request-running");
        this.isRunning = running;
    }

    public int compareTo(Request<T> other) {
        Priority left = getPriority();
        Priority right = other.getPriority();
        if (left == right) {
            return this.mSequence.intValue() - other.mSequence.intValue();
        }
        return right.ordinal() - left.ordinal();
    }

    public String toString() {
        return (this.mCanceled ? "[X] " : "[ ] ") + getUrl() + " " + ("0x" + Integer.toHexString(getTrafficStatsTag())) + " " + getPriority() + " " + this.mSequence;
    }
}
