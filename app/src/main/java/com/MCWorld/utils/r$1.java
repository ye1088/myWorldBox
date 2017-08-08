package com.MCWorld.utils;

import android.app.Activity;
import com.MCWorld.framework.base.utils.UtilsCamera;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;

/* compiled from: UtilsCameraHT */
class r$1 implements CommonMenuDialogListener {
    final /* synthetic */ Activity val$activity;

    r$1(Activity activity) {
        this.val$activity = activity;
    }

    public void pressMenuById(int inIndex, Object inItem) {
        if (inIndex == 0) {
            UtilsCamera.fromCamera(this.val$activity);
        } else if (inIndex == 1) {
            UtilsCamera.fromAlbums(this.val$activity);
        }
        r.KL().dismissDialog();
    }
}
