package io.netty.handler.codec.memcache.binary;

public final class BinaryMemcacheResponseStatus {
    public static final short AUTH_CONTINUE = (short) 33;
    public static final short AUTH_ERROR = (short) 32;
    public static final short DELTA_BADVAL = (short) 6;
    public static final short E2BIG = (short) 3;
    public static final short EINVA = (short) 4;
    public static final short ENOMEM = (short) 130;
    public static final short KEY_EEXISTS = (short) 2;
    public static final short KEY_ENOENT = (short) 1;
    public static final short NOT_STORED = (short) 5;
    public static final short SUCCESS = (short) 0;
    public static final short UNKNOWN_COMMAND = (short) 129;

    private BinaryMemcacheResponseStatus() {
    }
}
