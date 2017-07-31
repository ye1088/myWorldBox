package net.zhuoweizhang.mcpelauncher;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/* compiled from: TexturePack */
public interface h extends Closeable {
    InputStream cN(String str) throws IOException;

    long getSize(String str) throws IOException;

    List<String> ww() throws IOException;
}
