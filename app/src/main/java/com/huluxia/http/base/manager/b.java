package com.huluxia.http.base.manager;

import com.huluxia.framework.base.http.io.Response.ErrorListener;
import com.huluxia.framework.base.http.io.Response.Listener;
import com.huluxia.framework.base.http.io.impl.request.StringRequest;
import com.huluxia.framework.base.http.toolbox.error.AuthFailureError;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntity;

/* compiled from: MultipartPost */
public class b extends StringRequest {
    private MultipartEntity sl;

    public b(int method, String url, MultipartEntity entity, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.sl = entity;
    }

    public String getBodyContentType() {
        if (this.sl != null) {
            return this.sl.getContentType().getValue();
        }
        return super.getBodyContentType();
    }

    public HttpEntity getBody() throws AuthFailureError {
        if (this.sl == null) {
            return super.getBody();
        }
        return this.sl;
    }
}
