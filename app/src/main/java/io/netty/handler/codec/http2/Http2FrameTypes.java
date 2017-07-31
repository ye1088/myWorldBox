package io.netty.handler.codec.http2;

public final class Http2FrameTypes {
    public static final byte CONTINUATION = (byte) 9;
    public static final byte DATA = (byte) 0;
    public static final byte GO_AWAY = (byte) 7;
    public static final byte HEADERS = (byte) 1;
    public static final byte PING = (byte) 6;
    public static final byte PRIORITY = (byte) 2;
    public static final byte PUSH_PROMISE = (byte) 5;
    public static final byte RST_STREAM = (byte) 3;
    public static final byte SETTINGS = (byte) 4;
    public static final byte WINDOW_UPDATE = (byte) 8;

    private Http2FrameTypes() {
    }
}
