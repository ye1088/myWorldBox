package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;

/* synthetic */ class ReferenceCountedOpenSslContext$4 {
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol = new int[Protocol.values().length];
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior = new int[SelectedListenerFailureBehavior.values().length];
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior = new int[SelectorFailureBehavior.values().length];

    static {
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[SelectedListenerFailureBehavior.ACCEPT.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[SelectorFailureBehavior.NO_ADVERTISE.ordinal()] = 1;
        } catch (NoSuchFieldError e3) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL.ordinal()] = 2;
        } catch (NoSuchFieldError e4) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.NPN.ordinal()] = 1;
        } catch (NoSuchFieldError e5) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.ALPN.ordinal()] = 2;
        } catch (NoSuchFieldError e6) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.NPN_AND_ALPN.ordinal()] = 3;
        } catch (NoSuchFieldError e7) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.NONE.ordinal()] = 4;
        } catch (NoSuchFieldError e8) {
        }
    }
}
