package com.MCWorld.image.pipeline.request;

import android.net.Uri;
import com.MCWorld.framework.base.utils.Objects;
import com.MCWorld.image.base.imagepipeline.common.Priority;
import com.MCWorld.image.base.imagepipeline.common.a;
import com.MCWorld.image.base.imagepipeline.common.c;
import com.MCWorld.image.base.imagepipeline.common.d;
import java.io.File;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ImageRequest {
    @Nullable
    private final c Ev;
    private final d Ew;
    private final a Ex;
    private final boolean FQ;
    @Nullable
    private final com.MCWorld.image.pipeline.listener.c Fh;
    private final RequestLevel Jc;
    private final d Lg;
    private final CacheChoice Mm;
    private final Uri Mn;
    @Nullable
    private final c Mo;
    private File Mp;
    private final boolean Mq;
    private final Priority Mr;
    private final boolean Ms;

    public static ImageRequest v(@Nullable Uri uri) {
        return uri == null ? null : ImageRequestBuilder.w(uri).pM();
    }

    public static ImageRequest bL(@Nullable String uriString) {
        return (uriString == null || uriString.length() == 0) ? null : v(Uri.parse(uriString));
    }

    protected ImageRequest(ImageRequestBuilder builder) {
        this.Mm = builder.pu();
        this.Mn = builder.pv();
        this.Mo = builder.pw();
        this.FQ = builder.mq();
        this.Mq = builder.pJ();
        this.Ex = builder.pC();
        this.Ev = builder.pz();
        this.Ew = builder.pA() == null ? d.hA() : builder.pA();
        this.Mr = builder.pL();
        this.Jc = builder.oC();
        this.Ms = builder.pF();
        this.Lg = builder.pH();
        this.Fh = builder.pI();
    }

    public CacheChoice pu() {
        return this.Mm;
    }

    public Uri pv() {
        return this.Mn;
    }

    @Nullable
    public c pw() {
        return this.Mo;
    }

    public int px() {
        return this.Ev != null ? this.Ev.width : 2048;
    }

    public int py() {
        return this.Ev != null ? this.Ev.height : 2048;
    }

    @Nullable
    public c pz() {
        return this.Ev;
    }

    public d pA() {
        return this.Ew;
    }

    @Deprecated
    public boolean pB() {
        return this.Ew.hD();
    }

    public a pC() {
        return this.Ex;
    }

    public boolean pD() {
        return this.FQ;
    }

    public boolean pE() {
        return this.Mq;
    }

    public Priority oE() {
        return this.Mr;
    }

    public RequestLevel oC() {
        return this.Jc;
    }

    public boolean pF() {
        return this.Ms;
    }

    public synchronized File pG() {
        if (this.Mp == null) {
            this.Mp = new File(this.Mn.getPath());
        }
        return this.Mp;
    }

    @Nullable
    public d pH() {
        return this.Lg;
    }

    @Nullable
    public com.MCWorld.image.pipeline.listener.c pI() {
        return this.Fh;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ImageRequest)) {
            return false;
        }
        ImageRequest request = (ImageRequest) o;
        if (Objects.equal(this.Mn, request.Mn) && Objects.equal(this.Mm, request.Mm) && Objects.equal(this.Mo, request.Mo) && Objects.equal(this.Mp, request.Mp)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.Mm, this.Mn, this.Mo, this.Mp);
    }

    public String toString() {
        return Objects.toStringHelper((Object) this).add("uri", this.Mn).add("cacheChoice", this.Mm).add("decodeOptions", this.Ex).add("postprocessor", this.Lg).add("priority", this.Mr).add("resizeOptions", this.Ev).add("rotationOptions", this.Ew).toString();
    }
}
