package com.huluxia.ui.itemadapter.map;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.map.f.a;
import com.huluxia.k;

class DownAdapter$1 implements OnClickListener {
    final /* synthetic */ a aTh;
    final /* synthetic */ DownAdapter aTp;

    DownAdapter$1(DownAdapter this$0, a aVar) {
        this.aTp = this$0;
        this.aTh = aVar;
    }

    public void onClick(View v) {
        k.a(DownAdapter.a(this.aTp), (long) this.aTh.id, this.aTh.postID, this.aTp.aMW);
    }
}
