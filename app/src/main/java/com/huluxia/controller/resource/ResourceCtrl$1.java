package com.huluxia.controller.resource;

import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.segments.a;
import com.huluxia.framework.base.log.HLog;
import java.io.File;

class ResourceCtrl$1 implements Runnable {
    final /* synthetic */ boolean mA;
    final /* synthetic */ ResTaskInfo mB;
    final /* synthetic */ ResourceCtrl mC;

    ResourceCtrl$1(ResourceCtrl this$0, boolean z, ResTaskInfo resTaskInfo) {
        this.mC = this$0;
        this.mA = z;
        this.mB = resTaskInfo;
    }

    public void run() {
        if (this.mA) {
            File file = new File(this.mB.dir, this.mB.filename);
            HLog.debug("ResourceCtrl", "delete file " + file + ", exist " + file.exists(), new Object[0]);
            HLog.debug("ResourceCtrl", "delete file " + file + ", del " + file.delete(), new Object[0]);
        }
        ResourceCtrl.access$200(this.mC, this.mB.url);
        if (this.mB.mZ != null) {
            a.delete(this.mB.mZ.id);
        }
        ResourceCtrl.access$300(this.mC).remove(this.mB.url);
        ResTaskInfo taskInfo = (ResTaskInfo) ResourceCtrl.access$400(this.mC).remove(this.mB.url);
        if (taskInfo != null) {
            HLog.info("ResourceCtrl", "restart task that deleted " + taskInfo, new Object[0]);
            this.mC.addTask(taskInfo);
        }
    }
}
