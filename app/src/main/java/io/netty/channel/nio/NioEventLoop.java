package io.netty.channel.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.EventLoopException;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NioEventLoop extends SingleThreadEventLoop {
    private static final int CLEANUP_INTERVAL = 256;
    private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
    private int cancelledKeys;
    private volatile int ioRatio = 50;
    private boolean needsToSelectAgain;
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>() {
        public Integer call() throws Exception {
            return Integer.valueOf(super.pendingTasks());
        }
    };
    private final SelectorProvider provider;
    private final IntSupplier selectNowSupplier = new IntSupplier() {
        public int get() throws Exception {
            return NioEventLoop.this.selectNow();
        }
    };
    private final SelectStrategy selectStrategy;
    private SelectedSelectionKeySet selectedKeys;
    Selector selector;
    private final AtomicBoolean wakenUp = new AtomicBoolean();

    static {
        String key = "sun.nio.ch.bugLevel";
        if (SystemPropertyUtil.get("sun.nio.ch.bugLevel") == null) {
            try {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    public Void run() {
                        System.setProperty("sun.nio.ch.bugLevel", "");
                        return null;
                    }
                });
            } catch (SecurityException e) {
                logger.debug("Unable to get/set System Property: sun.nio.ch.bugLevel", e);
            }
        }
        int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        if (selectorAutoRebuildThreshold < 3) {
            selectorAutoRebuildThreshold = 0;
        }
        SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(DISABLE_KEYSET_OPTIMIZATION));
            logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(SELECTOR_AUTO_REBUILD_THRESHOLD));
        }
    }

    NioEventLoop(NioEventLoopGroup parent, Executor executor, SelectorProvider selectorProvider, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        if (selectorProvider == null) {
            throw new NullPointerException("selectorProvider");
        } else if (strategy == null) {
            throw new NullPointerException("selectStrategy");
        } else {
            this.provider = selectorProvider;
            this.selector = openSelector();
            this.selectStrategy = strategy;
        }
    }

    private Selector openSelector() {
        try {
            final Selector selector = this.provider.openSelector();
            if (!DISABLE_KEYSET_OPTIMIZATION) {
                final SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();
                Exception maybeSelectorImplClass = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                    public Object run() {
                        Object cls;
                        try {
                            cls = Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
                        } catch (ClassNotFoundException e) {
                            cls = e;
                        } catch (SecurityException e2) {
                            cls = e2;
                        }
                        return cls;
                    }
                });
                if ((maybeSelectorImplClass instanceof Class) && ((Class) maybeSelectorImplClass).isAssignableFrom(selector.getClass())) {
                    final Class<?> selectorImplClass = (Class) maybeSelectorImplClass;
                    Object maybeException = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                        public Object run() {
                            try {
                                Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
                                Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");
                                selectedKeysField.setAccessible(true);
                                publicSelectedKeysField.setAccessible(true);
                                selectedKeysField.set(selector, selectedKeySet);
                                publicSelectedKeysField.set(selector, selectedKeySet);
                                return null;
                            } catch (Object e) {
                                return e;
                            } catch (Object e2) {
                                return e2;
                            }
                        }
                    });
                    if (maybeException instanceof Exception) {
                        this.selectedKeys = null;
                        logger.trace("failed to instrument a_isRightVersion special java.util.Set into: {}", selector, (Exception) maybeException);
                    } else {
                        this.selectedKeys = selectedKeySet;
                        logger.trace("instrumented a_isRightVersion special java.util.Set into: {}", selector);
                    }
                } else if (maybeSelectorImplClass instanceof Exception) {
                    logger.trace("failed to instrument a_isRightVersion special java.util.Set into: {}", selector, maybeSelectorImplClass);
                }
            }
            return selector;
        } catch (IOException e) {
            throw new ChannelException("failed to open a_isRightVersion new selector", e);
        }
    }

    public SelectorProvider selectorProvider() {
        return this.provider;
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

    public void register(SelectableChannel ch, int interestOps, NioTask<?> task) {
        if (ch == null) {
            throw new NullPointerException("ch");
        } else if (interestOps == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        } else if (((ch.validOps() ^ -1) & interestOps) != 0) {
            throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
        } else if (task == null) {
            throw new NullPointerException("task");
        } else if (isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        } else {
            try {
                ch.register(this.selector, interestOps, task);
            } catch (Exception e) {
                throw new EventLoopException("failed to register a_isRightVersion channel", e);
            }
        }
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

    public void rebuildSelector() {
        if (inEventLoop()) {
            Selector oldSelector = this.selector;
            if (oldSelector != null) {
                try {
                    Selector newSelector = openSelector();
                    int nChannels = 0;
                    loop0:
                    while (true) {
                        for (SelectionKey key : oldSelector.keys()) {
                            Object a = key.attachment();
                            try {
                                if (key.isValid() && key.channel().keyFor(newSelector) == null) {
                                    int interestOps = key.interestOps();
                                    key.cancel();
                                    SelectionKey newKey = key.channel().register(newSelector, interestOps, a);
                                    if (a instanceof AbstractNioChannel) {
                                        ((AbstractNioChannel) a).selectionKey = newKey;
                                    }
                                    nChannels++;
                                }
                            } catch (Exception e) {
                                try {
                                    logger.warn("Failed to re-register a_isRightVersion Channel to the new Selector.", e);
                                    if (a instanceof AbstractNioChannel) {
                                        AbstractNioChannel ch = (AbstractNioChannel) a;
                                        ch.unsafe().close(ch.unsafe().voidPromise());
                                    } else {
                                        invokeChannelUnregistered((NioTask) a, key, e);
                                    }
                                } catch (ConcurrentModificationException e2) {
                                }
                            }
                        }
                        break loop0;
                    }
                    this.selector = newSelector;
                    try {
                        oldSelector.close();
                    } catch (Throwable t) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("Failed to close the old Selector.", t);
                        }
                    }
                    logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
                    return;
                } catch (Exception e3) {
                    logger.warn("Failed to create a_isRightVersion new Selector.", e3);
                    return;
                }
            }
            return;
        }
        execute(new Runnable() {
            public void run() {
                NioEventLoop.this.rebuildSelector();
            }
        });
    }

    protected void run() {
        while (true) {
            try {
                switch (this.selectStrategy.calculateStrategy(this.selectNowSupplier, hasTasks())) {
                    case -2:
                        continue;
                    case -1:
                        select(this.wakenUp.getAndSet(false));
                        if (this.wakenUp.get()) {
                            this.selector.wakeup();
                            break;
                        }
                        break;
                }
                this.cancelledKeys = 0;
                this.needsToSelectAgain = false;
                int ioRatio = this.ioRatio;
                if (ioRatio == 100) {
                    processSelectedKeys();
                    runAllTasks();
                } else {
                    long ioStartTime = System.nanoTime();
                    processSelectedKeys();
                    runAllTasks((((long) (100 - ioRatio)) * (System.nanoTime() - ioStartTime)) / ((long) ioRatio));
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

    private void processSelectedKeys() {
        if (this.selectedKeys != null) {
            processSelectedKeysOptimized(this.selectedKeys.flip());
        } else {
            processSelectedKeysPlain(this.selector.selectedKeys());
        }
    }

    protected void cleanup() {
        try {
            this.selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a_isRightVersion selector.", e);
        }
    }

    void cancel(SelectionKey key) {
        key.cancel();
        this.cancelledKeys++;
        if (this.cancelledKeys >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }

    protected Runnable pollTask() {
        Runnable task = super.pollTask();
        if (this.needsToSelectAgain) {
            selectAgain();
        }
        return task;
    }

    private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
        if (!selectedKeys.isEmpty()) {
            Iterator<SelectionKey> i = selectedKeys.iterator();
            while (true) {
                SelectionKey k = (SelectionKey) i.next();
                NioTask<SelectableChannel> a = k.attachment();
                i.remove();
                if (a instanceof AbstractNioChannel) {
                    processSelectedKey(k, (AbstractNioChannel) a);
                } else {
                    processSelectedKey(k, (NioTask) a);
                }
                if (!i.hasNext()) {
                    return;
                }
                if (this.needsToSelectAgain) {
                    selectAgain();
                    selectedKeys = this.selector.selectedKeys();
                    if (!selectedKeys.isEmpty()) {
                        i = selectedKeys.iterator();
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private void processSelectedKeysOptimized(SelectionKey[] selectedKeys) {
        int i = 0;
        while (true) {
            SelectionKey k = selectedKeys[i];
            if (k != null) {
                selectedKeys[i] = null;
                NioTask<SelectableChannel> a = k.attachment();
                if (a instanceof AbstractNioChannel) {
                    processSelectedKey(k, (AbstractNioChannel) a);
                } else {
                    processSelectedKey(k, (NioTask) a);
                }
                if (this.needsToSelectAgain) {
                    while (true) {
                        i++;
                        if (selectedKeys[i] == null) {
                            break;
                        }
                        selectedKeys[i] = null;
                    }
                    selectAgain();
                    selectedKeys = this.selectedKeys.flip();
                    i = -1;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
        AbstractNioChannel$NioUnsafe unsafe = ch.unsafe();
        if (k.isValid()) {
            try {
                int readyOps = k.readyOps();
                if ((readyOps & 17) != 0 || readyOps == 0) {
                    unsafe.read();
                    if (!ch.isOpen()) {
                        return;
                    }
                }
                if ((readyOps & 4) != 0) {
                    ch.unsafe().forceFlush();
                }
                if ((readyOps & 8) != 0) {
                    k.interestOps(k.interestOps() & -9);
                    unsafe.finishConnect();
                    return;
                }
                return;
            } catch (CancelledKeyException e) {
                unsafe.close(unsafe.voidPromise());
                return;
            }
        }
        try {
            NioEventLoop eventLoop = ch.eventLoop();
            if (eventLoop == this && eventLoop != null) {
                unsafe.close(unsafe.voidPromise());
            }
        } catch (Throwable th) {
        }
    }

    private static void processSelectedKey(SelectionKey k, NioTask<SelectableChannel> task) {
        try {
            task.channelReady(k.channel(), k);
            switch (1) {
                case 0:
                    k.cancel();
                    invokeChannelUnregistered(task, k, null);
                    return;
                case 1:
                    if (!k.isValid()) {
                        invokeChannelUnregistered(task, k, null);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
            k.cancel();
            invokeChannelUnregistered(task, k, e);
            switch (2) {
                case 0:
                    k.cancel();
                    invokeChannelUnregistered(task, k, null);
                    return;
                case 1:
                    if (!k.isValid()) {
                        invokeChannelUnregistered(task, k, null);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } catch (Throwable th) {
            switch (0) {
                case 0:
                    k.cancel();
                    invokeChannelUnregistered(task, k, null);
                    break;
                case 1:
                    if (!k.isValid()) {
                        invokeChannelUnregistered(task, k, null);
                        break;
                    }
                    break;
            }
        }
    }

    private void closeAll() {
        selectAgain();
        Set<SelectionKey> keys = this.selector.keys();
        Collection<AbstractNioChannel> channels = new ArrayList(keys.size());
        for (SelectionKey k : keys) {
            NioTask<SelectableChannel> a = k.attachment();
            if (a instanceof AbstractNioChannel) {
                channels.add((AbstractNioChannel) a);
            } else {
                k.cancel();
                invokeChannelUnregistered(a, k, null);
            }
        }
        for (AbstractNioChannel ch : channels) {
            ch.unsafe().close(ch.unsafe().voidPromise());
        }
    }

    private static void invokeChannelUnregistered(NioTask<SelectableChannel> task, SelectionKey k, Throwable cause) {
        try {
            task.channelUnregistered(k.channel(), cause);
        } catch (Exception e) {
            logger.warn("Unexpected exception while running NioTask.channelUnregistered()", e);
        }
    }

    protected void wakeup(boolean inEventLoop) {
        if (!inEventLoop && this.wakenUp.compareAndSet(false, true)) {
            this.selector.wakeup();
        }
    }

    int selectNow() throws IOException {
        try {
            int selectNow = this.selector.selectNow();
            return selectNow;
        } finally {
            if (this.wakenUp.get()) {
                this.selector.wakeup();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void select(boolean r19) throws java.io.IOException {
        /*
        r18 = this;
        r0 = r18;
        r9 = r0.selector;
        r5 = 0;
        r2 = java.lang.System.nanoTime();	 Catch:{ CancelledKeyException -> 0x00cc }
        r0 = r18;
        r14 = r0.delayNanos(r2);	 Catch:{ CancelledKeyException -> 0x00cc }
        r6 = r2 + r14;
    L_0x0011:
        r14 = r6 - r2;
        r16 = 500000; // 0x7a120 float:7.00649E-40 double:2.47033E-318;
        r14 = r14 + r16;
        r16 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
        r12 = r14 / r16;
        r14 = 0;
        r14 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r14 > 0) goto L_0x0045;
    L_0x0023:
        if (r5 != 0) goto L_0x0029;
    L_0x0025:
        r9.selectNow();	 Catch:{ CancelledKeyException -> 0x00cc }
        r5 = 1;
    L_0x0029:
        r14 = 3;
        if (r5 <= r14) goto L_0x0044;
    L_0x002c:
        r14 = logger;	 Catch:{ CancelledKeyException -> 0x00cc }
        r14 = r14.isDebugEnabled();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 == 0) goto L_0x0044;
    L_0x0034:
        r14 = logger;	 Catch:{ CancelledKeyException -> 0x00cc }
        r15 = "Selector.select() returned prematurely {} times in a_isRightVersion row for Selector {}.";
        r16 = r5 + -1;
        r16 = java.lang.Integer.valueOf(r16);	 Catch:{ CancelledKeyException -> 0x00cc }
        r0 = r16;
        r14.debug(r15, r0, r9);	 Catch:{ CancelledKeyException -> 0x00cc }
    L_0x0044:
        return;
    L_0x0045:
        r14 = r18.hasTasks();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 == 0) goto L_0x005d;
    L_0x004b:
        r0 = r18;
        r14 = r0.wakenUp;	 Catch:{ CancelledKeyException -> 0x00cc }
        r15 = 0;
        r16 = 1;
        r14 = r14.compareAndSet(r15, r16);	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 == 0) goto L_0x005d;
    L_0x0058:
        r9.selectNow();	 Catch:{ CancelledKeyException -> 0x00cc }
        r5 = 1;
        goto L_0x0029;
    L_0x005d:
        r8 = r9.select(r12);	 Catch:{ CancelledKeyException -> 0x00cc }
        r5 = r5 + 1;
        if (r8 != 0) goto L_0x0029;
    L_0x0065:
        if (r19 != 0) goto L_0x0029;
    L_0x0067:
        r0 = r18;
        r14 = r0.wakenUp;	 Catch:{ CancelledKeyException -> 0x00cc }
        r14 = r14.get();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 != 0) goto L_0x0029;
    L_0x0071:
        r14 = r18.hasTasks();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 != 0) goto L_0x0029;
    L_0x0077:
        r14 = r18.hasScheduledTasks();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 != 0) goto L_0x0029;
    L_0x007d:
        r14 = java.lang.Thread.interrupted();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 == 0) goto L_0x0095;
    L_0x0083:
        r14 = logger;	 Catch:{ CancelledKeyException -> 0x00cc }
        r14 = r14.isDebugEnabled();	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 == 0) goto L_0x0093;
    L_0x008b:
        r14 = logger;	 Catch:{ CancelledKeyException -> 0x00cc }
        r15 = "Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.";
        r14.debug(r15);	 Catch:{ CancelledKeyException -> 0x00cc }
    L_0x0093:
        r5 = 1;
        goto L_0x0029;
    L_0x0095:
        r10 = java.lang.System.nanoTime();	 Catch:{ CancelledKeyException -> 0x00cc }
        r14 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ CancelledKeyException -> 0x00cc }
        r14 = r14.toNanos(r12);	 Catch:{ CancelledKeyException -> 0x00cc }
        r14 = r10 - r14;
        r14 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r14 < 0) goto L_0x00a9;
    L_0x00a5:
        r5 = 1;
    L_0x00a6:
        r2 = r10;
        goto L_0x0011;
    L_0x00a9:
        r14 = SELECTOR_AUTO_REBUILD_THRESHOLD;	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r14 <= 0) goto L_0x00a6;
    L_0x00ad:
        r14 = SELECTOR_AUTO_REBUILD_THRESHOLD;	 Catch:{ CancelledKeyException -> 0x00cc }
        if (r5 < r14) goto L_0x00a6;
    L_0x00b1:
        r14 = logger;	 Catch:{ CancelledKeyException -> 0x00cc }
        r15 = "Selector.select() returned prematurely {} times in a_isRightVersion row; rebuilding Selector {}.";
        r16 = java.lang.Integer.valueOf(r5);	 Catch:{ CancelledKeyException -> 0x00cc }
        r0 = r16;
        r14.warn(r15, r0, r9);	 Catch:{ CancelledKeyException -> 0x00cc }
        r18.rebuildSelector();	 Catch:{ CancelledKeyException -> 0x00cc }
        r0 = r18;
        r9 = r0.selector;	 Catch:{ CancelledKeyException -> 0x00cc }
        r9.selectNow();	 Catch:{ CancelledKeyException -> 0x00cc }
        r5 = 1;
        goto L_0x0029;
    L_0x00cc:
        r4 = move-exception;
        r14 = logger;
        r14 = r14.isDebugEnabled();
        if (r14 == 0) goto L_0x0044;
    L_0x00d5:
        r14 = logger;
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r16 = java.nio.channels.CancelledKeyException.class;
        r16 = r16.getSimpleName();
        r15 = r15.append(r16);
        r16 = " raised by a_isRightVersion Selector {} - JDK bug?";
        r15 = r15.append(r16);
        r15 = r15.toString();
        r14.debug(r15, r9, r4);
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.nio.NioEventLoop.select(boolean):void");
    }

    private void selectAgain() {
        this.needsToSelectAgain = false;
        try {
            this.selector.selectNow();
        } catch (Throwable t) {
            logger.warn("Failed to update SelectionKeys.", t);
        }
    }
}
