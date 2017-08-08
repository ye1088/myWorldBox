package com.MCWorld.ui.profile;

import android.view.View;
import android.view.View.OnClickListener;

class ProfileOtherFragment$9 implements OnClickListener {
    final /* synthetic */ ProfileOtherFragment bgR;

    ProfileOtherFragment$9(ProfileOtherFragment this$0) {
        this.bgR = this$0;
    }

    public void onClick(View arg0) {
        if (ProfileOtherFragment.f(this.bgR) != null) {
            boolean newFollow = !ProfileOtherFragment.g(this.bgR);
            if (newFollow) {
                ProfileOtherFragment.f(this.bgR).K(newFollow);
                ProfileOtherFragment.f(this.bgR).execute();
                return;
            }
            ProfileOtherFragment.h(this.bgR);
        }
    }
}
