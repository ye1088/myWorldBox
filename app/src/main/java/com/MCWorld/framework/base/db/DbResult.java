package com.MCWorld.framework.base.db;

public class DbResult {
    public DbError error;
    public ResultCode resultCode;
    public Object resultObject;

    public enum ResultCode {
        Successful,
        Failed
    }

    public DbResult() {
        this.resultCode = ResultCode.Successful;
    }

    public DbResult(ResultCode resultCode, Object resultObject, DbError error) {
        this.resultCode = resultCode;
        this.resultObject = resultObject;
        this.error = error;
    }
}
