package com.MCWorld.framework.base.http.toolbox.error;

public class LocalFileError extends VolleyError {
    public LocalFileError(Throwable cause) {
        super(cause);
    }

    public int getErrorId() {
        return 39;
    }
}
