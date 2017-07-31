package com.huluxia.controller.resource.handler.segments.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.segments.c;
import java.io.File;

/* compiled from: SfcHandler */
public class n extends c {
    public n(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dG() + File.separator + "SFC" + File.separator + ((ResTaskInfo) getInfo()).filename;
    }

    public String getSuffix() {
        return "zip";
    }
}
