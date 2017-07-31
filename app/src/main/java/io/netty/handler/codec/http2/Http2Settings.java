package io.netty.handler.codec.http2;

import io.netty.util.collection.CharObjectHashMap;
import io.netty.util.internal.ObjectUtil;

public final class Http2Settings extends CharObjectHashMap<Long> {
    private static final int DEFAULT_CAPACITY = 13;
    private static final Long FALSE = Long.valueOf(0);
    private static final Long TRUE = Long.valueOf(1);

    public Http2Settings() {
        this(13);
    }

    public Http2Settings(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public Http2Settings(int initialCapacity) {
        super(initialCapacity);
    }

    public Long put(char key, Long value) {
        verifyStandardSetting(key, value);
        return (Long) super.put(key, (Object) value);
    }

    public Long headerTableSize() {
        return (Long) get('\u0001');
    }

    public Http2Settings headerTableSize(int value) {
        put('\u0001', Long.valueOf((long) value));
        return this;
    }

    public Boolean pushEnabled() {
        Long value = (Long) get('\u0002');
        if (value == null) {
            return null;
        }
        return Boolean.valueOf(TRUE.equals(value));
    }

    public Http2Settings pushEnabled(boolean enabled) {
        put('\u0002', enabled ? TRUE : FALSE);
        return this;
    }

    public Long maxConcurrentStreams() {
        return (Long) get('\u0003');
    }

    public Http2Settings maxConcurrentStreams(long value) {
        put('\u0003', Long.valueOf(value));
        return this;
    }

    public Integer initialWindowSize() {
        return getIntValue('\u0004');
    }

    public Http2Settings initialWindowSize(int value) {
        put('\u0004', Long.valueOf((long) value));
        return this;
    }

    public Integer maxFrameSize() {
        return getIntValue('\u0005');
    }

    public Http2Settings maxFrameSize(int value) {
        put('\u0005', Long.valueOf((long) value));
        return this;
    }

    public Integer maxHeaderListSize() {
        Integer value = getIntValue('\u0006');
        if (value == null || value.intValue() >= 0) {
            return value;
        }
        return Integer.valueOf(Integer.MAX_VALUE);
    }

    public Http2Settings maxHeaderListSize(int value) {
        if (value < 0) {
            value = Integer.MAX_VALUE;
        }
        put('\u0006', Long.valueOf((long) value));
        return this;
    }

    public Http2Settings copyFrom(Http2Settings settings) {
        clear();
        putAll(settings);
        return this;
    }

    public Integer getIntValue(char key) {
        Long value = (Long) get(key);
        if (value == null) {
            return null;
        }
        return Integer.valueOf(value.intValue());
    }

    private static void verifyStandardSetting(int key, Long value) {
        ObjectUtil.checkNotNull(value, "value");
        switch (key) {
            case 1:
                if (value.longValue() < 0 || value.longValue() > 2147483647L) {
                    throw new IllegalArgumentException("Setting HEADER_TABLE_SIZE is invalid: " + value);
                }
                return;
            case 2:
                if (value.longValue() != 0 && value.longValue() != 1) {
                    throw new IllegalArgumentException("Setting ENABLE_PUSH is invalid: " + value);
                }
                return;
            case 3:
                if (value.longValue() < 0 || value.longValue() > 4294967295L) {
                    throw new IllegalArgumentException("Setting MAX_CONCURRENT_STREAMS is invalid: " + value);
                }
                return;
            case 4:
                if (value.longValue() < 0 || value.longValue() > 2147483647L) {
                    throw new IllegalArgumentException("Setting INITIAL_WINDOW_SIZE is invalid: " + value);
                }
                return;
            case 5:
                if (!Http2CodecUtil.isMaxFrameSizeValid(value.intValue())) {
                    throw new IllegalArgumentException("Setting MAX_FRAME_SIZE is invalid: " + value);
                }
                return;
            case 6:
                if (value.longValue() < 0 || value.longValue() > Long.MAX_VALUE) {
                    throw new IllegalArgumentException("Setting MAX_HEADER_LIST_SIZE is invalid: " + value);
                }
                return;
            default:
                return;
        }
    }

    protected String keyToString(char key) {
        switch (key) {
            case '\u0001':
                return "HEADER_TABLE_SIZE";
            case '\u0002':
                return "ENABLE_PUSH";
            case '\u0003':
                return "MAX_CONCURRENT_STREAMS";
            case '\u0004':
                return "INITIAL_WINDOW_SIZE";
            case '\u0005':
                return "MAX_FRAME_SIZE";
            case '\u0006':
                return "MAX_HEADER_LIST_SIZE";
            default:
                return super.keyToString(key);
        }
    }
}
