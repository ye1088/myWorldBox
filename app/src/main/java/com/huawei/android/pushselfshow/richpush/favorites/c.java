package com.huawei.android.pushselfshow.richpush.favorites;

import com.huawei.android.pushagent.c.a.e;

class c implements Runnable {
    final /* synthetic */ FavoritesActivity a;

    c(FavoritesActivity favoritesActivity) {
        this.a = favoritesActivity;
    }

    public void run() {
        e.a("PushSelfShowLog", "onCreate mThread run");
        if (!FavoritesActivity.a(this.a)) {
            FavoritesActivity.a(this.a, FavoritesActivity.b(this.a));
        }
        FavoritesActivity.c(this.a).b();
        this.a.a.sendEmptyMessage(1000);
    }
}
