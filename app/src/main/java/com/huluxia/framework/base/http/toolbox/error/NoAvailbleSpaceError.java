package com.huluxia.framework.base.http.toolbox.error;

public class NoAvailbleSpaceError extends VolleyError {
    public NoAvailbleSpaceError(Throwable reason) {
        super(reason);
    }

    public int getErrorId() {
        return 21;
    }
}
