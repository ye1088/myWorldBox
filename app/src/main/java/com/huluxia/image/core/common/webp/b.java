package com.huluxia.image.core.common.webp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import java.io.FileDescriptor;
import java.io.InputStream;

/* compiled from: WebpBitmapFactory */
public interface b {

    /* compiled from: WebpBitmapFactory */
    public interface a {
        void m(String str, String str2);
    }

    void a(a aVar);

    void a(a aVar);

    Bitmap decodeByteArray(byte[] bArr, int i, int i2, Options options);

    Bitmap decodeFile(String str, Options options);

    Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor, Rect rect, Options options);

    Bitmap decodeStream(InputStream inputStream, Rect rect, Options options);
}
