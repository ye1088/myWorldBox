package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.huawei.android.pushselfshow.utils.d;

class FavoritesActivity$a implements OnClickListener {
    final /* synthetic */ FavoritesActivity a;
    private Context b;

    private FavoritesActivity$a(FavoritesActivity favoritesActivity, Context context) {
        this.a = favoritesActivity;
        this.b = context;
    }

    public void onClick(View view) {
        if (FavoritesActivity.a(this.a)) {
            FavoritesActivity.d(this.a);
            return;
        }
        FavoritesActivity.e(this.a).setVisibility(4);
        FavoritesActivity.f(this.a).setVisibility(8);
        FavoritesActivity.f(this.a).setText("");
        FavoritesActivity.g(this.a).setText(this.b.getString(d.a(this.b, "hwpush_msg_collect")));
        FavoritesActivity.h(this.a).c();
        FavoritesActivity.c(this.a).a(true);
        FavoritesActivity.i(this.a).setOnItemClickListener(new FavoritesActivity$e(this.a, null));
        FavoritesActivity.i(this.a).setLongClickable(true);
    }
}
