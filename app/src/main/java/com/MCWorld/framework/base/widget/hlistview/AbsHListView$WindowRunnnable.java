package com.MCWorld.framework.base.widget.hlistview;

class AbsHListView$WindowRunnnable {
    private int mOriginalAttachCount;
    final /* synthetic */ AbsHListView this$0;

    private AbsHListView$WindowRunnnable(AbsHListView absHListView) {
        this.this$0 = absHListView;
    }

    public void rememberWindowAttachCount() {
        this.mOriginalAttachCount = AbsHListView.access$200(this.this$0);
    }

    public boolean sameWindow() {
        return this.this$0.hasWindowFocus() && AbsHListView.access$300(this.this$0) == this.mOriginalAttachCount;
    }
}
