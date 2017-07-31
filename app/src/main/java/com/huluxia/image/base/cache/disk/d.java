package com.huluxia.image.base.cache.disk;

import android.content.Context;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.cache.common.CacheErrorLogger;
import com.huluxia.image.base.cache.common.CacheErrorLogger.CacheErrorCategory;
import com.huluxia.image.base.cache.common.CacheEventListener;
import com.huluxia.image.base.cache.common.CacheEventListener.EvictionReason;
import com.huluxia.image.base.cache.common.c;
import com.huluxia.image.base.cache.common.i;
import com.huluxia.image.core.common.statfs.StatFsHelper;
import com.huluxia.image.core.common.statfs.StatFsHelper.StorageType;
import com.tencent.mm.sdk.platformtools.LocaleUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
/* compiled from: DiskStorageCache */
public class d implements h, com.huluxia.image.core.common.disk.a {
    private static final Class<?> tF = d.class;
    public static final int uo = 1;
    private static final long uq = TimeUnit.HOURS.toMillis(2);
    private static final long ur = TimeUnit.MINUTES.toMillis(30);
    private static final double us = 0.02d;
    private static final long ut = -1;
    private static final String uu = "disk_entries_list";
    private final Object mLock = new Object();
    private final CacheErrorLogger tN;
    private final com.huluxia.image.core.common.time.a tO;
    @GuardedBy("mLock")
    private long uA;
    private final long uB;
    private final StatFsHelper uC;
    private final c uD;
    private final a uE;
    private boolean uF;
    private final g ue;
    private final CacheEventListener uf;
    private final boolean uh;
    private final long uv;
    private final long uw;
    private final CountDownLatch ux;
    private long uy;
    @VisibleForTesting
    @GuardedBy("mLock")
    final Set<String> uz;

    @VisibleForTesting
    /* compiled from: DiskStorageCache */
    static class a {
        private long mCount = -1;
        private boolean mInitialized = false;
        private long uH = -1;

        a() {
        }

        public synchronized boolean isInitialized() {
            return this.mInitialized;
        }

        public synchronized void reset() {
            this.mInitialized = false;
            this.mCount = -1;
            this.uH = -1;
        }

        public synchronized void b(long size, long count) {
            this.mCount = count;
            this.uH = size;
            this.mInitialized = true;
        }

        public synchronized void c(long sizeIncrement, long countIncrement) {
            if (this.mInitialized) {
                this.uH += sizeIncrement;
                this.mCount += countIncrement;
            }
        }

        public synchronized long getSize() {
            return this.uH;
        }

        public synchronized long getCount() {
            return this.mCount;
        }
    }

    /* compiled from: DiskStorageCache */
    public static class b {
        public final long uB;
        public final long uv;
        public final long uw;

        public b(long cacheSizeLimitMinimum, long lowDiskSpaceCacheSizeLimit, long defaultCacheSizeLimit) {
            this.uB = cacheSizeLimitMinimum;
            this.uv = lowDiskSpaceCacheSizeLimit;
            this.uw = defaultCacheSizeLimit;
        }
    }

    public d(c diskStorage, g entryEvictionComparatorSupplier, b params, CacheEventListener cacheEventListener, CacheErrorLogger cacheErrorLogger, @Nullable com.huluxia.image.core.common.disk.b diskTrimmableRegistry, final Context context, Executor executorForBackgrountInit, boolean indexPopulateAtStartupEnabled) {
        this.uv = params.uv;
        this.uw = params.uw;
        this.uy = params.uw;
        this.uC = StatFsHelper.iE();
        this.uD = diskStorage;
        this.ue = entryEvictionComparatorSupplier;
        this.uA = -1;
        this.uf = cacheEventListener;
        this.uB = params.uB;
        this.tN = cacheErrorLogger;
        this.uE = new a();
        if (diskTrimmableRegistry != null) {
            diskTrimmableRegistry.a(this);
        }
        this.tO = com.huluxia.image.core.common.time.d.iK();
        this.uh = indexPopulateAtStartupEnabled;
        this.uz = new HashSet();
        if (this.uh) {
            this.ux = new CountDownLatch(1);
            executorForBackgrountInit.execute(new Runnable(this) {
                final /* synthetic */ d uG;

                {
                    this.uG = this$0;
                }

                public void run() {
                    synchronized (this.uG.mLock) {
                        this.uG.gS();
                    }
                    this.uG.ux.countDown();
                }
            });
        } else {
            this.ux = new CountDownLatch(0);
        }
        executorForBackgrountInit.execute(new Runnable(this) {
            final /* synthetic */ d uG;

            public void run() {
                d.r(context, this.uG.uD.gp());
            }
        });
    }

