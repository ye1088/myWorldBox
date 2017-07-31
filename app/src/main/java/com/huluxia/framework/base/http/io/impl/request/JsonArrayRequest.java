package com.huluxia.framework.base.http.io.impl.request;

import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.toolbox.HttpHeaderParser;
import com.huluxia.framework.base.http.toolbox.error.ParseError;
import org.json.JSONArray;

public class JsonArrayRequest extends JsonRequest<JSONArray> {
    public JsonArrayRequest(String url, Listener<JSONArray> listener, ErrorListener errorListener) {
        super(0, url, null, listener, errorListener);
    }

    public Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new JSONArray(new String(response.data, HttpHeaderParser.parseCharset(response.headers))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Throwable e) {
            return Response.error(new ParseError(e));
        } catch (Throwable je) {
            return Response.error(new ParseError(je));
        }
    }
}
