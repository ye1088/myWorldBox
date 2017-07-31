package com.handmark.pulltorefresh.library;

class PullToRefreshBase$2 implements Runnable {
    final /* synthetic */ PullToRefreshBase this$0;

    PullToRefreshBase$2(PullToRefreshBase this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        this.this$0.requestLayout();
    }
}
