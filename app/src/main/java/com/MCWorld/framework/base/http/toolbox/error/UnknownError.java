package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public class UnknownError extends VolleyError {
    public final NetworkResponse networkResponse;

    public UnknownError() {
        this.networkResponse = null;
    }

    public UnknownError(NetworkResponse response) {
        this.networkResponse = response;
    }

    public UnknownError(String exceptionMessage) {
        super(exceptionMessage);
        this.networkResponse = null;
    }

    public UnknownError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        this.networkResponse = null;
    }

    public UnknownError(Throwable cause) {
        super(cause);
        this.networkResponse = null;
    }

    public int getErrorId() {
        return 255;
    }
}
