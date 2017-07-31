package bolts;

import java.io.Closeable;

/* compiled from: CancellationTokenRegistration */
public class d implements Closeable {
    private e cY;
    private Runnable cZ;
    private boolean closed;
    private final Object lock = new Object();

    d(e tokenSource, Runnable action) {
        this.cY = tokenSource;
        this.cZ = action;
    }

    public void close() {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.cY.a(this);
            this.cY = null;
            this.cZ = null;
        }
    }

    void aU() {
        synchronized (this.lock) {
            aV();
            this.cZ.run();
            close();
        }
    }

    private void aV() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }
}