    public com.huluxia.image.base.cache.disk.c.a gs() throws IOException {
        return this.uD.gs();
    }

    public boolean isEnabled() {
        return this.uD.isEnabled();
    }

    @VisibleForTesting
    protected void gM() {
        try {
            this.ux.await();
        } catch (InterruptedException e) {
            HLog.error(tF, "Memory Index is not ready yet. ", new Object[0]);
        }
    }

    public boolean gN() {
        return this.uF || !this.uh;
    }

    public com.huluxia.image.base.binaryresource.a d(com.huluxia.image.base.cache.common.b key) {
        String resourceId = null;
        j cacheEvent = j.gY().i(key);
        com.huluxia.image.base.binaryresource.a aVar;
        try {
            synchronized (this.mLock) {
                aVar = null;
                List<String> resourceIds = c.a(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    resourceId = (String) resourceIds.get(i);
                    cacheEvent.bi(resourceId);
                    aVar = this.uD.d(resourceId, key);
                    if (aVar != null) {
                        break;
                    }
                }
                if (aVar == null) {
                    this.uf.b(cacheEvent);
                    this.uz.remove(resourceId);
                } else {
                    this.uf.a(cacheEvent);
                    this.uz.add(resourceId);
                }
            }
            cacheEvent.recycle();
            return aVar;
        } catch (IOException ioe) {
            try {
                this.tN.a(CacheErrorCategory.GENERIC_IO, tF, "getResource", ioe);
                cacheEvent.a(ioe);
                this.uf.e(cacheEvent);
                aVar = null;
                return aVar;
            } finally {
                cacheEvent.recycle();
            }
        }
    }

