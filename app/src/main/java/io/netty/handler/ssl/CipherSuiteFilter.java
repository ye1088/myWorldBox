package io.netty.handler.ssl;

import java.util.List;
import java.util.Set;

public interface CipherSuiteFilter {
    String[] filterCipherSuites(Iterable<String> iterable, List<String> list, Set<String> set);
}
