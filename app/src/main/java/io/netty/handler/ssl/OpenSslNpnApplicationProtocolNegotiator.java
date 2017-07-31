package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

@Deprecated
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final List<String> protocols;

    public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> protocols) {
        this.protocols = (List) ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
    }

    public OpenSslNpnApplicationProtocolNegotiator(String... protocols) {
        this.protocols = (List) ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(protocols), "protocols");
    }

    public Protocol protocol() {
        return Protocol.NPN;
    }

    public List<String> protocols() {
        return this.protocols;
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return SelectedListenerFailureBehavior.ACCEPT;
    }
}
