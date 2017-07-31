package com.huluxia.widget;

/* compiled from: Zipper */
class h$1 implements Runnable {
    final /* synthetic */ h buO;

    h$1(h this$0) {
        this.buO = this$0;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            h.a(this.buO);
        } catch (InterruptedException e) {
        }
    }
}
