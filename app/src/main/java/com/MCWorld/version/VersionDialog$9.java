package com.MCWorld.version;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.t;

class VersionDialog$9 implements OnClickListener {
    final /* synthetic */ VersionDialog bow;
    final /* synthetic */ e box;

    VersionDialog$9(VersionDialog this$0, e eVar) {
        this.bow = this$0;
        this.box = eVar;
    }

    public void onClick(View view) {
        this.bow.dismissAllowingStateLoss();
        t.q(VersionDialog.d(this.bow).getActivity(), this.box.url);
    }
}
