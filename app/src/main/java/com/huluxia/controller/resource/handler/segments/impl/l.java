package com.huluxia.controller.resource.handler.segments.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.segments.c;
import java.io.File;

/* compiled from: NgpHandler */
public class l extends c {
    public l(ResTaskInfo info) {
        super(info);
    }

    protected String dV() {
        return b.dE().dG() + File.separator + "NGP" + File.separator + ((ResTaskInfo) getInfo()).filename;
    }

    public String getSuffix() {
        return "ngp";
    }
}
