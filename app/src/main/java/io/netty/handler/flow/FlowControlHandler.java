package io.netty.handler.flow;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Recycler;
import io.netty.util.Recycler$Handle;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;

public class FlowControlHandler extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(FlowControlHandler.class);
    private ChannelConfig config;
    private RecyclableArrayDeque queue;
    private final boolean releaseMessages;
    private boolean shouldConsume;

    private static final class RecyclableArrayDeque extends ArrayDeque<Object> {
        private static final int DEFAULT_NUM_ELEMENTS = 2;
        private static final Recycler<RecyclableArrayDeque> RECYCLER = new Recycler<RecyclableArrayDeque>() {
            protected RecyclableArrayDeque newObject(Recycler$Handle<RecyclableArrayDeque> handle) {
                return new RecyclableArrayDeque(2, handle);
            }
        };
        private static final long serialVersionUID = 0;
        private final Recycler$Handle<RecyclableArrayDeque> handle;

        public static RecyclableArrayDeque newInstance() {
            return (RecyclableArrayDeque) RECYCLER.get();
        }

        private RecyclableArrayDeque(int numElements, Recycler$Handle<RecyclableArrayDeque> handle) {
            super(numElements);
            this.handle = handle;
        }

        public void recycle() {
            clear();
            this.handle.recycle(this);
        }
    }

    public FlowControlHandler() {
        this(true);
    }

    public FlowControlHandler(boolean releaseMessages) {
        this.releaseMessages = releaseMessages;
    }

    boolean isQueueEmpty() {
        return this.queue.isEmpty();
    }

    private void destroy() {
        if (this.queue != null) {
            if (!this.queue.isEmpty()) {
                logger.trace("Non-empty queue: {}", this.queue);
                if (this.releaseMessages) {
                    while (true) {
                        Object msg = this.queue.poll();
                        if (msg == null) {
                            break;
                        }
                        ReferenceCountUtil.safeRelease(msg);
                    }
                }
            }
            this.queue.recycle();
            this.queue = null;
        }
    }

    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.config = ctx.channel().config();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        destroy();
        ctx.fireChannelInactive();
    }

    public void read(ChannelHandlerContext ctx) throws Exception {
        if (dequeue(ctx, 1) == 0) {
            this.shouldConsume = true;
            ctx.read();
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int minConsume;
        if (this.queue == null) {
            this.queue = RecyclableArrayDeque.newInstance();
        }
        this.queue.offer(msg);
        if (this.shouldConsume) {
            minConsume = 1;
        } else {
            minConsume = 0;
        }
        this.shouldConsume = false;
        dequeue(ctx, minConsume);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    private int dequeue(ChannelHandlerContext ctx, int minConsume) {
        if (this.queue == null) {
            return 0;
        }
        int consumed = 0;
        while (true) {
            if (consumed >= minConsume && !this.config.isAutoRead()) {
                break;
            }
            Object msg = this.queue.poll();
            if (msg == null) {
                break;
            }
            consumed++;
            ctx.fireChannelRead(msg);
        }
        if (!this.queue.isEmpty() || consumed <= 0) {
            return consumed;
        }
        ctx.fireChannelReadComplete();
        return consumed;
    }
}
