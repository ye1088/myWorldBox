package com.huluxia.image.pipeline.core;

import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.core.common.webp.b;

/* compiled from: ImagePipelineExperiments */
public class g {
    private final int EZ;
    private final boolean FR;
    private final int FS;
    private final boolean FT;
    private final int FU;
    private final boolean FV;
    private final Supplier<Boolean> FW;
    private final com.huluxia.image.core.common.webp.b.a FX;
    private final boolean FY;
    private final b FZ;
    private final boolean Ga;

    /* compiled from: ImagePipelineExperiments */
    public static class a {
        private static final int Gc = 5;
        private int EZ = 0;
        private boolean FR = false;
        private int FS;
        private boolean FT = false;
        private int FU = 5;
        private boolean FV = false;
        private Supplier<Boolean> FW = null;
        private com.huluxia.image.core.common.webp.b.a FX;
        private boolean FY = false;
        private b FZ;
        private boolean Ga = false;
        private final com.huluxia.image.pipeline.core.f.a Gd;

        public a(com.huluxia.image.pipeline.core.f.a configBuilder) {
            this.Gd = configBuilder;
        }

        public com.huluxia.image.pipeline.core.f.a aj(boolean decodeFileDescriptorEnabled) {
            this.FT = decodeFileDescriptorEnabled;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a ak(boolean externalCreatedBitmapLogEnabled) {
            this.FV = externalCreatedBitmapLogEnabled;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a ck(int forceSmallCacheThresholdBytes) {
            this.EZ = forceSmallCacheThresholdBytes;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a h(Supplier<Boolean> mediaVariationsIndexEnabled) {
            this.FW = mediaVariationsIndexEnabled;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a al(boolean webpSupportEnabled) {
            this.FR = webpSupportEnabled;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a am(boolean decodeCancellationEnabled) {
            this.FY = decodeCancellationEnabled;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a b(com.huluxia.image.core.common.webp.b.a webpErrorLogger) {
            this.FX = webpErrorLogger;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a a(b webpBitmapFactory) {
            this.FZ = webpBitmapFactory;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a cl(int enhancedWebpTranscodingType) {
            this.FS = enhancedWebpTranscodingType;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a an(boolean suppressBitmapPrefetching) {
            this.Ga = suppressBitmapPrefetching;
            return this.Gd;
        }

        public com.huluxia.image.pipeline.core.f.a cm(int throttlingMaxSimultaneousRequests) {
            this.FU = throttlingMaxSimultaneousRequests;
            return this.Gd;
        }

        public g my() {
            return new g(this, this.Gd);
        }
    }

    private g(a builder, com.huluxia.image.pipeline.core.f.a configBuilder) {
        this.EZ = builder.EZ;
        this.FR = builder.FR;
        this.FS = builder.FS;
        boolean z = configBuilder.lT() && builder.FT;
        this.FT = z;
        this.FU = builder.FU;
        this.FV = builder.FV;
        if (builder.FW != null) {
            this.FW = builder.FW;
        } else {
            this.FW = new Supplier<Boolean>(this) {
                final /* synthetic */ g Gb;

                {
                    this.Gb = this$0;
                }

                public /* synthetic */ Object get() {
                    return mn();
                }

                public Boolean mn() {
                    return Boolean.FALSE;
                }
            };
        }
        this.FX = builder.FX;
        this.FY = builder.FY;
        this.FZ = builder.FZ;
        this.Ga = builder.Ga;
    }

    public boolean lR() {
        return this.FT;
    }

    public boolean mr() {
        return this.FV;
    }

    public int lX() {
        return this.EZ;
    }

    public boolean ms() {
        return ((Boolean) this.FW.get()).booleanValue();
    }

    public boolean lU() {
        return this.FR;
    }

    public boolean mt() {
        return this.FY;
    }

    public int mu() {
        return this.FS;
    }

    public int mv() {
        return this.FU;
    }

    public com.huluxia.image.core.common.webp.b.a mw() {
        return this.FX;
    }

    public b mx() {
        return this.FZ;
    }

    public static a z(com.huluxia.image.pipeline.core.f.a configBuilder) {
        return new a(configBuilder);
    }
}
