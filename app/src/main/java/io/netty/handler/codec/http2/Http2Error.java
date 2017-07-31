package io.netty.handler.codec.http2;

public enum Http2Error {
    NO_ERROR(0),
    PROTOCOL_ERROR(1),
    INTERNAL_ERROR(2),
    FLOW_CONTROL_ERROR(3),
    SETTINGS_TIMEOUT(4),
    STREAM_CLOSED(5),
    FRAME_SIZE_ERROR(6),
    REFUSED_STREAM(7),
    CANCEL(8),
    COMPRESSION_ERROR(9),
    CONNECT_ERROR(10),
    ENHANCE_YOUR_CALM(11),
    INADEQUATE_SECURITY(12),
    HTTP_1_1_REQUIRED(13);
    
    private static final Http2Error[] INT_TO_ENUM_MAP = null;
    private final long code;

    static {
        Http2Error[] errors = values();
        Http2Error[] map = new Http2Error[errors.length];
        for (Http2Error error : errors) {
            map[(int) error.code()] = error;
        }
        INT_TO_ENUM_MAP = map;
    }

    private Http2Error(long code) {
        this.code = code;
    }

    public long code() {
        return this.code;
    }

    public static Http2Error valueOf(long value) {
        return (value >= ((long) INT_TO_ENUM_MAP.length) || value < 0) ? null : INT_TO_ENUM_MAP[(int) value];
    }
}
