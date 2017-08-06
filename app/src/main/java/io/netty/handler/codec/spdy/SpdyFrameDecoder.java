package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bytedeco.javacpp.avutil;

public class SpdyFrameDecoder {
    private final SpdyFrameDecoderDelegate delegate;
    private byte flags;
    private int length;
    private final int maxChunkSize;
    private int numSettings;
    private final int spdyVersion;
    private State state;
    private int streamId;

    private enum State {
        READ_COMMON_HEADER,
        READ_DATA_FRAME,
        READ_SYN_STREAM_FRAME,
        READ_SYN_REPLY_FRAME,
        READ_RST_STREAM_FRAME,
        READ_SETTINGS_FRAME,
        READ_SETTING,
        READ_PING_FRAME,
        READ_GOAWAY_FRAME,
        READ_HEADERS_FRAME,
        READ_WINDOW_UPDATE_FRAME,
        READ_HEADER_BLOCK,
        DISCARD_FRAME,
        FRAME_ERROR
    }

    public SpdyFrameDecoder(SpdyVersion spdyVersion, SpdyFrameDecoderDelegate delegate) {
        this(spdyVersion, delegate, 8192);
    }

    public SpdyFrameDecoder(SpdyVersion spdyVersion, SpdyFrameDecoderDelegate delegate, int maxChunkSize) {
        if (spdyVersion == null) {
            throw new NullPointerException("spdyVersion");
        } else if (delegate == null) {
            throw new NullPointerException("delegate");
        } else if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a_isRightVersion positive integer: " + maxChunkSize);
        } else {
            this.spdyVersion = spdyVersion.getVersion();
            this.delegate = delegate;
            this.maxChunkSize = maxChunkSize;
            this.state = State.READ_COMMON_HEADER;
        }
    }

    public void decode(ByteBuf buffer) {
        while (true) {
            boolean last;
            int statusCode;
            switch (this.state) {
                case READ_COMMON_HEADER:
                    if (buffer.readableBytes() >= 8) {
                        int version;
                        int type;
                        int frameOffset = buffer.readerIndex();
                        int flagsOffset = frameOffset + 4;
                        int lengthOffset = frameOffset + 5;
                        buffer.skipBytes(8);
                        if ((buffer.getByte(frameOffset) & 128) != 0) {
                            version = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset) & avutil.FF_LAMBDA_MAX;
                            type = SpdyCodecUtil.getUnsignedShort(buffer, frameOffset + 2);
                            this.streamId = 0;
                        } else {
                            version = this.spdyVersion;
                            type = 0;
                            this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, frameOffset);
                        }
                        this.flags = buffer.getByte(flagsOffset);
                        this.length = SpdyCodecUtil.getUnsignedMedium(buffer, lengthOffset);
                        if (version == this.spdyVersion) {
                            if (!isValidFrameHeader(this.streamId, type, this.flags, this.length)) {
                                this.state = State.FRAME_ERROR;
                                this.delegate.readFrameError("Invalid Frame Error");
                                break;
                            }
                            this.state = getNextState(type, this.length);
                            break;
                        }
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SPDY Version");
                        break;
                    }
                    return;
                case READ_DATA_FRAME:
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readDataFrame(this.streamId, hasFlag(this.flags, (byte) 1), Unpooled.buffer(0));
                        break;
                    }
                    int dataLength = Math.min(this.maxChunkSize, this.length);
                    if (buffer.readableBytes() >= dataLength) {
                        ByteBuf data = buffer.alloc().buffer(dataLength);
                        data.writeBytes(buffer, dataLength);
                        this.length -= dataLength;
                        if (this.length == 0) {
                            this.state = State.READ_COMMON_HEADER;
                        }
                        last = this.length == 0 && hasFlag(this.flags, (byte) 1);
                        this.delegate.readDataFrame(this.streamId, last, data);
                        break;
                    }
                    return;
                case READ_SYN_STREAM_FRAME:
                    if (buffer.readableBytes() >= 10) {
                        int offset = buffer.readerIndex();
                        this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, offset);
                        int associatedToStreamId = SpdyCodecUtil.getUnsignedInt(buffer, offset + 4);
                        byte priority = (byte) ((buffer.getByte(offset + 8) >> 5) & 7);
                        last = hasFlag(this.flags, (byte) 1);
                        boolean unidirectional = hasFlag(this.flags, (byte) 2);
                        buffer.skipBytes(10);
                        this.length -= 10;
                        if (this.streamId != 0) {
                            this.state = State.READ_HEADER_BLOCK;
                            this.delegate.readSynStreamFrame(this.streamId, associatedToStreamId, priority, last, unidirectional);
                            break;
                        }
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SYN_STREAM Frame");
                        break;
                    }
                    return;
                case READ_SYN_REPLY_FRAME:
                    if (buffer.readableBytes() >= 4) {
                        this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        last = hasFlag(this.flags, (byte) 1);
                        buffer.skipBytes(4);
                        this.length -= 4;
                        if (this.streamId != 0) {
                            this.state = State.READ_HEADER_BLOCK;
                            this.delegate.readSynReplyFrame(this.streamId, last);
                            break;
                        }
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid SYN_REPLY Frame");
                        break;
                    }
                    return;
                case READ_RST_STREAM_FRAME:
                    if (buffer.readableBytes() >= 8) {
                        this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
                        buffer.skipBytes(8);
                        if (this.streamId != 0 && statusCode != 0) {
                            this.state = State.READ_COMMON_HEADER;
                            this.delegate.readRstStreamFrame(this.streamId, statusCode);
                            break;
                        }
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid RST_STREAM Frame");
                        break;
                    }
                    return;
                    break;
                case READ_SETTINGS_FRAME:
                    if (buffer.readableBytes() >= 4) {
                        boolean clear = hasFlag(this.flags, (byte) 1);
                        this.numSettings = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        buffer.skipBytes(4);
                        this.length -= 4;
                        if ((this.length & 7) != 0 || (this.length >> 3) != this.numSettings) {
                            this.state = State.FRAME_ERROR;
                            this.delegate.readFrameError("Invalid SETTINGS Frame");
                            break;
                        }
                        this.state = State.READ_SETTING;
                        this.delegate.readSettingsFrame(clear);
                        break;
                    }
                    return;
                case READ_SETTING:
                    if (this.numSettings == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readSettingsEnd();
                        break;
                    } else if (buffer.readableBytes() >= 8) {
                        byte settingsFlags = buffer.getByte(buffer.readerIndex());
                        int id = SpdyCodecUtil.getUnsignedMedium(buffer, buffer.readerIndex() + 1);
                        int value = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
                        boolean persistValue = hasFlag(settingsFlags, (byte) 1);
                        boolean persisted = hasFlag(settingsFlags, (byte) 2);
                        buffer.skipBytes(8);
                        this.numSettings--;
                        this.delegate.readSetting(id, value, persistValue, persisted);
                        break;
                    } else {
                        return;
                    }
                case READ_PING_FRAME:
                    if (buffer.readableBytes() >= 4) {
                        int pingId = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex());
                        buffer.skipBytes(4);
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readPingFrame(pingId);
                        break;
                    }
                    return;
                case READ_GOAWAY_FRAME:
                    if (buffer.readableBytes() >= 8) {
                        int lastGoodStreamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        statusCode = SpdyCodecUtil.getSignedInt(buffer, buffer.readerIndex() + 4);
                        buffer.skipBytes(8);
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readGoAwayFrame(lastGoodStreamId, statusCode);
                        break;
                    }
                    return;
                case READ_HEADERS_FRAME:
                    if (buffer.readableBytes() >= 4) {
                        this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        last = hasFlag(this.flags, (byte) 1);
                        buffer.skipBytes(4);
                        this.length -= 4;
                        if (this.streamId != 0) {
                            this.state = State.READ_HEADER_BLOCK;
                            this.delegate.readHeadersFrame(this.streamId, last);
                            break;
                        }
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid HEADERS Frame");
                        break;
                    }
                    return;
                case READ_WINDOW_UPDATE_FRAME:
                    if (buffer.readableBytes() >= 8) {
                        this.streamId = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex());
                        int deltaWindowSize = SpdyCodecUtil.getUnsignedInt(buffer, buffer.readerIndex() + 4);
                        buffer.skipBytes(8);
                        if (deltaWindowSize != 0) {
                            this.state = State.READ_COMMON_HEADER;
                            this.delegate.readWindowUpdateFrame(this.streamId, deltaWindowSize);
                            break;
                        }
                        this.state = State.FRAME_ERROR;
                        this.delegate.readFrameError("Invalid WINDOW_UPDATE Frame");
                        break;
                    }
                    return;
                case READ_HEADER_BLOCK:
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        this.delegate.readHeaderBlockEnd();
                        break;
                    } else if (buffer.isReadable()) {
                        int compressedBytes = Math.min(buffer.readableBytes(), this.length);
                        ByteBuf headerBlock = buffer.alloc().buffer(compressedBytes);
                        headerBlock.writeBytes(buffer, compressedBytes);
                        this.length -= compressedBytes;
                        this.delegate.readHeaderBlock(headerBlock);
                        break;
                    } else {
                        return;
                    }
                case DISCARD_FRAME:
                    int numBytes = Math.min(buffer.readableBytes(), this.length);
                    buffer.skipBytes(numBytes);
                    this.length -= numBytes;
                    if (this.length == 0) {
                        this.state = State.READ_COMMON_HEADER;
                        break;
                    }
                    return;
                case FRAME_ERROR:
                    buffer.skipBytes(buffer.readableBytes());
                    return;
                default:
                    throw new Error("Shouldn't reach here.");
            }
        }
    }

    private static boolean hasFlag(byte flags, byte flag) {
        return (flags & flag) != 0;
    }

    private static State getNextState(int type, int length) {
        switch (type) {
            case 0:
                return State.READ_DATA_FRAME;
            case 1:
                return State.READ_SYN_STREAM_FRAME;
            case 2:
                return State.READ_SYN_REPLY_FRAME;
            case 3:
                return State.READ_RST_STREAM_FRAME;
            case 4:
                return State.READ_SETTINGS_FRAME;
            case 6:
                return State.READ_PING_FRAME;
            case 7:
                return State.READ_GOAWAY_FRAME;
            case 8:
                return State.READ_HEADERS_FRAME;
            case 9:
                return State.READ_WINDOW_UPDATE_FRAME;
            default:
                if (length != 0) {
                    return State.DISCARD_FRAME;
                }
                return State.READ_COMMON_HEADER;
        }
    }

    private static boolean isValidFrameHeader(int streamId, int type, byte flags, int length) {
        switch (type) {
            case 0:
                if (streamId == 0) {
                    return false;
                }
                return true;
            case 1:
                if (length < 10) {
                    return false;
                }
                return true;
            case 2:
                if (length < 4) {
                    return false;
                }
                return true;
            case 3:
                if (flags == (byte) 0 && length == 8) {
                    return true;
                }
                return false;
            case 4:
                if (length < 4) {
                    return false;
                }
                return true;
            case 6:
                if (length != 4) {
                    return false;
                }
                return true;
            case 7:
                if (length != 8) {
                    return false;
                }
                return true;
            case 8:
                if (length < 4) {
                    return false;
                }
                return true;
            case 9:
                return length == 8;
            default:
                return true;
        }
    }
}
