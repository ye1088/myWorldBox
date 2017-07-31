package com.huluxia.image.pipeline.producers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import bolts.h;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.base.cache.common.c;
import com.huluxia.image.base.imagepipeline.image.d;
import com.huluxia.image.pipeline.request.c$b;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

/* compiled from: MediaVariationsIndexDatabase */
public class ae implements ad {
    private static final String[] Kf = new String[]{b.KI, b.KG, "height"};
    private static final String Kx = "DROP TABLE IF EXISTS media_variations_index";
    private static final String TAG = ae.class.getSimpleName();
    private final Executor EE;
    private final Executor EF;
    private final c Ky;

    /* compiled from: MediaVariationsIndexDatabase */
    private static class a extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "FrescoMediaVariationsIndex.db";
        public static final int DATABASE_VERSION = 1;
        private static final String KA = " TEXT";
        private static final String KC = " INTEGER";
        private static final String KD = "CREATE TABLE media_variations_index (_id INTEGER PRIMARY KEY,media_id TEXT,width INTEGER,height INTEGER,cache_key TEXT,resource_id TEXT )";
        private static final String KE = "CREATE INDEX index_media_id ON media_variations_index (media_id)";

        public a(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.beginTransaction();
            try {
                db.execSQL(KD);
                db.execSQL(KE);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.beginTransaction();
            try {
                db.execSQL(ae.Kx);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    /* compiled from: MediaVariationsIndexDatabase */
    private static final class b implements BaseColumns {
        public static final String KF = "media_id";
        public static final String KG = "width";
        public static final String KH = "height";
        public static final String KI = "cache_key";
        public static final String KJ = "resource_id";
        public static final String TABLE_NAME = "media_variations_index";

        private b() {
        }
    }

    public ae(Context context, Executor readExecutor, Executor writeExecutor) {
        this.Ky = new c(context, null);
        this.EE = readExecutor;
        this.EF = writeExecutor;
    }

    public h<List<c$b>> bJ(final String mediaId) {
        try {
            return h.a(new Callable<List<c$b>>(this) {
                final /* synthetic */ ae Kz;

                public /* synthetic */ Object call() throws Exception {
                    return oY();
                }

                public List<c$b> oY() throws Exception {
                    return this.Kz.bK(mediaId);
                }
            }, this.EE);
        } catch (Exception exception) {
            HLog.warn(TAG, "Failed to schedule query task for" + mediaId + ", ex " + exception, new Object[0]);
            return h.f(exception);
        }
    }

    private synchronized List<c$b> bK(String mediaId) {
        List<c$b> list = null;
        synchronized (this) {
            SQLiteDatabase db = this.Ky.getWritableDatabase();
            Cursor c = null;
            try {
                String[] selectionArgs = new String[]{mediaId};
                c = db.query(b.TABLE_NAME, Kf, "media_id = ?", selectionArgs, null, null, null);
                if (c.getCount() == 0) {
                    if (c != null) {
                        c.close();
                    }
                    db.close();
                } else {
                    int columnIndexCacheKey = c.getColumnIndexOrThrow(b.KI);
                    int columnIndexWidth = c.getColumnIndexOrThrow(b.KG);
                    int columnIndexHeight = c.getColumnIndexOrThrow("height");
                    list = new ArrayList(c.getCount());
                    while (c.moveToNext()) {
                        list.add(new c$b(Uri.parse(c.getString(columnIndexCacheKey)), c.getInt(columnIndexWidth), c.getInt(columnIndexHeight)));
                    }
                    if (c != null) {
                        c.close();
                    }
                    db.close();
                }
            } catch (SQLException x) {
                HLog.error(TAG, "Error reading for " + mediaId + ", ex " + x, new Object[0]);
                throw x;
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
                db.close();
            }
        }
        return list;
    }

    public void a(final String mediaId, final com.huluxia.image.base.cache.common.b cacheKey, final d encodedImage) {
        this.EF.execute(new Runnable(this) {
            final /* synthetic */ ae Kz;

            public void run() {
                SQLiteDatabase db = this.Kz.Ky.getWritableDatabase();
                try {
                    db.beginTransaction();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(b.KF, mediaId);
                    contentValues.put(b.KG, Integer.valueOf(encodedImage.getWidth()));
                    contentValues.put("height", Integer.valueOf(encodedImage.getHeight()));
                    contentValues.put(b.KI, cacheKey.getUriString());
                    contentValues.put(b.KJ, c.b(cacheKey));
                    db.insertOrThrow(b.TABLE_NAME, null, contentValues);
                    db.setTransactionSuccessful();
                } catch (Exception x) {
                    HLog.error(ae.TAG, "Error writing for " + mediaId + ", ex " + x, new Object[0]);
                } finally {
                    db.endTransaction();
                }
            }
        });
    }
}
