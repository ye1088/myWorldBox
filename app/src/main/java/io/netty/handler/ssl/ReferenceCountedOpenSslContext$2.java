package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import java.util.Collections;
import java.util.List;

class ReferenceCountedOpenSslContext$2 implements OpenSslApplicationProtocolNegotiator {
    ReferenceCountedOpenSslContext$2() {
    }

    public Protocol protocol() {
        return Protocol.NONE;
    }

    public List<String> protocols() {
        return Collections.emptyList();
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return SelectedListenerFailureBehavior.ACCEPT;
    }
}
