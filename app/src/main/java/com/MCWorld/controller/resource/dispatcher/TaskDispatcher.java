package com.MCWorld.controller.resource.dispatcher;

import android.os.Process;
import android.os.SystemClock;
import com.MCWorld.controller.c;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.factory.a;
import com.MCWorld.controller.resource.factory.b;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import java.util.concurrent.BlockingQueue;

public class TaskDispatcher<T extends ResTaskInfo> extends Thread implements a {
    private static final String TAG = "TaskDispatcher";
    private Object LOCK = new Object();
    private final BlockingQueue<T> mQueue;
    private volatile boolean mQuit = false;
    a<T> nd;
    private ResTaskInfo ne = null;

    public TaskDispatcher(BlockingQueue<T> queue) {
        super("task-dispatcher-" + SystemClock.elapsedRealtime());
        this.mQueue = queue;
        this.nd = new b();
    }

    public void quit() {
        HLog.info(this, "task dispatcher quit", new Object[0]);
        finish();
        this.mQuit = true;
        interrupt();
    }

    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                ResTaskInfo task = (ResTaskInfo) this.mQueue.take();
                com.MCWorld.controller.resource.handler.base.a<T> handler = this.nd.a(task);
                if (handler == null) {
                    HLog.error(TAG, "not suitable handle for info : %s", new Object[]{task});
                } else {
                    ResourceCtrl.getInstance().addRunningHandler(task.url, handler);
                    this.ne = task;
                    try {
                        HLog.info(TAG, "task prepare return intermediately preparesucc %b", new Object[]{Boolean.valueOf(handler.prepare())});
                        if (handler.prepare()) {
                            finish();
                        } else if (handler.process(this)) {
                            finish();
                        } else {
                            ResourceCtrl.getInstance().startServiceForeground(task);
                            synchronized (this.LOCK) {
                                try {
                                    this.LOCK.wait();
                                } catch (InterruptedException e) {
                                    HLog.error(TAG, "LOCK wait interupt for info : %s, err %s", new Object[]{task, e});
                                }
                            }
                        }
                    } catch (Exception e2) {
                        HLog.error(TAG, "task prepare error %e", e2, new Object[0]);
                        finish();
                    }
                }
            } catch (InterruptedException e3) {
                if (this.mQuit) {
                    return;
                }
            }
        }
    }

    public void finish() {
        HLog.info(TAG, "task dispatcher run next...", new Object[0]);
        ResTaskInfo taskInfo = this.ne;
        this.ne = null;
        if (taskInfo != null) {
            ResourceCtrl.getInstance().stopServiceForeground(taskInfo);
            ResourceCtrl.getInstance().removeRunningHandler(taskInfo.url);
            EventNotifyCenter.notifyEventUiThread(c.class, 258, new Object[]{taskInfo.url});
        }
        synchronized (this.LOCK) {
            this.LOCK.notify();
        }
    }

    public boolean isRunning() {
        return this.ne != null;
    }

    public ResTaskInfo dN() {
        return this.ne;
    }
}
