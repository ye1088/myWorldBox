package com.MCWorld.framework.base.http.toolbox.error;

import com.MCWorld.framework.base.http.io.NetworkResponse;

public class ServerError extends VolleyError {
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public int getErrorId() {
        return 17;
    }
}
