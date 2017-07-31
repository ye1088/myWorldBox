package com.j256.ormlite.android.apptools.support;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import com.j256.ormlite.android.AndroidCompiledStatement;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.DaoObserver;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import java.sql.SQLException;

public class OrmLiteCursorLoader<T> extends AsyncTaskLoader<Cursor> implements DaoObserver {
    protected Cursor cursor;
    protected Dao<T, ?> dao;
    protected PreparedQuery<T> query;

    public OrmLiteCursorLoader(Context context, Dao<T, ?> dao, PreparedQuery<T> query) {
        super(context);
        this.dao = dao;
        this.query = query;
    }

    public Cursor loadInBackground() {
        try {
            Cursor cursor = ((AndroidCompiledStatement) this.query.compile(this.dao.getConnectionSource().getReadOnlyConnection(this.dao.getTableName()), StatementType.SELECT)).getCursor();
            cursor.getCount();
            return cursor;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deliverResult(Cursor newCursor) {
        if (!isReset()) {
            Cursor oldCursor = this.cursor;
            this.cursor = newCursor;
            if (isStarted()) {
                super.deliverResult(newCursor);
            }
            if (oldCursor != null && oldCursor != newCursor && !oldCursor.isClosed()) {
                oldCursor.close();
            }
        } else if (newCursor != null) {
            newCursor.close();
        }
    }

    protected void onStartLoading() {
        this.dao.registerObserver(this);
        if (this.cursor == null) {
            forceLoad();
            return;
        }
        deliverResult(this.cursor);
        if (takeContentChanged()) {
            forceLoad();
        }
    }

    protected void onStopLoading() {
        cancelLoad();
    }

    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (this.cursor != null) {
            if (!this.cursor.isClosed()) {
                this.cursor.close();
            }
            this.cursor = null;
        }
        this.dao.unregisterObserver(this);
    }

    public void onChange() {
        onContentChanged();
    }

    public PreparedQuery<T> getQuery() {
        return this.query;
    }

    public void setQuery(PreparedQuery<T> mQuery) {
        this.query = mQuery;
    }
}
