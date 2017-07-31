package com.j256.ormlite.stmt;

import com.j256.ormlite.field.SqlType;

public class ThreadLocalSelectArg extends BaseArgumentHolder {
    private ThreadLocal<ValueWrapper> threadValue = new ThreadLocal();

    private static class ValueWrapper {
        Object value;

        public ValueWrapper(Object value) {
            this.value = value;
        }
    }

    public ThreadLocalSelectArg(String columnName, Object value) {
        super(columnName);
        setValue(value);
    }

    public ThreadLocalSelectArg(SqlType sqlType, Object value) {
        super(sqlType);
        setValue(value);
    }

    public ThreadLocalSelectArg(Object value) {
        setValue(value);
    }

    protected Object getValue() {
        ValueWrapper wrapper = (ValueWrapper) this.threadValue.get();
        if (wrapper == null) {
            return null;
        }
        return wrapper.value;
    }

    public void setValue(Object value) {
        this.threadValue.set(new ValueWrapper(value));
    }

    protected boolean isValueSet() {
        return this.threadValue.get() != null;
    }
}
