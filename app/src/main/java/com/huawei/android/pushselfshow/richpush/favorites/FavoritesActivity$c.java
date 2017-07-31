package com.huawei.android.pushselfshow.richpush.favorites;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import com.huawei.android.pushselfshow.utils.d;
import java.util.List;

class FavoritesActivity$c implements OnItemClickListener {
    final /* synthetic */ FavoritesActivity a;
    private Context b;

    private FavoritesActivity$c(FavoritesActivity favoritesActivity, Context context) {
        this.a = favoritesActivity;
        this.b = context;
    }

    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        CheckBox checkBox = (CheckBox) view.findViewById(d.d(this.b, "hwpush_delCheck"));
        f a = FavoritesActivity.c(this.a).a(i);
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
            a.a(false);
        } else {
            checkBox.setChecked(true);
            a.a(true);
        }
        FavoritesActivity.c(this.a).a(i, a);
        List<f> a2 = FavoritesActivity.c(this.a).a();
        int i2 = 0;
        for (f a3 : a2) {
            i2 = a3.a() ? i2 + 1 : i2;
        }
        if (i2 > 0) {
            FavoritesActivity.f(this.a).setVisibility(0);
            FavoritesActivity.f(this.a).setText(String.valueOf(i2));
            FavoritesActivity.h(this.a).b(FavoritesActivity.j(this.a));
            if (i2 == a2.size()) {
                FavoritesActivity.a(this.a, this.b, true);
                return;
            } else {
                FavoritesActivity.a(this.a, this.b, false);
                return;
            }
        }
        FavoritesActivity.f(this.a).setVisibility(8);
        FavoritesActivity.f(this.a).setText("");
        FavoritesActivity.h(this.a).a(FavoritesActivity.j(this.a));
        FavoritesActivity.a(this.a, this.b, false);
    }
}
