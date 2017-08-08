package com.MCWorld.controller.resource.handler.segments;

import com.MCWorld.controller.resource.handler.segments.f.a;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;

/* compiled from: PauseSegmentAction */
public class b implements com.MCWorld.controller.resource.action.b {
    private static final String TAG = "PauseSegmentAction";
    private d nT;

    public b(d action) {
        this.nT = action;
    }

    public boolean run() {
        boolean canceled = false;
        if (UtilsFunction.empty(this.nT.dR())) {
            canceled = this.nT.pause();
            this.nT.addMarker("pause-no-downloading-segment-" + canceled);
            HLog.info(TAG, "pause segment download but no downloading segments, canceld " + canceled, new Object[0]);
            return canceled;
        }
        for (a segment : this.nT.dR()) {
            if (com.MCWorld.controller.resource.http.a.dX().pauseDownloadRequest(segment.oq.url) || canceled) {
                canceled = true;
            } else {
                canceled = false;
            }
        }
        this.nT.addMarker("pause-downloading-" + UtilsFunction.size(this.nT.dR()) + "-" + canceled);
        HLog.info(TAG, "pause downloading segment " + this.nT.dR(), new Object[0]);
        return canceled;
    }
}
