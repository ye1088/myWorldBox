package com.huluxia.ui.area.news;

import com.huluxia.framework.base.utils.SpeedScrollListener;
import com.huluxia.l;

class ResourceNewsFragment$3 extends SpeedScrollListener {
    final /* synthetic */ ResourceNewsFragment aIi;

    ResourceNewsFragment$3(ResourceNewsFragment this$0) {
        this.aIi = this$0;
    }

    public void onFling() {
        l.cb().getImageLoader().pauseTag(ResourceNewsFragment.c(this.aIi));
    }

    public void onFlingStop() {
        l.cb().getImageLoader().resumeTag(ResourceNewsFragment.c(this.aIi));
    }
}
