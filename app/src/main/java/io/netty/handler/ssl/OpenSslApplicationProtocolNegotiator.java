package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;

public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
    Protocol protocol();

    SelectedListenerFailureBehavior selectedListenerFailureBehavior();

    SelectorFailureBehavior selectorFailureBehavior();
}
