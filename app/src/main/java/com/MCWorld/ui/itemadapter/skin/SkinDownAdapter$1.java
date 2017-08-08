package com.MCWorld.ui.itemadapter.skin;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.map.f.a;
import com.MCWorld.k;

class SkinDownAdapter$1 implements OnClickListener {
    final /* synthetic */ a aTh;
    final /* synthetic */ SkinDownAdapter aWx;

    SkinDownAdapter$1(SkinDownAdapter this$0, a aVar) {
        this.aWx = this$0;
        this.aTh = aVar;
    }

    public void onClick(View v) {
        k.d(this.aWx.aTg, (long) this.aTh.id, this.aTh.postID);
    }
}
