package com.MCWorld.ui.itemadapter.server;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.server.a.a;
import com.MCWorld.r;

class ServerListAdapter$3 implements OnClickListener {
    final /* synthetic */ a aWp;
    final /* synthetic */ ServerListAdapter aWq;

    ServerListAdapter$3(ServerListAdapter this$0, a aVar) {
        this.aWq = this$0;
        this.aWp = aVar;
    }

    public void onClick(View v) {
        r.ck().j(hlx.data.tongji.a.bMS, String.valueOf(this.aWp.id));
        ServerListAdapter.a(this.aWq, this.aWp);
    }
}
