package bolts;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: AndroidExecutors */
final class a {
    static final int CORE_POOL_SIZE = (cQ + 1);
    static final int MAX_POOL_SIZE = ((cQ * 2) + 1);
    private static final a cO = new a();
    private static final int cQ = Runtime.getRuntime().availableProcessors();
    static final long cR = 1;
    private final Executor cP = new a();

    /* compiled from: AndroidExecutors */
    private static class a implements Executor {
        private a() {
        }

        public void execute(Runnable command) {
            new Handler(Looper.getMainLooper()).post(command);
        }
    }

    private a() {
    }

    public static ExecutorService newCachedThreadPool() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue());
        a(executor, true);
        return executor;
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(), threadFactory);
        a(executor, true);
        return executor;
    }

    @SuppressLint({"NewApi"})
    public static void a(ThreadPoolExecutor executor, boolean value) {
        if (VERSION.SDK_INT >= 9) {
            executor.allowCoreThreadTimeOut(value);
        }
    }

    public static Executor aL() {
        return cO.cP;
    }
}
