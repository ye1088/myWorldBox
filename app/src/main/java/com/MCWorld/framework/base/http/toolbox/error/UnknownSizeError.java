package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public class UnknownSizeError extends VolleyError {
    public UnknownSizeError(String message) {
        super(message);
    }

    public UnknownSizeError(Throwable cause) {
        super(cause);
    }

    public UnknownSizeError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 28;
    }
}
