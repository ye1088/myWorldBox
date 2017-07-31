package io.netty.handler.ssl;

import io.netty.util.internal.InternalThreadLocalMap;
import java.util.List;
import java.util.Set;

public final class SupportedCipherSuiteFilter implements CipherSuiteFilter {
    public static final SupportedCipherSuiteFilter INSTANCE = new SupportedCipherSuiteFilter();

    private SupportedCipherSuiteFilter() {
    }

    public String[] filterCipherSuites(Iterable<String> ciphers, List<String> defaultCiphers, Set<String> supportedCiphers) {
        if (defaultCiphers == null) {
            throw new NullPointerException("defaultCiphers");
        } else if (supportedCiphers == null) {
            throw new NullPointerException("supportedCiphers");
        } else {
            List<String> newCiphers;
            if (ciphers == null) {
                newCiphers = InternalThreadLocalMap.get().arrayList(defaultCiphers.size());
                ciphers = defaultCiphers;
            } else {
                newCiphers = InternalThreadLocalMap.get().arrayList(supportedCiphers.size());
            }
            for (String c : ciphers) {
                if (c == null) {
                    break;
                } else if (supportedCiphers.contains(c)) {
                    newCiphers.add(c);
                }
            }
            return (String[]) newCiphers.toArray(new String[newCiphers.size()]);
        }
    }
}
