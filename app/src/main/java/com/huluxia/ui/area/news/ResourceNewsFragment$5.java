package com.huluxia.ui.area.news;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.t;

class ResourceNewsFragment$5 implements OnClickListener {
    final /* synthetic */ ResourceNewsFragment aIi;

    ResourceNewsFragment$5(ResourceNewsFragment this$0) {
        this.aIi = this$0;
    }

    public void onClick(View v) {
        if (v.getId() == g.img_msg) {
            t.a(this.aIi.getActivity(), HTApplication.bM());
        }
    }
}
