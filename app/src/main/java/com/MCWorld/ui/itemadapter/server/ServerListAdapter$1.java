package com.MCWorld.ui.itemadapter.server;

import com.MCWorld.data.server.a.a;
import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.mcinterface.h;
import hlx.launch.game.d;

class ServerListAdapter$1 implements OkCancelDialogListener {
    final /* synthetic */ a aWp;
    final /* synthetic */ ServerListAdapter aWq;

    ServerListAdapter$1(ServerListAdapter this$0, a aVar) {
        this.aWq = this$0;
        this.aWp = aVar;
    }

    public void onCancel() {
        ServerListAdapter.a(this.aWq).dismissDialog();
    }

    public void onOk() {
        h.bP(true);
        d.b(this.aWq.aTg, this.aWp);
    }
}
