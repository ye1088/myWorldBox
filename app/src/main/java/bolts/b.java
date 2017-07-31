package bolts;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: BoltsExecutors */
final class b {
    private static final b cS = new b();
    private final ExecutorService cT;
    private final ScheduledExecutorService cU;
    private final Executor cV;

    /* compiled from: BoltsExecutors */
    private static class a implements Executor {
        private static final int cW = 15;
        private ThreadLocal<Integer> cX;

        private a() {
            this.cX = new ThreadLocal();
        }

        private int aQ() {
            Integer oldDepth = (Integer) this.cX.get();
            if (oldDepth == null) {
                oldDepth = Integer.valueOf(0);
            }
            int newDepth = oldDepth.intValue() + 1;
            this.cX.set(Integer.valueOf(newDepth));
            return newDepth;
        }

        private int aR() {
            Integer oldDepth = (Integer) this.cX.get();
            if (oldDepth == null) {
                oldDepth = Integer.valueOf(0);
            }
            int newDepth = oldDepth.intValue() - 1;
            if (newDepth == 0) {
                this.cX.remove();
            } else {
                this.cX.set(Integer.valueOf(newDepth));
            }
            return newDepth;
        }

        public void execute(Runnable command) {
            if (aQ() <= 15) {
                try {
                    command.run();
                } catch (Throwable th) {
                    aR();
                }
            } else {
                b.aN().execute(command);
            }
            aR();
        }
    }

    private static boolean aM() {
        String javaRuntimeName = System.getProperty("java.runtime.name");
        if (javaRuntimeName == null) {
            return false;
        }
        return javaRuntimeName.toLowerCase(Locale.US).contains("android");
    }

    private b() {
        this.cT = !aM() ? Executors.newCachedThreadPool() : a.newCachedThreadPool();
        this.cU = Executors.newSingleThreadScheduledExecutor();
        this.cV = new a();
    }

    public static ExecutorService aN() {
        return cS.cT;
    }

    static ScheduledExecutorService aO() {
        return cS.cU;
    }

    static Executor aP() {
        return cS.cV;
    }
}
