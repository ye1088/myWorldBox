package com.huluxia.image.pipeline.decoder;

import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imagepipeline.image.g;
import java.util.Collections;
import java.util.List;

/* compiled from: SimpleProgressiveJpegConfig */
public class f implements d {
    private final b Hi;

    /* compiled from: SimpleProgressiveJpegConfig */
    public interface b {
        List<Integer> nI();

        int nJ();
    }

    /* compiled from: SimpleProgressiveJpegConfig */
    private static class a implements b {
        private a() {
        }

        public List<Integer> nI() {
            return Collections.EMPTY_LIST;
        }

        public int nJ() {
            return 0;
        }
    }

    public f() {
        this(new a());
    }

    public f(b dynamicValueConfig) {
        this.Hi = (b) Preconditions.checkNotNull(dynamicValueConfig);
    }

    public int cn(int scanNumber) {
        List<Integer> scansToDecode = this.Hi.nI();
        if (scansToDecode == null || scansToDecode.isEmpty()) {
            return scanNumber + 1;
        }
        for (int i = 0; i < scansToDecode.size(); i++) {
            if (((Integer) scansToDecode.get(i)).intValue() > scanNumber) {
                return ((Integer) scansToDecode.get(i)).intValue();
            }
        }
        return Integer.MAX_VALUE;
    }

    public g co(int scanNumber) {
        boolean z;
        if (scanNumber >= this.Hi.nJ()) {
            z = true;
        } else {
            z = false;
        }
        return com.huluxia.image.base.imagepipeline.image.f.a(scanNumber, z, false);
    }
}
