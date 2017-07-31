package com.huluxia.framework.base.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;

class DialogManager$7 implements OnClickListener {
    final /* synthetic */ DialogManager this$0;
    final /* synthetic */ DialogManager$OkDialogListener val$l;

    DialogManager$7(DialogManager this$0, DialogManager$OkDialogListener dialogManager$OkDialogListener) {
        this.this$0 = this$0;
        this.val$l = dialogManager$OkDialogListener;
    }

    public void onClick(View v) {
        this.this$0.mDialog.dismiss();
        if (this.val$l != null) {
            this.val$l.onOk();
        }
    }
}
