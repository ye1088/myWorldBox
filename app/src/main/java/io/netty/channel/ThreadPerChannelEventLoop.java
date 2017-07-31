package io.netty.channel;

public class ThreadPerChannelEventLoop extends SingleThreadEventLoop {
    private Channel ch;
    private final ThreadPerChannelEventLoopGroup parent;

    public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup parent) {
        super((EventLoopGroup) parent, parent.executor, true);
        this.parent = parent;
    }

    public ChannelFuture register(ChannelPromise promise) {
        return super.register(promise).addListener(new 1(this));
    }

    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        return super.register(channel, promise).addListener(new 2(this));
    }

    protected void run() {
        while (true) {
            Runnable task = takeTask();
            if (task != null) {
                task.run();
                updateLastExecutionTime();
            }
            Channel ch = this.ch;
            if (isShuttingDown()) {
                if (ch != null) {
                    ch.unsafe().close(ch.unsafe().voidPromise());
                }
                if (confirmShutdown()) {
                    return;
                }
            } else if (!(ch == null || ch.isRegistered())) {
                runAllTasks();
                deregister();
            }
        }
    }

    protected void deregister() {
        this.ch = null;
        this.parent.activeChildren.remove(this);
        this.parent.idleChildren.add(this);
    }
}
