package com.MCWorld.framework.base.http.toolbox.reader;

import java.io.IOException;

public interface IAdapterToStreamAndRaf {
    void close() throws IOException;

    void flush() throws IOException;

    void write(int i) throws IOException;

    void write(byte[] bArr) throws IOException;

    void write(byte[] bArr, int i, int i2) throws IOException;
}
