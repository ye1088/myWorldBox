package com.MCWorld.image.base.imagepipeline.common;

import android.graphics.Bitmap.Config;

/* compiled from: ImageDecodeOptionsBuilder */
public class b {
    private int wn = 100;
    private boolean wo;
    private boolean wp;
    private boolean wq;
    private boolean wr;
    private Config ws = Config.ARGB_8888;

    public b a(a options) {
        this.wo = options.wi;
        this.wp = options.wj;
        this.wq = options.wk;
        this.wr = options.wl;
        this.ws = options.wm;
        return this;
    }

    public b bm(int intervalMs) {
        this.wn = intervalMs;
        return this;
    }

    public int ht() {
        return this.wn;
    }

    public b M(boolean decodePreviewFrame) {
        this.wo = decodePreviewFrame;
        return this;
    }

    public boolean hu() {
        return this.wo;
    }

    public boolean hv() {
        return this.wp;
    }

    public b N(boolean useLastFrameForPreview) {
        this.wp = useLastFrameForPreview;
        return this;
    }

    public boolean hw() {
        return this.wq;
    }

    public b O(boolean decodeAllFrames) {
        this.wq = decodeAllFrames;
        return this;
    }

    public b P(boolean forceStaticImage) {
        this.wr = forceStaticImage;
        return this;
    }

    public boolean hx() {
        return this.wr;
    }

    public Config hy() {
        return this.ws;
    }

    public void a(Config bitmapConfig) {
        this.ws = bitmapConfig;
    }

    public a hz() {
        return new a(this);
    }
}
