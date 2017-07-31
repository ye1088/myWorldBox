package com.huluxia.framework.base.http.toolbox.error;

public class CreateDirectoryError extends VolleyError {
    public CreateDirectoryError(Throwable reason) {
        super(reason);
    }

    public int getErrorId() {
        return 34;
    }
}
