package com.huluxia.mojang;

import com.huluxia.framework.base.notification.EventNotifyCenter;

class Mojang$4 implements Runnable {
    final /* synthetic */ Mojang this$0;
    final /* synthetic */ int val$msg;
    final /* synthetic */ Object[] val$objects;

    Mojang$4(Mojang this$0, int i, Object[] objArr) {
        this.this$0 = this$0;
        this.val$msg = i;
        this.val$objects = objArr;
    }

    public void run() {
        EventNotifyCenter.notifyEvent(MojangMessage.class, this.val$msg, this.val$objects);
    }
}
