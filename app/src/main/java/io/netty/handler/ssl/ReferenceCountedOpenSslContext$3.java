package io.netty.handler.ssl;

import io.netty.util.internal.SystemPropertyUtil;
import java.security.PrivilegedAction;

class ReferenceCountedOpenSslContext$3 implements PrivilegedAction<String> {
    ReferenceCountedOpenSslContext$3() {
    }

    public String run() {
        return SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
    }
}
