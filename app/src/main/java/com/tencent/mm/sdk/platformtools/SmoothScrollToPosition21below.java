package com.tencent.mm.sdk.platformtools;

import android.widget.ListView;
import com.tencent.mm.sdk.platformtools.BackwardSupportUtil.SmoothScrollFactory.IScroll;

class SmoothScrollToPosition21below implements IScroll {
    SmoothScrollToPosition21below() {
    }

    public void doScroll(ListView listView) {
        listView.setSelection(0);
    }

    public void doScroll(ListView listView, int i) {
        listView.setSelection(i);
    }
}
