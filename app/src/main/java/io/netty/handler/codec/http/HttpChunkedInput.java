package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;

public class HttpChunkedInput implements ChunkedInput<HttpContent> {
    private final ChunkedInput<ByteBuf> input;
    private final LastHttpContent lastHttpContent;
    private boolean sentLastChunk;

    public HttpChunkedInput(ChunkedInput<ByteBuf> input) {
        this.input = input;
        this.lastHttpContent = LastHttpContent.EMPTY_LAST_CONTENT;
    }

    public HttpChunkedInput(ChunkedInput<ByteBuf> input, LastHttpContent lastHttpContent) {
        this.input = input;
        this.lastHttpContent = lastHttpContent;
    }

    public boolean isEndOfInput() throws Exception {
        if (this.input.isEndOfInput()) {
            return this.sentLastChunk;
        }
        return false;
    }

    public void close() throws Exception {
        this.input.close();
    }

    @Deprecated
    public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception {
        return readChunk(ctx.alloc());
    }

    public HttpContent readChunk(ByteBufAllocator allocator) throws Exception {
        if (!this.input.isEndOfInput()) {
            ByteBuf buf = (ByteBuf) this.input.readChunk(allocator);
            if (buf != null) {
                return new DefaultHttpContent(buf);
            }
            return null;
        } else if (this.sentLastChunk) {
            return null;
        } else {
            this.sentLastChunk = true;
            return this.lastHttpContent;
        }
    }

    public long length() {
        return this.input.length();
    }

    public long progress() {
        return this.input.progress();
    }
}
