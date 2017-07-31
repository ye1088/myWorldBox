package com.huluxia;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.bbs.b.g;

protected class DownloadDialog$a implements OnClickListener {
    final /* synthetic */ DownloadDialog eZ;

    protected DownloadDialog$a(DownloadDialog this$0) {
        this.eZ = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.tv_cancel) {
            this.eZ.dismissAllowingStateLoss();
            DownloadDialog.a(this.eZ);
        } else if (id == g.dlg_download_tv_close) {
            this.eZ.dismissAllowingStateLoss();
        }
    }
}
