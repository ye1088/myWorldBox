package com.MCWorld.image.base.imagepipeline.cache;

/* compiled from: MemoryCacheParams */
public class f {
    public final int wb;
    public final int wc;
    public final int wd;
    public final int we;
    public final int wf;

    public f(int maxCacheSize, int maxCacheEntries, int maxEvictionQueueSize, int maxEvictionQueueEntries, int maxCacheEntrySize) {
        this.wb = maxCacheSize;
        this.wc = maxCacheEntries;
        this.wd = maxEvictionQueueSize;
        this.we = maxEvictionQueueEntries;
        this.wf = maxCacheEntrySize;
    }
}
