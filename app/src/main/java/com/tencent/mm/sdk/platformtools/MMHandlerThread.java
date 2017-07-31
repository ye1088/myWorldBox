package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import junit.framework.Assert;

public class MMHandlerThread {
    public static long mainThreadID = -1;
    private HandlerThread ao = null;
    private Handler ap = null;

    public interface IWaitWorkThread {
        boolean doInBackground();

        boolean onPostExecute();
    }

    public interface ResetCallback {
        void callback();
    }

    public MMHandlerThread() {
        c();
    }

    static /* synthetic */ void a(Runnable runnable) {
        if (runnable != null) {
            new Handler(Looper.getMainLooper()).postAtFrontOfQueue(runnable);
        }
    }

    private void c() {
        Log.d("MicroMsg.MMHandlerThread", "MMHandlerThread init [%s]", Util.getStack());
        this.ap = null;
        this.ao = new HandlerThread("MMHandlerThread", 1);
        this.ao.start();
    }

    public static boolean isMainThread() {
        Assert.assertFalse("mainThreadID not init ", mainThreadID == -1);
        return Thread.currentThread().getId() == mainThreadID;
    }

    public static void postToMainThread(Runnable runnable) {
        if (runnable != null) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public static void postToMainThreadDelayed(Runnable runnable, long j) {
        if (runnable != null) {
            new Handler(Looper.getMainLooper()).postDelayed(runnable, j);
        }
    }

    public static void setMainThreadID(long j) {
        if (mainThreadID < 0 && j > 0) {
            mainThreadID = j;
        }
    }

    public Looper getLooper() {
        return this.ao.getLooper();
    }

    public Handler getWorkerHandler() {
        if (this.ap == null) {
            this.ap = new Handler(this.ao.getLooper());
        }
        return this.ap;
    }

    public int postAtFrontOfWorker(final IWaitWorkThread iWaitWorkThread) {
        return iWaitWorkThread == null ? -1 : new Handler(getLooper()).postAtFrontOfQueue(new Runnable(this) {
            final /* synthetic */ MMHandlerThread ar;

            public void run() {
                iWaitWorkThread.doInBackground();
                MMHandlerThread.a(new Runnable(this) {
                    final /* synthetic */ AnonymousClass3 au;

                    {
                        this.au = r1;
                    }

                    public void run() {
                        iWaitWorkThread.onPostExecute();
                    }
                });
            }
        }) ? 0 : -2;
    }

    public int postToWorker(Runnable runnable) {
        if (runnable == null) {
            return -1;
        }
        getWorkerHandler().post(runnable);
        return 0;
    }

    public int reset(final IWaitWorkThread iWaitWorkThread) {
        return postAtFrontOfWorker(new IWaitWorkThread(this) {
            final /* synthetic */ MMHandlerThread ar;

            public boolean doInBackground() {
                if (iWaitWorkThread != null) {
                    return iWaitWorkThread.doInBackground();
                }
                this.ar.ao.quit();
                this.ar.c();
                return true;
            }

            public boolean onPostExecute() {
                return iWaitWorkThread != null ? iWaitWorkThread.onPostExecute() : true;
            }
        });
    }

    public int syncReset(final ResetCallback resetCallback) {
        int postAtFrontOfWorker;
        Assert.assertTrue("syncReset should in mainThread", isMainThread());
        final Object obj = new byte[0];
        IWaitWorkThread anonymousClass2 = new IWaitWorkThread(this) {
            final /* synthetic */ MMHandlerThread ar;

            public boolean doInBackground() {
                Log.d("MicroMsg.MMHandlerThread", "syncReset doInBackground");
                this.ar.ao.quit();
                if (resetCallback != null) {
                    resetCallback.callback();
                }
                this.ar.c();
                synchronized (obj) {
                    obj.notify();
                }
                return true;
            }

            public boolean onPostExecute() {
                Log.d("MicroMsg.MMHandlerThread", "syncReset onPostExecute");
                return true;
            }
        };
        synchronized (obj) {
            postAtFrontOfWorker = postAtFrontOfWorker(anonymousClass2);
            if (postAtFrontOfWorker == 0) {
                try {
                    obj.wait();
                } catch (Exception e) {
                }
            }
        }
        return postAtFrontOfWorker;
    }
}
