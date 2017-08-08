package com.MCWorld.mojang;

import com.MCWorld.framework.base.log.HLog;
import java.io.IOException;

class Mojang$2 implements Runnable {
    final /* synthetic */ Mojang this$0;
    final /* synthetic */ String val$filename;
    final /* synthetic */ Object val$object;

    Mojang$2(Mojang this$0, String str, Object obj) {
        this.this$0 = this$0;
        this.val$filename = str;
        this.val$object = obj;
    }

    public void run() {
        try {
            this.this$0.reloadLevelData();
            if (this.this$0.worldItem != null && this.val$filename != null && this.val$filename.equals(this.this$0.worldItem.getFileName())) {
                HLog.info("Mojang_Tag", "post asyn load level data when init with filename = " + this.val$filename + ", current level = " + Mojang.access$200(this.this$0), new Object[0]);
                Mojang.access$100(this.this$0, 263, new Object[]{Boolean.valueOf(true), this.val$object});
                Mojang.access$100(this.this$0, 265, new Object[]{Boolean.valueOf(true)});
            }
        } catch (IOException e) {
            HLog.error("Mojang_Tag", "asynReloadLevelData error", e, new Object[0]);
            Mojang.access$100(this.this$0, 263, new Object[]{Boolean.valueOf(false), this.val$object});
            Mojang.access$100(this.this$0, 265, new Object[]{Boolean.valueOf(false)});
        }
    }
}
