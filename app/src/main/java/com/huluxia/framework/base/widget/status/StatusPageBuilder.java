package com.huluxia.framework.base.widget.status;

import android.view.View.OnClickListener;
import com.huluxia.framework.base.utils.Supplier;

public abstract class StatusPageBuilder<T, P extends StatusBasePage<T>, V extends StatusPageBuilder> {
    protected int mBoundedView;
    protected Supplier<OnClickListener> mClickListener;
    protected T mPage;

    abstract V getThis();

    abstract P newPage();

    public StatusPageBuilder(T page) {
        this.mPage = page;
    }

    public V setClickListener(Supplier<OnClickListener> clickListener) {
        this.mClickListener = clickListener;
        return getThis();
    }

    public V setBoundedView(int mboundedView) {
        this.mBoundedView = mboundedView;
        return getThis();
    }

    public P build() {
        P page = newPage();
        page.setBoundedView(this.mBoundedView);
        return page;
    }
}
