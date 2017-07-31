package com.huluxia.framework.base.http.toolbox.retrypolicy;

import com.huluxia.framework.base.http.toolbox.error.VolleyError;

public interface RetryPolicy {
    int getCurrentRetryCount();

    int getCurrentTimeout();

    void retry(VolleyError volleyError) throws VolleyError;
}
