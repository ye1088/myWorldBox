package com.huluxia.ui.home;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.t;

class BbsFragment$2 implements OnClickListener {
    final /* synthetic */ BbsFragment aSb;

    BbsFragment$2(BbsFragment this$0) {
        this.aSb = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.sys_header_flright_img) {
            t.i(this.aSb.getActivity());
        } else if (id == g.img_msg) {
            t.a(this.aSb.getActivity(), HTApplication.bM());
        }
    }
}
