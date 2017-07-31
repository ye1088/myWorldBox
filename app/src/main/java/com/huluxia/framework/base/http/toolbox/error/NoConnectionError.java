package com.huluxia.framework.base.http.toolbox.error;

public class NoConnectionError extends NetworkError {
    public NoConnectionError(Throwable reason) {
        super(reason);
    }

    public int getErrorId() {
        return 22;
    }
}
