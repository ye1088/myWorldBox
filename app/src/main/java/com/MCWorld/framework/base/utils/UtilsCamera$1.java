package com.MCWorld.framework.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

class UtilsCamera$1 implements OnClickListener {
    final /* synthetic */ Activity val$activity;
    final /* synthetic */ AlertDialog val$dialog;

    UtilsCamera$1(AlertDialog alertDialog, Activity activity) {
        this.val$dialog = alertDialog;
        this.val$activity = activity;
    }

    public void onClick(View arg0) {
        this.val$dialog.dismiss();
        UtilsCamera.fromCamera(this.val$activity);
    }
}
