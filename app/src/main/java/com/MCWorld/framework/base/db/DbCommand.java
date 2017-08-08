package com.MCWorld.framework.base.db;

import com.MCWorld.framework.base.db.DbResult.ResultCode;

public abstract class DbCommand {
    protected DbResult result;

    public interface Callback {
        void onFail(DbError dbError);

        void onSucceed(Object obj);
    }

    public abstract void execute() throws Exception;

    public abstract void onFail(DbError dbError);

    public abstract void onSucceed(Object obj);

    protected void realExecute() {
        this.result = new DbResult();
        try {
            execute();
        } catch (Exception e) {
            this.result.resultCode = ResultCode.Failed;
            this.result.error = new DbError(e);
        }
    }

    public DbResult getResult() {
        return this.result;
    }
}
