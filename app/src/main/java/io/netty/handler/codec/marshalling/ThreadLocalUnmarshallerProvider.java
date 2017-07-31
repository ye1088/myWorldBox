package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

public class ThreadLocalUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;
    private final FastThreadLocal<Unmarshaller> unmarshallers = new FastThreadLocal();

    public ThreadLocalUnmarshallerProvider(MarshallerFactory factory, MarshallingConfiguration config) {
        this.factory = factory;
        this.config = config;
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext ctx) throws Exception {
        Unmarshaller unmarshaller = (Unmarshaller) this.unmarshallers.get();
        if (unmarshaller != null) {
            return unmarshaller;
        }
        unmarshaller = this.factory.createUnmarshaller(this.config);
        this.unmarshallers.set(unmarshaller);
        return unmarshaller;
    }
}
