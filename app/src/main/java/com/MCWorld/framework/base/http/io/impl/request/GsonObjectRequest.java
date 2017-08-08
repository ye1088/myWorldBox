package com.MCWorld.framework.base.http.io.impl.request;

import com.MCWorld.framework.base.http.io.NetworkResponse;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.io.Response;
import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.toolbox.HttpHeaderParser;
import com.MCWorld.framework.base.http.toolbox.error.AuthFailureError;
import com.MCWorld.framework.base.http.toolbox.error.InvalidParamError;
import com.MCWorld.framework.base.http.toolbox.error.ParseError;
import com.MCWorld.framework.base.json.Json;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.util.HashMap;
import java.util.Map;

public class GsonObjectRequest<T> extends Request<T> {
    private final Class<T> clz;
    private final Listener<T> mListener;
    private Map<String, String> postParam;

    GsonObjectRequest(Class<T> clz, String url, Listener<T> listener, ErrorListener errorListener) {
        this(clz, -1, url, listener, errorListener);
    }

    public GsonObjectRequest(Class<T> clz, int method, String url, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.postParam = new HashMap();
        this.mListener = listener;
        this.clz = clz;
    }

    public Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(Json.parseJsonObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers)), this.clz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        } catch (Throwable e2) {
            return Response.error(new ParseError(e2));
        }
    }

    public void deliverResponse(T response) {
        this.mListener.onResponse(response);
    }

    public void setPostParam(Map<String, String> postParam) {
        if (!UtilsFunction.empty(postParam)) {
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
