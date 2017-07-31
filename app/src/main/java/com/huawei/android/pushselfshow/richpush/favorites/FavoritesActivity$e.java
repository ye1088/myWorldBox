package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

class FavoritesActivity$e implements OnItemClickListener {
    final /* synthetic */ FavoritesActivity a;

    private FavoritesActivity$e(FavoritesActivity favoritesActivity) {
        this.a = favoritesActivity;
    }

    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        f a = FavoritesActivity.c(this.a).a(i);
        Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
        intent.putExtra("type", a.b().D);
        intent.putExtra("selfshow_info", a.b().c());
        intent.putExtra("selfshow_token", a.b().d());
        intent.putExtra("selfshow_from_list", true);
        intent.setFlags(268468240);
        intent.setPackage(FavoritesActivity.b(this.a).getPackageName());
        FavoritesActivity.b(this.a).finish();
        FavoritesActivity.b(this.a).startActivity(intent);
    }
}
