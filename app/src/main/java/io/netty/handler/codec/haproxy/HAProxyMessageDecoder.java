package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ProtocolDetectionResult;
import io.netty.util.CharsetUtil;
import java.util.List;
import org.apache.tools.tar.TarConstants;

public class HAProxyMessageDecoder extends ByteToMessageDecoder {
    private static final byte[] BINARY_PREFIX = new byte[]{(byte) 13, (byte) 10, (byte) 13, (byte) 10, (byte) 0, (byte) 13, (byte) 10, (byte) 81, (byte) 85, (byte) 73, (byte) 84, (byte) 10};
    private static final int BINARY_PREFIX_LENGTH = BINARY_PREFIX.length;
    private static final int DELIMITER_LENGTH = 2;
    private static final ProtocolDetectionResult<HAProxyProtocolVersion> DETECTION_RESULT_V1 = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V1);
    private static final ProtocolDetectionResult<HAProxyProtocolVersion> DETECTION_RESULT_V2 = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V2);
    private static final byte[] TEXT_PREFIX = new byte[]{(byte) 80, (byte) 82, (byte) 79, TarConstants.LF_PAX_EXTENDED_HEADER_UC, (byte) 89};
    private static final int V1_MAX_LENGTH = 108;
    private static final int V2_MAX_LENGTH = 65551;
    private static final int V2_MAX_TLV = 65319;
    private static final int V2_MIN_LENGTH = 232;
    private int discardedBytes;
    private boolean discarding;
    private boolean finished;
    private final int v2MaxHeaderSize;
    private int version;

    public HAProxyMessageDecoder() {
        this.version = -1;
        this.v2MaxHeaderSize = 65551;
    }

    public HAProxyMessageDecoder(int maxTlvSize) {
        this.version = -1;
        if (maxTlvSize < 1) {
            this.v2MaxHeaderSize = V2_MIN_LENGTH;
        } else if (maxTlvSize > V2_MAX_TLV) {
            this.v2MaxHeaderSize = 65551;
        } else {
            int calcMax = maxTlvSize + V2_MIN_LENGTH;
            if (calcMax > 65551) {
                this.v2MaxHeaderSize = 65551;
            } else {
                this.v2MaxHeaderSize = calcMax;
            }
        }
    }

    private static int findVersion(ByteBuf buffer) {
        if (buffer.readableBytes() < 13) {
            return -1;
        }
        int idx = buffer.readerIndex();
        return match(BINARY_PREFIX, buffer, idx) ? buffer.getByte(BINARY_PREFIX_LENGTH + idx) : 1;
    }

    private static int findEndOfHeader(ByteBuf buffer) {
        int n = buffer.readableBytes();
        if (n < 16) {
            return -1;
        }
        int totalHeaderBytes = buffer.getUnsignedShort(buffer.readerIndex() + 14) + 16;
        if (n < totalHeaderBytes) {
            return -1;
        }
        return totalHeaderBytes;
    }

    private static int findEndOfLine(ByteBuf buffer) {
        int n = buffer.writerIndex();
        int i = buffer.readerIndex();
        while (i < n) {
            if (buffer.getByte(i) == (byte) 13 && i < n - 1 && buffer.getByte(i + 1) == (byte) 10) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean isSingleDecode() {
        return true;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (this.finished) {
            ctx.pipeline().remove((ChannelHandler) this);
        }
    }

    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf decoded;
        if (this.version == -1) {
            int findVersion = findVersion(in);
            this.version = findVersion;
            if (findVersion == -1) {
                return;
            }
        }
        if (this.version == 1) {
            decoded = decodeLine(ctx, in);
        } else {
            decoded = decodeStruct(ctx, in);
        }
        if (decoded != null) {
            this.finished = true;
            try {
                if (this.version == 1) {
                    out.add(HAProxyMessage.decodeHeader(decoded.toString(CharsetUtil.US_ASCII)));
                } else {
                    out.add(HAProxyMessage.decodeHeader(decoded));
                }
            } catch (HAProxyProtocolException e) {
                fail(ctx, null, e);
            }
        }
    }

    private ByteBuf decodeStruct(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        int eoh = findEndOfHeader(buffer);
        if (this.discarding) {
            if (eoh >= 0) {
                buffer.readerIndex(eoh);
                this.discardedBytes = 0;
                this.discarding = false;
                return null;
            }
            this.discardedBytes = buffer.readableBytes();
            buffer.skipBytes(this.discardedBytes);
            return null;
        } else if (eoh >= 0) {
            length = eoh - buffer.readerIndex();
            if (length <= this.v2MaxHeaderSize) {
                return buffer.readSlice(length);
            }
            buffer.readerIndex(eoh);
            failOverLimit(ctx, length);
            return null;
        } else {
            length = buffer.readableBytes();
            if (length <= this.v2MaxHeaderSize) {
                return null;
            }
            this.discardedBytes = length;
            buffer.skipBytes(length);
            this.discarding = true;
            failOverLimit(ctx, "over " + this.discardedBytes);
            return null;
        }
    }

    private ByteBuf decodeLine(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        int delimLength = 2;
        int eol = findEndOfLine(buffer);
        if (this.discarding) {
            if (eol >= 0) {
                if (buffer.getByte(eol) != (byte) 13) {
                    delimLength = 1;
                }
                buffer.readerIndex(eol + delimLength);
                this.discardedBytes = 0;
                this.discarding = false;
                return null;
            }
            this.discardedBytes = buffer.readableBytes();
            buffer.skipBytes(this.discardedBytes);
            return null;
        } else if (eol >= 0) {
            length = eol - buffer.readerIndex();
            if (length > 108) {
                buffer.readerIndex(eol + 2);
                failOverLimit(ctx, length);
                return null;
            }
            ByteBuf frame = buffer.readSlice(length);
            buffer.skipBytes(2);
            return frame;
        } else {
            length = buffer.readableBytes();
            if (length <= 108) {
                return null;
            }
            this.discardedBytes = length;
            buffer.skipBytes(length);
            this.discarding = true;
            failOverLimit(ctx, "over " + this.discardedBytes);
            return null;
        }
    }

    private void failOverLimit(ChannelHandlerContext ctx, int length) {
        failOverLimit(ctx, String.valueOf(length));
    }

    private void failOverLimit(ChannelHandlerContext ctx, String length) {
        fail(ctx, "header length (" + length + ") exceeds the allowed maximum (" + (this.version == 1 ? 108 : this.v2MaxHeaderSize) + ')', null);
    }

    private void fail(ChannelHandlerContext ctx, String errMsg, Throwable t) {
        HAProxyProtocolException ppex;
        this.finished = true;
        ctx.close();
        if (errMsg != null && t != null) {
            ppex = new HAProxyProtocolException(errMsg, t);
        } else if (errMsg != null) {
            ppex = new HAProxyProtocolException(errMsg);
        } else if (t != null) {
            ppex = new HAProxyProtocolException(t);
        } else {
            ppex = new HAProxyProtocolException();
        }
        throw ppex;
    }

    public static ProtocolDetectionResult<HAProxyProtocolVersion> detectProtocol(ByteBuf buffer) {
        if (buffer.readableBytes() < 12) {
            return ProtocolDetectionResult.needsMoreData();
        }
        int idx = buffer.readerIndex();
        if (match(BINARY_PREFIX, buffer, idx)) {
            return DETECTION_RESULT_V2;
        }
        if (match(TEXT_PREFIX, buffer, idx)) {
            return DETECTION_RESULT_V1;
        }
        return ProtocolDetectionResult.invalid();
    }

    private static boolean match(byte[] prefix, ByteBuf buffer, int idx) {
        for (int i = 0; i < prefix.length; i++) {
            if (buffer.getByte(idx + i) != prefix[i]) {
                return false;
            }
        }
        return true;
    }
}
