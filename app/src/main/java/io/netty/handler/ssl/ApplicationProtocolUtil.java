package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;

final class ApplicationProtocolUtil {
    private static final int DEFAULT_LIST_SIZE = 2;

    private ApplicationProtocolUtil() {
    }

    static List<String> toList(Iterable<String> protocols) {
        return toList(2, (Iterable) protocols);
    }

    static List<String> toList(int initialListSize, Iterable<String> protocols) {
        if (protocols == null) {
            return null;
        }
        List<String> result = new ArrayList(initialListSize);
        for (String p : protocols) {
            if (p == null || p.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            result.add(p);
        }
        if (!result.isEmpty()) {
            return result;
        }
        throw new IllegalArgumentException("protocols cannot empty");
    }

    static List<String> toList(String... protocols) {
        return toList(2, protocols);
    }

    static List<String> toList(int initialListSize, String... protocols) {
        if (protocols == null) {
            return null;
        }
        List<String> result = new ArrayList(initialListSize);
        for (String p : protocols) {
            if (p == null || p.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            result.add(p);
        }
        if (!result.isEmpty()) {
            return result;
        }
        throw new IllegalArgumentException("protocols cannot empty");
    }
}
