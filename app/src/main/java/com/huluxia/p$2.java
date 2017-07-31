package com.huluxia;

import com.huluxia.image.pipeline.producers.ah$a;
import com.huluxia.image.pipeline.producers.e;
import java.util.concurrent.Future;

/* compiled from: PainterNetworkFetcher */
class p$2 extends e {
    final /* synthetic */ ah$a gr;
    final /* synthetic */ p gs;
    final /* synthetic */ Future gt;

    p$2(p this$0, Future future, ah$a com_huluxia_image_pipeline_producers_ah_a) {
        this.gs = this$0;
        this.gt = future;
        this.gr = com_huluxia_image_pipeline_producers_ah_a;
    }

    public void cj() {
        if (this.gt.cancel(false)) {
            this.gr.iq();
        }
    }
}
