package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

public final class OpenSslDefaultApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final ApplicationProtocolConfig config;

    public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig config) {
        this.config = (ApplicationProtocolConfig) ObjectUtil.checkNotNull(config, "config");
    }

    public List<String> protocols() {
        return this.config.supportedProtocols();
    }

    public Protocol protocol() {
        return this.config.protocol();
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return this.config.selectorFailureBehavior();
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.config.selectedListenerFailureBehavior();
    }
}
