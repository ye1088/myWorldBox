package com.huluxia.framework.base.http.transport;

import com.huluxia.framework.base.http.io.NetworkResponse;
import com.huluxia.framework.base.http.io.Request;
import com.huluxia.framework.base.http.toolbox.error.VolleyError;

public interface Network<T extends Request<?>> {
    NetworkResponse performRequest(T t) throws VolleyError;
}
