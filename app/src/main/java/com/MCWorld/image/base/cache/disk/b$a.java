package com.MCWorld.image.base.cache.disk;

import android.content.Context;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.utils.Suppliers;
import com.MCWorld.image.base.cache.common.CacheErrorLogger;
import com.MCWorld.image.base.cache.common.CacheEventListener;
import com.MCWorld.image.core.common.disk.b;
import java.io.File;
import javax.annotation.Nullable;

/* compiled from: DiskCacheConfig */
public class b$a {
    @Nullable
    private final Context mContext;
    private CacheErrorLogger tN;
    private int tY;
    private String tZ;
    private Supplier<File> ua;
    private g ue;
    private CacheEventListener uf;
    private b ug;
    private boolean uh;
    private long ui;
    private long uj;
    private long uk;

    private b$a(@Nullable Context context) {
        this.tY = 1;
        this.tZ = "image_cache";
        this.ui = 41943040;
        this.uj = 10485760;
        this.uk = 2097152;
        this.ue = new a();
        this.mContext = context;
    }

    public b$a bl(int version) {
        this.tY = version;
        return this;
    }

    public b$a bh(String baseDirectoryName) {
        this.tZ = baseDirectoryName;
        return this;
    }

    public b$a r(File baseDirectoryPath) {
        this.ua = Suppliers.of(baseDirectoryPath);
        return this;
    }

    public b$a a(Supplier<File> baseDirectoryPathSupplier) {
        this.ua = baseDirectoryPathSupplier;
        return this;
    }

    public b$a K(long maxCacheSize) {
        this.ui = maxCacheSize;
        return this;
    }

    public b$a L(long maxCacheSizeOnLowDiskSpace) {
        this.uj = maxCacheSizeOnLowDiskSpace;
        return this;
    }

    public b$a M(long maxCacheSizeOnVeryLowDiskSpace) {
        this.uk = maxCacheSizeOnVeryLowDiskSpace;
        return this;
    }

    public b$a a(g supplier) {
        this.ue = supplier;
        return this;
    }

    public b$a a(CacheErrorLogger cacheErrorLogger) {
        this.tN = cacheErrorLogger;
        return this;
    }

    public b$a a(CacheEventListener cacheEventListener) {
        this.uf = cacheEventListener;
        return this;
    }

    public b$a a(b diskTrimmableRegistry) {
        this.ug = diskTrimmableRegistry;
        return this;
    }

    public b$a L(boolean indexEnabled) {
        this.uh = indexEnabled;
        return this;
    }

    public b gK() {
        boolean z = (this.ua == null && this.mContext == null) ? false : true;
        Preconditions.checkState(z, "Either a_isRightVersion non-null context or a_isRightVersion base directory path or supplier must be provided.");
        if (this.ua == null && this.mContext != null) {
            this.ua = new 1(this);
        }
        return new b(this, null);
    }
}
