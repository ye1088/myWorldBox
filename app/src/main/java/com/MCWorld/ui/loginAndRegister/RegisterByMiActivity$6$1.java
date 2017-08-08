package com.MCWorld.ui.loginAndRegister;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
.6;
import com.MCWorld.widget.dialog.d;

class RegisterByMiActivity$6$1 implements OnClickListener {
    final /* synthetic */ d aYR;
    final /* synthetic */ TextView aYS;
    final /* synthetic */ 6 aZg;
    final /* synthetic */ AlertDialog val$dialog;

    RegisterByMiActivity$6$1(6 this$1, AlertDialog alertDialog, d dVar, TextView textView) {
        this.aZg = this$1;
        this.val$dialog = alertDialog;
        this.aYR = dVar;
        this.aYS = textView;
    }

    public void onClick(View arg0) {
        this.val$dialog.dismiss();
        RegisterByMiActivity.c(this.aZg.aZf).setBirthday(this.aYR.getDate().getTime());
        this.aYS.setText(RegisterByMiActivity.d(this.aZg.aZf).format(this.aYR.getDate()));
    }
}
