package com.MCWorld.framework.base.http.toolbox.retrypolicy;

import com.MCWorld.framework.base.http.toolbox.error.VolleyError;

public interface RetryPolicy {
    int getCurrentRetryCount();

    int getCurrentTimeout();

    void retry(VolleyError volleyError) throws VolleyError;
}
