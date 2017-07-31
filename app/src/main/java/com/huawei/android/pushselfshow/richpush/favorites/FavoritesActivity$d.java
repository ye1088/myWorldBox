package com.huawei.android.pushselfshow.richpush.favorites;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import java.util.HashSet;
import java.util.Set;

class FavoritesActivity$d implements OnItemLongClickListener {
    final /* synthetic */ FavoritesActivity a;

    private FavoritesActivity$d(FavoritesActivity favoritesActivity) {
        this.a = favoritesActivity;
    }

    public boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
        FavoritesActivity.k(this.a);
        FavoritesActivity.h(this.a).b(FavoritesActivity.j(this.a));
        Set hashSet = new HashSet();
        hashSet.add(Integer.valueOf(i));
        FavoritesActivity.c(this.a).a(false, hashSet);
        FavoritesActivity.f(this.a).setVisibility(0);
        FavoritesActivity.f(this.a).setText("1");
        return true;
    }
}
