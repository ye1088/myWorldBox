package com.huluxia.controller.resource.handler.segments.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.segments.c;
import java.io.File;

/* compiled from: NesHandler */
public class k extends c {
    public k(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dG() + File.separator + "NES" + File.separator + ((ResTaskInfo) getInfo()).filename;
    }

    public String getSuffix() {
        return "nes";
    }
}
