package com.huluxia.framework.base.widget.dialog;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.huluxia.framework.base.utils.ImeUtil;

class DialogManager$21 implements OnClickListener {
    final /* synthetic */ DialogManager this$0;
    final /* synthetic */ boolean val$autoHideIme;
    final /* synthetic */ EditText val$etInput;
    final /* synthetic */ DialogManager$InputDialogListener val$listener;

    DialogManager$21(DialogManager this$0, DialogManager$InputDialogListener dialogManager$InputDialogListener, EditText editText, boolean z) {
        this.this$0 = this$0;
        this.val$listener = dialogManager$InputDialogListener;
        this.val$etInput = editText;
        this.val$autoHideIme = z;
    }

    public void onClick(View v) {
        if (this.val$listener != null) {
            this.val$listener.confirm(this.val$etInput.getText().toString());
        }
        if (this.val$autoHideIme) {
            ImeUtil.hideIME((Activity) DialogManager.access$000(this.this$0), this.val$etInput);
        }
        this.this$0.dismissDialog();
    }
}
