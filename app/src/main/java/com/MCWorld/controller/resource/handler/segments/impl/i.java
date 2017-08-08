package com.MCWorld.controller.resource.handler.segments.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.handler.segments.c;

/* compiled from: N64Handler */
public class i extends c {
    public i(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dF();
    }

    public String getSuffix() {
        return "zip";
    }
}
