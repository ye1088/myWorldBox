package io.netty.handler.codec.xml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.HttpConstants;
import java.util.List;

public class XmlFrameDecoder extends ByteToMessageDecoder {
    private final int maxFrameLength;

    public XmlFrameDecoder(int maxFrameLength) {
        if (maxFrameLength < 1) {
            throw new IllegalArgumentException("maxFrameLength must be a positive int");
        }
        this.maxFrameLength = maxFrameLength;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        boolean openingBracketFound = false;
        boolean atLeastOneXmlElementFound = false;
        boolean inCDATASection = false;
        long openBracketsCount = 0;
        int length = 0;
        int leadingWhiteSpaceCount = 0;
        int bufferLength = in.writerIndex();
        if (bufferLength > this.maxFrameLength) {
            in.skipBytes(in.readableBytes());
            fail((long) bufferLength);
            return;
        }
        int i = in.readerIndex();
        while (i < bufferLength) {
            byte readByte = in.getByte(i);
            if (!openingBracketFound && Character.isWhitespace(readByte)) {
                leadingWhiteSpaceCount++;
            } else if (!openingBracketFound && readByte != (byte) 60) {
                fail(ctx);
                in.skipBytes(in.readableBytes());
                return;
            } else if (!inCDATASection && readByte == (byte) 60) {
                openingBracketFound = true;
                if (i < bufferLength - 1) {
                    byte peekAheadByte = in.getByte(i + 1);
                    if (peekAheadByte == (byte) 47) {
                        for (int peekFurtherAheadIndex = i + 2; peekFurtherAheadIndex <= bufferLength - 1; peekFurtherAheadIndex++) {
                            if (in.getByte(peekFurtherAheadIndex) == (byte) 62) {
                                openBracketsCount--;
                                break;
                            }
                        }
                    } else if (isValidStartCharForXmlElement(peekAheadByte)) {
                        atLeastOneXmlElementFound = true;
                        openBracketsCount++;
                    } else if (peekAheadByte == (byte) 33) {
                        if (isCommentBlockStart(in, i)) {
                            openBracketsCount++;
                        } else if (isCDATABlockStart(in, i)) {
                            openBracketsCount++;
                            inCDATASection = true;
                        }
                    } else if (peekAheadByte == (byte) 63) {
                        openBracketsCount++;
                    }
                }
            } else if (inCDATASection || readByte != (byte) 47) {
                if (readByte == (byte) 62) {
                    length = i + 1;
                    if (i - 1 > -1) {
                        byte peekBehindByte = in.getByte(i - 1);
                        if (inCDATASection) {
                            if (peekBehindByte == (byte) 93 && i - 2 > -1 && in.getByte(i - 2) == (byte) 93) {
                                openBracketsCount--;
                                inCDATASection = false;
                            }
                        } else if (peekBehindByte == (byte) 63) {
                            openBracketsCount--;
                        } else if (peekBehindByte == (byte) 45 && i - 2 > -1 && in.getByte(i - 2) == (byte) 45) {
                            openBracketsCount--;
                        }
                    }
                    if (atLeastOneXmlElementFound && openBracketsCount == 0) {
                        break;
                    }
                }
                continue;
            } else if (i < bufferLength - 1 && in.getByte(i + 1) == (byte) 62) {
                openBracketsCount--;
            }
            i++;
        }
        int readerIndex = in.readerIndex();
        int xmlElementLength = length - readerIndex;
        if (openBracketsCount == 0 && xmlElementLength > 0) {
            if (readerIndex + xmlElementLength >= bufferLength) {
                xmlElementLength = in.readableBytes();
            }
            ByteBuf frame = extractFrame(in, readerIndex + leadingWhiteSpaceCount, xmlElementLength - leadingWhiteSpaceCount);
            in.skipBytes(xmlElementLength);
            out.add(frame);
        }
    }

    private void fail(long frameLength) {
        if (frameLength > 0) {
            throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + frameLength + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
    }

    private static void fail(ChannelHandlerContext ctx) {
        ctx.fireExceptionCaught(new CorruptedFrameException("frame contains content before the xml starts"));
    }

    private static ByteBuf extractFrame(ByteBuf buffer, int index, int length) {
        return buffer.copy(index, length);
    }

    private static boolean isValidStartCharForXmlElement(byte b) {
        return (b >= (byte) 97 && b <= (byte) 122) || ((b >= (byte) 65 && b <= (byte) 90) || b == HttpConstants.COLON || b == (byte) 95);
    }

    private static boolean isCommentBlockStart(ByteBuf in, int i) {
        return i < in.writerIndex() + -3 && in.getByte(i + 2) == (byte) 45 && in.getByte(i + 3) == (byte) 45;
    }

    private static boolean isCDATABlockStart(ByteBuf in, int i) {
        return i < in.writerIndex() + -8 && in.getByte(i + 2) == (byte) 91 && in.getByte(i + 3) == (byte) 67 && in.getByte(i + 4) == (byte) 68 && in.getByte(i + 5) == (byte) 65 && in.getByte(i + 6) == (byte) 84 && in.getByte(i + 7) == (byte) 65 && in.getByte(i + 8) == (byte) 91;
    }
}
