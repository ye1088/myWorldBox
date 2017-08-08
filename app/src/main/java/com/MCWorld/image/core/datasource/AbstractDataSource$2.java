package com.MCWorld.image.core.datasource;

class AbstractDataSource$2 implements Runnable {
    final /* synthetic */ AbstractDataSource zu;
    final /* synthetic */ e zv;

    AbstractDataSource$2(AbstractDataSource this$0, e eVar) {
        this.zu = this$0;
        this.zv = eVar;
    }

    public void run() {
        this.zv.onProgressUpdate(this.zu);
    }
}
