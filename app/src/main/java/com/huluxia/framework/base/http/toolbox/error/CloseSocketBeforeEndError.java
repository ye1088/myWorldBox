package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class CloseSocketBeforeEndError extends VolleyError {
    public CloseSocketBeforeEndError(String message) {
        super(message);
    }

    public CloseSocketBeforeEndError(Throwable cause) {
        super(cause);
    }

    public CloseSocketBeforeEndError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 31;
    }
}
