package com.MCWorld.image.fresco;

import com.MCWorld.framework.base.utils.ImmutableList;
import javax.annotation.Nullable;

/* compiled from: DraweeConfig */
public class b {
    @Nullable
    private final ImmutableList<a> DX;
    private final boolean DY;

    private b(a builder) {
        this.DX = a.a(builder) != null ? ImmutableList.copyOf(a.a(builder)) : null;
        this.DY = a.b(builder);
    }

    @Nullable
    public ImmutableList<a> lc() {
        return this.DX;
    }

    public boolean ld() {
        return this.DY;
    }

    public static a le() {
        return new a();
    }
}
