package com.j256.ormlite.stmt.query;

import com.j256.ormlite.stmt.ArgumentHolder;

public class OrderBy {
    private final boolean ascending;
    private final String columnName;
    private final ArgumentHolder[] orderByArgs;
    private final String rawSql;

    public OrderBy(String columnName, boolean ascending) {
        this.columnName = columnName;
        this.ascending = ascending;
        this.rawSql = null;
        this.orderByArgs = null;
    }

    public OrderBy(String rawSql, ArgumentHolder[] orderByArgs) {
        this.columnName = null;
        this.ascending = true;
        this.rawSql = rawSql;
        this.orderByArgs = orderByArgs;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public boolean isAscending() {
        return this.ascending;
    }

    public String getRawSql() {
        return this.rawSql;
    }

    public ArgumentHolder[] getOrderByArgs() {
        return this.orderByArgs;
    }
}
