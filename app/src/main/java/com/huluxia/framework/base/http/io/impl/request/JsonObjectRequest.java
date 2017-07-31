package com.huluxia.framework.base.http.io.impl.request;

import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.HttpHeaderParser;
import com.huluxia.framework.base.http.toolbox.error.ParseError;
import org.json.JSONObject;

public class JsonObjectRequest extends JsonRequest<JSONObject> {
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, jsonRequest == null ? null : jsonRequest.toString(), listener, errorListener);
    }

    public JsonObjectRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
        this(jsonRequest == null ? 0 : 1, url, jsonRequest, listener, errorListener);
    }

    public Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new JSONObject(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        } catch (Throwable je) {
            return Response.error(new ParseError(je));
        }
    }
}
