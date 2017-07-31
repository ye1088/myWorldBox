package io.netty.handler.traffic;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandlerContext;

final class AbstractTrafficShapingHandler$ReopenReadTimerTask implements Runnable {
    final ChannelHandlerContext ctx;

    AbstractTrafficShapingHandler$ReopenReadTimerTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void run() {
        ChannelConfig config = this.ctx.channel().config();
        if (config.isAutoRead() || !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
            if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                if (!config.isAutoRead() || AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                    AbstractTrafficShapingHandler.access$000().debug("Normal unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                } else {
                    AbstractTrafficShapingHandler.access$000().debug("Unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                }
            }
            this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
            config.setAutoRead(true);
            this.ctx.channel().read();
        } else {
            if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                AbstractTrafficShapingHandler.access$000().debug("Not unsuspend: " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
            }
            this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(Boolean.valueOf(false));
        }
        if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
            AbstractTrafficShapingHandler.access$000().debug("Unsupsend final status => " + config.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
        }
    }
}
