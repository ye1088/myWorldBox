package io.netty.handler.ssl;

import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;

public interface JdkApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {

    public interface SslEngineWrapperFactory {
        SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z);
    }

    public interface ProtocolSelectionListener {
        void selected(String str) throws Exception;

        void unsupported();
    }

    public interface ProtocolSelectionListenerFactory {
        ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list);
    }

    public interface ProtocolSelector {
        String select(List<String> list) throws Exception;

        void unsupported();
    }

    public interface ProtocolSelectorFactory {
        ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set);
    }

    ProtocolSelectionListenerFactory protocolListenerFactory();

    ProtocolSelectorFactory protocolSelectorFactory();

    SslEngineWrapperFactory wrapperFactory();
}
