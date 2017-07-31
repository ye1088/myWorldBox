package com.huluxia.mojang;

import com.huluxia.framework.base.log.HLog;
import java.io.IOException;

class Mojang$9 implements Runnable {
    final /* synthetic */ Mojang this$0;

    Mojang$9(Mojang this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        try {
            this.this$0.readInventory();
            Mojang.access$100(this.this$0, 260, new Object[]{Boolean.valueOf(true)});
        } catch (IOException e) {
            HLog.error("Mojang_Tag", "asynReadInventory1 error", e, new Object[0]);
            Mojang.access$100(this.this$0, 260, new Object[]{Boolean.valueOf(false)});
        }
    }
}
