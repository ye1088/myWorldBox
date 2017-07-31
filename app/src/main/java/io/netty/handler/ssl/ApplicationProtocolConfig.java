package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;

public final class ApplicationProtocolConfig {
    public static final ApplicationProtocolConfig DISABLED = new ApplicationProtocolConfig();
    private final Protocol protocol;
    private final SelectedListenerFailureBehavior selectedBehavior;
    private final SelectorFailureBehavior selectorBehavior;
    private final List<String> supportedProtocols;

    public enum Protocol {
        NONE,
        NPN,
        ALPN,
        NPN_AND_ALPN
    }

    public enum SelectedListenerFailureBehavior {
        ACCEPT,
        FATAL_ALERT,
        CHOOSE_MY_LAST_PROTOCOL
    }

    public enum SelectorFailureBehavior {
        FATAL_ALERT,
        NO_ADVERTISE,
        CHOOSE_MY_LAST_PROTOCOL
    }

    public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, Iterable<String> supportedProtocols) {
        this(protocol, selectorBehavior, selectedBehavior, ApplicationProtocolUtil.toList((Iterable) supportedProtocols));
    }

    public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, String... supportedProtocols) {
        this(protocol, selectorBehavior, selectedBehavior, ApplicationProtocolUtil.toList(supportedProtocols));
    }

    private ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorBehavior, SelectedListenerFailureBehavior selectedBehavior, List<String> supportedProtocols) {
        this.supportedProtocols = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(supportedProtocols, "supportedProtocols"));
        this.protocol = (Protocol) ObjectUtil.checkNotNull(protocol, "protocol");
        this.selectorBehavior = (SelectorFailureBehavior) ObjectUtil.checkNotNull(selectorBehavior, "selectorBehavior");
        this.selectedBehavior = (SelectedListenerFailureBehavior) ObjectUtil.checkNotNull(selectedBehavior, "selectedBehavior");
        if (protocol == Protocol.NONE) {
            throw new IllegalArgumentException("protocol (" + Protocol.NONE + ") must not be " + Protocol.NONE + '.');
        } else if (supportedProtocols.isEmpty()) {
            throw new IllegalArgumentException("supportedProtocols must be not empty");
        }
    }

    private ApplicationProtocolConfig() {
        this.supportedProtocols = Collections.emptyList();
        this.protocol = Protocol.NONE;
        this.selectorBehavior = SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        this.selectedBehavior = SelectedListenerFailureBehavior.ACCEPT;
    }

    public List<String> supportedProtocols() {
        return this.supportedProtocols;
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return this.selectorBehavior;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.selectedBehavior;
    }
}
