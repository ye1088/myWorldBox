package io.netty.handler.ssl;

import java.util.Enumeration;
import java.util.NoSuchElementException;

final class OpenSslSessionContext$EmptyEnumeration implements Enumeration<byte[]> {
    private OpenSslSessionContext$EmptyEnumeration() {
    }

    public boolean hasMoreElements() {
        return false;
    }

    public byte[] nextElement() {
        throw new NoSuchElementException();
    }
}
