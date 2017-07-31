package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLEngine;

final class JdkDefaultApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    private static final SslEngineWrapperFactory DEFAULT_SSL_ENGINE_WRAPPER_FACTORY = new SslEngineWrapperFactory() {
        public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            return engine;
        }
    };
    public static final JdkDefaultApplicationProtocolNegotiator INSTANCE = new JdkDefaultApplicationProtocolNegotiator();

    private JdkDefaultApplicationProtocolNegotiator() {
    }

    public SslEngineWrapperFactory wrapperFactory() {
        return DEFAULT_SSL_ENGINE_WRAPPER_FACTORY;
    }

    public ProtocolSelectorFactory protocolSelectorFactory() {
        throw new UnsupportedOperationException("Application protocol negotiation unsupported");
    }

    public ProtocolSelectionListenerFactory protocolListenerFactory() {
        throw new UnsupportedOperationException("Application protocol negotiation unsupported");
    }

    public List<String> protocols() {
        return Collections.emptyList();
    }
}
