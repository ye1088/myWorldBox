package io.netty.channel;

import io.netty.util.internal.TypeParameterMatcher;

public abstract class SimpleChannelInboundHandler<I> extends ChannelInboundHandlerAdapter {
    private final boolean autoRelease;
    private final TypeParameterMatcher matcher;

    public void channelRead(io.netty.channel.ChannelHandlerContext r5, java.lang.Object r6) throws java.lang.Exception {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0014 in list [B:7:0x000f]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r1 = 1;
        r2 = r4.acceptInboundMessage(r6);	 Catch:{ all -> 0x001a }
        if (r2 == 0) goto L_0x0015;	 Catch:{ all -> 0x001a }
    L_0x0007:
        r0 = r6;	 Catch:{ all -> 0x001a }
        r4.channelRead0(r5, r0);	 Catch:{ all -> 0x001a }
    L_0x000b:
        r2 = r4.autoRelease;
        if (r2 == 0) goto L_0x0014;
    L_0x000f:
        if (r1 == 0) goto L_0x0014;
    L_0x0011:
        io.netty.util.ReferenceCountUtil.release(r6);
    L_0x0014:
        return;
    L_0x0015:
        r1 = 0;
        r5.fireChannelRead(r6);	 Catch:{ all -> 0x001a }
        goto L_0x000b;
    L_0x001a:
        r2 = move-exception;
        r3 = r4.autoRelease;
        if (r3 == 0) goto L_0x0024;
    L_0x001f:
        if (r1 == 0) goto L_0x0024;
    L_0x0021:
        io.netty.util.ReferenceCountUtil.release(r6);
    L_0x0024:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.channel.SimpleChannelInboundHandler.channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object):void");
    }

    protected abstract void channelRead0(ChannelHandlerContext channelHandlerContext, I i) throws Exception;

    protected SimpleChannelInboundHandler() {
        this(true);
    }

    protected SimpleChannelInboundHandler(boolean autoRelease) {
        this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
        this.autoRelease = autoRelease;
    }

    protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType) {
        this(inboundMessageType, true);
    }

    protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType, boolean autoRelease) {
        this.matcher = TypeParameterMatcher.get(inboundMessageType);
        this.autoRelease = autoRelease;
    }

    public boolean acceptInboundMessage(Object msg) throws Exception {
        return this.matcher.match(msg);
    }
}
