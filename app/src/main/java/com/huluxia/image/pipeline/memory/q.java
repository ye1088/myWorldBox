package com.huluxia.image.pipeline.memory;

import com.huluxia.image.core.common.references.b;
import java.util.LinkedList;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
/* compiled from: OOMSoftReferenceBucket */
class q<V> extends e<V> {
    private LinkedList<b<V>> Ic = new LinkedList();

    public q(int itemSize, int maxLength, int inUseLength) {
        super(itemSize, maxLength, inUseLength);
    }

    public V pop() {
        b<V> ref = (b) this.HH.poll();
        V value = ref.get();
        ref.clear();
        this.Ic.add(ref);
        return value;
    }

    void L(V value) {
        b<V> ref = (b) this.Ic.poll();
        if (ref == null) {
            ref = new b();
        }
        ref.set(value);
        this.HH.add(ref);
    }
}
