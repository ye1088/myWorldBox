package com.MCWorld.framework.base.http.toolbox.reader;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class BufferOutputStreamAdapter extends BufferedOutputStream implements IAdapterToStreamAndRaf {
    public BufferOutputStreamAdapter(OutputStream out) {
        super(out);
    }

    public BufferOutputStreamAdapter(OutputStream out, int size) {
        super(out, size);
    }
}
