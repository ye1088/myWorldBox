package com.MCWorld.image.core.datasource;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: DataSource */
public interface c<T> {
    void a(e<T> eVar, Executor executor);

    boolean close();

    float getProgress();

    @Nullable
    T getResult();

    boolean iN();

    boolean iO();

    @Nullable
    Throwable iP();

    boolean isClosed();

    boolean isFinished();
}
