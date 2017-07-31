package com.huluxia.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;

/* compiled from: AvatarDialogManager */
class c$2 implements OnClickListener {
    final /* synthetic */ c bwo;
    final /* synthetic */ OkCancelDialogListener val$l;

    c$2(c this$0, OkCancelDialogListener okCancelDialogListener) {
        this.bwo = this$0;
        this.val$l = okCancelDialogListener;
    }

    public void onClick(View v) {
        c.b(this.bwo).dismiss();
        if (this.val$l != null) {
            this.val$l.onCancel();
        }
    }
}
