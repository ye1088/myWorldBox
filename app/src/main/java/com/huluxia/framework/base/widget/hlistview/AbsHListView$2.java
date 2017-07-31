package com.huluxia.framework.base.widget.hlistview;

class AbsHListView$2 implements Runnable {
    final /* synthetic */ AbsHListView this$0;

    AbsHListView$2(AbsHListView this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (this.this$0.mCachingStarted) {
            AbsHListView absHListView = this.this$0;
            this.this$0.mCachingActive = false;
            absHListView.mCachingStarted = false;
            AbsHListView.access$2000(this.this$0, false);
            if ((this.this$0.getPersistentDrawingCache() & 2) == 0) {
                AbsHListView.access$2100(this.this$0, false);
            }
            if (!this.this$0.isAlwaysDrawnWithCacheEnabled()) {
                this.this$0.invalidate();
            }
        }
    }
}
