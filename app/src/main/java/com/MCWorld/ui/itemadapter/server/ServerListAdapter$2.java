package com.MCWorld.ui.itemadapter.server;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.server.a.a;
import com.MCWorld.k;

class ServerListAdapter$2 implements OnClickListener {
    final /* synthetic */ a aWp;
    final /* synthetic */ ServerListAdapter aWq;

    ServerListAdapter$2(ServerListAdapter this$0, a aVar) {
        this.aWq = this$0;
        this.aWp = aVar;
    }

    public void onClick(View v) {
        k.a(this.aWq.aTg, this.aWp);
    }
}
