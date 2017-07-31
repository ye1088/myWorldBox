package com.huluxia.image.pipeline.request;

import android.net.Uri;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.image.base.imagepipeline.common.Priority;
import com.huluxia.image.base.imagepipeline.common.a;
import com.huluxia.image.base.imagepipeline.common.c;
import com.huluxia.image.base.imagepipeline.common.d;
import com.huluxia.image.pipeline.core.f;
import com.huluxia.image.pipeline.request.ImageRequest.CacheChoice;
import com.huluxia.image.pipeline.request.ImageRequest.RequestLevel;
import javax.annotation.Nullable;

public class ImageRequestBuilder {
    @Nullable
    private c Ev = null;
    @Nullable
    private d Ew = null;
    private a Ex = a.hr();
    private boolean FQ = f.lQ().mq();
    @Nullable
    private com.huluxia.image.pipeline.listener.c Fh;
    private RequestLevel Jc = RequestLevel.FULL_FETCH;
    @Nullable
    private d Lg = null;
    private CacheChoice Mm = CacheChoice.DEFAULT;
    private Uri Mn = null;
    @Nullable
    private c Mo = null;
    private boolean Mq = false;
    private Priority Mr = Priority.HIGH;
    private boolean Mt = true;

    public static ImageRequestBuilder w(Uri uri) {
        return new ImageRequestBuilder().x(uri);
    }

    public static ImageRequestBuilder cO(int resId) {
        return w(com.huluxia.image.core.common.util.f.by(resId));
    }

    public static ImageRequestBuilder r(ImageRequest imageRequest) {
        return w(imageRequest.pv()).b(imageRequest.pC()).a(imageRequest.pu()).av(imageRequest.pE()).a(imageRequest.oC()).a(imageRequest.pH()).au(imageRequest.pD()).c(imageRequest.oE()).c(imageRequest.pz()).a(imageRequest.pI()).a(imageRequest.pA());
    }

    private ImageRequestBuilder() {
    }

    public ImageRequestBuilder x(Uri uri) {
        Preconditions.checkNotNull(uri);
        this.Mn = uri;
        return this;
    }

    public Uri pv() {
        return this.Mn;
    }

    public ImageRequestBuilder a(c mediaVariations) {
        this.Mo = mediaVariations;
        return this;
    }

    @Nullable
    public c pw() {
        return this.Mo;
    }

    public ImageRequestBuilder a(RequestLevel requestLevel) {
        this.Jc = requestLevel;
        return this;
    }

    public RequestLevel oC() {
        return this.Jc;
    }

    @Deprecated
    public ImageRequestBuilder at(boolean enabled) {
        if (enabled) {
            return a(d.hA());
        }
        return a(d.hB());
    }

    public ImageRequestBuilder c(@Nullable c resizeOptions) {
        this.Ev = resizeOptions;
        return this;
    }

    @Nullable
    public c pz() {
        return this.Ev;
    }

    public ImageRequestBuilder a(@Nullable d rotationOptions) {
        this.Ew = rotationOptions;
        return this;
    }

    @Nullable
    public d pA() {
        return this.Ew;
    }

    public ImageRequestBuilder b(a imageDecodeOptions) {
        this.Ex = imageDecodeOptions;
        return this;
    }

    public a pC() {
        return this.Ex;
    }

    public ImageRequestBuilder a(CacheChoice cacheChoice) {
        this.Mm = cacheChoice;
        return this;
    }

    public CacheChoice pu() {
        return this.Mm;
    }

    public ImageRequestBuilder au(boolean enabled) {
        this.FQ = enabled;
        return this;
    }

    public boolean mq() {
        return this.FQ;
    }

    public ImageRequestBuilder av(boolean enabled) {
        this.Mq = enabled;
        return this;
    }

    public boolean pJ() {
        return this.Mq;
    }

    public ImageRequestBuilder pK() {
        this.Mt = false;
        return this;
    }

    public boolean pF() {
        return this.Mt && com.huluxia.image.core.common.util.f.isNetworkUri(this.Mn);
    }

    public ImageRequestBuilder c(Priority requestPriority) {
        this.Mr = requestPriority;
        return this;
    }

    public Priority pL() {
        return this.Mr;
    }

    public ImageRequestBuilder a(d postprocessor) {
        this.Lg = postprocessor;
        return this;
    }

    @Nullable
    public d pH() {
        return this.Lg;
    }

    public ImageRequestBuilder a(com.huluxia.image.pipeline.listener.c requestListener) {
        this.Fh = requestListener;
        return this;
    }

    @Nullable
    public com.huluxia.image.pipeline.listener.c pI() {
        return this.Fh;
    }

    public ImageRequest pM() {
        validate();
        return new ImageRequest(this);
    }

    protected void validate() {
        if (this.Mn == null) {
            throw new BuilderException("Source must be set!");
        }
        if (com.huluxia.image.core.common.util.f.isLocalResourceUri(this.Mn)) {
            if (!this.Mn.isAbsolute()) {
                throw new BuilderException("Resource URI path must be absolute.");
            } else if (this.Mn.getPath().isEmpty()) {
                throw new BuilderException("Resource URI must not be empty");
            } else {
                try {
                    Integer.parseInt(this.Mn.getPath().substring(1));
                } catch (NumberFormatException e) {
                    throw new BuilderException("Resource URI path must be a resource id.");
                }
            }
        }
        if (com.huluxia.image.core.common.util.f.isLocalAssetUri(this.Mn) && !this.Mn.isAbsolute()) {
            throw new BuilderException("Asset URI path must be absolute.");
        }
    }
}
