package io.netty.handler.codec.stomp;

import io.netty.handler.codec.DecoderResult;

public class DefaultStompHeadersSubframe implements StompHeadersSubframe {
    protected final StompCommand command;
    protected DecoderResult decoderResult = DecoderResult.SUCCESS;
    protected final StompHeaders headers = new DefaultStompHeaders();

    public DefaultStompHeadersSubframe(StompCommand command) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        this.command = command;
    }

    public StompCommand command() {
        return this.command;
    }

    public StompHeaders headers() {
        return this.headers;
    }

    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    public void setDecoderResult(DecoderResult decoderResult) {
        this.decoderResult = decoderResult;
    }

    public String toString() {
        return "StompFrame{command=" + this.command + ", headers=" + this.headers + '}';
    }
}
