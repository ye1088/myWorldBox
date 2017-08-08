package com.MCWorld.framework.base.http.transport;

import com.MCWorld.framework.base.http.io.NetworkResponse;
import com.MCWorld.framework.base.http.io.Request;
import com.MCWorld.framework.base.http.toolbox.error.VolleyError;

public interface Network<T extends Request<?>> {
    NetworkResponse performRequest(T t) throws VolleyError;
}
