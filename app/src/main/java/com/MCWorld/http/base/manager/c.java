package com.MCWorld.http.base.manager;

import com.MCWorld.framework.base.http.io.Response.ErrorListener;
import com.MCWorld.framework.base.http.io.Response.Listener;
import com.MCWorld.framework.base.http.io.impl.request.StringRequest;
import com.MCWorld.framework.base.http.toolbox.error.AuthFailureError;
import java.util.Map;

/* compiled from: StringPost */
public class c extends StringRequest {
    private Map<String, String> params = null;

    public c(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = params;
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }
}
