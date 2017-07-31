package io.netty.handler.ssl;

import io.netty.util.internal.SystemPropertyUtil;
import java.security.PrivilegedAction;

class OpenSsl$1 implements PrivilegedAction<Boolean> {
    OpenSsl$1() {
    }

    public Boolean run() {
        return Boolean.valueOf(SystemPropertyUtil.getBoolean("io.netty.handler.ssl.openssl.useKeyManagerFactory", true));
    }
}
