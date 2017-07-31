package io.netty.handler.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        int preIndex = in.readerIndex();
        int length = readRawVarint32(in);
        if (preIndex != in.readerIndex()) {
            if (length < 0) {
                throw new CorruptedFrameException("negative length: " + length);
            } else if (in.readableBytes() < length) {
                in.resetReaderIndex();
            } else {
                out.add(in.readRetainedSlice(length));
            }
        }
    }

    private static int readRawVarint32(ByteBuf buffer) {
        if (!buffer.isReadable()) {
            return 0;
        }
        buffer.markReaderIndex();
        int tmp = buffer.readByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & 127;
        if (buffer.isReadable()) {
            byte tmp2 = buffer.readByte();
            if (tmp2 >= (byte) 0) {
                result |= tmp2 << 7;
            } else {
                result |= (tmp2 & 127) << 7;
                if (buffer.isReadable()) {
                    tmp2 = buffer.readByte();
                    if (tmp2 >= (byte) 0) {
                        result |= tmp2 << 14;
                    } else {
                        result |= (tmp2 & 127) << 14;
                        if (buffer.isReadable()) {
                            tmp2 = buffer.readByte();
                            if (tmp2 >= (byte) 0) {
                                result |= tmp2 << 21;
                            } else {
                                result |= (tmp2 & 127) << 21;
                                if (buffer.isReadable()) {
                                    tmp2 = buffer.readByte();
                                    result |= tmp2 << 28;
                                    if (tmp2 < (byte) 0) {
                                        throw new CorruptedFrameException("malformed varint.");
                                    }
                                }
                                buffer.resetReaderIndex();
                                return 0;
                            }
                        }
                        buffer.resetReaderIndex();
                        return 0;
                    }
                }
                buffer.resetReaderIndex();
                return 0;
            }
            return result;
        }
        buffer.resetReaderIndex();
        return 0;
    }
}
