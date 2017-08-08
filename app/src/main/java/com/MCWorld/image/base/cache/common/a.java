package com.MCWorld.image.base.cache.common;

import com.MCWorld.image.base.cache.common.CacheEventListener.EvictionReason;
import java.io.IOException;
import javax.annotation.Nullable;

/* compiled from: CacheEvent */
public interface a {
    @Nullable
    b gb();

    @Nullable
    String gd();

    long ge();

    long gf();

    long gg();

    @Nullable
    IOException gh();

    @Nullable
    EvictionReason gi();
}
