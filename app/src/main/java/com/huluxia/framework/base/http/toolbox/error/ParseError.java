package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class ParseError extends VolleyError {
    public ParseError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }

    public int getErrorId() {
        return 20;
    }
}
