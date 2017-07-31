package com.huluxia.framework.base.http.toolbox.error;

import com.huluxia.framework.base.http.io.NetworkResponse;

public class ClientError extends VolleyError {
    public ClientError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 24;
    }
}
