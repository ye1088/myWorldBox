package com.MCWorld.image.pipeline.producers.ishare;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import com.MCWorld.framework.base.utils.ImmutableMap;
import com.MCWorld.framework.base.utils.VisibleForTesting;
import com.MCWorld.image.base.imagepipeline.image.b;
import com.MCWorld.image.base.imagepipeline.image.f;
import com.MCWorld.image.core.common.references.a;
import com.MCWorld.image.pipeline.producers.StatefulProducerRunnable;
import com.MCWorld.image.pipeline.producers.am;
import com.MCWorld.image.pipeline.producers.ao;
import com.MCWorld.image.pipeline.producers.aq;
import com.MCWorld.image.pipeline.producers.e;
import com.MCWorld.image.pipeline.producers.j;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.j256.ormlite.field.FieldType;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: ShareTransferImagePathProducer */
public class c implements am<a<b>> {
    public static final String Ji = "ShareTransferImagePathProducer";
    @VisibleForTesting
    static final String Kl = "shareTransferImagePath";
    public static final String yW = "authority_picture_path";
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;

    public c(Executor executor, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mContentResolver = contentResolver;
    }

    public Options ps() {
        Options opt = new Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        return opt;
    }

    public void b(j<a<b>> consumer, ao producerContext) {
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.oA();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<a<b>>(this, consumer, listener, Ji, requestId) {
            final /* synthetic */ c Mj;

            protected /* synthetic */ Map M(Object obj) {
                return j((a) obj);
            }

            protected /* synthetic */ Object getResult() throws Exception {
                return nt();
            }

            protected /* synthetic */ void p(Object obj) {
                k((a) obj);
            }

            protected a<b> nt() throws Exception {
                Uri uri = imageRequest.pv();
                String path = uri.getPath();
                Bitmap bitmap = null;
                String authority = uri.getAuthority();
                int orientation = 0;
                if (path != null && path.trim().length() > 0) {
                    path = path.substring(1);
                    if ("authority_picture_path".equals(authority)) {
                        String[] str = new String[]{path};
                        Cursor cursor = this.Mj.mContentResolver.query(Media.EXTERNAL_CONTENT_URI, null, "_data = ? ", str, null);
                        if (cursor == null) {
                            return null;
                        }
                        if (cursor.getCount() == 0) {
                            return null;
                        }
                        while (cursor.moveToNext()) {
                            int imageID = cursor.getInt(cursor.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX));
                            String orientationStr = cursor.getString(cursor.getColumnIndex("orientation"));
                            if (orientationStr != null && orientationStr.trim().length() > 0) {
                                try {
                                    orientation = Integer.parseInt(orientationStr);
                                } catch (Exception e) {
                                }
                            }
                            try {
                                bitmap = Thumbnails.getThumbnail(this.Mj.mContentResolver, (long) imageID, 1, this.Mj.ps());
                            } finally {
                                cursor.close();
                            }
                        }
                        cursor.close();
                    }
                }
                if (bitmap == null) {
                    return null;
                }
                return a.b(new com.MCWorld.image.base.imagepipeline.image.c(bitmap, com.MCWorld.image.base.imagepipeline.bitmaps.b.hf(), f.wZ, orientation));
            }

            protected Map<String, String> j(a<b> result) {
                return ImmutableMap.of(c.Kl, String.valueOf(result != null));
            }

            protected void k(a<b> result) {
                a.c(result);
            }
        };
        producerContext.a(new e(this) {
            final /* synthetic */ c Mj;

            public void cj() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }
}
