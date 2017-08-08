package com.MCWorld.mojang;

import com.MCWorld.framework.base.log.HLog;
import java.io.IOException;

class Mojang$8 implements Runnable {
    final /* synthetic */ Mojang this$0;

    Mojang$8(Mojang this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        try {
            Mojang.access$300(this.this$0);
            this.this$0.reloadLevelData();
            Mojang.access$100(this.this$0, 259, new Object[]{Boolean.valueOf(true)});
        } catch (IOException e) {
            HLog.error("Mojang_Tag", "addInventory asyn error", e, new Object[0]);
            Mojang.access$100(this.this$0, 259, new Object[]{Boolean.valueOf(false)});
        }
    }
}
