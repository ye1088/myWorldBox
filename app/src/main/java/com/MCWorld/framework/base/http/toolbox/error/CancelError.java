package com.MCWorld.framework.base.http.toolbox.error;

public class CancelError extends VolleyError {
    public CancelError(String message) {
        super(message);
    }

    public int getErrorId() {
        return 33;
    }
}
