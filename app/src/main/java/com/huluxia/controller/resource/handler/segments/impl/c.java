package com.huluxia.controller.resource.handler.segments.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import java.io.File;

/* compiled from: GbaHandler */
public class c extends com.huluxia.controller.resource.handler.segments.c {
    public c(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dG() + File.separator + "GBA" + File.separator + ((ResTaskInfo) getInfo()).filename;
    }

    public String getSuffix() {
        return "gba";
    }
}
