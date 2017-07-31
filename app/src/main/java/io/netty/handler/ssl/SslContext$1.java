package io.netty.handler.ssl;

/* synthetic */ class SslContext$1 {
    static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$SslProvider = new int[SslProvider.values().length];

    static {
        try {
            $SwitchMap$io$netty$handler$ssl$SslProvider[SslProvider.JDK.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$SslProvider[SslProvider.OPENSSL.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            $SwitchMap$io$netty$handler$ssl$SslProvider[SslProvider.OPENSSL_REFCNT.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
    }
}
