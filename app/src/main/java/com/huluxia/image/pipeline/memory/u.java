package com.huluxia.image.pipeline.memory;

/* compiled from: PoolStatsTracker */
public interface u {
    public static final String IA = "soft_cap";
    public static final String IB = "hard_cap";
    public static final String Iv = "buckets_used_";
    public static final String Iw = "used_count";
    public static final String Ix = "used_bytes";
    public static final String Iy = "free_count";
    public static final String Iz = "free_bytes";

    void a(BasePool basePool);

    void cG(int i);

    void cH(int i);

    void cI(int i);

    void cJ(int i);

    void od();

    void oe();
}
