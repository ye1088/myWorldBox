package com.huluxia.image.pipeline.decoder;

import com.huluxia.image.base.imageformat.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ImageDecoderConfig */
public class c {
    private final Map<d, b> GS;
    private final List<com.huluxia.image.base.imageformat.d.a> GU;

    /* compiled from: ImageDecoderConfig */
    public static class a {
        private Map<d, b> GS;
        private List<com.huluxia.image.base.imageformat.d.a> GU;

        public a a(d imageFormat, com.huluxia.image.base.imageformat.d.a imageFormatChecker, b decoder) {
            if (this.GU == null) {
                this.GU = new ArrayList();
            }
            this.GU.add(imageFormatChecker);
            a(imageFormat, decoder);
            return this;
        }

        public a a(d imageFormat, b decoder) {
            if (this.GS == null) {
                this.GS = new HashMap();
            }
            this.GS.put(imageFormat, decoder);
            return this;
        }

        public c nE() {
            return new c();
        }
    }

    private c(a builder) {
        this.GS = builder.GS;
        this.GU = builder.GU;
    }

    public Map<d, b> nB() {
        return this.GS;
    }

    public List<com.huluxia.image.base.imageformat.d.a> nC() {
        return this.GU;
    }

    public static a nD() {
        return new a();
    }
}
