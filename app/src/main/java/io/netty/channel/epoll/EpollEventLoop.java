package io.netty.channel.epoll;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.unix.FileDescriptor;
import io.netty.util.IntSupplier;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class EpollEventLoop extends SingleThreadEventLoop {
    static final /* synthetic */ boolean $assertionsDisabled = (!EpollEventLoop.class.desiredAssertionStatus());
    private static final AtomicIntegerFieldUpdater<EpollEventLoop> WAKEN_UP_UPDATER;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
    private final boolean allowGrowing;
    private final IntObjectMap<AbstractEpollChannel> channels = new IntObjectHashMap(4096);
    private final FileDescriptor epollFd;
    private final FileDescriptor eventFd;
    private final EpollEventArray events;
    private volatile int ioRatio = 50;
    private final IovArray iovArray = new IovArray();
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
        public Integer call() throws Exception {
            return Integer.valueOf(super.pendingTasks());
        }
    };
    private final IntSupplier selectNowSupplier = new IntSupplier() {
        public int get() throws Exception {
            return Native.epollWait(EpollEventLoop.this.epollFd.intValue(), EpollEventLoop.this.events, 0);
        }
    };
    private final SelectStrategy selectStrategy;
    private volatile int wakenUp;

    static {
        AtomicIntegerFieldUpdater<EpollEventLoop> updater = PlatformDependent.newAtomicIntegerFieldUpdater(EpollEventLoop.class, "wakenUp");
        if (updater == null) {
            updater = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
        }
        WAKEN_UP_UPDATER = updater;
    }

    EpollEventLoop(EventLoopGroup parent, Executor executor, int maxEvents, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        this.selectStrategy = (SelectStrategy) ObjectUtil.checkNotNull(strategy, "strategy");
        if (maxEvents == 0) {
            this.allowGrowing = true;
            this.events = new EpollEventArray(4096);
        } else {
            this.allowGrowing = false;
            this.events = new EpollEventArray(maxEvents);
        }
        FileDescriptor epollFd = null;
        FileDescriptor eventFd = null;
        try {
            epollFd = Native.newEpollCreate();
            this.epollFd = epollFd;
            eventFd = Native.newEventFd();
            this.eventFd = eventFd;
            Native.epollCtlAdd(epollFd.intValue(), eventFd.intValue(), Native.EPOLLIN);
            if (!true) {
                if (epollFd != null) {
                    try {
                        epollFd.close();
                    } catch (Exception e) {
                    }
                }
                if (eventFd != null) {
                    try {
                        eventFd.close();
                    } catch (Exception e2) {
                    }
                }
            }
        } catch (IOException e3) {
            throw new IllegalStateException("Unable to add eventFd filedescriptor to epoll", e3);
        } catch (Throwable th) {
            if (!false) {
                if (epollFd != null) {
                    try {
                        epollFd.close();
                    } catch (Exception e4) {
                    }
                }
                if (eventFd != null) {
                    try {
                        eventFd.close();
                    } catch (Exception e5) {
                    }
                }
            }
        }
    }

    IovArray cleanArray() {
        this.iovArray.clear();
        return this.iovArray;
    }

    protected void wakeup(boolean inEventLoop) {
        if (!inEventLoop && WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)) {
            Native.eventFdWrite(this.eventFd.intValue(), 1);
        }
    }

    void add(AbstractEpollChannel ch) throws IOException {
        if ($assertionsDisabled || inEventLoop()) {
            int fd = ch.fd().intValue();
            Native.epollCtlAdd(this.epollFd.intValue(), fd, ch.flags);
            this.channels.put(fd, ch);
            return;
        }
        throw new AssertionError();
    }

    void modify(AbstractEpollChannel ch) throws IOException {
        if ($assertionsDisabled || inEventLoop()) {
            Native.epollCtlMod(this.epollFd.intValue(), ch.fd().intValue(), ch.flags);
            return;
        }
        throw new AssertionError();
    }

    void remove(AbstractEpollChannel ch) throws IOException {
        if (!$assertionsDisabled && !inEventLoop()) {
            throw new AssertionError();
        } else if (ch.isOpen()) {
            if (this.channels.remove(ch.fd().intValue()) != null) {
                Native.epollCtlDel(this.epollFd.intValue(), ch.fd().intValue());
            }
        }
    }

    protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
        return PlatformDependent.newMpscQueue(maxPendingTasks);
    }

    public int pendingTasks() {
        if (inEventLoop()) {
            return super.pendingTasks();
        }
        return ((Integer) submit(this.pendingTasksCallable).syncUninterruptibly().getNow()).intValue();
    }

    public int getIoRatio() {
        return this.ioRatio;
    }

    public void setIoRatio(int ioRatio) {
        if (ioRatio <= 0 || ioRatio > 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = ioRatio;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int epollWait(boolean r15) throws java.io.IOException {
        /*
        r14 = this;
        r3 = 0;
        r0 = java.lang.System.nanoTime();
        r10 = r14.delayNanos(r0);
        r4 = r0 + r10;
    L_0x000b:
        r10 = r4 - r0;
        r12 = 500000; // 0x7a120 float:7.00649E-40 double:2.47033E-318;
        r10 = r10 + r12;
        r12 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r8 = r10 / r12;
        r10 = 0;
        r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r7 > 0) goto L_0x002e;
    L_0x001c:
        if (r3 != 0) goto L_0x0077;
    L_0x001e:
        r7 = r14.epollFd;
        r7 = r7.intValue();
        r10 = r14.events;
        r11 = 0;
        r2 = io.netty.channel.epoll.Native.epollWait(r7, r10, r11);
        if (r2 <= 0) goto L_0x0077;
    L_0x002d:
        return r2;
    L_0x002e:
        r7 = r14.hasTasks();
        if (r7 == 0) goto L_0x004c;
    L_0x0034:
        r7 = WAKEN_UP_UPDATER;
        r10 = 0;
        r11 = 1;
        r7 = r7.compareAndSet(r14, r10, r11);
        if (r7 == 0) goto L_0x004c;
    L_0x003e:
        r7 = r14.epollFd;
        r7 = r7.intValue();
        r10 = r14.events;
        r11 = 0;
        r2 = io.netty.channel.epoll.Native.epollWait(r7, r10, r11);
        goto L_0x002d;
    L_0x004c:
        r7 = r14.epollFd;
        r7 = r7.intValue();
        r10 = r14.events;
        r11 = (int) r8;
        r6 = io.netty.channel.epoll.Native.epollWait(r7, r10, r11);
        r3 = r3 + 1;
        if (r6 != 0) goto L_0x0070;
    L_0x005d:
        if (r15 != 0) goto L_0x0070;
    L_0x005f:
        r7 = r14.wakenUp;
        r10 = 1;
        if (r7 == r10) goto L_0x0070;
    L_0x0064:
        r7 = r14.hasTasks();
        if (r7 != 0) goto L_0x0070;
    L_0x006a:
        r7 = r14.hasScheduledTasks();
        if (r7 == 0) goto L_0x0072;
    L_0x0070:
        r2 = r6;
        goto L_0x002d;
    L_0x0072:
        r0 = java.lang.System.nanoTime();
        goto L_0x000b;
    L_0x0077:
        r2 = 0;
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.epoll.EpollEventLoop.epollWait(boolean):int");
    }

    protected void run() {
        while (true) {
            try {
                int strategy = this.selectStrategy.calculateStrategy(this.selectNowSupplier, hasTasks());
                switch (strategy) {
                    case -2:
                        continue;
                    case -1:
                        boolean z;
                        if (WAKEN_UP_UPDATER.getAndSet(this, 0) == 1) {
                            z = true;
                        } else {
                            z = false;
                        }
                        strategy = epollWait(z);
                        if (this.wakenUp == 1) {
                            Native.eventFdWrite(this.eventFd.intValue(), 1);
                            break;
                        }
                        break;
                }
                int ioRatio = this.ioRatio;
                if (ioRatio == 100) {
                    if (strategy > 0) {
                        processReady(this.events, strategy);
                    }
                    runAllTasks();
                } else {
                    long ioStartTime = System.nanoTime();
                    if (strategy > 0) {
                        processReady(this.events, strategy);
                    }
                    runAllTasks((((long) (100 - ioRatio)) * (System.nanoTime() - ioStartTime)) / ((long) ioRatio));
                }
                if (this.allowGrowing && strategy == this.events.length()) {
                    this.events.increase();
                }
                if (isShuttingDown()) {
                    closeAll();
                    if (confirmShutdown()) {
                        return;
                    }
                } else {
                    continue;
                }
            } catch (Throwable t) {
                logger.warn("Unexpected exception in the selector loop.", t);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private void closeAll() {
        try {
            Native.epollWait(this.epollFd.intValue(), this.events, 0);
        } catch (IOException e) {
        }
        Collection<AbstractEpollChannel> array = new ArrayList(this.channels.size());
        for (AbstractEpollChannel channel : this.channels.values()) {
            array.add(channel);
        }
        for (AbstractEpollChannel ch : array) {
            ch.unsafe().close(ch.unsafe().voidPromise());
        }
    }

    private void processReady(EpollEventArray events, int ready) {
        for (int i = 0; i < ready; i++) {
            int fd = events.fd(i);
            if (fd == this.eventFd.intValue()) {
                Native.eventFdRead(this.eventFd.intValue());
            } else {
                long ev = (long) events.events(i);
                AbstractEpollChannel ch = (AbstractEpollChannel) this.channels.get(fd);
                if (ch != null) {
                    AbstractEpollUnsafe unsafe = (AbstractEpollUnsafe) ch.unsafe();
                    if ((((long) (Native.EPOLLERR | Native.EPOLLOUT)) & ev) != 0) {
                        unsafe.epollOutReady();
                    }
                    if ((((long) (Native.EPOLLERR | Native.EPOLLIN)) & ev) != 0) {
                        unsafe.epollInReady();
                    }
                    if ((((long) Native.EPOLLRDHUP) & ev) != 0) {
                        unsafe.epollRdHupReady();
                    }
                } else {
                    try {
                        Native.epollCtlDel(this.epollFd.intValue(), fd);
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    protected void cleanup() {
        try {
            this.epollFd.close();
        } catch (IOException e) {
            logger.warn("Failed to close the epoll fd.", e);
        } catch (Throwable th) {
            this.iovArray.release();
            this.events.free();
        }
        try {
            this.eventFd.close();
        } catch (IOException e2) {
            logger.warn("Failed to close the event fd.", e2);
        }
        this.iovArray.release();
        this.events.free();
    }
}
