package com.huluxia.controller.resource.handler.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.base.utils.UtilsFunction;

/* compiled from: N64Handler */
public class l extends p {
    public l(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().dF();
        }
    }

    public String getSuffix() {
        return "zip";
    }
}
