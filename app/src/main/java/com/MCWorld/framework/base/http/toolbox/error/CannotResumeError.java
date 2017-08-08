package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public class CannotResumeError extends VolleyError {
    private int error = 29;

    public CannotResumeError(String message) {
        super(message);
    }

    public CannotResumeError(Throwable cause) {
        super(cause);
    }

    public CannotResumeError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getErrorId() {
        return this.error;
    }
}
