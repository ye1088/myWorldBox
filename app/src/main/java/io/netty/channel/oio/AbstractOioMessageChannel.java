package io.netty.channel.oio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.RecvByteBufAllocator.Handle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOioMessageChannel extends AbstractOioChannel {
    private final List<Object> readBuf = new ArrayList();

    protected abstract int doReadMessages(List<Object> list) throws Exception;

    protected AbstractOioMessageChannel(Channel parent) {
        super(parent);
    }

    protected void doRead() {
        if (this.readPending) {
            this.readPending = false;
            ChannelConfig config = config();
            ChannelPipeline pipeline = pipeline();
            Handle allocHandle = unsafe().recvBufAllocHandle();
            allocHandle.reset(config);
            boolean closed = false;
            Throwable exception = null;
            do {
                int localRead = doReadMessages(this.readBuf);
                if (localRead == 0) {
                    break;
                } else if (localRead < 0) {
                    closed = true;
                    break;
                } else {
                    try {
                        allocHandle.incMessagesRead(localRead);
                    } catch (Throwable t) {
                        exception = t;
                    }
                }
            } while (allocHandle.continueReading());
            boolean readData = false;
            int size = this.readBuf.size();
            if (size > 0) {
                readData = true;
                for (int i = 0; i < size; i++) {
                    this.readPending = false;
                    pipeline.fireChannelRead(this.readBuf.get(i));
                }
                this.readBuf.clear();
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
            }
            if (exception != null) {
                if (exception instanceof IOException) {
                    closed = true;
                }
                pipeline.fireExceptionCaught(exception);
            }
            if (closed) {
                if (isOpen()) {
                    unsafe().close(unsafe().voidPromise());
                }
            } else if (this.readPending || config.isAutoRead() || (!readData && isActive())) {
                read();
            }
        }
    }
}
