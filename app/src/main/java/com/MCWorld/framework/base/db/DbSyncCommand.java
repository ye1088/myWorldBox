package com.MCWorld.framework.base.db;

import android.os.Looper;
import com.MCWorld.framework.base.log.HLog;

public class DbSyncCommand extends DbCommand {
    private SyncRunnable runnable;

    public static abstract class SyncRunnable {
        protected DbSyncCommand command = this;

        protected abstract void sync() throws Exception;

        public DbResult getResult() {
            return this.command.getResult();
        }
    }

    public DbSyncCommand(SyncRunnable runnable) {
        this.runnable = runnable;
    }

    private void checkThread() {
        if (Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId()) {
            HLog.error(this, "don't run db command in UI thread", new Object[0]);
        }
    }

    public void execute() throws Exception {
        checkThread();
        this.runnable.sync();
    }

    public void onSucceed(Object obj) {
    }

    public void onFail(DbError error) {
    }

    public DbResult getResult() {
        return this.result;
    }
}
