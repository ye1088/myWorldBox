package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ConstantPool<T extends Constant<T>> {
    private final ConcurrentMap<String, T> constants = PlatformDependent.newConcurrentHashMap();
    private AtomicInteger nextId = new AtomicInteger(1);

    protected abstract T newConstant(int i, String str);

    public T valueOf(Class<?> firstNameComponent, String secondNameComponent) {
        if (firstNameComponent == null) {
            throw new NullPointerException("firstNameComponent");
        } else if (secondNameComponent != null) {
            return valueOf(firstNameComponent.getName() + '#' + secondNameComponent);
        } else {
            throw new NullPointerException("secondNameComponent");
        }
    }

    public T valueOf(String name) {
        checkNotNullAndNotEmpty(name);
        return getOrCreate(name);
    }

    private T getOrCreate(String name) {
        T constant = (Constant) this.constants.get(name);
        if (constant == null) {
            T tempConstant = newConstant(nextId(), name);
            constant = (Constant) this.constants.putIfAbsent(name, tempConstant);
            if (constant == null) {
                return tempConstant;
            }
        }
        return constant;
    }

    public boolean exists(String name) {
        checkNotNullAndNotEmpty(name);
        return this.constants.containsKey(name);
    }

    public T newInstance(String name) {
        checkNotNullAndNotEmpty(name);
        return createOrThrow(name);
    }

    private T createOrThrow(String name) {
        if (((Constant) this.constants.get(name)) == null) {
            T tempConstant = newConstant(nextId(), name);
            if (((Constant) this.constants.putIfAbsent(name, tempConstant)) == null) {
                return tempConstant;
            }
        }
        throw new IllegalArgumentException(String.format("'%s' is already in use", new Object[]{name}));
    }

    private String checkNotNullAndNotEmpty(String name) {
        ObjectUtil.checkNotNull(name, "name");
        if (!name.isEmpty()) {
            return name;
        }
        throw new IllegalArgumentException("empty name");
    }

    @Deprecated
    public final int nextId() {
        return this.nextId.getAndIncrement();
    }
}
