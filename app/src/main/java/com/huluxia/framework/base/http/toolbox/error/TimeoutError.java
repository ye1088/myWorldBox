package com.huluxia.framework.base.http.toolbox.error;

public class TimeoutError extends VolleyError {
    public TimeoutError(String message) {
        super(message);
    }

    public int getErrorId() {
        return 23;
    }
}
