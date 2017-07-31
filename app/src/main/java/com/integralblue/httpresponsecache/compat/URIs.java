package com.integralblue.httpresponsecache.compat;

import java.net.URI;

public class URIs {
    public static final int getEffectivePort(URI uri) {
        return getEffectivePort(uri.getScheme(), uri.getPort());
    }

    public static int getEffectivePort(String scheme, int specifiedPort) {
        if (specifiedPort != -1) {
            return specifiedPort;
        }
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        }
        return "https".equalsIgnoreCase(scheme) ? 443 : -1;
    }
}
