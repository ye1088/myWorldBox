package com.MCWorld.ui.itemadapter.wood;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.map.f.a;
import com.MCWorld.k;

class WoodDownAdapter$1 implements OnClickListener {
    final /* synthetic */ a aTh;
    final /* synthetic */ WoodDownAdapter aXE;

    WoodDownAdapter$1(WoodDownAdapter this$0, a aVar) {
        this.aXE = this$0;
        this.aTh = aVar;
    }

    public void onClick(View v) {
        k.e(this.aXE.aTg, (long) this.aTh.id, this.aTh.postID);
    }
}
