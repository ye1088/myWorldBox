package com.huluxia.image.pipeline.nativecode;

import com.huluxia.framework.base.utils.DoNotStrip;
import com.huluxia.framework.base.utils.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@DoNotStrip
public class JpegTranscoder {
    public static final int IL = 0;
    public static final int IM = 100;
    public static final int IO = 1;
    public static final int IP = 16;
    public static final int IQ = 8;

    @DoNotStrip
    private static native void nativeTranscodeJpeg(InputStream inputStream, OutputStream outputStream, int i, int i2, int i3) throws IOException;

    static {
        a.load();
    }

    public static boolean cL(int degrees) {
        return degrees >= 0 && degrees <= 270 && degrees % 90 == 0;
    }

    public static void a(InputStream inputStream, OutputStream outputStream, int rotationAngle, int scaleNumerator, int quality) throws IOException {
        boolean z;
        boolean z2 = false;
        Preconditions.checkArgument(scaleNumerator >= 1);
        if (scaleNumerator <= 16) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (quality >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        if (quality <= 100) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        Preconditions.checkArgument(cL(rotationAngle));
        if (!(scaleNumerator == 8 && rotationAngle == 0)) {
            z2 = true;
        }
        Preconditions.checkArgument(z2, "no transformation requested");
        nativeTranscodeJpeg((InputStream) Preconditions.checkNotNull(inputStream), (OutputStream) Preconditions.checkNotNull(outputStream), rotationAngle, scaleNumerator, quality);
    }
}
