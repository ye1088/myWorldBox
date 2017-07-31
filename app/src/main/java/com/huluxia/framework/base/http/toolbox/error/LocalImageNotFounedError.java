package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class LocalImageNotFounedError extends VolleyError {
    public LocalImageNotFounedError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public LocalImageNotFounedError(Throwable cause) {
        super(cause);
    }

    public int getErrorId() {
        return 38;
    }
}
