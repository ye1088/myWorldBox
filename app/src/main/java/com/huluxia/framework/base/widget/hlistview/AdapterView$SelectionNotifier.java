package com.huluxia.framework.base.widget.hlistview;

class AdapterView$SelectionNotifier implements Runnable {
    final /* synthetic */ AdapterView this$0;

    private AdapterView$SelectionNotifier(AdapterView adapterView) {
        this.this$0 = adapterView;
    }

    public void run() {
        if (!this.this$0.mDataChanged) {
            AdapterView.access$200(this.this$0);
            AdapterView.access$300(this.this$0);
        } else if (this.this$0.getAdapter() != null) {
            this.this$0.post(this);
        }
    }
}
