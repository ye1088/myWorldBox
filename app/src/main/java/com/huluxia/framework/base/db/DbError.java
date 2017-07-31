package com.huluxia.framework.base.db;

public class DbError {
    private Exception exception;

    public DbError(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return this.exception;
    }
}
