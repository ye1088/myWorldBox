package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;

public interface MDBItem {
    void convertFrom(Cursor cursor);

    ContentValues convertTo();
}
