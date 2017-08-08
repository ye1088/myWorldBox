package com.MCWorld.ui.loginAndRegister;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
.9;
import com.MCWorld.widget.dialog.d;

class RegisterActivity$9$1 implements OnClickListener {
    final /* synthetic */ d aYR;
    final /* synthetic */ TextView aYS;
    final /* synthetic */ 9 aYT;
    final /* synthetic */ AlertDialog val$dialog;

    RegisterActivity$9$1(9 this$1, AlertDialog alertDialog, d dVar, TextView textView) {
        this.aYT = this$1;
        this.val$dialog = alertDialog;
        this.aYR = dVar;
        this.aYS = textView;
    }

    public void onClick(View arg0) {
        this.val$dialog.dismiss();
        RegisterActivity.b(this.aYT.aYN, String.valueOf(this.aYR.getDate().getTime()));
        this.aYS.setText(RegisterActivity.g(this.aYT.aYN).format(this.aYR.getDate()));
    }
}
