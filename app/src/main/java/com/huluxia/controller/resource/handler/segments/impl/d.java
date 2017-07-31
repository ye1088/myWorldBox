package com.huluxia.controller.resource.handler.segments.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.segments.c;
import java.io.File;

/* compiled from: GbcHandler */
public class d extends c {
    public d(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dG() + File.separator + "GBC" + File.separator + ((ResTaskInfo) getInfo()).filename;
    }

    public String getSuffix() {
        return "zip";
    }
}
