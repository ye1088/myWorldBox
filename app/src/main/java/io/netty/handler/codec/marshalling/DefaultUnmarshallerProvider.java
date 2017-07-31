package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public class DefaultUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;

    public DefaultUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
        this.factory = factory;
        this.config = config;
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
        return this.factory.createUnmarshaller(this.config);
    }
}
