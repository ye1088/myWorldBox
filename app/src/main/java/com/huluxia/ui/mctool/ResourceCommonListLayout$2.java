package com.huluxia.ui.mctool;

import com.huluxia.utils.c.a;

class ResourceCommonListLayout$2 implements a {
    final /* synthetic */ ResourceCommonListLayout bbK;

    ResourceCommonListLayout$2(ResourceCommonListLayout this$0) {
        this.bbK = this$0;
    }

    public void onLoadData() {
        if (ResourceCommonListLayout.b(this.bbK) != null) {
            ResourceCommonListLayout.a(this.bbK).Z(this.bbK.bbI, ResourceCommonListLayout.b(this.bbK).start, 20);
        }
    }

    public boolean shouldLoadData() {
        if (ResourceCommonListLayout.b(this.bbK) == null) {
            ResourceCommonListLayout.c(this.bbK).onLoadComplete();
            return false;
        } else if (ResourceCommonListLayout.b(this.bbK).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
