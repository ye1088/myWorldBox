package com.MCWorld.image.pipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images.Thumbnails;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.image.base.imagepipeline.common.c;
import com.MCWorld.image.base.imagepipeline.image.d;
import com.MCWorld.image.core.common.util.f;
import com.MCWorld.image.pipeline.memory.b;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.j256.ormlite.field.FieldType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: LocalContentUriThumbnailFetchProducer */
public class w extends y implements az<d> {
    public static final String Ji = "LocalContentUriThumbnailFetchProducer";
    private static final String[] Kf = new String[]{FieldType.FOREIGN_ID_FIELD_SUFFIX, "_data"};
    private static final String[] Kg = new String[]{"_data"};
    private static final Rect Kh = new Rect(0, 0, 512, b.HD);
    private static final Rect Ki = new Rect(0, 0, 96, 96);
    private static final int Kj = 0;
    private static final Class<?> tF = w.class;
    private final ContentResolver mContentResolver;

    public w(Executor executor, com.MCWorld.image.base.imagepipeline.memory.d pooledByteBufferFactory, ContentResolver contentResolver, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mContentResolver = contentResolver;
    }

    public boolean a(c resizeOptions) {
        return ba.a(Kh.width(), Kh.height(), resizeOptions);
    }

    protected d l(ImageRequest imageRequest) throws IOException {
        Uri uri = imageRequest.pv();
        if (f.e(uri)) {
            d cameraImage = a(uri, imageRequest.pz());
            if (cameraImage != null) {
                return cameraImage;
            }
        }
        return null;
    }

    @Nullable
    private d a(Uri uri, c resizeOptions) throws IOException {
        Cursor cursor = this.mContentResolver.query(uri, Kf, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            String pathname = cursor.getString(cursor.getColumnIndex("_data"));
            if (resizeOptions != null) {
                d thumbnail = a(resizeOptions, cursor.getInt(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX)));
                if (thumbnail != null) {
                    thumbnail.bo(bH(pathname));
                    cursor.close();
                    return thumbnail;
                }
            }
            cursor.close();
            return null;
        } finally {
            cursor.close();
        }
    }

    private d a(c resizeOptions, int imageId) throws IOException {
        d dVar = null;
        int thumbnailKind = b(resizeOptions);
        if (thumbnailKind != 0) {
            Cursor thumbnailCursor = null;
            try {
                thumbnailCursor = Thumbnails.queryMiniThumbnail(this.mContentResolver, (long) imageId, thumbnailKind, Kg);
                if (thumbnailCursor != null) {
                    thumbnailCursor.moveToFirst();
                    if (thumbnailCursor.getCount() > 0) {
                        String thumbnailUri = thumbnailCursor.getString(thumbnailCursor.getColumnIndex("_data"));
                        if (new File(thumbnailUri).exists()) {
                            dVar = g(new FileInputStream(thumbnailUri), bG(thumbnailUri));
                            if (thumbnailCursor != null) {
                                thumbnailCursor.close();
                            }
                        }
                    }
                    if (thumbnailCursor != null) {
                        thumbnailCursor.close();
                    }
                } else if (thumbnailCursor != null) {
                    thumbnailCursor.close();
                }
            } catch (Throwable th) {
                if (thumbnailCursor != null) {
                    thumbnailCursor.close();
                }
            }
        }
        return dVar;
    }

    private static int b(c resizeOptions) {
        if (ba.a(Ki.width(), Ki.height(), resizeOptions)) {
            return 3;
        }
        if (ba.a(Kh.width(), Kh.height(), resizeOptions)) {
            return 1;
        }
        return 0;
    }

    private static int bG(String pathname) {
        return pathname == null ? -1 : (int) new File(pathname).length();
    }

    protected String oK() {
        return Ji;
    }

    private static int bH(String pathname) {
        int i = 0;
        if (pathname != null) {
            try {
                i = com.MCWorld.image.base.imageutils.b.bv(new ExifInterface(pathname).getAttributeInt("Orientation", 1));
            } catch (IOException ioe) {
                HLog.error(tF, "Unable to retrieve thumbnail rotation for " + pathname, ioe, new Object[i]);
            }
        }
        return i;
    }
}
