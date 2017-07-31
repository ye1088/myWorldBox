package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import java.util.List;
import javax.net.ssl.SSLEngine;

public final class JdkAlpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final SslEngineWrapperFactory ALPN_WRAPPER = new SslEngineWrapperFactory() {
        {
            if (!JdkAlpnSslEngine.isAvailable()) {
                throw new RuntimeException("ALPN unsupported. Is your classpatch configured correctly? See http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting");
            }
        }

        public SSLEngine wrapSslEngine(SSLEngine engine, JdkApplicationProtocolNegotiator applicationNegotiator, boolean isServer) {
            return new JdkAlpnSslEngine(engine, applicationNegotiator, isServer);
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

    public JdkAlpnApplicationProtocolNegotiator(Iterable<String> protocols) {
        this(false, (Iterable) protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(String... protocols) {
        this(false, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, Iterable<String> protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, (Iterable) protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean failIfNoCommonProtocols, String... protocols) {
        this(failIfNoCommonProtocols, failIfNoCommonProtocols, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, Iterable<String> protocols) {
        this(serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, (Iterable) protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean clientFailIfNoCommonProtocols, boolean serverFailIfNoCommonProtocols, String... protocols) {
        this(serverFailIfNoCommonProtocols ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, clientFailIfNoCommonProtocols ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
        super(ALPN_WRAPPER, selectorFactory, listenerFactory, (Iterable) protocols);
    }

    public JdkAlpnApplicationProtocolNegotiator(ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
        super(ALPN_WRAPPER, selectorFactory, listenerFactory, protocols);
    }
}
