package io.netty.buffer;

import io.netty.util.ByteProcessor;

@Deprecated
public interface ByteBufProcessor extends ByteProcessor {
    @Deprecated
    public static final ByteBufProcessor FIND_CR = new 3();
    @Deprecated
    public static final ByteBufProcessor FIND_CRLF = new 7();
    @Deprecated
    public static final ByteBufProcessor FIND_LF = new 5();
    @Deprecated
    public static final ByteBufProcessor FIND_LINEAR_WHITESPACE = new 9();
    @Deprecated
    public static final ByteBufProcessor FIND_NON_CR = new 4();
    @Deprecated
    public static final ByteBufProcessor FIND_NON_CRLF = new 8();
    @Deprecated
    public static final ByteBufProcessor FIND_NON_LF = new 6();
    @Deprecated
    public static final ByteBufProcessor FIND_NON_LINEAR_WHITESPACE = new 10();
    @Deprecated
    public static final ByteBufProcessor FIND_NON_NUL = new 2();
    @Deprecated
    public static final ByteBufProcessor FIND_NUL = new 1();
}
