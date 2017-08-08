package com.MCWorld;

/* compiled from: StatisticsApp */
class r$1 implements Runnable {
    final /* synthetic */ r ha;

    r$1(r this$0) {
        this.ha = this$0;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            r.a(this.ha);
            r.b(this.ha);
        } catch (InterruptedException e) {
        }
    }
}
