package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class InvalidParamError extends VolleyError {
    public InvalidParamError(String message) {
        super(message);
    }

    public InvalidParamError(Throwable cause) {
        super(cause);
    }

    public InvalidParamError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 19;
    }
}
