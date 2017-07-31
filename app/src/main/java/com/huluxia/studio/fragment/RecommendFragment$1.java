package com.huluxia.studio.fragment;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.j;
import com.huluxia.framework.R;
import com.huluxia.t;

class RecommendFragment$1 implements OnClickListener {
    final /* synthetic */ RecommendFragment aGD;

    RecommendFragment$1(RecommendFragment this$0) {
        this.aGD = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sys_header_back) {
            ((Activity) RecommendFragment.a(this.aGD)).finish();
        } else if (id != R.id.sys_header_right) {
        } else {
            if (j.ep().ey()) {
                this.aGD.aGB.execute();
            } else {
                t.an(RecommendFragment.a(this.aGD));
            }
        }
    }
}
