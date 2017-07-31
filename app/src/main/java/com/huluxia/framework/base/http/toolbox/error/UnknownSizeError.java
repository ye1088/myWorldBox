package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

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
