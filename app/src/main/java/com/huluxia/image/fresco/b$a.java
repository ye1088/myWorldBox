package com.huluxia.image.fresco;

import java.util.ArrayList;
import java.util.List;

/* compiled from: DraweeConfig */
public class b$a {
    private boolean DY;
    private List<a> DZ;

    public b$a a(a factory) {
        if (this.DZ == null) {
            this.DZ = new ArrayList();
        }
        this.DZ.add(factory);
        return this;
    }

    public b$a ae(boolean drawDebugOverlay) {
        this.DY = drawDebugOverlay;
        return this;
    }

    public b lf() {
        return new b(this, null);
    }
}
