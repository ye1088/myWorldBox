package com.huawei.android.pushselfshow.richpush.favorites;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a;
import com.huawei.android.pushselfshow.utils.d;

class FavoritesActivity$b implements OnClickListener {
    final /* synthetic */ FavoritesActivity a;
    private Context b;

    private FavoritesActivity$b(FavoritesActivity favoritesActivity, Context context) {
        this.a = favoritesActivity;
        this.b = context;
    }

    public void onClick(View view) {
        CharSequence quantityString;
        String str = "";
        try {
            quantityString = this.b.getResources().getQuantityString(d.b(this.b, "hwpush_delete_tip"), FavoritesActivity.l(this.a));
        } catch (Throwable e) {
            Throwable th = e;
            quantityString = "";
            e.c("PushSelfShowLog", th.toString(), th);
        }
        FavoritesActivity.a(this.a, new Builder(this.b, a.h(this.b)).setTitle(quantityString).setPositiveButton(d.a(this.b, "hwpush_delete"), new d(this)).setNegativeButton(d.a(this.b, "hwpush_cancel"), null).create());
        FavoritesActivity.m(this.a).show();
        FavoritesActivity.m(this.a).getButton(-1).setTextColor(Color.parseColor("#ffd43e25"));
    }
}
