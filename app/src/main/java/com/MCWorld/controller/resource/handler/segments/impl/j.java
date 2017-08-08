package com.MCWorld.controller.resource.handler.segments.impl;

import com.MCWorld.controller.b;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.handler.segments.c;

/* compiled from: NdsHandler */
public class j extends c {
    public j(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dF();
    }

    public String getSuffix() {
        return "nds";
    }
}
