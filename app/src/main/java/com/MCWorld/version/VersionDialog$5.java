package com.MCWorld.version;

import android.view.View;
import android.view.View.OnClickListener;

class VersionDialog$5 implements OnClickListener {
    final /* synthetic */ VersionDialog bow;

    VersionDialog$5(VersionDialog this$0) {
        this.bow = this$0;
    }

    public void onClick(View v) {
        e info = (e) v.getTag();
        if (info != null) {
            VersionDialog.f(this.bow, info);
        }
    }
}
