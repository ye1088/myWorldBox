package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public class InterceptError extends VolleyError {
    public InterceptError(String message) {
        super(message);
    }

    public InterceptError(Throwable cause) {
        super(cause);
    }

    public InterceptError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 25;
    }
}
