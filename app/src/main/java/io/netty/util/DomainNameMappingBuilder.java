package io.netty.util;

import io.netty.util.internal.ObjectUtil;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DomainNameMappingBuilder<V> {
    private final V defaultValue;
    private final Map<String, V> map;

    public DomainNameMappingBuilder(V defaultValue) {
        this(4, defaultValue);
    }

    public DomainNameMappingBuilder(int initialCapacity, V defaultValue) {
        this.defaultValue = ObjectUtil.checkNotNull(defaultValue, "defaultValue");
        this.map = new LinkedHashMap(initialCapacity);
    }

    public DomainNameMappingBuilder<V> add(String hostname, V output) {
        this.map.put(ObjectUtil.checkNotNull(hostname, "hostname"), ObjectUtil.checkNotNull(output, "output"));
        return this;
    }

    public DomainNameMapping<V> build() {
        return new ImmutableDomainNameMapping(this.defaultValue, this.map, null);
    }
}
