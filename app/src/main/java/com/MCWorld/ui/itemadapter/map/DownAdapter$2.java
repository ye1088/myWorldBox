package com.MCWorld.ui.itemadapter.map;

import com.MCWorld.controller.resource.bean.ResTaskInfo;

class DownAdapter$2 implements Runnable {
    final /* synthetic */ DownAdapter aTp;
    final /* synthetic */ DownAdapter$b aTq;
    final /* synthetic */ ResTaskInfo aTr;

    DownAdapter$2(DownAdapter this$0, DownAdapter$b downAdapter$b, ResTaskInfo resTaskInfo) {
        this.aTp = this$0;
        this.aTq = downAdapter$b;
        this.aTr = resTaskInfo;
    }

    public void run() {
        this.aTq.aTJ.setProgressNotInUiThread((int) this.aTr.mN.progress);
        this.aTq.aTJ.setMaxProgress((int) this.aTr.mN.total);
    }
}
