package com.MCWorld.image.pipeline.producers.ishare;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import com.MCWorld.image.base.imagepipeline.common.c;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.pipeline.producers.ba;
import com.MCWorld.image.pipeline.producers.y;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.j256.ormlite.field.FieldType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executor;

/* compiled from: ShareTransferImageContentFetchProducer */
public class b extends y {
    public static final String Ji = "ShareTransferImageFetchProducer";
    private static final String[] Kg = new String[]{"_data"};
    private static final Rect Kh = new Rect(0, 0, 512, com.MCWorld.image.pipeline.memory.b.HD);
    private static final Rect Ki = new Rect(0, 0, 96, 96);
    private static final int Kj = 0;
    public static final String yU = "authority_picture";
    public static final String yV = "authority_thumbnails";
    public static final String yW = "authority_picture_path";
    private final ContentResolver mContentResolver;

    public b(Executor executor, d pooledByteBufferFactory, ContentResolver contentResolver, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mContentResolver = contentResolver;
    }

    protected com.MCWorld.image.base.imagepipeline.image.d l(ImageRequest imageRequest) throws IOException {
        Uri uri = imageRequest.pv();
        Uri contentUri = null;
        String path = uri.getPath();
        String authority = uri.getAuthority();
        if (path != null && path.trim().length() > 0) {
            path = path.substring(1);
            if ("authority_thumbnails".equals(authority)) {
                contentUri = ContentUris.withAppendedId(Thumbnails.EXTERNAL_CONTENT_URI, Long.parseLong(path));
            } else if ("authority_picture".equals(authority)) {
                contentUri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, Long.parseLong(path));
            } else if ("authority_picture_path".equals(authority)) {
                String[] str = new String[]{path};
                Cursor cursor = this.mContentResolver.query(Media.EXTERNAL_CONTENT_URI, null, "_data = ? ", str, null);
                if (cursor == null) {
                    return null;
                }
                try {
                    if (cursor.getCount() == 0) {
                        return null;
                    }
                    if (cursor.moveToNext()) {
                        com.MCWorld.image.base.imagepipeline.image.d encodedImage = a(imageRequest.pz(), cursor.getInt(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX)));
                        cursor.close();
                        return encodedImage;
                    }
                    cursor.close();
                } finally {
                    cursor.close();
                }
            }
        }
        if (contentUri == null) {
            return null;
        }
        return g(this.mContentResolver.openInputStream(contentUri), -1);
    }

    private com.MCWorld.image.base.imagepipeline.image.d a(c resizeOptions, int imageId) throws IOException {
        com.MCWorld.image.base.imagepipeline.image.d dVar = null;
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
}
