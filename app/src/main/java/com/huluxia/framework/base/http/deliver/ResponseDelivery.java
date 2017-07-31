package com.huluxia.framework.base.http.deliver;

import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.io.Response;
import com.huluxia.framework.base.http.toolbox.error.CancelError;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;

public interface ResponseDelivery {
    void postCancel(Request<?> request, CancelError cancelError);

    void postError(Request<?> request, VolleyError volleyError);

    void postResponse(Request<?> request, Response<?> response);

    void postResponse(Request<?> request, Response<?> response, Runnable runnable);
}
