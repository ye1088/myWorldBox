package com.MCWorld.ui.mctool;

import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.mcinterface.h;
.7;
import hlx.launch.game.d;

class ServerDetailActivity$7$1 implements OkCancelDialogListener {
    final /* synthetic */ 7 bcc;

    ServerDetailActivity$7$1(7 this$1) {
        this.bcc = this$1;
    }

    public void onCancel() {
        ServerDetailActivity.c(this.bcc.bca).dismissDialog();
    }

    public void onOk() {
        h.bP(true);
        d.b(this.bcc.bca, ServerDetailActivity.a(this.bcc.bca));
    }
}
