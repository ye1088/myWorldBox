package com.huluxia.controller.resource.handler.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.base.utils.UtilsFunction;

/* compiled from: Mp4Handler */
public class k extends p {
    public k(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().getDownloadPath();
        }
    }

    public String getSuffix() {
        return "mp4";
    }
}
