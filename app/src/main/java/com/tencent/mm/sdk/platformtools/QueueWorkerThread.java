package com.tencent.mm.sdk.platformtools;

import android.os.Handler;
import android.os.Message;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.tools.ant.util.FileUtils;

public class QueueWorkerThread {
    private LinkedBlockingQueue<ThreadObject> aQ;
    private boolean aR;
    private int aS;
    private Vector<WorkerThread> aT;
    private Handler aU;
    private Object lock;
    private String name;
    private int priority;

    public interface ThreadObject {
        boolean doInBackground();

        boolean onPostExecute();
    }

    final class WorkerThread extends Thread {
        final /* synthetic */ QueueWorkerThread aV;
        private int aW;

        private WorkerThread(QueueWorkerThread queueWorkerThread) {
            this.aV = queueWorkerThread;
            super(queueWorkerThread.name);
            this.aW = 60;
            setPriority(queueWorkerThread.priority);
            queueWorkerThread.aT.add(this);
        }

        public final void run() {
            while (this.aW > 0) {
                ThreadObject threadObject;
                synchronized (this.aV.lock) {
                    try {
                        if (this.aV.aR) {
                            this.aV.lock.wait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    threadObject = (ThreadObject) this.aV.aQ.poll(FileUtils.FAT_FILE_TIMESTAMP_GRANULARITY, TimeUnit.MILLISECONDS);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    threadObject = null;
                }
                if (threadObject == null) {
                    this.aW--;
                } else {
                    this.aW = 60;
                    if (threadObject.doInBackground()) {
                        this.aV.aU.sendMessage(this.aV.aU.obtainMessage(0, threadObject));
                    }
                }
            }
            this.aV.aT.remove(this);
            Log.d("QueueWorkerThread.QueueWorkerThread", "dktest Finish queueToReqSize:" + this.aV.aQ.size() + " ThreadSize:" + this.aV.aT.size());
        }
    }

    public QueueWorkerThread(int i, String str) {
        this(i, str, 1);
    }

    public QueueWorkerThread(int i, String str, int i2) {
        this.aQ = new LinkedBlockingQueue();
        this.aR = false;
        this.aS = 1;
        this.priority = 1;
        this.name = "";
        this.lock = new byte[0];
        this.aT = new Vector();
        this.aU = new Handler(this) {
            final /* synthetic */ QueueWorkerThread aV;

            {
                this.aV = r1;
            }

            public void handleMessage(Message message) {
                if (message != null && message.obj != null) {
                    ((ThreadObject) message.obj).onPostExecute();
                }
            }
        };
        this.aS = i2;
        this.name = str;
        this.priority = i;
    }

    public int add(ThreadObject threadObject) {
        if (threadObject == null) {
            Log.e("QueueWorkerThread.QueueWorkerThread", "add empty thread object");
            return -1;
        }
        try {
            if (!this.aQ.offer(threadObject, 1, TimeUnit.MILLISECONDS)) {
                Log.e("QueueWorkerThread.QueueWorkerThread", "add To Queue failed");
                return -2;
            } else if (this.aT.size() != 0 && (this.aQ.size() <= 0 || this.aS <= this.aT.size())) {
                return 0;
            } else {
                new WorkerThread().start();
                return 0;
            }
        } catch (Exception e) {
            Log.e("QueueWorkerThread.QueueWorkerThread", "add To Queue failed :" + e.getMessage());
            e.printStackTrace();
            return -3;
        }
    }

    public int getQueueSize() {
        return this.aQ.size();
    }

    public boolean isDead() {
        return this.aT == null || this.aT.size() == 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pause(boolean r4) {
        /*
        r3 = this;
        r1 = r3.lock;
        monitor-enter(r1);
        r3.aR = r4;	 Catch:{ all -> 0x0015 }
        if (r4 != 0) goto L_0x0010;
    L_0x0007:
        r2 = r3.lock;	 Catch:{ all -> 0x0015 }
        monitor-enter(r2);	 Catch:{ all -> 0x0015 }
        r0 = r3.lock;	 Catch:{ all -> 0x0012 }
        r0.notifyAll();	 Catch:{ all -> 0x0012 }
        monitor-exit(r2);	 Catch:{ all -> 0x0012 }
    L_0x0010:
        monitor-exit(r1);	 Catch:{ all -> 0x0015 }
        return;
    L_0x0012:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0015 }
        throw r0;	 Catch:{ all -> 0x0015 }
    L_0x0015:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mm.sdk.platformtools.QueueWorkerThread.pause(boolean):void");
    }
}
