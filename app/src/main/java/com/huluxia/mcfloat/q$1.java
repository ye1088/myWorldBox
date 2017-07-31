package com.huluxia.mcfloat;

import android.os.Handler;
import android.os.Message;
import com.huluxia.k;

/* compiled from: FloatManager */
class q$1 extends Handler {
    final /* synthetic */ q VV;

    q$1(q this$0) {
        this.VV = this$0;
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 256:
                dI(msg.arg1);
                return;
            default:
                return;
        }
    }

    private void dI(int type) {
        if (type == 16) {
            q.a(this.VV).aI(false);
            q.b(this.VV).aI(false);
        } else if (type == 17) {
            if (q.b(this.VV).bT()) {
                q.a(this.VV).aI(false);
                q.b(this.VV).aI(true);
                return;
            }
            k.l(q.c(this.VV), "请进入游戏后再开启悬浮窗");
        } else if (type == 18) {
            q.a(this.VV).aI(true);
            q.b(this.VV).aI(false);
        }
    }
}
