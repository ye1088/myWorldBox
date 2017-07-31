package io.netty.resolver;

import io.netty.util.concurrent.EventExecutor;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public abstract class InetNameResolver extends SimpleNameResolver<InetAddress> {
    private volatile AddressResolver<InetSocketAddress> addressResolver;

    protected InetNameResolver(EventExecutor executor) {
        super(executor);
    }

    public AddressResolver<InetSocketAddress> asAddressResolver() {
        AddressResolver<InetSocketAddress> result = this.addressResolver;
        if (result == null) {
            synchronized (this) {
                try {
                    result = this.addressResolver;
                    if (result == null) {
                        AddressResolver<InetSocketAddress> result2 = new InetSocketAddressResolver(executor(), this);
                        try {
                            this.addressResolver = result2;
                            result = result2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            result = result2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return result;
    }
}
