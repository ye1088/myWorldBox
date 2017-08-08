package com.MCWorld.controller.resource.handler.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.framework.base.utils.UtilsFunction;

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
