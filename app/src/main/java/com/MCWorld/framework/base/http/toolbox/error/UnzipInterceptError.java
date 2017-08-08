package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public class UnzipInterceptError extends InterceptError {
    public UnzipInterceptError(String message) {
        super(message);
    }

    public UnzipInterceptError(Throwable cause) {
        super(cause);
    }

    public UnzipInterceptError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 26;
    }
}
