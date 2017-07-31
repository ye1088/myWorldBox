package com.huluxia.ui.area.news;

import com.huluxia.module.news.i;
import com.huluxia.utils.aa.a;

class ResourceNewsFragment$2 implements a {
    final /* synthetic */ ResourceNewsFragment aIi;

    ResourceNewsFragment$2(ResourceNewsFragment this$0) {
        this.aIi = this$0;
    }

    public void onLoadData() {
        i.Ea().ar(ResourceNewsFragment.a(this.aIi) == null ? 0 : ResourceNewsFragment.a(this.aIi).start, 20);
    }

    public boolean shouldLoadData() {
        if (ResourceNewsFragment.a(this.aIi) == null) {
            ResourceNewsFragment.b(this.aIi).onLoadComplete();
            return false;
        } else if (ResourceNewsFragment.a(this.aIi).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
