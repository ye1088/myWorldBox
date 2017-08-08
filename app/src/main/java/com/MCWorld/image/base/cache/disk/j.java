package com.MCWorld.image.base.cache.disk;

import com.MCWorld.image.base.cache.common.CacheEventListener.EvictionReason;
import com.MCWorld.image.base.cache.common.a;
import com.MCWorld.image.base.cache.common.b;
import java.io.IOException;
import javax.annotation.Nullable;

/* compiled from: SettableCacheEvent */
public class j implements a {
    private static final Object uO = new Object();
    private static final int uP = 5;
    private static j uQ;
    private static int uR;
    private String tU;
    private b uS;
    private long uT;
    private long uU;
    private long uV;
    private IOException uW;
    private EvictionReason uX;
    private j uY;

    public static j gY() {
        synchronized (uO) {
            if (uQ != null) {
                j eventToReuse = uQ;
                uQ = eventToReuse.uY;
                eventToReuse.uY = null;
                uR--;
                return eventToReuse;
            }
            return new j();
        }
    }

    private j() {
    }

    @Nullable
    public b gb() {
        return this.uS;
    }

    public j i(b cacheKey) {
        this.uS = cacheKey;
        return this;
    }

    @Nullable
    public String gd() {
        return this.tU;
    }

    public j bi(String resourceId) {
        this.tU = resourceId;
        return this;
    }

    public long ge() {
        return this.uT;
    }

    public j O(long itemSize) {
        this.uT = itemSize;
        return this;
    }

    public long gf() {
        return this.uV;
    }

    public j P(long cacheSize) {
        this.uV = cacheSize;
        return this;
    }

    public long gg() {
        return this.uU;
    }

    public j Q(long cacheLimit) {
        this.uU = cacheLimit;
        return this;
    }

    @Nullable
    public IOException gh() {
        return this.uW;
    }

    public j a(IOException exception) {
        this.uW = exception;
        return this;
    }

    @Nullable
    public EvictionReason gi() {
        return this.uX;
    }

    public j a(EvictionReason evictionReason) {
        this.uX = evictionReason;
        return this;
    }

    public void recycle() {
        synchronized (uO) {
            if (uR < 5) {
                reset();
                uR++;
                if (uQ != null) {
                    this.uY = uQ;
                }
                uQ = this;
            }
        }
    }

    private void reset() {
        this.uS = null;
        this.tU = null;
        this.uT = 0;
        this.uU = 0;
        this.uV = 0;
        this.uW = null;
        this.uX = null;
    }
}
