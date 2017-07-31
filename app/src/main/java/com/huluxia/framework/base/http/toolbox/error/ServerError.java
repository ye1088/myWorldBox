package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class ServerError extends VolleyError {
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 17;
    }
}
