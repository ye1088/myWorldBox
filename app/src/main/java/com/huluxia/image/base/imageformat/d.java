package com.huluxia.image.base.imageformat;

import javax.annotation.Nullable;

/* compiled from: ImageFormat */
public class d {
    public static final d vz = new d("UNKNOWN", null);
    private final String mName;
    private final String vA;

    /* compiled from: ImageFormat */
    public interface a {
        @Nullable
        d b(byte[] bArr, int i);

        int getHeaderSize();
    }

    @Deprecated
    public static String c(d imageFormat) throws UnsupportedOperationException {
        String extension = imageFormat.hb();
        if (extension != null) {
            return extension;
        }
        throw new UnsupportedOperationException("Unknown image format " + imageFormat.getName());
    }

    public d(String name, @Nullable String fileExtension) {
        this.mName = name;
        this.vA = fileExtension;
    }

    @Nullable
    public String hb() {
        return this.vA;
    }

    public String toString() {
        return getName();
    }

    public String getName() {
        return this.mName;
    }
}
