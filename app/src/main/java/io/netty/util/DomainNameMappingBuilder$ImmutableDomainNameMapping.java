package io.netty.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class DomainNameMappingBuilder$ImmutableDomainNameMapping<V> extends DomainNameMapping<V> {
    private static final int REPR_CONST_PART_LENGTH = ((REPR_HEADER.length() + REPR_MAP_OPENING.length()) + REPR_MAP_CLOSING.length());
    private static final String REPR_HEADER = "ImmutableDomainNameMapping(default: ";
    private static final String REPR_MAP_CLOSING = "})";
    private static final String REPR_MAP_OPENING = ", map: {";
    private final String[] domainNamePatterns;
    private final Map<String, V> map;
    private final V[] values;

    private DomainNameMappingBuilder$ImmutableDomainNameMapping(V defaultValue, Map<String, V> map) {
        super(null, (Object) defaultValue);
        Set<Entry<String, V>> mappings = map.entrySet();
        int numberOfMappings = mappings.size();
        this.domainNamePatterns = new String[numberOfMappings];
        this.values = new Object[numberOfMappings];
        Map<String, V> mapCopy = new LinkedHashMap(map.size());
        int index = 0;
        for (Entry<String, V> mapping : mappings) {
            String hostname = DomainNameMapping.normalizeHostname((String) mapping.getKey());
            V value = mapping.getValue();
            this.domainNamePatterns[index] = hostname;
            this.values[index] = value;
            mapCopy.put(hostname, value);
            index++;
        }
        this.map = Collections.unmodifiableMap(mapCopy);
    }

    @Deprecated
    public DomainNameMapping<V> add(String hostname, V v) {
        throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
    }

    public V map(String hostname) {
        if (hostname != null) {
            hostname = DomainNameMapping.normalizeHostname(hostname);
            int length = this.domainNamePatterns.length;
            for (int index = 0; index < length; index++) {
                if (DomainNameMapping.matches(this.domainNamePatterns[index], hostname)) {
                    return this.values[index];
                }
            }
        }
        return this.defaultValue;
    }

    public Map<String, V> asMap() {
        return this.map;
    }

    public String toString() {
        String defaultValueStr = this.defaultValue.toString();
        int numberOfMappings = this.domainNamePatterns.length;
        if (numberOfMappings == 0) {
            return REPR_HEADER + defaultValueStr + REPR_MAP_OPENING + REPR_MAP_CLOSING;
        }
        String pattern0 = this.domainNamePatterns[0];
        String value0 = this.values[0].toString();
        StringBuilder sb = new StringBuilder(estimateBufferSize(defaultValueStr.length(), numberOfMappings, (pattern0.length() + value0.length()) + 3)).append(REPR_HEADER).append(defaultValueStr).append(REPR_MAP_OPENING);
        appendMapping(sb, pattern0, value0);
        for (int index = 1; index < numberOfMappings; index++) {
            sb.append(", ");
            appendMapping(sb, index);
        }
        return sb.append(REPR_MAP_CLOSING).toString();
    }

    private static int estimateBufferSize(int defaultValueLength, int numberOfMappings, int estimatedMappingLength) {
        return (REPR_CONST_PART_LENGTH + defaultValueLength) + ((int) (((double) (estimatedMappingLength * numberOfMappings)) * 1.1d));
    }

    private StringBuilder appendMapping(StringBuilder sb, int mappingIndex) {
        return appendMapping(sb, this.domainNamePatterns[mappingIndex], this.values[mappingIndex].toString());
    }

    private static StringBuilder appendMapping(StringBuilder sb, String domainNamePattern, String value) {
        return sb.append(domainNamePattern).append('=').append(value);
    }
}
