package com.MCWorld.ui.maptool;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/* compiled from: MapListDialog */
class a$1 implements OnItemClickListener {
    final /* synthetic */ a aZL;

    a$1(a this$0) {
        this.aZL = this$0;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        a.b(this.aZL).m(((a$a) a.a(this.aZL).get(position)).name, ((a$a) a.a(this.aZL).get(position)).aZM, ((a$a) a.a(this.aZL).get(position)).size);
        this.aZL.dismiss();
    }
}