    public boolean e(com.huluxia.image.base.cache.common.b key) {
        try {
            synchronized (this.mLock) {
                List<String> resourceIds = c.a(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String resourceId = (String) resourceIds.get(i);
                    if (this.uD.f(resourceId, key)) {
                        this.uz.add(resourceId);
                        return true;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            j cacheEvent = j.gY().i(key).bi(null).a(e);
            this.uf.e(cacheEvent);
            cacheEvent.recycle();
            return false;
        }
    }

    private com.huluxia.image.base.cache.disk.c.d a(String resourceId, com.huluxia.image.base.cache.common.b key) throws IOException {
        gO();
        return this.uD.c(resourceId, key);
    }

    private com.huluxia.image.base.binaryresource.a a(com.huluxia.image.base.cache.disk.c.d inserter, com.huluxia.image.base.cache.common.b key, String resourceId) throws IOException {
        com.huluxia.image.base.binaryresource.a resource;
        synchronized (this.mLock) {
            resource = inserter.h(key);
            this.uz.add(resourceId);
            this.uE.c(resource.size(), 1);
        }
        return resource;
    }

    public com.huluxia.image.base.binaryresource.a a(com.huluxia.image.base.cache.common.b key, i callback) throws IOException {
        String resourceId;
        com.huluxia.image.base.cache.disk.c.d inserter;
        j cacheEvent = j.gY().i(key);
        this.uf.c(cacheEvent);
        synchronized (this.mLock) {
            resourceId = c.b(key);
        }
        cacheEvent.bi(resourceId);
        try {
            inserter = a(resourceId, key);
            inserter.a(callback, key);
            com.huluxia.image.base.binaryresource.a resource = a(inserter, key, resourceId);
            cacheEvent.O(resource.size()).P(this.uE.getSize());
            this.uf.d(cacheEvent);
            if (!inserter.gy()) {
                HLog.error(tF, "Failed to delete temp file", new Object[0]);
            }
            cacheEvent.recycle();
            return resource;
        } catch (IOException ioe) {
            try {
                cacheEvent.a(ioe);
                this.uf.f(cacheEvent);
                HLog.error(tF, "Failed inserting a file into the cache", ioe, new Object[0]);
                throw ioe;
            } catch (Throwable th) {
                cacheEvent.recycle();
            }
        } catch (Throwable th2) {
            if (!inserter.gy()) {
                HLog.error(tF, "Failed to delete temp file", new Object[0]);
            }
        }
    }

    public void f(com.huluxia.image.base.cache.common.b key) {
        synchronized (this.mLock) {
            try {
                List<String> resourceIds = c.a(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String resourceId = (String) resourceIds.get(i);
                    this.uD.bf(resourceId);
                    this.uz.remove(resourceId);
                }
            } catch (IOException e) {
                this.tN.a(CacheErrorCategory.DELETE_FILE, tF, "delete: " + e.getMessage(), e);
            }
        }
    }

    public long N(long cacheExpirationMs) {
        long oldestRemainingEntryAgeMs = 0;
        synchronized (this.mLock) {
            try {
                long now = this.tO.now();
                Collection<c.c> allEntries = this.uD.gu();
                long cacheSizeBeforeClearance = this.uE.getSize();
                int itemsRemovedCount = 0;
                long itemsRemovedSize = 0;
                for (c.c entry : allEntries) {
                    long entryAgeMs = Math.max(1, Math.abs(now - entry.getTimestamp()));
                    if (entryAgeMs >= cacheExpirationMs) {
                        long entryRemovedSize = this.uD.a(entry);
                        this.uz.remove(entry.getId());
                        if (entryRemovedSize > 0) {
                            itemsRemovedCount++;
                            itemsRemovedSize += entryRemovedSize;
                            j cacheEvent = j.gY().bi(entry.getId()).a(EvictionReason.CONTENT_STALE).O(entryRemovedSize).P(cacheSizeBeforeClearance - itemsRemovedSize);
                            this.uf.g(cacheEvent);
                            cacheEvent.recycle();
                        }
                    } else {
                        oldestRemainingEntryAgeMs = Math.max(oldestRemainingEntryAgeMs, entryAgeMs);
                    }
                }
                this.uD.gr();
                if (itemsRemovedCount > 0) {
                    gS();
                    this.uE.c(-itemsRemovedSize, (long) (-itemsRemovedCount));
                }
            } catch (IOException ioe) {
                this.tN.a(CacheErrorCategory.EVICTION, tF, "clearOldEntries: " + ioe.getMessage(), ioe);
            }
        }
        return oldestRemainingEntryAgeMs;
    }

    private void gO() throws IOException {
        synchronized (this.mLock) {
            boolean calculatedRightNow = gS();
            gP();
            long cacheSize = this.uE.getSize();
            if (cacheSize > this.uy && !calculatedRightNow) {
                this.uE.reset();
                gS();
            }
            if (cacheSize > this.uy) {
                a((this.uy * 9) / 10, EvictionReason.CACHE_FULL);
            }
        }
    }

    @GuardedBy("mLock")
    private void a(long desiredSize, EvictionReason reason) throws IOException {
        try {
            Collection<c.c> entries = f(this.uD.gu());
            long cacheSizeBeforeClearance = this.uE.getSize();
            long deleteSize = cacheSizeBeforeClearance - desiredSize;
            int itemCount = 0;
            long sumItemSizes = 0;
            for (c.c entry : entries) {
                if (sumItemSizes > deleteSize) {
                    break;
                }
                long deletedSize = this.uD.a(entry);
                this.uz.remove(entry.getId());
                if (deletedSize > 0) {
                    itemCount++;
                    sumItemSizes += deletedSize;
                    j cacheEvent = j.gY().bi(entry.getId()).a(reason).O(deletedSize).P(cacheSizeBeforeClearance - sumItemSizes).Q(desiredSize);
                    this.uf.g(cacheEvent);
                    cacheEvent.recycle();
                }
            }
            this.uE.c(-sumItemSizes, (long) (-itemCount));
            this.uD.gr();
        } catch (IOException ioe) {
            this.tN.a(CacheErrorCategory.EVICTION, tF, "evictAboveSize: " + ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    private Collection<c.c> f(Collection<c.c> allEntries) {
        long threshold = this.tO.now() + uq;
        ArrayList<c.c> sortedList = new ArrayList(allEntries.size());
        ArrayList<c.c> listToSort = new ArrayList(allEntries.size());
        for (c.c entry : allEntries) {
            if (entry.getTimestamp() > threshold) {
                sortedList.add(entry);
            } else {
                listToSort.add(entry);
            }
        }
        Collections.sort(listToSort, this.ue.gz());
        sortedList.addAll(listToSort);
        return sortedList;
    }

    @GuardedBy("mLock")
    private void gP() {
        if (this.uC.a(this.uD.go() ? StorageType.EXTERNAL : StorageType.INTERNAL, this.uw - this.uE.getSize())) {
            this.uy = this.uv;
        } else {
            this.uy = this.uw;
        }
    }

    public long getSize() {
        return this.uE.getSize();
    }

    public long getCount() {
        return this.uE.getCount();
    }

    public void clearAll() {
        synchronized (this.mLock) {
            try {
                this.uD.clearAll();
                this.uz.clear();
                this.uf.gj();
            } catch (IOException ioe) {
                this.tN.a(CacheErrorCategory.EVICTION, tF, "clearAll: " + ioe.getMessage(), ioe);
            }
            this.uE.reset();
        }
    }

    public boolean g(com.huluxia.image.base.cache.common.b key) {
        boolean z;
        synchronized (this.mLock) {
            List<String> resourceIds = c.a(key);
            for (int i = 0; i < resourceIds.size(); i++) {
                if (this.uz.contains((String) resourceIds.get(i))) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        return z;
    }

    public boolean h(com.huluxia.image.base.cache.common.b key) {
        synchronized (this.mLock) {
            if (g(key)) {
                return true;
            }
            try {
                List<String> resourceIds = c.a(key);
                for (int i = 0; i < resourceIds.size(); i++) {
                    String resourceId = (String) resourceIds.get(i);
                    if (this.uD.e(resourceId, key)) {
                        this.uz.add(resourceId);
                        return true;
                    }
                }
                return false;
            } catch (IOException e) {
                return false;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void gQ() {
        /*
        r12 = this;
        r8 = 0;
        r5 = r12.mLock;
        monitor-enter(r5);
        r12.gS();	 Catch:{ all -> 0x0037 }
        r4 = r12.uE;	 Catch:{ all -> 0x0037 }
        r0 = r4.getSize();	 Catch:{ all -> 0x0037 }
        r6 = r12.uB;	 Catch:{ all -> 0x0037 }
        r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r4 <= 0) goto L_0x001e;
    L_0x0014:
        r4 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1));
        if (r4 <= 0) goto L_0x001e;
    L_0x0018:
        r6 = r12.uB;	 Catch:{ all -> 0x0037 }
        r4 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1));
        if (r4 >= 0) goto L_0x0020;
    L_0x001e:
        monitor-exit(r5);	 Catch:{ all -> 0x0037 }
    L_0x001f:
        return;
    L_0x0020:
        r6 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r8 = r12.uB;	 Catch:{ all -> 0x0037 }
        r8 = (double) r8;	 Catch:{ all -> 0x0037 }
        r10 = (double) r0;	 Catch:{ all -> 0x0037 }
        r8 = r8 / r10;
        r2 = r6 - r8;
        r6 = 4581421828931458171; // 0x3f947ae147ae147b float:89128.96 double:0.02;
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 <= 0) goto L_0x0035;
    L_0x0032:
        r12.c(r2);	 Catch:{ all -> 0x0037 }
    L_0x0035:
        monitor-exit(r5);	 Catch:{ all -> 0x0037 }
        goto L_0x001f;
    L_0x0037:
        r4 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0037 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.base.cache.disk.d.gQ():void");
    }

    public void gR() {
        clearAll();
    }

    private void c(double trimRatio) {
        synchronized (this.mLock) {
            try {
                this.uE.reset();
                gS();
                long cacheSize = this.uE.getSize();
                a(cacheSize - ((long) (((double) cacheSize) * trimRatio)), EvictionReason.CACHE_MANAGER_TRIMMED);
            } catch (IOException ioe) {
                this.tN.a(CacheErrorCategory.EVICTION, tF, "trimBy: " + ioe.getMessage(), ioe);
            }
        }
    }

    @GuardedBy("mLock")
    private boolean gS() {
        long now = this.tO.now();
        if (!this.uE.isInitialized() || this.uA == -1 || now - this.uA > ur) {
            return gT();
        }
        return false;
    }

    @GuardedBy("mLock")
    private boolean gT() {
        Set<String> tempResourceIndex;
        long size = 0;
        int count = 0;
        boolean foundFutureTimestamp = false;
        int numFutureFiles = 0;
        int sizeFutureFiles = 0;
        long maxTimeDelta = -1;
        long now = this.tO.now();
        long timeThreshold = now + uq;
        if (this.uh && this.uz.isEmpty()) {
            tempResourceIndex = this.uz;
        } else if (this.uh) {
            tempResourceIndex = new HashSet();
        } else {
            tempResourceIndex = null;
        }
        try {
            for (c.c entry : this.uD.gu()) {
                count++;
                size += entry.getSize();
                if (entry.getTimestamp() > timeThreshold) {
                    foundFutureTimestamp = true;
                    numFutureFiles++;
                    sizeFutureFiles = (int) (((long) sizeFutureFiles) + entry.getSize());
                    maxTimeDelta = Math.max(entry.getTimestamp() - now, maxTimeDelta);
                } else if (this.uh) {
                    tempResourceIndex.add(entry.getId());
                }
            }
            if (foundFutureTimestamp) {
                this.tN.a(CacheErrorCategory.READ_INVALID_ENTRY, tF, "Future timestamp found in " + numFutureFiles + " files , with a total size of " + sizeFutureFiles + " bytes, and a maximum time delta of " + maxTimeDelta + LocaleUtil.MALAY, null);
            }
            if (!(this.uE.getCount() == ((long) count) && this.uE.getSize() == size)) {
                if (this.uh && this.uz != tempResourceIndex) {
                    this.uF = true;
                } else if (this.uh) {
                    this.uz.clear();
                    this.uz.addAll(tempResourceIndex);
                }
                this.uE.b(size, (long) count);
            }
            this.uA = now;
            return true;
        } catch (IOException ioe) {
            this.tN.a(CacheErrorCategory.GENERIC_IO, tF, "calcFileCacheSize: " + ioe.getMessage(), ioe);
            return false;
        }
    }

    private static void r(Context context, String directoryName) {
        File file = new File((context.getApplicationContext().getFilesDir().getParent() + File.separator + "shared_prefs" + File.separator + uu + directoryName) + ".xml");
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            HLog.error(tF, "Fail to delete SharedPreference from file system. ", new Object[0]);
        }
    }
}
