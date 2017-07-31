package com.huawei.android.pushselfshow.richpush.favorites;

import com.huawei.android.pushselfshow.utils.a.c;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;

class e implements Runnable {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public void run() {
        com.huawei.android.pushagent.c.a.e.a("PushSelfShowLog", "deleteTipDialog mThread run");
        Object obj = null;
        for (f fVar : FavoritesActivity.c(this.a.a.a).a()) {
            Object obj2;
            if (fVar.a()) {
                c.a(this.a.a.b, fVar.c());
                obj2 = 1;
            } else {
                obj2 = obj;
            }
            obj = obj2;
        }
        if (obj != null) {
            if (!FavoritesActivity.a(this.a.a.a)) {
                FavoritesActivity.c(this.a.a.a).b();
            }
            this.a.a.a.a.sendEmptyMessage(ErrorCode.ERROR_HOSTAPP_UNAVAILABLE);
        }
    }
}
