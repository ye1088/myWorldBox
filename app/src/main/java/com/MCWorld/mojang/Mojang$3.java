package com.MCWorld.mojang;

import android.util.Log;
import com.MCWorld.framework.base.log.HLog;
import java.io.IOException;

class Mojang$3 implements Runnable {
    final /* synthetic */ Mojang this$0;

    Mojang$3(Mojang this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        try {
            Log.d("Mojang_Tag", "asynReadEntityData2");
            this.this$0.readEntityData();
            Mojang.access$100(this.this$0, 256, new Object[]{Boolean.valueOf(true)});
        } catch (IOException e) {
            HLog.error("Mojang_Tag", "aysnReadEntityData error", e, new Object[0]);
            Mojang.access$100(this.this$0, 256, new Object[]{Boolean.valueOf(false)});
        }
    }
}
