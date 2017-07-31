package com.huluxia.framework.base.widget.hlistview;

class HListView$FocusSelector implements Runnable {
    private int mPosition;
    private int mPositionLeft;
    final /* synthetic */ HListView this$0;

    private HListView$FocusSelector(HListView hListView) {
        this.this$0 = hListView;
    }

    public HListView$FocusSelector setup(int position, int left) {
        this.mPosition = position;
        this.mPositionLeft = left;
        return this;
    }

    public void run() {
        this.this$0.setSelectionFromLeft(this.mPosition, this.mPositionLeft);
    }
}
