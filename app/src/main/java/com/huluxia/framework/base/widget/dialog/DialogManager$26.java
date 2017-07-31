package com.huluxia.framework.base.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;

class DialogManager$26 implements OnClickListener {
    final /* synthetic */ DialogManager this$0;
    final /* synthetic */ DialogManager$SelectGenderDialogListener val$l;

    DialogManager$26(DialogManager this$0, DialogManager$SelectGenderDialogListener dialogManager$SelectGenderDialogListener) {
        this.this$0 = this$0;
        this.val$l = dialogManager$SelectGenderDialogListener;
    }

    public void onClick(View v) {
        if (this.val$l != null) {
            this.val$l.onCancel();
        }
        this.this$0.mDialog.dismiss();
    }
}
