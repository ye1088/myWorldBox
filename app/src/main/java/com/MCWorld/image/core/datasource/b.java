package com.MCWorld.image.core.datasource;

/* compiled from: BaseDataSubscriber */
public abstract class b<T> implements e<T> {
    protected abstract void onFailureImpl(c<T> cVar);

    protected abstract void onNewResultImpl(c<T> cVar);

    public void onNewResult(c<T> dataSource) {
        boolean shouldClose = dataSource.isFinished();
        try {
            onNewResultImpl(dataSource);
        } finally {
            if (shouldClose) {
                dataSource.close();
            }
        }
    }

    public void onFailure(c<T> dataSource) {
        try {
            onFailureImpl(dataSource);
        } finally {
            dataSource.close();
        }
    }

    public void onCancellation(c<T> cVar) {
    }

    public void onProgressUpdate(c<T> cVar) {
    }
}
