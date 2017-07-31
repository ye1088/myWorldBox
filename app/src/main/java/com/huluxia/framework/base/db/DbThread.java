package com.huluxia.framework.base.db;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.SafeDispatchHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DbThread extends Thread implements DbContext {
    private static final int CloseDbHelperMsg = 4;
    private static final int CreateDbHelperMsg = 3;
    private static final int DbCommandMsg = 1;
    private static final int DbCommandResultMsg = 2;
    private static final String TAG = "DbThread";
    private List<DbCommand> cachedCommands;
    private CommandHandler commandHandler;
    protected DbHelper dbHelper;
    protected String dbName;
    private volatile boolean isReady = false;
    private final Object mCommandLock = new Object();
    private Handler resultHandler;

    @SuppressLint({"HandlerLeak"})
    private class CommandHandler extends SafeDispatchHandler {
        private CommandHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    processCommand(msg.obj);
                    return;
                case 3:
                    DbThread.this.dbName = (String) msg.obj;
                    DbThread.this.createDbHelper(DbThread.this.dbName);
                    return;
                case 4:
                    DbThread.this.closeDbHelper();
                    return;
                default:
                    return;
            }
        }

        public void processCommand(DbCommand cmd) {
            if (cmd != null) {
                cmd.realExecute();
                if (DbThread.this.resultHandler != null) {
                    DbThread.this.resultHandler.sendMessage(DbThread.this.resultHandler.obtainMessage(2, cmd));
                }
            }
        }
    }

    public abstract void createDbHelper(String str);

    public DbThread(String name, String dbName) {
        setPriority(10);
        setName("db-" + name + "-" + dbName);
        this.dbName = dbName;
        HLog.debug(TAG, "DbThread constructor", new Object[0]);
        this.cachedCommands = Collections.synchronizedList(new ArrayList());
        this.resultHandler = new SafeDispatchHandler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        DbCommand cmd = msg.obj;
                        if (cmd != null) {
                            DbResult dbResult = cmd.getResult();
                            switch (dbResult.resultCode) {
                                case Successful:
                                    cmd.onSucceed(dbResult.resultObject);
                                    return;
                                case Failed:
                                    cmd.onFail(dbResult.error);
                                    return;
                                default:
                                    return;
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        };
    }

    public void sendCommand(DbCommand cmd) {
        if (cmd == null) {
            return;
        }
        if (this.isReady) {
            Message msg = this.commandHandler.obtainMessage(1, cmd);
            if (msg != null) {
                this.commandHandler.sendMessage(msg);
                return;
            }
            return;
        }
        this.cachedCommands.add(cmd);
    }

    public DbResult syncCommnad(DbSyncCommand cmd) {
        if (cmd == null) {
            return null;
        }
        DbResult dbResult;
        synchronized (this.mCommandLock) {
            while (!this.isReady) {
                try {
                    this.mCommandLock.wait();
                } catch (InterruptedException e) {
                }
            }
            cmd.realExecute();
            dbResult = cmd.getResult();
        }
        return dbResult;
    }

    private void processCachedCommands() {
        if (this.dbHelper != null) {
            List<DbCommand> commands = new ArrayList(this.cachedCommands);
            if (commands.size() > 0) {
                HLog.info(TAG, "handle cached commands: " + commands.size(), new Object[0]);
                for (DbCommand cmd : commands) {
                    this.commandHandler.processCommand(cmd);
                }
            }
            commands.clear();
        }
        this.cachedCommands.clear();
    }

    public void run() {
        Looper.prepare();
        if (this.dbName != null) {
            createDbHelper(this.dbName);
        }
        this.commandHandler = new CommandHandler();
        this.isReady = true;
        synchronized (this.mCommandLock) {
            this.mCommandLock.notifyAll();
        }
        HLog.info(TAG, "DbThread ready", new Object[0]);
        processCachedCommands();
        Looper.loop();
    }

    protected void threadCreateDbHelper(String dbName) {
        Message msg = this.commandHandler.obtainMessage(3, dbName);
        if (msg != null) {
            this.commandHandler.sendMessage(msg);
        }
    }

    public DbHelper getDbHelper() {
        return this.dbHelper;
    }

    public void closeDbHelper() {
        if (Looper.myLooper().getThread() != this) {
            Message msg = this.commandHandler.obtainMessage(4, this.dbName);
            if (msg != null) {
                this.commandHandler.sendMessage(msg);
            }
        } else if (this.dbHelper != null) {
            HLog.info(TAG, "close dbHelper: " + this.dbHelper.getDbName(), new Object[0]);
            this.dbHelper.close();
            this.dbHelper = null;
        }
    }

    public void open() {
        start();
    }

    public boolean isOpen() {
        return this.dbHelper != null && this.dbHelper.isOpen();
    }
}
