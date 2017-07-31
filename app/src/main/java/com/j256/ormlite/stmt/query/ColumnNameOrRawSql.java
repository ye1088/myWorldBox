package com.j256.ormlite.stmt.query;

public class ColumnNameOrRawSql {
    private final String columnName;
    private final String rawSql;

    public static ColumnNameOrRawSql withColumnName(String columnName) {
        return new ColumnNameOrRawSql(columnName, null);
    }

    public static ColumnNameOrRawSql withRawSql(String rawSql) {
        return new ColumnNameOrRawSql(null, rawSql);
    }

    private ColumnNameOrRawSql(String columnName, String rawSql) {
        this.columnName = columnName;
        this.rawSql = rawSql;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getRawSql() {
        return this.rawSql;
    }

    public String toString() {
        if (this.rawSql == null) {
            return this.columnName;
        }
        return this.rawSql;
    }
}
