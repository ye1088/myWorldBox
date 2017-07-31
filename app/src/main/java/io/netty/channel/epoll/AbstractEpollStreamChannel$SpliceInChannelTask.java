package io.netty.channel.epoll;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator.Handle;
import io.netty.channel.unix.FileDescriptor;

final class AbstractEpollStreamChannel$SpliceInChannelTask extends AbstractEpollStreamChannel$SpliceInTask implements ChannelFutureListener {
    static final /* synthetic */ boolean $assertionsDisabled = (!AbstractEpollStreamChannel.class.desiredAssertionStatus());
    private final AbstractEpollStreamChannel ch;
    final /* synthetic */ AbstractEpollStreamChannel this$0;

    AbstractEpollStreamChannel$SpliceInChannelTask(AbstractEpollStreamChannel abstractEpollStreamChannel, AbstractEpollStreamChannel ch, int len, ChannelPromise promise) {
        this.this$0 = abstractEpollStreamChannel;
        super(abstractEpollStreamChannel, len, promise);
        this.ch = ch;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            this.promise.setFailure(future.cause());
        }
    }

    public boolean spliceIn(Handle handle) {
        if (!$assertionsDisabled && !this.ch.eventLoop().inEventLoop()) {
            throw new AssertionError();
        } else if (this.len == 0) {
            this.promise.setSuccess();
            return true;
        } else {
            try {
                FileDescriptor pipeOut = AbstractEpollStreamChannel.access$900(this.ch);
                if (pipeOut == null) {
                    FileDescriptor[] pipe = FileDescriptor.pipe();
                    AbstractEpollStreamChannel.access$1002(this.ch, pipe[0]);
                    pipeOut = AbstractEpollStreamChannel.access$902(this.ch, pipe[1]);
                }
                int splicedIn = spliceIn(pipeOut, handle);
                if (splicedIn > 0) {
                    ChannelPromise splicePromise;
                    if (this.len != Integer.MAX_VALUE) {
                        this.len -= splicedIn;
                    }
                    if (this.len == 0) {
                        splicePromise = this.promise;
                    } else {
                        splicePromise = this.ch.newPromise().addListener(this);
                    }
                    boolean autoRead = this.this$0.config().isAutoRead();
                    this.ch.unsafe().write(new AbstractEpollStreamChannel$SpliceOutTask(this.this$0, this.ch, splicedIn, autoRead), splicePromise);
                    this.ch.unsafe().flush();
                    if (autoRead && !splicePromise.isDone()) {
                        this.this$0.config().setAutoRead(false);
                    }
                }
                if (this.len != 0) {
                    return false;
                }
                return true;
            } catch (Throwable cause) {
                this.promise.setFailure(cause);
                return true;
            }
        }
    }
}
