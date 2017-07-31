package com.huluxia.framework.base.utils;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

class UtilsCamera$3 implements OnClickListener {
    final /* synthetic */ AlertDialog val$dialog;

    UtilsCamera$3(AlertDialog alertDialog) {
        this.val$dialog = alertDialog;
    }

    public void onClick(View v) {
        this.val$dialog.dismiss();
    }
}
