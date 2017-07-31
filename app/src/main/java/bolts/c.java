package bolts;

import java.util.Locale;
import java.util.concurrent.CancellationException;

/* compiled from: CancellationToken */
public class c {
    private final e cY;

    c(e tokenSource) {
        this.cY = tokenSource;
    }

    public boolean aS() {
        return this.cY.aS();
    }

    public d c(Runnable action) {
        return this.cY.c(action);
    }

    public void aT() throws CancellationException {
        this.cY.aT();
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(this.cY.aS())});
    }
}
