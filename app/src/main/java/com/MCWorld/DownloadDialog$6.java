package com.MCWorld;

import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.module.GameInfo;
import java.io.File;

class DownloadDialog$6 implements OkCancelDialogListener {
    final /* synthetic */ DownloadDialog eZ;
    final /* synthetic */ File fa;
    final /* synthetic */ GameInfo fb;

    DownloadDialog$6(DownloadDialog this$0, File file, GameInfo gameInfo) {
        this.eZ = this$0;
        this.fa = file;
        this.fb = gameInfo;
    }

    public void onCancel() {
        this.eZ.dismissAllowingStateLoss();
    }

    public void onOk() {
        DownloadDialog.b(this.eZ, this.fa, this.fb);
        this.eZ.dismissAllowingStateLoss();
    }
}
