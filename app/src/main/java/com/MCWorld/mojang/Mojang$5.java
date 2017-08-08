package com.MCWorld.mojang;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mojang.converter.EntityDataConverter;
import com.MCWorld.mojang.entity.Entity;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class Mojang$5 implements Runnable {
    final /* synthetic */ Mojang this$0;
    final /* synthetic */ Map val$entityTypeIntegerMap;
    final /* synthetic */ List val$result;

    Mojang$5(Mojang this$0, List list, Map map) {
        this.this$0 = this$0;
        this.val$result = list;
        this.val$entityTypeIntegerMap = map;
    }

    public void run() {
        try {
            this.this$0.reloadLevelData();
            for (Entity entity : this.val$result) {
                if (this.val$entityTypeIntegerMap.keySet().contains(entity)) {
                    entity.setLocation(Mojang.access$200(this.this$0).getPlayer().getPos());
                }
            }
            EntityDataConverter.write(this.val$result, null, this.this$0.worldItem.db);
            this.this$0.readEntityData();
            Mojang.access$100(this.this$0, 257, new Object[]{Boolean.valueOf(true)});
        } catch (IOException e) {
            HLog.error("Mojang_Tag", "batchAddEntity asyn error", e, new Object[0]);
            Mojang.access$100(this.this$0, 257, new Object[]{Boolean.valueOf(false)});
        }
    }
}
