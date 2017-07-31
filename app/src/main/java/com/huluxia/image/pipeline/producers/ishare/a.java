package com.huluxia.image.pipeline.producers.ishare;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.utils.ImmutableMap;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imagepipeline.image.b;
import com.huluxia.image.base.imagepipeline.image.c;
import com.huluxia.image.base.imagepipeline.image.f;
import com.huluxia.image.pipeline.producers.StatefulProducerRunnable;
import com.huluxia.image.pipeline.producers.am;
import com.huluxia.image.pipeline.producers.ao;
import com.huluxia.image.pipeline.producers.aq;
import com.huluxia.image.pipeline.producers.e;
import com.huluxia.image.pipeline.producers.j;
import com.huluxia.image.pipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: ShareAppIconProducer */
public class a implements am<com.huluxia.image.core.common.references.a<b>> {
    public static final String DELIMITER = "===";
    public static final String Ji = "ShareAppIconProducer";
    @VisibleForTesting
    static final String Kl = "shareAppIcon";
    private final Executor mExecutor;

    public a(Executor executor) {
        this.mExecutor = executor;
    }

    public void b(j<com.huluxia.image.core.common.references.a<b>> consumer, ao producerContext) {
        aq listener = producerContext.oB();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.oA();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<com.huluxia.image.core.common.references.a<b>>(this, consumer, listener, Ji, requestId) {
            final /* synthetic */ a Mi;

            protected /* synthetic */ Map M(Object obj) {
                return j((com.huluxia.image.core.common.references.a) obj);
            }

            protected /* synthetic */ Object getResult() throws Exception {
                return nt();
            }

            protected /* synthetic */ void p(Object obj) {
                k((com.huluxia.image.core.common.references.a) obj);
            }

            protected com.huluxia.image.core.common.references.a<b> nt() throws Exception {
                Bitmap thumbnailBitmap = null;
                String path = imageRequest.pv().getPath();
                if (path != null && path.trim().length() > 0) {
                    String[] delimeter = path.substring(1).split(a.DELIMITER);
                    if (delimeter.length == 1) {
                        thumbnailBitmap = this.Mi.o(delimeter[0], "");
                    } else if (delimeter.length == 2) {
                        thumbnailBitmap = this.Mi.o(delimeter[0], delimeter[1]);
                    }
                }
                if (thumbnailBitmap == null) {
                    return null;
                }
                return com.huluxia.image.core.common.references.a.b(new c(thumbnailBitmap, com.huluxia.image.base.imagepipeline.bitmaps.b.hf(), f.wZ, 0));
            }

            protected Map<String, String> j(com.huluxia.image.core.common.references.a<b> result) {
                return ImmutableMap.of(a.Kl, String.valueOf(result != null));
            }

            protected void k(com.huluxia.image.core.common.references.a<b> result) {
                com.huluxia.image.core.common.references.a.c(result);
            }
        };
        producerContext.a(new e(this) {
            final /* synthetic */ a Mi;

            public void cj() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    public Bitmap o(String pkgName, String path) {
        if (pkgName == null || pkgName.trim().length() == 0) {
            pkgName = AppConfig.getInstance().getAppContext().getPackageManager().getPackageArchiveInfo(path, 0).packageName;
        }
        if (pkgName != null && pkgName.trim().length() > 0) {
            Bitmap drawable = p(pkgName, path);
            if (drawable != null) {
                return drawable;
            }
        }
        return null;
    }

    public Bitmap p(String pkgName, String path) {
        PackageManager pm = null;
        try {
            pm = AppConfig.getInstance().getAppContext().getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(pkgName, 0);
            if (appInfo != null) {
                BitmapDrawable drawable = (BitmapDrawable) appInfo.loadIcon(pm);
                if (drawable != null) {
                    return ThumbnailUtils.extractThumbnail(drawable.getBitmap(), 96, 96);
                }
            }
        } catch (Exception e) {
            try {
                PackageInfo pi = pm.getPackageArchiveInfo(path, 0);
                pi.applicationInfo.sourceDir = path;
                pi.applicationInfo.publicSourceDir = path;
                BitmapDrawable appIcon = (BitmapDrawable) pm.getApplicationIcon(pi.applicationInfo);
                if (appIcon != null) {
                    return ThumbnailUtils.extractThumbnail(appIcon.getBitmap(), 96, 96);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
