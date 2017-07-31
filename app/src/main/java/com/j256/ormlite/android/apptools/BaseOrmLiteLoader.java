package com.j256.ormlite.android.apptools;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.DaoObserver;
import java.util.List;

public abstract class BaseOrmLiteLoader<T, ID> extends AsyncTaskLoader<List<T>> implements DaoObserver {
    private List<T> cachedResults;
    protected Dao<T, ID> dao;

    public BaseOrmLiteLoader(Context context) {
        super(context);
    }

    public BaseOrmLiteLoader(Context context, Dao<T, ID> dao) {
        super(context);
        this.dao = dao;
    }

    public void deliverResult(List<T> results) {
        if (!isReset() && isStarted()) {
            super.deliverResult(results);
        }
    }

    protected void onStartLoading() {
        if (this.cachedResults != null) {
            deliverResult(this.cachedResults);
        }
        if (takeContentChanged() || this.cachedResults == null) {
            forceLoad();
        }
        this.dao.registerObserver(this);
    }

    protected void onStopLoading() {
        cancelLoad();
    }

    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (this.cachedResults != null) {
            this.cachedResults.clear();
            this.cachedResults = null;
        }
        this.dao.unregisterObserver(this);
    }

    public void onChange() {
        onContentChanged();
    }

    public void setDao(Dao<T, ID> dao) {
        this.dao = dao;
    }
}
