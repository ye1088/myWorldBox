package io.netty.util;

import io.netty.util.concurrent.FastThreadLocal;

class Recycler$2 extends FastThreadLocal<Recycler$Stack<T>> {
    final /* synthetic */ Recycler this$0;

    Recycler$2(Recycler recycler) {
        this.this$0 = recycler;
    }

    protected Recycler$Stack<T> initialValue() {
        return new Recycler$Stack(this.this$0, Thread.currentThread(), Recycler.access$000(this.this$0), Recycler.access$100(this.this$0), Recycler.access$200(this.this$0), Recycler.access$300(this.this$0));
    }
}
