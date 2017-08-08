package com.MCWorld.image.core.datasource;

/* compiled from: BaseBooleanSubscriber */
public abstract class a implements e<Boolean> {
    protected abstract void R(boolean z);

    protected abstract void onFailureImpl(c<Boolean> cVar);

    public void onNewResult(c<Boolean> dataSource) {
        try {
            R(((Boolean) dataSource.getResult()).booleanValue());
        } finally {
            dataSource.close();
        }
    }

    public void onFailure(c<Boolean> dataSource) {
        try {
            onFailureImpl(dataSource);
        } finally {
            dataSource.close();
        }
    }

    public void onCancellation(c<Boolean> cVar) {
    }

    public void onProgressUpdate(c<Boolean> cVar) {
    }
}
