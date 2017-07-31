package io.netty.handler.codec.redis;

import com.xiaomi.mipush.sdk.MiPushClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import java.util.List;
import org.apache.tools.tar.TarConstants;

public final class RedisDecoder extends ByteToMessageDecoder {
    private final int maxInlineMessageLength;
    private final RedisMessagePool messagePool;
    private int remainingBulkLength;
    private State state;
    private final ToPositiveLongProcessor toPositiveLongProcessor;
    private RedisMessageType type;

    private enum State {
        DECODE_TYPE,
        DECODE_INLINE,
        DECODE_LENGTH,
        DECODE_BULK_STRING_EOL,
        DECODE_BULK_STRING_CONTENT
    }

    private static final class ToPositiveLongProcessor implements ByteProcessor {
        private long result;

        private ToPositiveLongProcessor() {
        }

        public boolean process(byte value) throws Exception {
            if (value < TarConstants.LF_NORMAL || value > (byte) 57) {
                throw new RedisCodecException("bad byte in number: " + value);
            }
            this.result = (this.result * 10) + ((long) (value - 48));
            return true;
        }

        public long content() {
            return this.result;
        }

        public void reset() {
            this.result = 0;
        }
    }

    public RedisDecoder() {
        this(65536, FixedRedisMessagePool.INSTANCE);
    }

    public RedisDecoder(int maxInlineMessageLength, RedisMessagePool messagePool) {
        this.toPositiveLongProcessor = new ToPositiveLongProcessor();
        this.state = State.DECODE_TYPE;
        if (maxInlineMessageLength <= 0 || maxInlineMessageLength > 536870912) {
            throw new RedisCodecException("maxInlineMessageLength: " + maxInlineMessageLength + " (expected: <= " + 536870912 + ")");
        }
        this.maxInlineMessageLength = maxInlineMessageLength;
        this.messagePool = messagePool;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (true) {
            try {
                switch (this.state) {
                    case DECODE_TYPE:
                        if (decodeType(in)) {
                            break;
                        }
                        return;
                    case DECODE_INLINE:
                        if (decodeInline(in, out)) {
                            break;
                        }
                        return;
                    case DECODE_LENGTH:
                        if (decodeLength(in, out)) {
                            break;
                        }
                        return;
                    case DECODE_BULK_STRING_EOL:
                        if (decodeBulkStringEndOfLine(in, out)) {
                            break;
                        }
                        return;
                    case DECODE_BULK_STRING_CONTENT:
                        if (decodeBulkStringContent(in, out)) {
                            break;
                        }
                        return;
                    default:
                        throw new RedisCodecException("Unknown state: " + this.state);
                }
            } catch (RedisCodecException e) {
                resetDecoder();
                throw e;
            } catch (Throwable e2) {
                resetDecoder();
                throw new RedisCodecException(e2);
            }
        }
    }

    private void resetDecoder() {
        this.state = State.DECODE_TYPE;
        this.remainingBulkLength = 0;
    }

    private boolean decodeType(ByteBuf in) throws Exception {
        if (!in.isReadable()) {
            return false;
        }
        this.type = RedisMessageType.valueOf(in.readByte());
        this.state = this.type.isInline() ? State.DECODE_INLINE : State.DECODE_LENGTH;
        return true;
    }

    private boolean decodeInline(ByteBuf in, List<Object> out) throws Exception {
        ByteBuf lineBytes = readLine(in);
        if (lineBytes != null) {
            out.add(newInlineRedisMessage(this.type, lineBytes));
            resetDecoder();
            return true;
        } else if (in.readableBytes() <= this.maxInlineMessageLength) {
            return false;
        } else {
            throw new RedisCodecException("length: " + in.readableBytes() + " (expected: <= " + this.maxInlineMessageLength + ")");
        }
    }

    private boolean decodeLength(ByteBuf in, List<Object> out) throws Exception {
        ByteBuf lineByteBuf = readLine(in);
        if (lineByteBuf == null) {
            return false;
        }
        long length = parseRedisNumber(lineByteBuf);
        if (length < -1) {
            throw new RedisCodecException("length: " + length + " (expected: >= " + -1 + ")");
        }
        switch (this.type) {
            case ARRAY_HEADER:
                out.add(new ArrayHeaderRedisMessage(length));
                resetDecoder();
                return true;
            case BULK_STRING:
                if (length > 536870912) {
                    throw new RedisCodecException("length: " + length + " (expected: <= " + 536870912 + ")");
                }
                this.remainingBulkLength = (int) length;
                return decodeBulkString(in, out);
            default:
                throw new RedisCodecException("bad type: " + this.type);
        }
    }

