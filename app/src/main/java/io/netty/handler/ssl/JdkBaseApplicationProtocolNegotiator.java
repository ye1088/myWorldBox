package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;

class JdkBaseApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    static final ProtocolSelectionListenerFactory FAIL_SELECTION_LISTENER_FACTORY = new ProtocolSelectionListenerFactory() {
        public ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
            return new FailProtocolSelectionListener((JdkSslEngine) engine, supportedProtocols);
        }
    };
    static final ProtocolSelectorFactory FAIL_SELECTOR_FACTORY = new ProtocolSelectorFactory() {
        public ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
            return new FailProtocolSelector((JdkSslEngine) engine, supportedProtocols);
        }
    };
    static final ProtocolSelectionListenerFactory NO_FAIL_SELECTION_LISTENER_FACTORY = new ProtocolSelectionListenerFactory() {
        public ProtocolSelectionListener newListener(SSLEngine engine, List<String> supportedProtocols) {
            return new NoFailProtocolSelectionListener((JdkSslEngine) engine, supportedProtocols);
        }
    };
    static final ProtocolSelectorFactory NO_FAIL_SELECTOR_FACTORY = new ProtocolSelectorFactory() {
        public ProtocolSelector newSelector(SSLEngine engine, Set<String> supportedProtocols) {
            return new NoFailProtocolSelector((JdkSslEngine) engine, supportedProtocols);
        }
    };
    private final ProtocolSelectionListenerFactory listenerFactory;
    private final List<String> protocols;
    private final ProtocolSelectorFactory selectorFactory;
    private final SslEngineWrapperFactory wrapperFactory;

    protected static class NoFailProtocolSelectionListener implements ProtocolSelectionListener {
        private final JdkSslEngine jettyWrapper;
        private final List<String> supportedProtocols;

        public NoFailProtocolSelectionListener(JdkSslEngine jettyWrapper, List<String> supportedProtocols) {
            this.jettyWrapper = jettyWrapper;
            this.supportedProtocols = supportedProtocols;
        }

        public void unsupported() {
            this.jettyWrapper.getSession().setApplicationProtocol(null);
        }

        public void selected(String protocol) throws Exception {
            if (this.supportedProtocols.contains(protocol)) {
                this.jettyWrapper.getSession().setApplicationProtocol(protocol);
            } else {
                noSelectedMatchFound(protocol);
            }
        }

        public void noSelectedMatchFound(String protocol) throws Exception {
        }
    }

    protected static final class FailProtocolSelectionListener extends NoFailProtocolSelectionListener {
        public FailProtocolSelectionListener(JdkSslEngine jettyWrapper, List<String> supportedProtocols) {
            super(jettyWrapper, supportedProtocols);
        }

        public void noSelectedMatchFound(String protocol) throws Exception {
            throw new SSLHandshakeException("No compatible protocols found");
        }
    }

    protected static class NoFailProtocolSelector implements ProtocolSelector {
        private final JdkSslEngine jettyWrapper;
        private final Set<String> supportedProtocols;

        public NoFailProtocolSelector(JdkSslEngine jettyWrapper, Set<String> supportedProtocols) {
            this.jettyWrapper = jettyWrapper;
            this.supportedProtocols = supportedProtocols;
        }

        public void unsupported() {
            this.jettyWrapper.getSession().setApplicationProtocol(null);
        }

        public String select(List<String> protocols) throws Exception {
            for (String p : this.supportedProtocols) {
                if (protocols.contains(p)) {
                    this.jettyWrapper.getSession().setApplicationProtocol(p);
                    return p;
                }
            }
            return noSelectMatchFound();
        }

        public String noSelectMatchFound() throws Exception {
            this.jettyWrapper.getSession().setApplicationProtocol(null);
            return null;
        }
    }

    protected static final class FailProtocolSelector extends NoFailProtocolSelector {
        public FailProtocolSelector(JdkSslEngine jettyWrapper, Set<String> supportedProtocols) {
            super(jettyWrapper, supportedProtocols);
        }

        public String noSelectMatchFound() throws Exception {
            throw new SSLHandshakeException("Selected protocol is not supported");
        }
    }

    protected JdkBaseApplicationProtocolNegotiator(SslEngineWrapperFactory wrapperFactory, ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, Iterable<String> protocols) {
        this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList((Iterable) protocols));
    }

    protected JdkBaseApplicationProtocolNegotiator(SslEngineWrapperFactory wrapperFactory, ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, String... protocols) {
        this(wrapperFactory, selectorFactory, listenerFactory, ApplicationProtocolUtil.toList(protocols));
    }

    private JdkBaseApplicationProtocolNegotiator(SslEngineWrapperFactory wrapperFactory, ProtocolSelectorFactory selectorFactory, ProtocolSelectionListenerFactory listenerFactory, List<String> protocols) {
        this.wrapperFactory = (SslEngineWrapperFactory) ObjectUtil.checkNotNull(wrapperFactory, "wrapperFactory");
        this.selectorFactory = (ProtocolSelectorFactory) ObjectUtil.checkNotNull(selectorFactory, "selectorFactory");
        this.listenerFactory = (ProtocolSelectionListenerFactory) ObjectUtil.checkNotNull(listenerFactory, "listenerFactory");
        this.protocols = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(protocols, "protocols"));
    }

    public List<String> protocols() {
        return this.protocols;
    }

    public ProtocolSelectorFactory protocolSelectorFactory() {
        return this.selectorFactory;
    }

    public ProtocolSelectionListenerFactory protocolListenerFactory() {
        return this.listenerFactory;
    }

    public SslEngineWrapperFactory wrapperFactory() {
        return this.wrapperFactory;
    }
}
