package com.huluxia.mojang.converter;

import android.util.Log;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mojang.converter.EntityDataConverter.EntityData;
import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.entity.EntityType;
import com.huluxia.mojang.tileentity.TileEntity;
import com.huluxia.utils.g;
import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.spout.nbt.CompoundTag;
import org.spout.nbt.stream.NBTInputStream;
import org.spout.nbt.stream.NBTOutputStream;

class LevelDBConverter {
    private static final String LOCAL_PLAYER_KEY = "~local_player";
    private static final String TAG = "LevelDBConverter";

    public static void writeAllEntities(java.util.List<com.huluxia.mojang.entity.Entity> r19, java.io.File r20) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source)
	at java.util.HashMap$KeyIterator.next(Unknown Source)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:80)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = 0;
        r5 = new com.huluxia.mojang.converter.DBKey;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r5.<init>();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = 50;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r5.setType(r0);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r15 = new java.util.HashMap;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r15.<init>();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r19.iterator();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0016:
        r17 = r16.hasNext();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r17 == 0) goto L_0x0083;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x001c:
        r7 = r16.next();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r7 = (com.huluxia.mojang.entity.Entity) r7;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r12 = com.huluxia.mojang.converter.NBTConverter.writeEntity(r7);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r13 = r7.getLocation();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r13.getX();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = (int) r0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r17 >> 4;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r5.setX(r0);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = r13.getZ();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r18;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = (int) r0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = r0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = r18 >> 4;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17.setZ(r18);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r2 = r15.get(r5);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r2 = (java.io.ByteArrayOutputStream) r2;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r2 != 0) goto L_0x0056;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0051:
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r2.<init>();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0056:
        r10 = new com.huluxia.mojang.converter.DBKey;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r10.<init>(r5);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r11 = new org.spout.nbt.stream.NBTOutputStream;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = 0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = 1;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r1 = r18;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r11.<init>(r2, r0, r1);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r11.writeTag(r12);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r15.put(r10, r2);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        goto L_0x0016;
    L_0x006f:
        r6 = move-exception;
        r16 = "LevelDBConverter";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = "writeAllEntities error 2";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r1 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        android.util.Log.e(r0, r1, r6);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r3 == 0) goto L_0x0082;
    L_0x007f:
        r3.close();
    L_0x0082:
        return;
    L_0x0083:
        r16 = "LevelDBConverter";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17.<init>();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = "writeData size = ";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r17.append(r18);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = r15.size();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r17.append(r18);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r17.toString();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        android.util.Log.d(r16, r17);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r3 = openDatabase(r20);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r4 = r3.iterator();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r4 != 0) goto L_0x00c2;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x00ab:
        r16 = "LevelDBConverter";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = "writeAllEntities error, iterator is NULL";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = 0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r18;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = new java.lang.Object[r0];	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = r0;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        com.huluxia.framework.base.log.HLog.error(r16, r17, r18);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r3 == 0) goto L_0x0082;
    L_0x00be:
        r3.close();
        goto L_0x0082;
    L_0x00c2:
        r4.seekToFirst();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r14 = new com.litl.leveldb.WriteBatch;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r14.<init>();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x00ca:
        r16 = r4.isValid();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r16 == 0) goto L_0x011f;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x00d0:
        r16 = r4.getKey();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r5.fromBytes(r0);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r5.getType();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = 50;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r1 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r0 != r1) goto L_0x0114;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x00e5:
        r16 = r15.containsKey(r5);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r16 != 0) goto L_0x0114;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x00eb:
        r16 = "LevelDBConverter";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17.<init>();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r18 = "write delete batch with key = ";	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r17.append(r18);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r0.append(r5);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = r17.toString();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        android.util.Log.d(r16, r17);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r5.toBytes();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = java.nio.ByteBuffer.wrap(r16);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r14.delete(r0);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0114:
        r4.next();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        goto L_0x00ca;
    L_0x0118:
        r16 = move-exception;
        if (r3 == 0) goto L_0x011e;
    L_0x011b:
        r3.close();
    L_0x011e:
        throw r16;
    L_0x011f:
        r4.close();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r3.write(r14);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r14.clear();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r15.entrySet();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r9 = r16.iterator();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0130:
        r16 = r9.hasNext();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r16 == 0) goto L_0x0160;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0136:
        r8 = r9.next();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r8 = (java.util.Map.Entry) r8;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r8.getKey();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = (com.huluxia.mojang.converter.DBKey) r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r16.toBytes();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r17 = java.nio.ByteBuffer.wrap(r16);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r8.getValue();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = (java.io.ByteArrayOutputStream) r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = r16.toByteArray();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r16 = java.nio.ByteBuffer.wrap(r16);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r0 = r17;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r1 = r16;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r14.put(r0, r1);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        goto L_0x0130;	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
    L_0x0160:
        r3.write(r14);	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        r14.close();	 Catch:{ IOException -> 0x006f, all -> 0x0118 }
        if (r3 == 0) goto L_0x0082;
    L_0x0168:
        r3.close();
        goto L_0x0082;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.mojang.converter.LevelDBConverter.writeAllEntities(java.util.List, java.io.File):void");
    }

    LevelDBConverter() {
    }

    private static byte[] bytes(String paramString) {
        return paramString.getBytes(Charset.forName("utf-8"));
    }

    public static DB openDatabase(File paramFile) {
        Exception localException;
        g.eL("openDatabase param file ");
        int i = 0;
        DB localDB = null;
        while (i < 10) {
            DB localDB2;
            try {
                localDB2 = new DB(paramFile);
                try {
                    localDB2.open();
                    return localDB2;
                } catch (Exception e) {
                    localException = e;
                }
            } catch (Exception e2) {
                localException = e2;
                localDB2 = localDB;
                Log.e(TAG, "openDatabase failed1", localException);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException localInterruptedException) {
                    Log.e(TAG, "openDatabase failed2", localInterruptedException);
                }
                i++;
                localDB = localDB2;
            }
        }
        return localDB;
    }

    public static EntityData readAllEntities(File paramFile) throws IOException {
        DB localDB = openDatabase(paramFile);
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        Iterator localIterator = localDB.iterator();
        if (localIterator == null) {
            HLog.error(TAG, "writeAllEntities error, iterator is NULL", new Object[0]);
            return new EntityData(new ArrayList(), new ArrayList());
        }
        DBKey localDBKey = new DBKey();
        localIterator.seekToFirst();
        while (localIterator.isValid()) {
            localDBKey.fromBytes(localIterator.getKey());
            if (localDBKey.getType() == 50) {
                Log.d(TAG, "readAllEntities Handled entity 50 key " + localDBKey);
                ByteArrayInputStream localByteArrayInputStream1 = new ByteArrayInputStream(localIterator.getValue());
                while (localByteArrayInputStream1.available() > 0) {
                    Log.d(TAG, "readAllEntities read single entity");
                    NBTInputStream localNBTInputStream1 = new NBTInputStream(localByteArrayInputStream1, false, true);
                    Entity localEntity = NBTConverter.readSingleEntity((CompoundTag) localNBTInputStream1.readTag());
                    if (localEntity == null) {
                        Log.e(TAG, "readAllEntities Not possible: null entity.");
                    } else if (EntityType.shouldUsed(localEntity.getEntityType())) {
                        localArrayList1.add(localEntity);
                    } else {
                        try {
                            Log.e(TAG, "readAllEntities not use entity =" + localEntity);
                        } catch (Throwable th) {
                            localNBTInputStream1.close();
                        }
                    }
                    localNBTInputStream1.close();
                }
                localByteArrayInputStream1.close();
            } else if (localDBKey.getType() == 49) {
                Log.d(TAG, "readAllEntities Handled entity 49 key " + localDBKey);
                ByteArrayInputStream localByteArrayInputStream2 = new ByteArrayInputStream(localIterator.getValue());
                try {
                    NBTInputStream localNBTInputStream2 = new NBTInputStream(localByteArrayInputStream2, false, true);
                    while (localByteArrayInputStream2.available() > 0) {
                        Log.d(TAG, "readAllEntities read single tile entity");
                        TileEntity localTileEntity = NBTConverter.readSingleTileEntity((CompoundTag) localNBTInputStream2.readTag());
                        if (localTileEntity == null) {
                            Log.e(TAG, "readAllEntities Not possible: null tile entity.");
                        } else {
                            localArrayList2.add(localTileEntity);
                        }
                    }
                } finally {
                    localByteArrayInputStream2.close();
                }
            } else {
                continue;
            }
            localIterator.next();
        }
        localIterator.close();
        localDB.close();
        return new EntityData(localArrayList1, localArrayList2);
    }

    public static void readLevel(LevelV010 paramLevel, File paramFile) throws IOException {
        DB localDB = openDatabase(paramFile);
        try {
            byte[] arrayOfByte = localDB.get(bytes(LOCAL_PLAYER_KEY));
            if (arrayOfByte != null) {
                paramLevel.setPlayer(NBTConverter.readPlayer((CompoundTag) new NBTInputStream(new ByteArrayInputStream(arrayOfByte), false, true).readTag()));
            }
            Log.d(TAG, "read level = " + paramLevel);
        } finally {
            Log.d(TAG, "Closing db");
            localDB.close();
        }
    }

    public static void readLevelV0110(LevelV0110 paramLevel, File paramFile) throws IOException {
        DB localDB = openDatabase(paramFile);
        try {
            byte[] arrayOfByte = localDB.get(bytes(LOCAL_PLAYER_KEY));
            if (arrayOfByte != null) {
                paramLevel.setPlayer(NBTConverter.readPlayerV0110((CompoundTag) new NBTInputStream(new ByteArrayInputStream(arrayOfByte), false, true).readTag()));
            }
            Log.d(TAG, "read level = " + paramLevel);
        } finally {
            Log.d(TAG, "Closing db");
            localDB.close();
        }
    }

    public static void writeLevel(LevelV010 paramLevel, File paramFile) throws IOException {
        DB localDB = openDatabase(paramFile);
        g.eL("write level paramLevel is " + paramLevel);
        try {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            new NBTOutputStream(localByteArrayOutputStream, false, true).writeTag(NBTConverter.writePlayer(paramLevel.getPlayer(), "", true));
            localDB.put(bytes(LOCAL_PLAYER_KEY), localByteArrayOutputStream.toByteArray());
            localByteArrayOutputStream.close();
        } catch (Exception localException) {
            Log.e(TAG, "exception: " + localException);
        } finally {
            Log.e(TAG, "Closing db");
            localDB.close();
        }
    }

    public static void writeLevelV0110(LevelV0110 level, File paramFile) throws IOException {
        DB localDB = openDatabase(paramFile);
        g.eL("write level level is " + level);
        try {
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            new NBTOutputStream(localByteArrayOutputStream, false, true).writeTag(NBTConverter.writePlayerV0110(level.getPlayer(), "", true));
            localDB.put(bytes(LOCAL_PLAYER_KEY), localByteArrayOutputStream.toByteArray());
            localByteArrayOutputStream.close();
        } catch (Exception localException) {
            Log.e(TAG, "exception: " + localException);
        } finally {
            Log.e(TAG, "Closing db");
            localDB.close();
        }
    }
}
