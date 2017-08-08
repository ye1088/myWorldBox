package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public abstract class VolleyError extends Exception {
    public final NetworkResponse networkResponse;

    protected abstract int getErrorId();

    public VolleyError() {
        this.networkResponse = null;
    }

    public VolleyError(NetworkResponse response) {
        this.networkResponse = response;
    }

    public VolleyError(String exceptionMessage) {
        super(exceptionMessage);
        this.networkResponse = null;
    }

    public VolleyError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        this.networkResponse = null;
    }

    public VolleyError(Throwable cause) {
        super(cause);
        this.networkResponse = null;
    }

    public static int getErrorId(VolleyError error) {
        return error == null ? 255 : error.getErrorId();
    }

    public String toString() {
        return "[type=" + getClass().getName() + ", id=" + getErrorId() + ", msg=" + getMessage() + " ]";
    }
}
