package com.huluxia.image.pipeline.datasource;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.core.datasource.AbstractDataSource;
import com.huluxia.image.pipeline.listener.c;
import com.huluxia.image.pipeline.producers.am;
import com.huluxia.image.pipeline.producers.at;
import com.huluxia.image.pipeline.producers.b;
import com.huluxia.image.pipeline.producers.j;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: AbstractProducerToDataSourceAdapter */
public abstract class a<T> extends AbstractDataSource<T> {
    private final c Fh;
    private final at GK;

    protected a(am<T> producer, at settableProducerContext, c requestListener) {
        this.GK = settableProducerContext;
        this.Fh = requestListener;
        this.Fh.a(settableProducerContext.oA(), this.GK.gk(), this.GK.getId(), this.GK.oD());
        producer.b(nr(), settableProducerContext);
    }

    private j<T> nr() {
        return new b<T>(this) {
            final /* synthetic */ a GL;

            {
                this.GL = this$0;
            }

            protected void d(@Nullable T newResult, boolean isLast) {
                this.GL.d(newResult, isLast);
            }

            protected void h(Throwable throwable) {
                this.GL.h(throwable);
            }

            protected void ns() {
                this.GL.ns();
            }

            protected void n(float progress) {
                this.GL.d(progress);
            }
        };
    }

    protected void d(@Nullable T result, boolean isLast) {
        if (super.b(result, isLast) && isLast) {
            this.Fh.a(this.GK.oA(), this.GK.getId(), this.GK.oD());
        }
    }

    private void h(Throwable throwable) {
        if (super.b(throwable)) {
            this.Fh.a(this.GK.oA(), this.GK.getId(), throwable, this.GK.oD());
        }
    }

    private synchronized void ns() {
        Preconditions.checkState(isClosed());
    }

    public boolean close() {
        if (!super.close()) {
            return false;
        }
        if (!super.isFinished()) {
            this.Fh.bD(this.GK.getId());
            this.GK.cancel();
        }
        return true;
    }
}
