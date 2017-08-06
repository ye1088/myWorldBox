package bolts;

/* compiled from: TaskCompletionSource */
public class i<TResult> {
    private final h<TResult> dN = new h();

    public h<TResult> bi() {
        return this.dN;
    }

    public boolean bh() {
        return this.dN.bh();
    }

    public boolean c(TResult result) {
        return this.dN.c((Object) result);
    }

    public boolean g(Exception error) {
        return this.dN.g(error);
    }

    public void bj() {
        if (!bh()) {
            throw new IllegalStateException("Cannot cancel a_isRightVersion completed task.");
        }
    }

    public void setResult(TResult result) {
        if (!c(result)) {
            throw new IllegalStateException("Cannot set the result of a_isRightVersion completed task.");
        }
    }

    public void h(Exception error) {
        if (!g(error)) {
            throw new IllegalStateException("Cannot set the error on a_isRightVersion completed task.");
        }
    }
}
