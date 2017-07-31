package io.netty.channel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ChannelHandler {

    @Inherited
    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Sharable {
    }

    @Deprecated
    void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception;

    void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception;

    void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception;
}
