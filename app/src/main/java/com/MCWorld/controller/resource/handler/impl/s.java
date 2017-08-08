package com.MCWorld.controller.resource.handler.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.io.File;

/* compiled from: SfcHandler */
public class s extends p {
    public s(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().dG() + File.separator + "SFC" + File.separator + info.filename;
        }
    }

    public String getSuffix() {
        return "zip";
    }
}
