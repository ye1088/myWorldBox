package com.MCWorld.image.pipeline.producers;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: NetworkFetchProducer */
class ag$1 implements ah$a {
    final /* synthetic */ ag KZ;
    final /* synthetic */ s gq;

    ag$1(ag this$0, s sVar) {
        this.KZ = this$0;
        this.gq = sVar;
    }

    public void h(InputStream response, int responseLength) throws IOException {
        ag.a(this.KZ, this.gq, response, responseLength);
    }

    public void j(Throwable throwable) {
        ag.a(this.KZ, this.gq, throwable);
    }

    public void iq() {
        ag.a(this.KZ, this.gq);
    }
}
