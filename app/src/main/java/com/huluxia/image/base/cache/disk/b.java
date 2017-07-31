package com.huluxia.image.base.cache.disk;

import android.content.Context;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.cache.common.CacheErrorLogger;
import com.huluxia.image.base.cache.common.CacheEventListener;
import com.huluxia.image.base.cache.common.f;
import com.huluxia.image.base.cache.common.g;
import com.huluxia.image.core.common.disk.c;
import java.io.File;
import javax.annotation.Nullable;

/* compiled from: DiskCacheConfig */
public class b {
    private final Context mContext;
    private final CacheErrorLogger tN;
    private final int tY;
    private final String tZ;
    private final Supplier<File> ua;
    private final long ub;
    private final long uc;
    private final long ud;
    private final g ue;
    private final CacheEventListener uf;
    private final com.huluxia.image.core.common.disk.b ug;
    private final boolean uh;

    private b(a builder) {
        CacheErrorLogger gm;
        CacheEventListener gn;
        com.huluxia.image.core.common.disk.b ij;
        this.tY = a.a(builder);
        this.tZ = (String) Preconditions.checkNotNull(a.b(builder));
        this.ua = (Supplier) Preconditions.checkNotNull(a.c(builder));
        this.ub = a.d(builder);
        this.uc = a.e(builder);
        this.ud = a.f(builder);
        this.ue = (g) Preconditions.checkNotNull(a.g(builder));
        if (a.h(builder) == null) {
            gm = f.gm();
        } else {
            gm = a.h(builder);
        }
        this.tN = gm;
        if (a.i(builder) == null) {
            gn = g.gn();
        } else {
            gn = a.i(builder);
        }
        this.uf = gn;
        if (a.j(builder) == null) {
            ij = c.ij();
        } else {
            ij = a.j(builder);
        }
        this.ug = ij;
        this.mContext = a.k(builder);
        this.uh = a.l(builder);
    }

    public int getVersion() {
        return this.tY;
    }

    public String gA() {
        return this.tZ;
    }

    public Supplier<File> gB() {
        return this.ua;
    }

    public long gC() {
        return this.ub;
    }

    public long gD() {
        return this.uc;
    }

    public long gE() {
        return this.ud;
    }

    public g gF() {
        return this.ue;
    }

    public CacheErrorLogger gG() {
        return this.tN;
    }

    public CacheEventListener gH() {
        return this.uf;
    }

    public com.huluxia.image.core.common.disk.b gI() {
        return this.ug;
    }

    public Context getContext() {
        return this.mContext;
    }

    public boolean gJ() {
        return this.uh;
    }

    public static a aD(@Nullable Context context) {
        return new a(context, null);
    }
}
