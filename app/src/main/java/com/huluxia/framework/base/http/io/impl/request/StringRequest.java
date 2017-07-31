package com.huluxia.framework.base.http.io.impl.request;

import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.HttpHeaderParser;
import com.huluxia.framework.base.http.toolbox.error.AuthFailureError;
import com.huluxia.framework.base.http.toolbox.error.InvalidParamError;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringRequest extends Request<String> {
    private final Listener<String> mListener;
    private Map<String, String> postParam;

    protected StringRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.postParam = new HashMap();
        this.mListener = listener;
    }

    StringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        this(0, url, listener, errorListener);
    }

    public void deliverResponse(String response) {
        this.mListener.onResponse(response);
    }

    public Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    public void setPostParam(Map<String, String> postParam) {
        if (!UtilsFunction.empty((Map) postParam)) {
            this.postParam.putAll(postParam);
        }
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return this.postParam;
    }

    public Map<String, String> getHeaders() throws AuthFailureError, InvalidParamError {
        Map<String, String> headers = super.getHeaders();
        headers.put("Accept-Encoding", "gzip");
        return headers;
    }
}
