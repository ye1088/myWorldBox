package com.j256.ormlite.stmt;

import com.j256.ormlite.field.SqlType;

public class SelectArg extends BaseArgumentHolder {
    private boolean hasBeenSet = false;
    private Object value = null;

    public SelectArg(String columnName, Object value) {
        super(columnName);
        setValue(value);
    }

    public SelectArg(SqlType sqlType, Object value) {
        super(sqlType);
        setValue(value);
    }

    public SelectArg(SqlType sqlType) {
        super(sqlType);
    }

    public SelectArg(Object value) {
        setValue(value);
    }

    protected Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.hasBeenSet = true;
        this.value = value;
    }

    protected boolean isValueSet() {
        return this.hasBeenSet;
    }
}
