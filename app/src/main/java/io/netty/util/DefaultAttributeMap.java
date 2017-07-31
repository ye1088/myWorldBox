package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class DefaultAttributeMap implements AttributeMap {
    private static final int BUCKET_SIZE = 4;
    private static final int MASK = 3;
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater;
    private volatile AtomicReferenceArray<DefaultAttribute<?>> attributes;

    private static final class DefaultAttribute<T> extends AtomicReference<T> implements Attribute<T> {
        static final /* synthetic */ boolean $assertionsDisabled = (!DefaultAttributeMap.class.desiredAssertionStatus());
        private static final long serialVersionUID = -2661411462200283011L;
        private final DefaultAttribute<?> head;
        private final AttributeKey<T> key;
        private DefaultAttribute<?> next;
        private DefaultAttribute<?> prev;
        private volatile boolean removed;

        DefaultAttribute(DefaultAttribute<?> head, AttributeKey<T> key) {
            this.head = head;
            this.key = key;
        }

        DefaultAttribute() {
            this.head = this;
            this.key = null;
        }

        public AttributeKey<T> key() {
            return this.key;
        }

        public T setIfAbsent(T value) {
            while (!compareAndSet(null, value)) {
                T old = get();
                if (old != null) {
                    return old;
                }
            }
            return null;
        }

        public T getAndRemove() {
            this.removed = true;
            T oldValue = getAndSet(null);
            remove0();
            return oldValue;
        }

        public void remove() {
            this.removed = true;
            set(null);
            remove0();
        }

        private void remove0() {
            synchronized (this.head) {
                if (!$assertionsDisabled && this.head == null) {
                    throw new AssertionError();
                } else if (this.prev == null) {
                } else {
                    this.prev.next = this.next;
                    if (this.next != null) {
                        this.next.prev = this.prev;
                    }
                    this.prev = null;
                    this.next = null;
                }
            }
        }
    }

    static {
        AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> referenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultAttributeMap.class, "attributes");
        if (referenceFieldUpdater == null) {
            referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
        }
        updater = referenceFieldUpdater;
    }

    public <T> Attribute<T> attr(AttributeKey<T> key) {
        if (key == null) {
            throw new NullPointerException("key");
        }
        DefaultAttribute<T> attr;
        AtomicReferenceArray<DefaultAttribute<?>> attributes = this.attributes;
        if (attributes == null) {
            attributes = new AtomicReferenceArray(4);
            if (!updater.compareAndSet(this, null, attributes)) {
                attributes = this.attributes;
            }
        }
        int i = index(key);
        DefaultAttribute<?> head = (DefaultAttribute) attributes.get(i);
        if (head == null) {
            head = new DefaultAttribute();
            attr = new DefaultAttribute(head, key);
            head.next = attr;
            attr.prev = head;
            if (attributes.compareAndSet(i, null, head)) {
                return attr;
            }
            head = (DefaultAttribute) attributes.get(i);
        }
        synchronized (head) {
            DefaultAttribute<?> curr = head;
            while (true) {
                DefaultAttribute<?> next = curr.next;
                if (next == null) {
                    attr = new DefaultAttribute(head, key);
                    curr.next = attr;
                    attr.prev = curr;
                    return attr;
                } else if (next.key != key || next.removed) {
                    curr = next;
                } else {
                    return next;
                }
            }
        }
    }

    public <T> boolean hasAttr(AttributeKey<T> key) {
        boolean z = false;
        if (key == null) {
            throw new NullPointerException("key");
        }
        AtomicReferenceArray<DefaultAttribute<?>> attributes = this.attributes;
        if (attributes != null) {
            DefaultAttribute<?> head = (DefaultAttribute) attributes.get(index(key));
            if (head != null) {
                synchronized (head) {
                    DefaultAttribute<?> curr = head.next;
                    while (curr != null) {
                        if (curr.key == key && !curr.removed) {
                            z = true;
                            break;
                        }
                        curr = curr.next;
                    }
                }
            }
        }
        return z;
    }

    private static int index(AttributeKey<?> key) {
        return key.id() & 3;
    }
}
