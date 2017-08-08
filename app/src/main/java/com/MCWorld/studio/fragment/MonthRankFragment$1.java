package com.MCWorld.studio.fragment;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.R;
import hlx.ui.a;

class MonthRankFragment$1 implements OnClickListener {
    final /* synthetic */ MonthRankFragment aGv;

    MonthRankFragment$1(MonthRankFragment this$0) {
        this.aGv = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sys_header_back) {
            ((Activity) MonthRankFragment.a(this.aGv)).finish();
        } else if (id == R.id.sys_header_right) {
            a.bX(MonthRankFragment.a(this.aGv));
        }
    }
}
