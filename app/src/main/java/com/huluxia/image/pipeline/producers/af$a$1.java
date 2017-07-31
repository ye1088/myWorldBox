package com.huluxia.image.pipeline.producers;

import android.util.Pair;
import com.huluxia.image.pipeline.producers.af.a;
import java.util.List;

/* compiled from: MultiplexProducer */
class af$a$1 extends e {
    final /* synthetic */ Pair KT;
    final /* synthetic */ a KU;

    af$a$1(a this$1, Pair pair) {
        this.KU = this$1;
        this.KT = pair;
    }

    public void cj() {
        d contextToCancel = null;
        List<ap> isPrefetchCallbacks = null;
        List<ap> priorityCallbacks = null;
        List<ap> isIntermediateResultExpectedCallbacks = null;
        synchronized (this.KU) {
            boolean pairWasRemoved = a.b(this.KU).remove(this.KT);
            if (pairWasRemoved) {
                if (a.b(this.KU).isEmpty()) {
                    contextToCancel = a.c(this.KU);
                } else {
                    isPrefetchCallbacks = a.d(this.KU);
                    priorityCallbacks = a.e(this.KU);
                    isIntermediateResultExpectedCallbacks = a.f(this.KU);
                }
            }
        }
        d.r(isPrefetchCallbacks);
        d.t(priorityCallbacks);
        d.s(isIntermediateResultExpectedCallbacks);
        if (contextToCancel != null) {
            contextToCancel.cancel();
        }
        if (pairWasRemoved) {
            ((j) this.KT.first).iq();
        }
    }

    public void oH() {
        d.r(a.d(this.KU));
    }

    public void oI() {
        d.s(a.f(this.KU));
    }

    public void oJ() {
        d.t(a.e(this.KU));
    }
}
