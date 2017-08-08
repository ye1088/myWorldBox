package com.MCWorld.widget.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/* compiled from: HPopupWindow */
class j$1 implements OnItemClickListener {
    final /* synthetic */ j bxn;

    j$1(j this$0) {
        this.bxn = this$0;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (j.a(this.bxn) != null) {
            j.a(this.bxn).dismiss();
        }
        if (j.b(this.bxn) != null) {
            j.b(this.bxn).kz(position);
        }
    }
}
