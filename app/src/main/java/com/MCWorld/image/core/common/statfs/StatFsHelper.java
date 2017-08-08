package com.MCWorld.image.core.common.statfs;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import com.MCWorld.framework.base.utils.Throwables;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class StatFsHelper {
    private static StatFsHelper yu;
    private static final long yv = TimeUnit.MINUTES.toMillis(2);
    private volatile boolean mInitialized = false;
    @GuardedBy("lock")
    private long yA;
    private final Lock yB = new ReentrantLock();
    private volatile StatFs yw = null;
    private volatile File yx;
    private volatile StatFs yy = null;
    private volatile File yz;

    public enum StorageType {
        INTERNAL,
        EXTERNAL
    }

    public static synchronized StatFsHelper iE() {
        StatFsHelper statFsHelper;
        synchronized (StatFsHelper.class) {
            if (yu == null) {
                yu = new StatFsHelper();
            }
            statFsHelper = yu;
        }
        return statFsHelper;
    }

    protected StatFsHelper() {
    }

    private void iF() {
        if (!this.mInitialized) {
            this.yB.lock();
            try {
                if (!this.mInitialized) {
                    this.yx = Environment.getDataDirectory();
                    this.yz = Environment.getExternalStorageDirectory();
                    iI();
                    this.mInitialized = true;
                }
                this.yB.unlock();
            } catch (Throwable th) {
                this.yB.unlock();
            }
        }
    }

    public boolean a(StorageType storageType, long freeSpaceThreshold) {
        iF();
        long availableStorageSpace = a(storageType);
        if (availableStorageSpace <= 0 || availableStorageSpace < freeSpaceThreshold) {
            return true;
        }
        return false;
    }

    @SuppressLint({"DeprecatedMethod"})
    public long a(StorageType storageType) {
        iF();
        iG();
        StatFs statFS = storageType == StorageType.INTERNAL ? this.yw : this.yy;
        if (statFS == null) {
            return 0;
        }
        long blockSize;
        long availableBlocks;
        if (VERSION.SDK_INT >= 18) {
            blockSize = statFS.getBlockSizeLong();
            availableBlocks = statFS.getAvailableBlocksLong();
        } else {
            blockSize = (long) statFS.getBlockSize();
            availableBlocks = (long) statFS.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }

    private void iG() {
        if (this.yB.tryLock()) {
            try {
                if (SystemClock.uptimeMillis() - this.yA > yv) {
                    iI();
                }
                this.yB.unlock();
            } catch (Throwable th) {
                this.yB.unlock();
            }
        }
    }

    public void iH() {
        if (this.yB.tryLock()) {
            try {
                iF();
                iI();
            } finally {
                this.yB.unlock();
            }
        }
    }

    @GuardedBy("lock")
    private void iI() {
        this.yw = a(this.yw, this.yx);
        this.yy = a(this.yy, this.yz);
        this.yA = SystemClock.uptimeMillis();
    }

    private StatFs a(@Nullable StatFs statfs, @Nullable File dir) {
        if (dir == null || !dir.exists()) {
            return null;
        }
        if (statfs == null) {
            try {
                statfs = bp(dir.getAbsolutePath());
            } catch (IllegalArgumentException e) {
                statfs = null;
            } catch (Throwable ex) {
                RuntimeException propagate = Throwables.propagate(ex);
            }
        } else {
            statfs.restat(dir.getAbsolutePath());
        }
        return statfs;
    }

    protected static StatFs bp(String path) {
        return new StatFs(path);
    }
}
