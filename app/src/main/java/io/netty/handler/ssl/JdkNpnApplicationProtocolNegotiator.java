package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import java.util.List;
import javax.net.ssl.SSLEngine;

public final class JdkNpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final SslEngineWrapperFactory NPN_WRAPPER = new SslEngineWrapperFactory() {
        {
            if (!JdkNpnSslEngine.isAvailable()) {
                throw new RuntimeException("NPN unsupported. Is your classpatch configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
            }
        }

        public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            return new JdkNpnSslEngine(engine, applicationNegotiator, isServer);
        }
    };

    public /* bridge */ /* synthetic */ ProtocolSelectionListenerFactory protocolListenerFactory() {
        return super.protocolListenerFactory();
    }

    public /* bridge */ /* synthetic */ ProtocolSelectorFactory protocolSelectorFactory() {
        return super.protocolSelectorFactory();
    }

    public /* bridge */ /* synthetic */ List protocols() {
        return super.protocols();
    }

    public /* bridge */ /* synthetic */ SslEngineWrapperFactory wrapperFactory() {
        return super.wrapperFactory();
    }

    public JdkNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
        this(false, (Iterable) protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(String... protocols) {
        this(false, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, (Iterable) protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
        this(clientFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, serverFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, (Iterable) protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
        this(clientFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, serverFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
        super(NPN_WRAPPER, selectorFactory, listenerFactory, (Iterable) protocols);
    }

    public JdkNpnApplicationProtocolNegotiator(ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
        super(NPN_WRAPPER, selectorFactory, listenerFactory, protocols);
    }
}
