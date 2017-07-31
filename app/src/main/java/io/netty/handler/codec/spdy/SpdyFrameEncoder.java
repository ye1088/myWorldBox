package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.nio.ByteOrder;
import java.util.Set;

public class SpdyFrameEncoder {
    private final int version;

    public SpdyFrameEncoder(SpdyVersion spdyVersion) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        }
        this.version = spdyVersion.getVersion();
    }

    private void writeControlFrameHeader(ByteBuf buffer, int type, byte flags, int length) {
        buffer.writeShort(this.version | 32768);
        buffer.writeShort(type);
        buffer.writeByte(flags);
        buffer.writeMedium(length);
    }

    public ByteBuf encodeDataFrame(ByteBufAllocator allocator, int streamId, boolean last, ByteBuf data) {
        byte flags = last ? (byte) 1 : (byte) 0;
        int length = data.readableBytes();
        ByteBuf frame = allocator.ioBuffer(length + 8).order(ByteOrder.BIG_ENDIAN);
        frame.writeInt(Integer.MAX_VALUE & streamId);
        frame.writeByte(flags);
        frame.writeMedium(length);
        frame.writeBytes(data, data.readerIndex(), length);
        return frame;
    }

    public ByteBuf encodeSynStreamFrame(ByteBufAllocator allocator, int streamId, int associatedToStreamId, byte priority, boolean last, boolean unidirectional, ByteBuf headerBlock) {
        int headerBlockLength = headerBlock.readableBytes();
        byte flags = last ? (byte) 1 : (byte) 0;
        if (unidirectional) {
            flags = (byte) (flags | 2);
        }
        int length = headerBlockLength + 10;
        ByteBuf frame = allocator.ioBuffer(length + 8).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 1, flags, length);
        frame.writeInt(streamId);
        frame.writeInt(associatedToStreamId);
        frame.writeShort((priority & 255) << 13);
        frame.writeBytes(headerBlock, headerBlock.readerIndex(), headerBlockLength);
        return frame;
    }

    public ByteBuf encodeSynReplyFrame(ByteBufAllocator allocator, int streamId, boolean last, ByteBuf headerBlock) {
        int headerBlockLength = headerBlock.readableBytes();
        byte flags = last ? (byte) 1 : (byte) 0;
        int length = headerBlockLength + 4;
        ByteBuf frame = allocator.ioBuffer(length + 8).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 2, flags, length);
        frame.writeInt(streamId);
        frame.writeBytes(headerBlock, headerBlock.readerIndex(), headerBlockLength);
        return frame;
    }

    public ByteBuf encodeRstStreamFrame(ByteBufAllocator allocator, int streamId, int statusCode) {
        ByteBuf frame = allocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 3, (byte) 0, 8);
        frame.writeInt(streamId);
        frame.writeInt(statusCode);
        return frame;
    }

    public ByteBuf encodeSettingsFrame(ByteBufAllocator allocator, SpdySettingsFrame spdySettingsFrame) {
        Set<Integer> ids = spdySettingsFrame.ids();
        int numSettings = ids.size();
        byte flags = spdySettingsFrame.clearPreviouslyPersistedSettings() ? (byte) 1 : (byte) 0;
        int length = (numSettings * 8) + 4;
        ByteBuf frame = allocator.ioBuffer(length + 8).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 4, flags, length);
        frame.writeInt(numSettings);
        for (Integer id : ids) {
            flags = (byte) 0;
            if (spdySettingsFrame.isPersistValue(id.intValue())) {
                flags = (byte) 1;
            }
            if (spdySettingsFrame.isPersisted(id.intValue())) {
                flags = (byte) (flags | 2);
            }
            frame.writeByte(flags);
            frame.writeMedium(id.intValue());
            frame.writeInt(spdySettingsFrame.getValue(id.intValue()));
        }
        return frame;
    }

    public ByteBuf encodePingFrame(ByteBufAllocator allocator, int id) {
        ByteBuf frame = allocator.ioBuffer(12).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 6, (byte) 0, 4);
        frame.writeInt(id);
        return frame;
    }

    public ByteBuf encodeGoAwayFrame(ByteBufAllocator allocator, int lastGoodStreamId, int statusCode) {
        ByteBuf frame = allocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 7, (byte) 0, 8);
        frame.writeInt(lastGoodStreamId);
        frame.writeInt(statusCode);
        return frame;
    }

    public ByteBuf encodeHeadersFrame(ByteBufAllocator allocator, int streamId, boolean last, ByteBuf headerBlock) {
        int headerBlockLength = headerBlock.readableBytes();
        byte flags = last ? (byte) 1 : (byte) 0;
        int length = headerBlockLength + 4;
        ByteBuf frame = allocator.ioBuffer(length + 8).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 8, flags, length);
        frame.writeInt(streamId);
        frame.writeBytes(headerBlock, headerBlock.readerIndex(), headerBlockLength);
        return frame;
    }

    public ByteBuf encodeWindowUpdateFrame(ByteBufAllocator allocator, int streamId, int deltaWindowSize) {
        ByteBuf frame = allocator.ioBuffer(16).order(ByteOrder.BIG_ENDIAN);
        writeControlFrameHeader(frame, 9, (byte) 0, 8);
        frame.writeInt(streamId);
        frame.writeInt(deltaWindowSize);
        return frame;
    }
}
