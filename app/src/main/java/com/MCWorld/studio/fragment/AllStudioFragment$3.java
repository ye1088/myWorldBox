package com.MCWorld.studio.fragment;

import com.MCWorld.utils.c.a;

class AllStudioFragment$3 implements a {
    final /* synthetic */ AllStudioFragment aGh;

    AllStudioFragment$3(AllStudioFragment this$0) {
        this.aGh = this$0;
    }

    public void onLoadData() {
        if (this.aGh.aGf != null) {
            AllStudioFragment.c(this.aGh);
        }
    }

    public boolean shouldLoadData() {
        if (this.aGh.aGf == null) {
            AllStudioFragment.d(this.aGh).onLoadComplete();
            return false;
        } else if (this.aGh.aGf.more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