    private boolean decodeBulkString(ByteBuf in, List<Object> out) throws Exception {
        switch (this.remainingBulkLength) {
            case -1:
                out.add(FullBulkStringRedisMessage.NULL_INSTANCE);
                resetDecoder();
                return true;
            case 0:
                this.state = State.DECODE_BULK_STRING_EOL;
                return decodeBulkStringEndOfLine(in, out);
            default:
                out.add(new BulkStringHeaderRedisMessage(this.remainingBulkLength));
                this.state = State.DECODE_BULK_STRING_CONTENT;
                return decodeBulkStringContent(in, out);
        }
    }

    private boolean decodeBulkStringEndOfLine(ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 2) {
            return false;
        }
        readEndOfLine(in);
        out.add(FullBulkStringRedisMessage.EMPTY_INSTANCE);
        resetDecoder();
        return true;
    }

    private boolean decodeBulkStringContent(ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        if (readableBytes == 0) {
            return false;
        }
        if (readableBytes >= this.remainingBulkLength + 2) {
            ByteBuf content = in.readSlice(this.remainingBulkLength);
            readEndOfLine(in);
            out.add(new DefaultLastBulkStringRedisContent(content.retain()));
            resetDecoder();
            return true;
        }
        int toRead = Math.min(this.remainingBulkLength, readableBytes);
        this.remainingBulkLength -= toRead;
        out.add(new DefaultBulkStringRedisContent(in.readSlice(toRead).retain()));
        return true;
    }

    private static void readEndOfLine(ByteBuf in) {
        short delim = in.readShort();
        if (RedisConstants.EOL_SHORT != delim) {
            byte[] bytes = RedisCodecUtil.shortToBytes(delim);
            throw new RedisCodecException("delimiter: [" + bytes[0] + MiPushClient.ACCEPT_TIME_SEPARATOR + bytes[1] + "] (expected: \\r\\n)");
        }
    }

    private RedisMessage newInlineRedisMessage(RedisMessageType messageType, ByteBuf content) {
        SimpleStringRedisMessage cached;
        switch (messageType) {
            case SIMPLE_STRING:
                cached = this.messagePool.getSimpleString(content);
                if (cached != null) {
                    return cached;
                }
                return new SimpleStringRedisMessage(content.toString(CharsetUtil.UTF_8));
            case ERROR:
                cached = this.messagePool.getError(content);
                if (cached == null) {
                    return new ErrorRedisMessage(content.toString(CharsetUtil.UTF_8));
                }
                return cached;
            case INTEGER:
                cached = this.messagePool.getInteger(content);
                if (cached == null) {
                    return new IntegerRedisMessage(parseRedisNumber(content));
                }
                return cached;
            default:
                throw new RedisCodecException("bad type: " + messageType);
        }
    }

    private static ByteBuf readLine(ByteBuf in) {
        if (!in.isReadable(2)) {
            return null;
        }
        int lfIndex = in.forEachByte(ByteProcessor.FIND_LF);
        if (lfIndex < 0) {
            return null;
        }
        ByteBuf data = in.readSlice((lfIndex - in.readerIndex()) - 1);
        readEndOfLine(in);
        return data;
    }

    private long parseRedisNumber(ByteBuf byteBuf) {
        boolean negative;
        int extraOneByteForNegative = 1;
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes <= 0 || byteBuf.getByte(byteBuf.readerIndex()) != (byte) 45) {
            negative = false;
        } else {
            negative = true;
        }
        if (!negative) {
            extraOneByteForNegative = 0;
        }
        if (readableBytes <= extraOneByteForNegative) {
            throw new RedisCodecException("no number to parse: " + byteBuf.toString(CharsetUtil.US_ASCII));
        } else if (readableBytes > extraOneByteForNegative + 19) {
            throw new RedisCodecException("too many characters to be a valid RESP Integer: " + byteBuf.toString(CharsetUtil.US_ASCII));
        } else if (negative) {
            return -parsePositiveNumber(byteBuf.skipBytes(extraOneByteForNegative));
        } else {
            return parsePositiveNumber(byteBuf);
        }
    }

    private long parsePositiveNumber(ByteBuf byteBuf) {
        this.toPositiveLongProcessor.reset();
        byteBuf.forEachByte(this.toPositiveLongProcessor);
        return this.toPositiveLongProcessor.content();
    }
}
