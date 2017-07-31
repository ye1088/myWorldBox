package com.huluxia.controller.resource.handler.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;

/* compiled from: Mp3Handler */
public class j extends p {
    public j(ResTaskInfo info) {
        super(info);
        info.dir = b.dE().getDownloadPath();
    }

    public String getSuffix() {
        return "mp3";
    }
}
