package io.netty.handler.ssl;

import io.netty.util.internal.InternalThreadLocalMap;
import java.util.List;
import java.util.Set;

public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {
    public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter();

    private IdentityCipherSuiteFilter() {
    }

    public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
        if (ciphers == null) {
            return (String[]) defaultCiphers.toArray(new String[defaultCiphers.size()]);
        }
        List<String> newCiphers = InternalThreadLocalMap.get().arrayList(supportedCiphers.size());
        for (String c : ciphers) {
            if (c == null) {
                break;
            }
            newCiphers.add(c);
        }
        return (String[]) newCiphers.toArray(new String[newCiphers.size()]);
    }
}
