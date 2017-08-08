package com.MCWorld.image.core.datasource;

class AbstractDataSource$1 implements Runnable {
    final /* synthetic */ boolean zq;
    final /* synthetic */ e zr;
    final /* synthetic */ boolean zt;
    final /* synthetic */ AbstractDataSource zu;

    AbstractDataSource$1(AbstractDataSource this$0, boolean z, e eVar, boolean z2) {
        this.zu = this$0;
        this.zq = z;
        this.zr = eVar;
        this.zt = z2;
    }

    public void run() {
        if (this.zq) {
            this.zr.onFailure(this.zu);
        } else if (this.zt) {
            this.zr.onCancellation(this.zu);
        } else {
            this.zr.onNewResult(this.zu);
        }
    }
}
