package com.huluxia.image.base.imageformat;

import com.huluxia.framework.base.utils.ImmutableList;
import java.util.ArrayList;
import java.util.List;

/* compiled from: DefaultImageFormats */
public final class b {
    public static final d vl = new d("JPEG", "jpeg");
    public static final d vm = new d("PNG", "png");
    public static final d vn = new d("GIF", "gif");
    public static final d vo = new d("BMP", "bmp");
    public static final d vp = new d("WEBP_SIMPLE", "webp");
    public static final d vq = new d("WEBP_LOSSLESS", "webp");
    public static final d vr = new d("WEBP_EXTENDED", "webp");
    public static final d vs = new d("WEBP_EXTENDED_WITH_ALPHA", "webp");
    public static final d vt = new d("WEBP_ANIMATED", "webp");
    private static ImmutableList<d> vu;

    public static boolean a(d imageFormat) {
        return b(imageFormat) || imageFormat == vt;
    }

    public static boolean b(d imageFormat) {
        return imageFormat == vp || imageFormat == vq || imageFormat == vr || imageFormat == vs;
    }

    public static List<d> ha() {
        if (vu == null) {
            List<d> mDefaultFormats = new ArrayList(9);
            mDefaultFormats.add(vl);
            mDefaultFormats.add(vm);
            mDefaultFormats.add(vn);
            mDefaultFormats.add(vo);
            mDefaultFormats.add(vp);
            mDefaultFormats.add(vq);
            mDefaultFormats.add(vr);
            mDefaultFormats.add(vs);
            mDefaultFormats.add(vt);
            vu = ImmutableList.copyOf(mDefaultFormats);
        }
        return vu;
    }

    private b() {
    }
}
