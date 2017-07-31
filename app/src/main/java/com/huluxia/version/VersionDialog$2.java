package com.huluxia.version;

import android.view.View;
import android.view.View.OnClickListener;

class VersionDialog$2 implements OnClickListener {
    final /* synthetic */ VersionDialog bow;

    VersionDialog$2(VersionDialog this$0) {
        this.bow = this$0;
    }

    public void onClick(View view) {
        this.bow.dismissAllowingStateLoss();
    }
}
