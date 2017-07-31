package com.huluxia.framework.base.http.io;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.retrypolicy.DefaultRetryPolicy;
import java.util.HashMap;
import java.util.Map;

public abstract class Request$RequestParam<P> {
    public final Map<String, String> additionalHeaders = new HashMap();
    public boolean cache = true;
    public ErrorListener errListener;
    public int method = 0;
    public final Map<String, String> params = new HashMap();
    public final Map<String, String> postParam = new HashMap();
    public int retryCount = 3;
    public Listener<P> succListener;
    public int timeoutMs = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    public String url;
}
