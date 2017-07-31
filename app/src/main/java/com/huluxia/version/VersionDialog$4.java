package com.huluxia.version;

import android.view.View;
import android.view.View.OnClickListener;

class VersionDialog$4 implements OnClickListener {
    final /* synthetic */ VersionDialog bow;

    VersionDialog$4(VersionDialog this$0) {
        this.bow = this$0;
    }

    public void onClick(View v) {
        e info = (e) v.getTag();
        if (info != null) {
            VersionDialog.i(this.bow, info);
        }
    }
}
