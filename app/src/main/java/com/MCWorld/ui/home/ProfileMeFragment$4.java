package com.MCWorld.ui.home;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.g;
import com.MCWorld.t;

class ProfileMeFragment$4 implements OnClickListener {
    final /* synthetic */ ProfileMeFragment aSu;

    ProfileMeFragment$4(ProfileMeFragment this$0) {
        this.aSu = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.iv_msg) {
            t.a(this.aSu.getActivity(), HTApplication.bM());
            ProfileMeFragment.a(this.aSu, false);
        } else if (id == g.iv_back) {
            this.aSu.getActivity().finish();
        }
    }
}
