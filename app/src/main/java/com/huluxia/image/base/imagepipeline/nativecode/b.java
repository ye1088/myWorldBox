package com.huluxia.image.base.imagepipeline.nativecode;

/* compiled from: WebpTranscoderFactory */
public class b {
    private static a xm;
    public static boolean xn;

    static {
        xn = false;
        try {
            xm = (a) Class.forName("com.huluxia.image.base.imagepipeline.nativecode.WebpTranscoderImpl").newInstance();
            xn = true;
        } catch (Throwable th) {
            xn = false;
        }
    }

    public static a ii() {
        return xm;
    }
}
