package com.huluxia.image.base.imageformat;

import com.huluxia.framework.base.utils.Ints;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.core.common.webp.c;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import javax.annotation.Nullable;

/* compiled from: DefaultImageFormatChecker */
public class a implements com.huluxia.image.base.imageformat.d.a {
    private static final int va = 20;
    private static final int vb = 21;
    private static final byte[] vc = new byte[]{(byte) -1, (byte) -40, (byte) -1};
    private static final int vd = vc.length;
    private static final byte[] ve = new byte[]{(byte) -119, (byte) 80, (byte) 78, (byte) 71, (byte) 13, (byte) 10, BinaryMemcacheOpcodes.PREPENDQ, (byte) 10};
    private static final int vf = ve.length;
    private static final byte[] vg = f.bk("GIF87a");
    private static final byte[] vh = f.bk("GIF89a");
    private static final int vi = 6;
    private static final byte[] vj = f.bk("BM");
    private static final int vk = vj.length;
    final int uZ = Ints.max(new int[]{21, 20, vd, vf, 6, vk});

    public int getHeaderSize() {
        return this.uZ;
    }

    @Nullable
    public final d b(byte[] headerBytes, int headerSize) {
        Preconditions.checkNotNull(headerBytes);
        if (c.g(headerBytes, 0, headerSize)) {
            return c(headerBytes, headerSize);
        }
        if (d(headerBytes, headerSize)) {
            return b.vl;
        }
        if (e(headerBytes, headerSize)) {
            return b.vm;
        }
        if (f(headerBytes, headerSize)) {
            return b.vn;
        }
        if (g(headerBytes, headerSize)) {
            return b.vo;
        }
        return d.vz;
    }

    private static d c(byte[] imageHeaderBytes, int headerSize) {
        Preconditions.checkArgument(c.g(imageHeaderBytes, 0, headerSize));
        if (c.i(imageHeaderBytes, 0)) {
            return b.vp;
        }
        if (c.j(imageHeaderBytes, 0)) {
            return b.vq;
        }
        if (!c.f(imageHeaderBytes, 0, headerSize)) {
            return d.vz;
        }
        if (c.h(imageHeaderBytes, 0)) {
            return b.vt;
        }
        if (c.k(imageHeaderBytes, 0)) {
            return b.vs;
        }
        return b.vr;
    }

    private static boolean d(byte[] imageHeaderBytes, int headerSize) {
        return headerSize >= vc.length && f.c(imageHeaderBytes, vc);
    }

    private static boolean e(byte[] imageHeaderBytes, int headerSize) {
        return headerSize >= ve.length && f.c(imageHeaderBytes, ve);
    }

    private static boolean f(byte[] imageHeaderBytes, int headerSize) {
        if (headerSize < 6) {
            return false;
        }
        if (f.c(imageHeaderBytes, vg) || f.c(imageHeaderBytes, vh)) {
            return true;
        }
        return false;
    }

    private static boolean g(byte[] imageHeaderBytes, int headerSize) {
        if (headerSize < vj.length) {
            return false;
        }
        return f.c(imageHeaderBytes, vj);
    }
}
