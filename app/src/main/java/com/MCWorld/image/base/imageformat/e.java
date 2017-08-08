package com.MCWorld.image.base.imageformat;

import com.MCWorld.framework.base.utils.ByteStreams;
import com.MCWorld.framework.base.utils.Closeables;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Throwables;
import com.MCWorld.image.base.imageformat.d.a;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: ImageFormatChecker */
public class e {
    private static e vB;
    private int vC;
    @Nullable
    private List<a> vD;
    private final a vE = new a();

    private e() {
        hc();
    }

    public void j(@Nullable List<a> customImageFormatCheckers) {
        this.vD = customImageFormatCheckers;
        hc();
    }

    public d f(InputStream is) throws IOException {
        d format;
        Preconditions.checkNotNull(is);
        byte[] imageHeaderBytes = new byte[this.vC];
        int headerSize = a(this.vC, is, imageHeaderBytes);
        if (this.vD != null) {
            for (a formatChecker : this.vD) {
                format = formatChecker.b(imageHeaderBytes, headerSize);
                if (format != null && format != d.vz) {
                    return format;
                }
            }
        }
        format = this.vE.b(imageHeaderBytes, headerSize);
        if (format == null) {
            return d.vz;
        }
        return format;
    }

    private void hc() {
        this.vC = this.vE.getHeaderSize();
        if (this.vD != null) {
            for (a checker : this.vD) {
                this.vC = Math.max(this.vC, checker.getHeaderSize());
            }
        }
    }

    private static int a(int maxHeaderLength, InputStream is, byte[] imageHeaderBytes) throws IOException {
        Preconditions.checkNotNull(is);
        Preconditions.checkNotNull(imageHeaderBytes);
        Preconditions.checkArgument(imageHeaderBytes.length >= maxHeaderLength);
        if (!is.markSupported()) {
            return ByteStreams.read(is, imageHeaderBytes, 0, maxHeaderLength);
        }
        try {
            is.mark(maxHeaderLength);
            int read = ByteStreams.read(is, imageHeaderBytes, 0, maxHeaderLength);
            return read;
        } finally {
            is.reset();
        }
    }

    public static synchronized e hd() {
        e eVar;
        synchronized (e.class) {
            if (vB == null) {
                vB = new e();
            }
            eVar = vB;
        }
        return eVar;
    }

    public static d g(InputStream is) throws IOException {
        return hd().f(is);
    }

    public static d h(InputStream is) {
        try {
            return g(is);
        } catch (IOException ioe) {
            throw Throwables.propagate(ioe);
        }
    }

    public static d bj(String filename) {
        d g;
        Throwable th;
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(filename);
            try {
                g = g(fileInputStream2);
                Closeables.closeQuietly(fileInputStream2);
                fileInputStream = fileInputStream2;
            } catch (IOException e) {
                fileInputStream = fileInputStream2;
                try {
                    g = d.vz;
                    Closeables.closeQuietly(fileInputStream);
                    return g;
                } catch (Throwable th2) {
                    th = th2;
                    Closeables.closeQuietly(fileInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = fileInputStream2;
                Closeables.closeQuietly(fileInputStream);
                throw th;
            }
        } catch (IOException e2) {
            g = d.vz;
            Closeables.closeQuietly(fileInputStream);
            return g;
        }
        return g;
    }
}
