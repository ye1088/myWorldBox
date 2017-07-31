package io.netty.channel;

final class AbstractChannel$CloseFuture extends DefaultChannelPromise {
    AbstractChannel$CloseFuture(AbstractChannel ch) {
        super(ch);
    }

    public ChannelPromise setSuccess() {
        throw new IllegalStateException();
    }

    public ChannelPromise setFailure(Throwable cause) {
        throw new IllegalStateException();
    }

    public boolean trySuccess() {
        throw new IllegalStateException();
    }

    public boolean tryFailure(Throwable cause) {
        throw new IllegalStateException();
    }

    boolean setClosed() {
        return super.trySuccess();
    }
}
