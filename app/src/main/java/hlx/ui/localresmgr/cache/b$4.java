package hlx.ui.localresmgr.cache;

import hlx.ui.localresmgr.module.b;

/* compiled from: LocResCacheManager */
class b$4 implements Runnable {
    final /* synthetic */ b bZZ;

    b$4(b this$0) {
        this.bZZ = this$0;
    }

    public void run() {
        try {
            b.a(this.bZZ, b.nF(b.h(this.bZZ)));
            b.b(this.bZZ, b.nE(b.i(this.bZZ)));
            b.c(this.bZZ, b.nD(b.j(this.bZZ)));
            b.d(this.bZZ, b.nC(b.k(this.bZZ)));
            b.a(this.bZZ).clear();
        } catch (Exception e) {
        }
    }
}
