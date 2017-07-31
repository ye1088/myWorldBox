package com.j256.ormlite.android.apptools;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CursorAdapter;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.stmt.PreparedQuery;
import java.sql.SQLException;

public abstract class OrmLiteCursorAdapter<T, ViewType extends View> extends CursorAdapter {
    protected PreparedQuery<T> preparedQuery;

    public abstract void bindView(ViewType viewType, Context context, T t);

    public OrmLiteCursorAdapter(Context context) {
        super(context, null, false);
    }

    public final void bindView(View itemView, Context context, Cursor cursor) {
        doBindView(itemView, context, cursor);
    }

    protected void doBindView(View itemView, Context context, Cursor cursor) {
        try {
            bindView(itemView, context, cursorToObject(cursor));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public T getTypedItem(int position) {
        try {
            return cursorToObject((Cursor) super.getItem(position));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected T cursorToObject(Cursor cursor) throws SQLException {
        return this.preparedQuery.mapRow(new AndroidDatabaseResults(cursor, null, true));
    }

    public final void changeCursor(Cursor cursor) {
        throw new UnsupportedOperationException("Please use OrmLiteCursorAdapter.changeCursor(Cursor,PreparedQuery) instead");
    }

    public void changeCursor(Cursor cursor, PreparedQuery<T> preparedQuery) {
        setPreparedQuery(preparedQuery);
        super.changeCursor(cursor);
    }

    public void setPreparedQuery(PreparedQuery<T> preparedQuery) {
        this.preparedQuery = preparedQuery;
    }
}
