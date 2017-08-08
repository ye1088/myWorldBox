package com.MCWorld.framework.base.widget.dialog;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.MCWorld.framework.base.utils.ImeUtil;

class DialogManager$22 implements OnClickListener {
    final /* synthetic */ DialogManager this$0;
    final /* synthetic */ boolean val$autoHideIme;
    final /* synthetic */ EditText val$etInput;
    final /* synthetic */ DialogManager$InputDialogListener val$listener;

    DialogManager$22(DialogManager this$0, DialogManager$InputDialogListener dialogManager$InputDialogListener, boolean z, EditText editText) {
        this.this$0 = this$0;
        this.val$listener = dialogManager$InputDialogListener;
        this.val$autoHideIme = z;
        this.val$etInput = editText;
    }

    public void onClick(View v) {
        if (this.val$listener != null) {
            this.val$listener.cancel();
        }
        if (this.val$autoHideIme) {
            ImeUtil.hideIME((Activity) DialogManager.access$000(this.this$0), this.val$etInput);
        }
        this.this$0.dismissDialog();
    }
}
