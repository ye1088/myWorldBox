package com.MCWorld.controller.resource.handler.segments;

import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.handler.impl.h;
import java.io.File;

/* compiled from: RenameSegmentHandler */
public abstract class c extends g implements h {
    public c(ResTaskInfo info) {
        super(info);
        if (!info.filename.endsWith(getSuffix())) {
            info.filename += getSuffix();
        }
    }

    protected void dQ() {
        aj(new File(((ResTaskInfo) getInfo()).dir, ((ResTaskInfo) getInfo()).filename).getAbsolutePath());
        ((ResTaskInfo) getInfo()).state = State.SUCC.ordinal();
    }

    public void aj(String path) {
    }
}
