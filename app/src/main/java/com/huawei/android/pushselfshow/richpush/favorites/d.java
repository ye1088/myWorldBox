package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class d implements OnClickListener {
    final /* synthetic */ FavoritesActivity$b a;

    d(FavoritesActivity$b favoritesActivity$b) {
        this.a = favoritesActivity$b;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        FavoritesActivity.h(this.a.a).a(FavoritesActivity.j(this.a.a));
        new Thread(new e(this)).start();
    }
}
