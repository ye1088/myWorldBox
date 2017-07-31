package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import io.netty.util.internal.ObjectUtil;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import org.eclipse.jetty.alpn.ALPN;
import org.eclipse.jetty.alpn.ALPN.ClientProvider;
import org.eclipse.jetty.alpn.ALPN.ServerProvider;

final class JdkAlpnSslEngine extends JdkSslEngine {
    private static boolean available;

    static boolean isAvailable() {
        updateAvailability();
        return available;
    }

    private static void updateAvailability() {
        if (!available) {
            try {
                Class.forName("sun.security.ssl.ALPNExtension", true, null);
                available = true;
            } catch (Exception e) {
            }
        }
    }

    JdkAlpnSslEngine(SSLEngine engine, final JdkApplicationProtocolNegotiator applicationNegotiator, boolean server) {
        super(engine);
        ObjectUtil.checkNotNull(applicationNegotiator, "applicationNegotiator");
        if (server) {
            final ProtocolSelector protocolSelector = (ProtocolSelector) ObjectUtil.checkNotNull(applicationNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(applicationNegotiator.protocols())), "protocolSelector");
            ALPN.put(engine, new ServerProvider() {
                public String select(List<String> protocols) throws SSLException {
                    try {
                        return protocolSelector.select(protocols);
                    } catch (SSLHandshakeException e) {
                        throw e;
                    } catch (Throwable t) {
                        new SSLHandshakeException(t.getMessage()).initCause(t);
                    }
                }

                public void unsupported() {
                    protocolSelector.unsupported();
                }
            });
            return;
        }
        final ProtocolSelectionListener protocolListener = (ProtocolSelectionListener) ObjectUtil.checkNotNull(applicationNegotiator.protocolListenerFactory().newListener(this, applicationNegotiator.protocols()), "protocolListener");
        ALPN.put(engine, new ClientProvider() {
            public List<String> protocols() {
                return applicationNegotiator.protocols();
            }

            public void selected(String protocol) throws SSLException {
                try {
                    protocolListener.selected(protocol);
                } catch (SSLHandshakeException e) {
                    throw e;
                } catch (Throwable t) {
                    new SSLHandshakeException(t.getMessage()).initCause(t);
                }
            }

            public void unsupported() {
                protocolListener.unsupported();
            }
        });
    }

    public void closeInbound() throws SSLException {
        ALPN.remove(getWrappedEngine());
        super.closeInbound();
    }

    public void closeOutbound() {
        ALPN.remove(getWrappedEngine());
        super.closeOutbound();
    }
}
