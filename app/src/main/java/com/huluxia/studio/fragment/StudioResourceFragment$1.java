package com.huluxia.studio.fragment;

import com.huluxia.utils.c.a;

class StudioResourceFragment$1 implements a {
    final /* synthetic */ StudioResourceFragment aGP;

    StudioResourceFragment$1(StudioResourceFragment this$0) {
        this.aGP = this$0;
    }

    public void onLoadData() {
        if (StudioResourceFragment.a(this.aGP) != null) {
            StudioResourceFragment.b(this.aGP);
        }
    }

    public boolean shouldLoadData() {
        if (StudioResourceFragment.a(this.aGP) == null || StudioResourceFragment.a(this.aGP).more <= 0) {
            return false;
        }
        return true;
    }
}
