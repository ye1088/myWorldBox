package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;

/* synthetic */ class JdkSslContext$1 {
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol = new int[Protocol.values().length];
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior = new int[SelectedListenerFailureBehavior.values().length];
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior = new int[SelectorFailureBehavior.values().length];
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ClientAuth = new int[ClientAuth.values().length];

    static {
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.NONE.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.ALPN.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol[Protocol.NPN.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[SelectedListenerFailureBehavior.ACCEPT.ordinal()] = 1;
        } catch (NoSuchFieldError e4) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior[SelectedListenerFailureBehavior.FATAL_ALERT.ordinal()] = 2;
        } catch (NoSuchFieldError e5) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[SelectorFailureBehavior.FATAL_ALERT.ordinal()] = 1;
        } catch (NoSuchFieldError e6) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior[SelectorFailureBehavior.NO_ADVERTISE.ordinal()] = 2;
        } catch (NoSuchFieldError e7) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.OPTIONAL.ordinal()] = 1;
        } catch (NoSuchFieldError e8) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$ClientAuth[ClientAuth.REQUIRE.ordinal()] = 2;
        } catch (NoSuchFieldError e9) {
        }
    }
}
