package io.netty.handler.ssl;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;

/* synthetic */ class SslHandler$9 {
    static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = new int[HandshakeStatus.values().length];
    static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status = new int[Status.values().length];

    static {
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status[Status.BUFFER_OVERFLOW.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status[Status.CLOSED.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[HandshakeStatus.NEED_TASK.ordinal()] = 1;
        } catch (NoSuchFieldError e3) {
        }
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[HandshakeStatus.FINISHED.ordinal()] = 2;
        } catch (NoSuchFieldError e4) {
        }
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 3;
        } catch (NoSuchFieldError e5) {
        }
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[HandshakeStatus.NEED_WRAP.ordinal()] = 4;
        } catch (NoSuchFieldError e6) {
        }
        try {
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[HandshakeStatus.NEED_UNWRAP.ordinal()] = 5;
        } catch (NoSuchFieldError e7) {
        }
    }
}
