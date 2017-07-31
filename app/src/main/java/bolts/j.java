package bolts;

import bolts.h.b;

/* compiled from: UnobservedErrorNotifier */
class j {
    private h<?> dN;

    public j(h<?> task) {
        this.dN = task;
    }

    protected void finalize() throws Throwable {
        try {
            h faultedTask = this.dN;
            if (faultedTask != null) {
                b ueh = h.aY();
                if (ueh != null) {
                    ueh.a(faultedTask, new UnobservedTaskException(faultedTask.bb()));
                }
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public void bk() {
        this.dN = null;
    }
}
