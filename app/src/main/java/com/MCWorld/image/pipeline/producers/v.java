package com.MCWorld.image.pipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.core.common.util.f;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.j256.ormlite.field.FieldType;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* compiled from: LocalContentUriFetchProducer */
public class v extends y {
    public static final String Ji = "LocalContentUriFetchProducer";
    private static final String[] Kf = new String[]{FieldType.FOREIGN_ID_FIELD_SUFFIX, "_data"};
    private final ContentResolver mContentResolver;

    public v(Executor executor, d pooledByteBufferFactory, ContentResolver contentResolver, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mContentResolver = contentResolver;
    }

    protected com.MCWorld.image.base.imagepipeline.image.d l(ImageRequest imageRequest) throws IOException {
        Uri uri = imageRequest.pv();
        if (f.d(uri)) {
            InputStream inputStream;
            if (uri.toString().endsWith("/photo")) {
                inputStream = this.mContentResolver.openInputStream(uri);
            } else {
                inputStream = Contacts.openContactPhotoInputStream(this.mContentResolver, uri);
                if (inputStream == null) {
                    throw new IOException("Contact photo does not exist: " + uri);
                }
            }
            return g(inputStream, -1);
        }
        if (f.e(uri)) {
            com.MCWorld.image.base.imagepipeline.image.d cameraImage = t(uri);
            if (cameraImage != null) {
                return cameraImage;
            }
        }
        return g(this.mContentResolver.openInputStream(uri), -1);
    }

    @Nullable
    private com.MCWorld.image.base.imagepipeline.image.d t(Uri uri) throws IOException {
        com.MCWorld.image.base.imagepipeline.image.d dVar = null;
        Cursor cursor = this.mContentResolver.query(uri, Kf, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    String pathname = cursor.getString(cursor.getColumnIndex("_data"));
                    if (pathname != null) {
                        dVar = g(new FileInputStream(pathname), bG(pathname));
                        cursor.close();
                    } else {
                        cursor.close();
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return dVar;
    }

    private static int bG(String pathname) {
        return pathname == null ? -1 : (int) new File(pathname).length();
    }

    protected String oK() {
        return Ji;
    }
}
