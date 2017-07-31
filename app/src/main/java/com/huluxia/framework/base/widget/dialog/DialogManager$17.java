package com.huluxia.framework.base.widget.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;

class DialogManager$17 implements OnClickListener {
    final /* synthetic */ DialogManager this$0;
    final /* synthetic */ OkCancelDialogListener val$l;

    DialogManager$17(DialogManager this$0, OkCancelDialogListener okCancelDialogListener) {
        this.this$0 = this$0;
        this.val$l = okCancelDialogListener;
    }

    public void onClick(View v) {
        if (this.val$l != null) {
            this.val$l.onCancel();
        }
        this.this$0.mDialog.dismiss();
    }
}
