package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

class FavoritesActivity$f implements OnClickListener {
    final /* synthetic */ FavoritesActivity a;
    private Context b;

    private FavoritesActivity$f(FavoritesActivity favoritesActivity, Context context) {
        this.a = favoritesActivity;
        this.b = context;
    }

    public void onClick(View view) {
        boolean z;
        for (f a : FavoritesActivity.c(this.a).a()) {
            if (!a.a()) {
                z = true;
                break;
            }
        }
        z = false;
        FavoritesActivity.c(this.a).a(z, null);
        if (z) {
            FavoritesActivity.f(this.a).setVisibility(0);
            FavoritesActivity.f(this.a).setText(String.valueOf(FavoritesActivity.c(this.a).getCount()));
            FavoritesActivity.h(this.a).b(FavoritesActivity.j(this.a));
            FavoritesActivity.a(this.a, this.b, true);
            return;
        }
        FavoritesActivity.f(this.a).setVisibility(8);
        FavoritesActivity.f(this.a).setText("");
        FavoritesActivity.h(this.a).a(FavoritesActivity.j(this.a));
        FavoritesActivity.a(this.a, this.b, false);
    }
}
