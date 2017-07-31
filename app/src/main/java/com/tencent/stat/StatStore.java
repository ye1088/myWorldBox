package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.HandlerThread;
import com.huluxia.module.p;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.User;
import com.tencent.stat.event.Event;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class StatStore {
    private static StatStore instance = null;
    private static StatLogger logger = StatCommonHelper.getLogger();
    Handler handler = null;
    private StatStoreHelper helper;
    private HashMap<String, String> kvMap = new HashMap();
    volatile int numStoredEvents = 0;
    User user = null;

    static class StatStoreHelper extends SQLiteOpenHelper {
        private static String DATABASE_NAME = "tencent_analysis.db";
        private static int DATABASE_VERSION = 3;

        public StatStoreHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private void upgradeEventsToVer3(SQLiteDatabase sQLiteDatabase) {
            Cursor query = sQLiteDatabase.query("events", null, null, null, null, null, null);
            List<StoredEvent> arrayList = new ArrayList();
            while (query.moveToNext()) {
                arrayList.add(new StoredEvent(query.getLong(0), query.getString(1), query.getInt(2), query.getInt(3)));
            }
            ContentValues contentValues = new ContentValues();
            for (StoredEvent storedEvent : arrayList) {
                contentValues.put("content", StatCommonHelper.encode(storedEvent.content));
                sQLiteDatabase.update("events", contentValues, "event_id=?", new String[]{Long.toString(storedEvent.id)});
            }
        }

        private void upgradeUserToVer3(SQLiteDatabase sQLiteDatabase) {
            String[] strArr = null;
            Cursor query = sQLiteDatabase.query("user", null, null, null, null, null, null);
            ContentValues contentValues = new ContentValues();
            if (query.moveToNext()) {
                strArr = query.getString(0);
                query.getInt(1);
                query.getString(2);
                query.getLong(3);
                contentValues.put(p.UID, StatCommonHelper.encode(strArr));
            }
            if (strArr != null) {
                sQLiteDatabase.update("user", contentValues, "uid=?", new String[]{strArr});
            }
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
            sQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
            sQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            StatStore.logger.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
            if (i == 1) {
                sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
                upgradeUserToVer3(sQLiteDatabase);
                upgradeEventsToVer3(sQLiteDatabase);
            }
            if (i == 2) {
                upgradeUserToVer3(sQLiteDatabase);
                upgradeEventsToVer3(sQLiteDatabase);
            }
        }
    }

    static class StoredEvent {
        String content;
        long id;
        int send_count;
        int status;

        public StoredEvent(long j, String str, int i, int i2) {
            this.id = j;
            this.content = str;
            this.status = i;
            this.send_count = i2;
        }
    }

    private StatStore(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatStore");
            handlerThread.start();
            logger.w("Launch store thread:" + handlerThread);
            this.handler = new Handler(handlerThread.getLooper());
            Context applicationContext = context.getApplicationContext();
            this.helper = new StatStoreHelper(applicationContext);
            this.helper.getWritableDatabase();
            this.helper.getReadableDatabase();
            getUser(applicationContext);
            loadConfig();
            loadKeyValues();
            this.handler.post(new Runnable() {
                public void run() {
                    StatStore.this.loadUnsentEventCount();
                }
            });
        } catch (Object th) {
            logger.e(th);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void directDeleteEvents(java.util.List<com.tencent.stat.StatStore.StoredEvent> r11) {
        /*
        r10 = this;
        r0 = logger;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Delete ";
        r1 = r1.append(r2);
        r2 = r11.size();
        r1 = r1.append(r2);
        r2 = " sent events in thread:";
        r1 = r1.append(r2);
        r2 = java.lang.Thread.currentThread();
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.i(r1);
        r0 = r10.helper;	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0068 }
        r0.beginTransaction();	 Catch:{ SQLiteException -> 0x0068 }
        r1 = r11.iterator();	 Catch:{ SQLiteException -> 0x0068 }
    L_0x0039:
        r0 = r1.hasNext();	 Catch:{ SQLiteException -> 0x0068 }
        if (r0 == 0) goto L_0x0078;
    L_0x003f:
        r0 = r1.next();	 Catch:{ SQLiteException -> 0x0068 }
        r0 = (com.tencent.stat.StatStore.StoredEvent) r0;	 Catch:{ SQLiteException -> 0x0068 }
        r2 = r10.numStoredEvents;	 Catch:{ SQLiteException -> 0x0068 }
        r3 = r10.helper;	 Catch:{ SQLiteException -> 0x0068 }
        r3 = r3.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0068 }
        r4 = "events";
        r5 = "event_id = ?";
        r6 = 1;
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x0068 }
        r7 = 0;
        r8 = r0.id;	 Catch:{ SQLiteException -> 0x0068 }
        r0 = java.lang.Long.toString(r8);	 Catch:{ SQLiteException -> 0x0068 }
        r6[r7] = r0;	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r3.delete(r4, r5, r6);	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r2 - r0;
        r10.numStoredEvents = r0;	 Catch:{ SQLiteException -> 0x0068 }
        goto L_0x0039;
    L_0x0068:
        r0 = move-exception;
        r1 = logger;	 Catch:{ all -> 0x00a9 }
        r1.e(r0);	 Catch:{ all -> 0x00a9 }
        r0 = r10.helper;	 Catch:{ SQLiteException -> 0x00a2 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00a2 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00a2 }
    L_0x0077:
        return;
    L_0x0078:
        r0 = r10.helper;	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0068 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r10.helper;	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r0.getReadableDatabase();	 Catch:{ SQLiteException -> 0x0068 }
        r1 = "events";
        r0 = android.database.DatabaseUtils.queryNumEntries(r0, r1);	 Catch:{ SQLiteException -> 0x0068 }
        r0 = (int) r0;	 Catch:{ SQLiteException -> 0x0068 }
        r10.numStoredEvents = r0;	 Catch:{ SQLiteException -> 0x0068 }
        r0 = r10.helper;	 Catch:{ SQLiteException -> 0x009b }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x009b }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x009b }
        goto L_0x0077;
    L_0x009b:
        r0 = move-exception;
        r1 = logger;
        r1.e(r0);
        goto L_0x0077;
    L_0x00a2:
        r0 = move-exception;
        r1 = logger;
        r1.e(r0);
        goto L_0x0077;
    L_0x00a9:
        r0 = move-exception;
        r1 = r10.helper;	 Catch:{ SQLiteException -> 0x00b4 }
        r1 = r1.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00b4 }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x00b4 }
    L_0x00b3:
        throw r0;
    L_0x00b4:
        r1 = move-exception;
        r2 = logger;
        r2.e(r1);
        goto L_0x00b3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.StatStore.directDeleteEvents(java.util.List):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void directUpdateEvents(java.util.List<com.tencent.stat.StatStore.StoredEvent> r13, int r14) {
        /*
        r12 = this;
        r0 = logger;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Update ";
        r1 = r1.append(r2);
        r2 = r13.size();
        r1 = r1.append(r2);
        r2 = " sending events to status:";
        r1 = r1.append(r2);
        r1 = r1.append(r14);
        r2 = " in thread:";
        r1 = r1.append(r2);
        r2 = java.lang.Thread.currentThread();
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.i(r1);
        r1 = new android.content.ContentValues;	 Catch:{ SQLiteException -> 0x008c }
        r1.<init>();	 Catch:{ SQLiteException -> 0x008c }
        r0 = "status";
        r2 = java.lang.Integer.toString(r14);	 Catch:{ SQLiteException -> 0x008c }
        r1.put(r0, r2);	 Catch:{ SQLiteException -> 0x008c }
        r0 = r12.helper;	 Catch:{ SQLiteException -> 0x008c }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x008c }
        r0.beginTransaction();	 Catch:{ SQLiteException -> 0x008c }
        r2 = r13.iterator();	 Catch:{ SQLiteException -> 0x008c }
    L_0x0053:
        r0 = r2.hasNext();	 Catch:{ SQLiteException -> 0x008c }
        if (r0 == 0) goto L_0x0118;
    L_0x0059:
        r0 = r2.next();	 Catch:{ SQLiteException -> 0x008c }
        r0 = (com.tencent.stat.StatStore.StoredEvent) r0;	 Catch:{ SQLiteException -> 0x008c }
        r3 = r0.send_count;	 Catch:{ SQLiteException -> 0x008c }
        r3 = r3 + 1;
        r4 = com.tencent.stat.StatConfig.getMaxSendRetryCount();	 Catch:{ SQLiteException -> 0x008c }
        if (r3 <= r4) goto L_0x009c;
    L_0x0069:
        r3 = r12.numStoredEvents;	 Catch:{ SQLiteException -> 0x008c }
        r4 = r12.helper;	 Catch:{ SQLiteException -> 0x008c }
        r4 = r4.getWritableDatabase();	 Catch:{ SQLiteException -> 0x008c }
        r5 = "events";
        r6 = "event_id=?";
        r7 = 1;
        r7 = new java.lang.String[r7];	 Catch:{ SQLiteException -> 0x008c }
        r8 = 0;
        r10 = r0.id;	 Catch:{ SQLiteException -> 0x008c }
        r0 = java.lang.Long.toString(r10);	 Catch:{ SQLiteException -> 0x008c }
        r7[r8] = r0;	 Catch:{ SQLiteException -> 0x008c }
        r0 = r4.delete(r5, r6, r7);	 Catch:{ SQLiteException -> 0x008c }
        r0 = r3 - r0;
        r12.numStoredEvents = r0;	 Catch:{ SQLiteException -> 0x008c }
        goto L_0x0053;
    L_0x008c:
        r0 = move-exception;
        r1 = logger;	 Catch:{ all -> 0x010d }
        r1.e(r0);	 Catch:{ all -> 0x010d }
        r0 = r12.helper;	 Catch:{ SQLiteException -> 0x0144 }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x0144 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0144 }
    L_0x009b:
        return;
    L_0x009c:
        r3 = "send_count";
        r4 = r0.send_count;	 Catch:{ SQLiteException -> 0x008c }
        r4 = r4 + 1;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ SQLiteException -> 0x008c }
        r1.put(r3, r4);	 Catch:{ SQLiteException -> 0x008c }
        r3 = logger;	 Catch:{ SQLiteException -> 0x008c }
        r4 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x008c }
        r4.<init>();	 Catch:{ SQLiteException -> 0x008c }
        r5 = "Update event:";
        r4 = r4.append(r5);	 Catch:{ SQLiteException -> 0x008c }
        r6 = r0.id;	 Catch:{ SQLiteException -> 0x008c }
        r4 = r4.append(r6);	 Catch:{ SQLiteException -> 0x008c }
        r5 = " for content:";
        r4 = r4.append(r5);	 Catch:{ SQLiteException -> 0x008c }
        r4 = r4.append(r1);	 Catch:{ SQLiteException -> 0x008c }
        r4 = r4.toString();	 Catch:{ SQLiteException -> 0x008c }
        r3.i(r4);	 Catch:{ SQLiteException -> 0x008c }
        r3 = r12.helper;	 Catch:{ SQLiteException -> 0x008c }
        r3 = r3.getWritableDatabase();	 Catch:{ SQLiteException -> 0x008c }
        r4 = "events";
        r5 = "event_id=?";
        r6 = 1;
        r6 = new java.lang.String[r6];	 Catch:{ SQLiteException -> 0x008c }
        r7 = 0;
        r8 = r0.id;	 Catch:{ SQLiteException -> 0x008c }
        r0 = java.lang.Long.toString(r8);	 Catch:{ SQLiteException -> 0x008c }
        r6[r7] = r0;	 Catch:{ SQLiteException -> 0x008c }
        r0 = r3.update(r4, r1, r5, r6);	 Catch:{ SQLiteException -> 0x008c }
        if (r0 > 0) goto L_0x0053;
    L_0x00ee:
        r3 = logger;	 Catch:{ SQLiteException -> 0x008c }
        r4 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x008c }
        r4.<init>();	 Catch:{ SQLiteException -> 0x008c }
        r5 = "Failed to update db, error code:";
        r4 = r4.append(r5);	 Catch:{ SQLiteException -> 0x008c }
        r0 = java.lang.Integer.toString(r0);	 Catch:{ SQLiteException -> 0x008c }
        r0 = r4.append(r0);	 Catch:{ SQLiteException -> 0x008c }
        r0 = r0.toString();	 Catch:{ SQLiteException -> 0x008c }
        r3.e(r0);	 Catch:{ SQLiteException -> 0x008c }
        goto L_0x0053;
    L_0x010d:
        r0 = move-exception;
        r1 = r12.helper;	 Catch:{ SQLiteException -> 0x014c }
        r1 = r1.getWritableDatabase();	 Catch:{ SQLiteException -> 0x014c }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x014c }
    L_0x0117:
        throw r0;
    L_0x0118:
        r0 = r12.helper;	 Catch:{ SQLiteException -> 0x008c }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x008c }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x008c }
        r0 = r12.helper;	 Catch:{ SQLiteException -> 0x008c }
        r0 = r0.getReadableDatabase();	 Catch:{ SQLiteException -> 0x008c }
        r1 = "events";
        r0 = android.database.DatabaseUtils.queryNumEntries(r0, r1);	 Catch:{ SQLiteException -> 0x008c }
        r0 = (int) r0;	 Catch:{ SQLiteException -> 0x008c }
        r12.numStoredEvents = r0;	 Catch:{ SQLiteException -> 0x008c }
        r0 = r12.helper;	 Catch:{ SQLiteException -> 0x013c }
        r0 = r0.getWritableDatabase();	 Catch:{ SQLiteException -> 0x013c }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x013c }
        goto L_0x009b;
    L_0x013c:
        r0 = move-exception;
        r1 = logger;
        r1.e(r0);
        goto L_0x009b;
    L_0x0144:
        r0 = move-exception;
        r1 = logger;
        r1.e(r0);
        goto L_0x009b;
    L_0x014c:
        r1 = move-exception;
        r2 = logger;
        r2.e(r1);
        goto L_0x0117;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.StatStore.directUpdateEvents(java.util.List, int):void");
    }

    public static StatStore getInstance() {
        return instance;
    }

    public static StatStore getInstance(Context context) {
        if (instance == null) {
            instance = new StatStore(context);
        }
        return instance;
    }

    private void loadKeyValues() {
        Exception e;
        Throwable th;
        Cursor query;
        try {
            query = this.helper.getReadableDatabase().query("keyvalues", null, null, null, null, null, null);
            while (query.moveToNext()) {
                try {
                    this.kvMap.put(query.getString(0), query.getString(1));
                } catch (SQLiteException e2) {
                    e = e2;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            try {
                logger.e(e);
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
    }

    private void loadUnsentEventCount() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", Integer.valueOf(1));
        this.helper.getWritableDatabase().update("events", contentValues, "status=?", new String[]{Long.toString(2)});
        this.numStoredEvents = (int) DatabaseUtils.queryNumEntries(this.helper.getReadableDatabase(), "events");
        logger.i("Total " + this.numStoredEvents + " unsent events.");
    }

    private void peekEvents(List<StoredEvent> list, int i) {
        Exception e;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        try {
            Cursor query = this.helper.getReadableDatabase().query("events", null, "status=?", new String[]{Integer.toString(1)}, null, null, "event_id", Integer.toString(i));
            while (query.moveToNext()) {
                try {
                    list.add(new StoredEvent(query.getLong(0), StatCommonHelper.decode(query.getString(1)), query.getInt(2), query.getInt(3)));
                } catch (SQLiteException e2) {
                    e = e2;
                    cursor = query;
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = query;
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (SQLiteException e3) {
            e = e3;
            cursor = null;
            try {
                logger.e(e);
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th3) {
                th = th3;
                cursor2 = cursor;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (cursor2 != null) {
                cursor2.close();
            }
            throw th;
        }
    }

    void deleteEvents(final List<StoredEvent> list) {
        try {
            if (Thread.currentThread().getId() == this.handler.getLooper().getThread().getId()) {
                directDeleteEvents(list);
            } else {
                this.handler.post(new Runnable() {
                    public void run() {
                        StatStore.this.directDeleteEvents(list);
                    }
                });
            }
        } catch (Exception e) {
            logger.e(e);
        }
    }

    void directStoreEvent(Event event, StatDispatchCallback statDispatchCallback) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            if (this.numStoredEvents > StatConfig.getMaxStoreEventCount()) {
                logger.warn("Too many events stored in db.");
                this.numStoredEvents -= this.helper.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
            }
            ContentValues contentValues = new ContentValues();
            String encode = StatCommonHelper.encode(event.toJsonString());
            contentValues.put("content", encode);
            contentValues.put("send_count", "0");
            contentValues.put("status", Integer.toString(1));
            contentValues.put("timestamp", Long.valueOf(event.getTimestamp()));
            if (this.helper.getWritableDatabase().insert("events", null, contentValues) == -1) {
                logger.error("Failed to store event:" + encode);
                return;
            }
            this.numStoredEvents++;
            if (statDispatchCallback != null) {
                statDispatchCallback.onDispatchSuccess();
            }
        }
    }

    public int getNumStoredEvents() {
        return this.numStoredEvents;
    }

    public User getUser(Context context) {
        Cursor cursor;
        Throwable th;
        if (this.user != null) {
            return this.user;
        }
        Cursor query;
        Object obj;
        try {
            query = this.helper.getReadableDatabase().query("user", null, null, null, null, null, null, null);
            obj = null;
            try {
                String decode;
                String str;
                String deviceID;
                String str2 = "";
                if (query.moveToNext()) {
                    Object obj2;
                    decode = StatCommonHelper.decode(query.getString(0));
                    int i = query.getInt(1);
                    str2 = query.getString(2);
                    long currentTimeMillis = System.currentTimeMillis() / 1000;
                    int i2 = (i == 1 || StatCommonHelper.getDateFormat(query.getLong(3) * 1000).equals(StatCommonHelper.getDateFormat(1000 * currentTimeMillis))) ? i : 1;
                    int i3 = !str2.equals(StatCommonHelper.getAppVersion(context)) ? i2 | 2 : i2;
                    String[] split = decode.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                    if (split == null || split.length <= 0) {
                        str2 = StatCommonHelper.getUserID(context);
                        decode = str2;
                        str = str2;
                        int i4 = 1;
                    } else {
                        str2 = split[0];
                        if (str2 == null || str2.length() < 11) {
                            deviceID = StatCommonHelper.getDeviceID(context);
                            if (deviceID == null || deviceID.length() <= 10) {
                                deviceID = str2;
                                obj2 = null;
                            } else {
                                obj2 = 1;
                            }
                            str = decode;
                            decode = deviceID;
                        } else {
                            String str3 = str2;
                            obj2 = null;
                            str = decode;
                            decode = str3;
                        }
                    }
                    if (split == null || split.length < 2) {
                        deviceID = StatCommonHelper.getMacId(context);
                        if (deviceID != null && deviceID.length() > 0) {
                            str = decode + MiPushClient.ACCEPT_TIME_SEPARATOR + deviceID;
                            obj2 = 1;
                        }
                    } else {
                        deviceID = split[1];
                        str = decode + MiPushClient.ACCEPT_TIME_SEPARATOR + deviceID;
                    }
                    this.user = new User(decode, deviceID, i3);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(p.UID, StatCommonHelper.encode(str));
                    contentValues.put("user_type", Integer.valueOf(i3));
                    contentValues.put("app_ver", StatCommonHelper.getAppVersion(context));
                    contentValues.put("ts", Long.valueOf(currentTimeMillis));
                    if (obj2 != null) {
                        this.helper.getWritableDatabase().update("user", contentValues, "uid=?", new String[]{r10});
                    }
                    if (i3 != i) {
                        this.helper.getWritableDatabase().replace("user", null, contentValues);
                        obj = 1;
                    } else {
                        i2 = 1;
                    }
                }
                if (obj == null) {
                    str2 = StatCommonHelper.getUserID(context);
                    str = StatCommonHelper.getMacId(context);
                    deviceID = (str == null || str.length() <= 0) ? str2 : str2 + MiPushClient.ACCEPT_TIME_SEPARATOR + str;
                    long currentTimeMillis2 = System.currentTimeMillis() / 1000;
                    decode = StatCommonHelper.getAppVersion(context);
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put(p.UID, StatCommonHelper.encode(deviceID));
                    contentValues2.put("user_type", Integer.valueOf(0));
                    contentValues2.put("app_ver", decode);
                    contentValues2.put("ts", Long.valueOf(currentTimeMillis2));
                    this.helper.getWritableDatabase().insert("user", null, contentValues2);
                    this.user = new User(str2, str, 0);
                }
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return this.user;
    }

    void loadConfig() {
        this.handler.post(new Runnable() {
            public void run() {
                Exception e;
                Throwable th;
                Cursor query;
                try {
                    query = StatStore.this.helper.getReadableDatabase().query("config", null, null, null, null, null, null);
                    while (query.moveToNext()) {
                        try {
                            int i = query.getInt(0);
                            String string = query.getString(1);
                            String string2 = query.getString(2);
                            int i2 = query.getInt(3);
                            StatConfig$OnlineConfig statConfig$OnlineConfig = new StatConfig$OnlineConfig(i);
                            statConfig$OnlineConfig.type = i;
                            statConfig$OnlineConfig.props = new JSONObject(string);
                            statConfig$OnlineConfig.md5sum = string2;
                            statConfig$OnlineConfig.version = i2;
                            StatConfig.setConfig(statConfig$OnlineConfig);
                        } catch (Exception e2) {
                            e = e2;
                        }
                    }
                    if (query != null) {
                        query.close();
                    }
                } catch (Exception e3) {
                    e = e3;
                    query = null;
                    try {
                        StatStore.logger.e(e);
                        if (query != null) {
                            query.close();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    query = null;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
        });
    }

    void loadEvents(final int i) {
        this.handler.post(new Runnable() {
            public void run() {
                if (StatStore.this.numStoredEvents != 0) {
                    StatStore.logger.i("Load " + Integer.toString(StatStore.this.numStoredEvents) + " unsent events");
                    List arrayList = new ArrayList();
                    final List<StoredEvent> arrayList2 = new ArrayList();
                    int i = i;
                    final int maxLoadEventCount = (i == -1 || i > StatConfig.getMaxLoadEventCount()) ? StatConfig.getMaxLoadEventCount() : i;
                    StatStore statStore = StatStore.this;
                    statStore.numStoredEvents -= maxLoadEventCount;
                    StatStore.this.peekEvents(arrayList2, maxLoadEventCount);
                    StatStore.logger.i("Peek " + Integer.toString(arrayList2.size()) + " unsent events.");
                    if (!arrayList2.isEmpty()) {
                        StatStore.this.directUpdateEvents(arrayList2, 2);
                        for (StoredEvent storedEvent : arrayList2) {
                            arrayList.add(storedEvent.content);
                        }
                        StatDispatcher.getInstance().send(arrayList, new StatDispatchCallback() {
                            public void onDispatchFailure() {
                                StatStore statStore = StatStore.this;
                                statStore.numStoredEvents += maxLoadEventCount;
                                StatStore.this.updateEvents(arrayList2, 1);
                            }

                            public void onDispatchSuccess() {
                                StatStore.this.deleteEvents(arrayList2);
                                int size = i == -1 ? i : i - arrayList2.size();
                                if (size == -1 || size > 0) {
                                    StatStore.this.loadEvents(size);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    void storeCfg(final StatConfig$OnlineConfig statConfig$OnlineConfig) {
        if (statConfig$OnlineConfig != null) {
            try {
                this.handler.post(new Runnable() {
                    public void run() {
                        Cursor query;
                        Exception e;
                        Throwable th;
                        String toJsonString = statConfig$OnlineConfig.toJsonString();
                        String md5sum = StatCommonHelper.md5sum(toJsonString);
                        if (!md5sum.equals(statConfig$OnlineConfig.md5sum)) {
                            int i;
                            long insert;
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("content", statConfig$OnlineConfig.props.toString());
                            contentValues.put("md5sum", md5sum);
                            statConfig$OnlineConfig.md5sum = md5sum;
                            contentValues.put("version", Integer.valueOf(statConfig$OnlineConfig.version));
                            try {
                                query = StatStore.this.helper.getReadableDatabase().query("config", null, null, null, null, null, null);
                                do {
                                    try {
                                        if (!query.moveToNext()) {
                                            i = 0;
                                            break;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                    }
                                } while (query.getInt(0) != statConfig$OnlineConfig.type);
                                i = 1;
                                if (query != null) {
                                    query.close();
                                }
                            } catch (Exception e3) {
                                e = e3;
                                query = null;
                                try {
                                    StatStore.logger.e(e);
                                    if (query != null) {
                                        query.close();
                                        i = 0;
                                    } else {
                                        i = 0;
                                    }
                                    if (1 != i) {
                                        contentValues.put("type", Integer.valueOf(statConfig$OnlineConfig.type));
                                        insert = StatStore.this.helper.getWritableDatabase().insert("config", null, contentValues);
                                    } else {
                                        insert = (long) StatStore.this.helper.getWritableDatabase().update("config", contentValues, "type=?", new String[]{Integer.toString(statConfig$OnlineConfig.type)});
                                    }
                                    if (insert != -1) {
                                        StatStore.logger.i("Sucessed to store cfg:" + toJsonString);
                                    } else {
                                        StatStore.logger.error("Failed to store cfg:" + toJsonString);
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (query != null) {
                                        query.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                query = null;
                                if (query != null) {
                                    query.close();
                                }
                                throw th;
                            }
                            if (1 != i) {
                                insert = (long) StatStore.this.helper.getWritableDatabase().update("config", contentValues, "type=?", new String[]{Integer.toString(statConfig$OnlineConfig.type)});
                            } else {
                                contentValues.put("type", Integer.valueOf(statConfig$OnlineConfig.type));
                                insert = StatStore.this.helper.getWritableDatabase().insert("config", null, contentValues);
                            }
                            if (insert != -1) {
                                StatStore.logger.error("Failed to store cfg:" + toJsonString);
                            } else {
                                StatStore.logger.i("Sucessed to store cfg:" + toJsonString);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                logger.e(e);
            }
        }
    }

    void storeEvent(final Event event, final StatDispatchCallback statDispatchCallback) {
        if (StatConfig.isEnableStatService()) {
            try {
                if (Thread.currentThread().getId() == this.handler.getLooper().getThread().getId()) {
                    directStoreEvent(event, statDispatchCallback);
                } else {
                    this.handler.post(new Runnable() {
                        public void run() {
                            StatStore.this.directStoreEvent(event, statDispatchCallback);
                        }
                    });
                }
            } catch (Exception e) {
                logger.e(e);
            }
        }
    }

    void updateEvents(final List<StoredEvent> list, final int i) {
        try {
            if (Thread.currentThread().getId() == this.handler.getLooper().getThread().getId()) {
                directUpdateEvents(list, i);
            } else {
                this.handler.post(new Runnable() {
                    public void run() {
                        StatStore.this.directUpdateEvents(list, i);
                    }
                });
            }
        } catch (Exception e) {
            logger.e(e);
        }
    }
}
