package com.MCWorld;

import com.MCWorld.image.pipeline.producers.ah$a;
import com.MCWorld.image.pipeline.producers.s;

/* compiled from: PainterNetworkFetcher */
class p$1 implements Runnable {
    final /* synthetic */ s gq;
    final /* synthetic */ ah$a gr;
    final /* synthetic */ p gs;

    p$1(p this$0, s sVar, ah$a com_huluxia_image_pipeline_producers_ah_a) {
        this.gs = this$0;
        this.gq = sVar;
        this.gr = com_huluxia_image_pipeline_producers_ah_a;
    }

    public void run() {
        this.gs.b(this.gq, this.gr);
    }
}
