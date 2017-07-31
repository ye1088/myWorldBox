package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.misc.IOUtils;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawResultsImpl<T> implements GenericRawResults<T> {
    private final String[] columnNames = this.iterator.getRawResults().getColumnNames();
    private SelectIterator<T, Void> iterator;

    public RawResultsImpl(ConnectionSource connectionSource, DatabaseConnection connection, String query, Class<?> clazz, CompiledStatement compiledStmt, GenericRowMapper<T> rowMapper, ObjectCache objectCache) throws SQLException {
        this.iterator = new SelectIterator(clazz, null, rowMapper, connectionSource, connection, compiledStmt, query, objectCache);
    }

    public int getNumberColumns() {
        return this.columnNames.length;
    }

    public String[] getColumnNames() {
        return this.columnNames;
    }

    public List<T> getResults() throws SQLException {
        List<T> results = new ArrayList();
        while (this.iterator.hasNext()) {
            try {
                results.add(this.iterator.next());
            } finally {
                IOUtils.closeThrowSqlException(this, "raw results iterator");
            }
        }
        return results;
    }

    public T getFirstResult() throws SQLException {
        try {
            if (this.iterator.hasNextThrow()) {
                T nextThrow = this.iterator.nextThrow();
                return nextThrow;
            }
            IOUtils.closeThrowSqlException(this, "raw results iterator");
            return null;
        } finally {
            IOUtils.closeThrowSqlException(this, "raw results iterator");
        }
    }

    public CloseableIterator<T> iterator() {
        return this.iterator;
    }

    public CloseableIterator<T> closeableIterator() {
        return this.iterator;
    }

    public void close() throws IOException {
        if (this.iterator != null) {
            this.iterator.close();
            this.iterator = null;
        }
    }
}
