package com.huluxia.ui.profile;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.t;

class ProfileOtherFragment$5 implements OnClickListener {
    final /* synthetic */ ProfileOtherFragment bgR;

    ProfileOtherFragment$5(ProfileOtherFragment this$0) {
        this.bgR = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.iv_back) {
            this.bgR.getActivity().finish();
        } else if (id == g.iv_msg) {
            t.a(this.bgR.getActivity(), HTApplication.bM());
        }
    }
}
