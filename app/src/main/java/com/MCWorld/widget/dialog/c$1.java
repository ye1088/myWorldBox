package com.MCWorld.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;

/* compiled from: AvatarDialogManager */
class c$1 implements OnClickListener {
    final /* synthetic */ c bwo;
    final /* synthetic */ OkCancelDialogListener val$l;

    c$1(c this$0, OkCancelDialogListener okCancelDialogListener) {
        this.bwo = this$0;
        this.val$l = okCancelDialogListener;
    }

    public void onClick(View v) {
        c.a(this.bwo).dismiss();
        if (this.val$l != null) {
            this.val$l.onOk();
        }
    }
}
