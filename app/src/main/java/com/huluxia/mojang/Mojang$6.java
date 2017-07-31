package com.huluxia.mojang;

import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mojang.converter.EntityDataConverter;
import java.io.IOException;
import java.util.List;

class Mojang$6 implements Runnable {
    final /* synthetic */ Mojang this$0;
    final /* synthetic */ List val$result;

    Mojang$6(Mojang this$0, List list) {
        this.this$0 = this$0;
        this.val$result = list;
    }

    public void run() {
        try {
            Log.d("Mojang_Tag", "delete entity1 resutl = " + this.val$result);
            EntityDataConverter.write(this.val$result, null, this.this$0.worldItem.db);
            this.this$0.readEntityData();
            Mojang.access$100(this.this$0, 258, new Object[]{Boolean.valueOf(true)});
        } catch (IOException e) {
            HLog.error("Mojang_Tag", "asyn deleteEntity error", e, new Object[0]);
            Mojang.access$100(this.this$0, 258, new Object[]{Boolean.valueOf(false)});
        }
    }
}
