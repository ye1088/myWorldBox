package com.MCWorld.controller.resource.handler.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;

/* compiled from: ZipHandler */
public class u extends p {
    public u(ResTaskInfo info) {
        super(info);
        info.dir = b.dE().getDownloadPath();
    }

    public String getSuffix() {
        return "zip";
    }
}
