package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class PrepareError extends VolleyError {
    public PrepareError(String message) {
        super(message);
    }

    public PrepareError(Throwable cause) {
        super(cause);
    }

    public PrepareError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 27;
    }
}
