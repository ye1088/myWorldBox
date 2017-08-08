package com.MCWorld.ui.itemadapter.js;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.map.f.a;
import com.MCWorld.k;

class JsDownAdapter$1 implements OnClickListener {
    final /* synthetic */ a aTh;
    final /* synthetic */ JsDownAdapter aTi;

    JsDownAdapter$1(JsDownAdapter this$0, a aVar) {
        this.aTi = this$0;
        this.aTh = aVar;
    }

    public void onClick(View v) {
        k.c(this.aTi.aTg, (long) this.aTh.id, this.aTh.postID);
    }
}
