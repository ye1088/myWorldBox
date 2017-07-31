package com.huluxia.widget.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/* compiled from: AppInfoDialog */
class a$2 implements OnItemClickListener {
    final /* synthetic */ a bwh;

    a$2(a this$0) {
        this.bwh = this$0;
    }

    public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
        a.c(this.bwh).a((a$b) a.b(this.bwh).getItem(pos));
        this.bwh.dismiss();
    }
}
