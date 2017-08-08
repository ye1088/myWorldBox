package com.MCWorld.controller.resource.handler.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.framework.base.utils.UtilsFunction;

/* compiled from: NdsHandler */
public class m extends p {
    public m(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().dF();
        }
    }

    public String getSuffix() {
        return "nds";
    }
}
