package com.huluxia.controller.resource.handler.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;

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
