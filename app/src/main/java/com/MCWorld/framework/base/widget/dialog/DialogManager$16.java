package com.MCWorld.framework.base.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;

class DialogManager$16 implements OnClickListener {
    final /* synthetic */ DialogManager this$0;
    final /* synthetic */ OkCancelDialogListener val$l;

    DialogManager$16(DialogManager this$0, OkCancelDialogListener okCancelDialogListener) {
        this.this$0 = this$0;
        this.val$l = okCancelDialogListener;
    }

    public void onClick(View v) {
        if (this.val$l != null) {
            this.val$l.onOk();
        }
        this.this$0.mDialog.dismiss();
    }
}
