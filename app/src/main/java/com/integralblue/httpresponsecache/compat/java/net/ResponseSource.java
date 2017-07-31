package com.integralblue.httpresponsecache.compat.java.net;

public enum ResponseSource {
    CACHE,
    CONDITIONAL_CACHE,
    NETWORK;

    public boolean requiresConnection() {
        return this == CONDITIONAL_CACHE || this == NETWORK;
    }
}
