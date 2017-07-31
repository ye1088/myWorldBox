package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.internal.AppendableCharSequence;
import java.util.List;
import java.util.Locale;

public class StompSubframeDecoder extends ReplayingDecoder<State> {
    private static final int DEFAULT_CHUNK_SIZE = 8132;
    private static final int DEFAULT_MAX_LINE_LENGTH = 1024;
    private int alreadyReadChunkSize;
    private long contentLength;
    private LastStompContentSubframe lastContent;
    private final int maxChunkSize;
    private final int maxLineLength;

    enum State {
        SKIP_CONTROL_CHARACTERS,
        READ_HEADERS,
        READ_CONTENT,
        FINALIZE_FRAME_READ,
        BAD_FRAME,
        INVALID_CHUNK
    }

    public StompSubframeDecoder() {
        this(1024, DEFAULT_CHUNK_SIZE);
    }

    public StompSubframeDecoder(int maxLineLength, int maxChunkSize) {
        super(State.SKIP_CONTROL_CHARACTERS);
        this.contentLength = -1;
        if (maxLineLength <= 0) {
            throw new IllegalArgumentException("maxLineLength must be a positive integer: " + maxLineLength);
        } else if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
        } else {
            this.maxChunkSize = maxChunkSize;
            this.maxLineLength = maxLineLength;
        }
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Exception e;
        switch ((State) state()) {
            case SKIP_CONTROL_CHARACTERS:
                skipControlCharacters(in);
                checkpoint(State.READ_HEADERS);
                break;
            case READ_HEADERS:
                break;
            case BAD_FRAME:
                in.skipBytes(actualReadableBytes());
                return;
        }
        StompCommand stompCommand = StompCommand.UNKNOWN;
        StompHeadersSubframe stompHeadersSubframe = null;
        try {
            stompCommand = readCommand(in);
            StompHeadersSubframe frame = new DefaultStompHeadersSubframe(stompCommand);
            try {
                checkpoint(readHeaders(in, frame.headers()));
                out.add(frame);
                try {
                    switch ((State) state()) {
                        case READ_CONTENT:
                            int toRead = in.readableBytes();
                            if (toRead != 0) {
                                if (toRead > this.maxChunkSize) {
                                    toRead = this.maxChunkSize;
                                }
                                ByteBuf chunkBuffer;
                                if (this.contentLength >= 0) {
                                    int remainingLength = (int) (this.contentLength - ((long) this.alreadyReadChunkSize));
                                    if (toRead > remainingLength) {
                                        toRead = remainingLength;
                                    }
                                    chunkBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toRead);
                                    int i = this.alreadyReadChunkSize + toRead;
                                    this.alreadyReadChunkSize = i;
                                    if (((long) i) >= this.contentLength) {
                                        this.lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
                                        checkpoint(State.FINALIZE_FRAME_READ);
                                        break;
                                    }
                                    out.add(new DefaultStompContentSubframe(chunkBuffer));
                                    return;
                                }
                                int nulIndex = ByteBufUtil.indexOf(in, in.readerIndex(), in.writerIndex(), (byte) 0);
                                if (nulIndex == in.readerIndex()) {
                                    checkpoint(State.FINALIZE_FRAME_READ);
                                    break;
                                }
                                if (nulIndex > 0) {
                                    toRead = nulIndex - in.readerIndex();
                                } else {
                                    toRead = in.writerIndex() - in.readerIndex();
                                }
                                chunkBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toRead);
                                this.alreadyReadChunkSize += toRead;
                                if (nulIndex > 0) {
                                    this.lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
                                    checkpoint(State.FINALIZE_FRAME_READ);
                                    break;
                                }
                                out.add(new DefaultStompContentSubframe(chunkBuffer));
                                return;
                            }
                            return;
                        case FINALIZE_FRAME_READ:
                            break;
                        default:
                            return;
                    }
                    skipNullCharacter(in);
                    if (this.lastContent == null) {
                        this.lastContent = LastStompContentSubframe.EMPTY_LAST_CONTENT;
                    }
                    out.add(this.lastContent);
                    resetDecoder();
                } catch (Exception e2) {
                    StompContentSubframe errorContent = new DefaultLastStompContentSubframe(Unpooled.EMPTY_BUFFER);
                    errorContent.setDecoderResult(DecoderResult.failure(e2));
                    out.add(errorContent);
                    checkpoint(State.BAD_FRAME);
                }
            } catch (Exception e3) {
                e2 = e3;
                stompHeadersSubframe = frame;
                if (stompHeadersSubframe == null) {
                    stompHeadersSubframe = new DefaultStompHeadersSubframe(stompCommand);
                }
                stompHeadersSubframe.setDecoderResult(DecoderResult.failure(e2));
                out.add(stompHeadersSubframe);
                checkpoint(State.BAD_FRAME);
            }
        } catch (Exception e4) {
            e2 = e4;
            if (stompHeadersSubframe == null) {
                stompHeadersSubframe = new DefaultStompHeadersSubframe(stompCommand);
            }
            stompHeadersSubframe.setDecoderResult(DecoderResult.failure(e2));
            out.add(stompHeadersSubframe);
            checkpoint(State.BAD_FRAME);
        }
    }

    private StompCommand readCommand(ByteBuf in) {
        String commandStr = readLine(in, this.maxLineLength);
        StompCommand command = null;
        try {
            command = StompCommand.valueOf(commandStr);
        } catch (IllegalArgumentException e) {
        }
        if (command == null) {
            try {
                command = StompCommand.valueOf(commandStr.toUpperCase(Locale.US));
            } catch (IllegalArgumentException e2) {
            }
        }
        if (command != null) {
            return command;
        }
        throw new DecoderException("failed to read command from channel");
    }

    private State readHeaders(ByteBuf buffer, StompHeaders headers) {
        while (true) {
            String line = readLine(buffer, this.maxLineLength);
            if (line.isEmpty()) {
                break;
            }
            String[] split = line.split(":");
            if (split.length == 2) {
                headers.add(split[0], split[1]);
            }
        }
        if (headers.contains(StompHeaders.CONTENT_LENGTH)) {
            this.contentLength = getContentLength(headers, 0);
            if (this.contentLength == 0) {
                return State.FINALIZE_FRAME_READ;
            }
        }
        return State.READ_CONTENT;
    }

    private static long getContentLength(StompHeaders headers, long defaultValue) {
        long contentLength = headers.getLong(StompHeaders.CONTENT_LENGTH, defaultValue);
        if (contentLength >= 0) {
            return contentLength;
        }
        throw new DecoderException(StompHeaders.CONTENT_LENGTH + " must be non-negative");
    }

    private static void skipNullCharacter(ByteBuf buffer) {
        byte b = buffer.readByte();
        if (b != (byte) 0) {
            throw new IllegalStateException("unexpected byte in buffer " + b + " while expecting NULL byte");
        }
    }

    private static void skipControlCharacters(ByteBuf buffer) {
        while (true) {
            byte b = buffer.readByte();
            if (b != (byte) 13 && b != (byte) 10) {
                buffer.readerIndex(buffer.readerIndex() - 1);
                return;
            }
        }
    }

    private static String readLine(ByteBuf buffer, int maxLineLength) {
        AppendableCharSequence buf = new AppendableCharSequence(128);
        int lineLength = 0;
        while (true) {
            byte nextByte = buffer.readByte();
            if (nextByte == (byte) 13) {
                if (buffer.readByte() == (byte) 10) {
                    return buf.toString();
                }
            } else if (nextByte == (byte) 10) {
                return buf.toString();
            } else {
                if (lineLength >= maxLineLength) {
                    throw new TooLongFrameException("An STOMP line is larger than " + maxLineLength + " bytes.");
                }
                lineLength++;
                buf.append((char) nextByte);
            }
        }
    }

    private void resetDecoder() {
        checkpoint(State.SKIP_CONTROL_CHARACTERS);
        this.contentLength = -1;
        this.alreadyReadChunkSize = 0;
        this.lastContent = null;
    }
}
