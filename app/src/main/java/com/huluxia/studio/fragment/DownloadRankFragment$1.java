package com.huluxia.studio.fragment;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.framework.R;
import hlx.ui.a;

class DownloadRankFragment$1 implements OnClickListener {
    final /* synthetic */ DownloadRankFragment aGr;

    DownloadRankFragment$1(DownloadRankFragment this$0) {
        this.aGr = this$0;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sys_header_back) {
            ((Activity) DownloadRankFragment.a(this.aGr)).finish();
        } else if (id == R.id.sys_header_right) {
            a.bX(DownloadRankFragment.a(this.aGr));
        }
    }
}
