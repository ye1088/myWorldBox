package io.netty.util;

import io.netty.util.concurrent.FastThreadLocal;
import java.util.Map;
import java.util.WeakHashMap;

class Recycler$3 extends FastThreadLocal<Map<Recycler$Stack<?>, Recycler$WeakOrderQueue>> {
    Recycler$3() {
    }

    protected Map<Recycler$Stack<?>, Recycler$WeakOrderQueue> initialValue() {
        return new WeakHashMap();
    }
}
