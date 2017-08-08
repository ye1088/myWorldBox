package com.MCWorld.image.base.imageutils;

import com.MCWorld.framework.base.utils.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.mozilla.classfile.ByteCode;

/* compiled from: JfifUtil */
public class b {
    public static final int xA = 218;
    public static final int xB = 225;
    public static final int xC = 192;
    public static final int xD = 208;
    public static final int xE = 215;
    public static final int xF = 1165519206;
    public static final int xv = 255;
    public static final int xw = 0;
    public static final int xx = 216;
    public static final int xy = 1;
    public static final int xz = 217;

    private b() {
    }

    public static int bv(int orientation) {
        return d.bv(orientation);
    }

    public static int l(byte[] jpeg) {
        return k(new ByteArrayInputStream(jpeg));
    }

    public static int k(InputStream is) {
        int i = 0;
        try {
            int length = l(is);
            if (length != 0) {
                i = d.c(is, length);
            }
        } catch (IOException e) {
        }
        return i;
    }

    public static boolean b(InputStream is, int markerToFind) throws IOException {
        Preconditions.checkNotNull(is);
        while (c.a(is, 1, false) == 255) {
            int marker = 255;
            while (marker == 255) {
                marker = c.a(is, 1, false);
            }
            if ((markerToFind == 192 && bw(marker)) || marker == markerToFind) {
                return true;
            }
            if (!(marker == 216 || marker == 1)) {
                if (marker == 217 || marker == 218) {
                    return false;
                }
                is.skip((long) (c.a(is, 2, false) - 2));
            }
        }
        return false;
    }

    private static boolean bw(int marker) {
        switch (marker) {
            case 192:
            case ByteCode.INSTANCEOF /*193*/:
            case ByteCode.MONITORENTER /*194*/:
            case ByteCode.MONITOREXIT /*195*/:
            case ByteCode.MULTIANEWARRAY /*197*/:
            case ByteCode.IFNULL /*198*/:
            case ByteCode.IFNONNULL /*199*/:
            case 201:
            case 202:
            case 203:
            case 205:
            case 206:
            case 207:
                return true;
            default:
                return false;
        }
    }

    private static int l(InputStream is) throws IOException {
        if (b(is, xB)) {
            int length = c.a(is, 2, false) - 2;
            if (length > 6) {
                int magic = c.a(is, 4, false);
                length -= 4;
                int zero = c.a(is, 2, false);
                length -= 2;
                if (magic == xF && zero == 0) {
                    return length;
                }
            }
        }
        return 0;
    }
}
