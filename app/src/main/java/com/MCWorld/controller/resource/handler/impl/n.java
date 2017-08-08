package com.MCWorld.controller.resource.handler.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.io.File;

/* compiled from: NesHandler */
public class n extends p {
    public n(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().dG() + File.separator + "NES" + File.separator + info.filename;
        }
    }

    public String getSuffix() {
        return "nes";
    }
}
