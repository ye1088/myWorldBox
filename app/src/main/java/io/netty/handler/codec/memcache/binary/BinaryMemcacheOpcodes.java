package io.netty.handler.codec.memcache.binary;

public final class BinaryMemcacheOpcodes {
    public static final byte ADD = (byte) 2;
    public static final byte ADDQ = (byte) 18;
    public static final byte APPEND = (byte) 14;
    public static final byte APPENDQ = (byte) 25;
    public static final byte DECREMENT = (byte) 6;
    public static final byte DECREMENTQ = (byte) 22;
    public static final byte DELETE = (byte) 4;
    public static final byte DELETEQ = (byte) 20;
    public static final byte FLUSH = (byte) 8;
    public static final byte FLUSHQ = (byte) 24;
    public static final byte GAT = (byte) 29;
    public static final byte GATK = (byte) 35;
    public static final byte GATKQ = (byte) 36;
    public static final byte GATQ = (byte) 30;
    public static final byte GET = (byte) 0;
    public static final byte GETK = (byte) 12;
    public static final byte GETKQ = (byte) 13;
    public static final byte GETQ = (byte) 9;
    public static final byte INCREMENT = (byte) 5;
    public static final byte INCREMENTQ = (byte) 21;
    public static final byte NOOP = (byte) 10;
    public static final byte PREPEND = (byte) 15;
    public static final byte PREPENDQ = (byte) 26;
    public static final byte QUIT = (byte) 7;
    public static final byte QUITQ = (byte) 23;
    public static final byte REPLACE = (byte) 3;
    public static final byte REPLACEQ = (byte) 19;
    public static final byte SASL_AUTH = (byte) 33;
    public static final byte SASL_LIST_MECHS = (byte) 32;
    public static final byte SASL_STEP = (byte) 34;
    public static final byte SET = (byte) 1;
    public static final byte SETQ = (byte) 17;
    public static final byte STAT = (byte) 16;
    public static final byte TOUCH = (byte) 28;
    public static final byte VERSION = (byte) 11;

    private BinaryMemcacheOpcodes() {
    }
}
