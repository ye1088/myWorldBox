package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class OomError extends VolleyError {
    public OomError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public OomError(Throwable cause) {
        super(cause);
    }

    public int getErrorId() {
        return 37;
    }
}
