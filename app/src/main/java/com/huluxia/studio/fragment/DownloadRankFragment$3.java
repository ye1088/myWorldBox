package com.huluxia.studio.fragment;

import com.huluxia.utils.c.a;

class DownloadRankFragment$3 implements a {
    final /* synthetic */ DownloadRankFragment aGr;

    DownloadRankFragment$3(DownloadRankFragment this$0) {
        this.aGr = this$0;
    }

    public void onLoadData() {
        if (this.aGr.aGp != null) {
            DownloadRankFragment.c(this.aGr);
        }
    }

    public boolean shouldLoadData() {
        if (this.aGr.aGp == null) {
            DownloadRankFragment.d(this.aGr).onLoadComplete();
            return false;
        } else if (this.aGr.aGp.more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
