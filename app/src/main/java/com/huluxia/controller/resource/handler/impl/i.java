package com.huluxia.controller.resource.handler.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.base.utils.UtilsFunction;

/* compiled from: IsoHandler */
public class i extends p {
    public i(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().getDownloadPath();
        }
    }

    public String getSuffix() {
        return "iso";
    }
}
