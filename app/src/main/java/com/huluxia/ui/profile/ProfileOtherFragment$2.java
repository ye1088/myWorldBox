package com.huluxia.ui.profile;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.module.profile.g;

class ProfileOtherFragment$2 implements OnClickListener {
    final /* synthetic */ Dialog aJN;
    final /* synthetic */ long axx;
    final /* synthetic */ ProfileOtherFragment bgR;

    ProfileOtherFragment$2(ProfileOtherFragment this$0, Dialog dialog, long j) {
        this.bgR = this$0;
        this.aJN = dialog;
        this.axx = j;
    }

    public void onClick(View arg0) {
        this.aJN.dismiss();
        g.Eb().aT(this.axx);
        ProfileOtherFragment.a(this.bgR, true);
    }
}
