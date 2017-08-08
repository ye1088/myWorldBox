package com.MCWorld.studio.fragment;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.R;

class AllStudioFragment$1 implements OnClickListener {
    final /* synthetic */ AllStudioFragment aGh;

    AllStudioFragment$1(AllStudioFragment this$0) {
        this.aGh = this$0;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.sys_header_back) {
            ((Activity) AllStudioFragment.a(this.aGh)).finish();
        }
    }
}
