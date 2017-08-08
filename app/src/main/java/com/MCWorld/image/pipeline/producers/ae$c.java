package com.MCWorld.image.pipeline.producers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.annotation.Nullable;

/* compiled from: MediaVariationsIndexDatabase */
class ae$c {
    @Nullable
    private a KK;
    private final Context mContext;

    private ae$c(Context context) {
        this.mContext = context;
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        if (this.KK == null) {
            this.KK = new a(this.mContext);
        }
        return this.KK.getWritableDatabase();
    }
}
