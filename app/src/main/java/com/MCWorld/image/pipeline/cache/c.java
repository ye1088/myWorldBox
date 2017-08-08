package com.MCWorld.image.pipeline.cache;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.Preconditions;
import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.cache.common.i;
import com.MCWorld.image.base.cache.disk.h;
import com.MCWorld.image.base.imagepipeline.memory.PooledByteBuffer;
import com.MCWorld.image.base.imagepipeline.memory.d;
import com.MCWorld.image.base.imagepipeline.memory.g;
import com.MCWorld.image.core.common.references.a;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: BufferedDiskCache */
public class c {
    private static final Class<?> tF = c.class;
    private final h EC;
    private final g ED;
    private final Executor EE;
    private final Executor EF;
    private final r EG = r.lH();
    private final k EH;
    private final d Eo;

    public c(h fileCache, d pooledByteBufferFactory, g pooledByteStreams, Executor readExecutor, Executor writeExecutor, k imageCacheStatsTracker) {
        this.EC = fileCache;
        this.Eo = pooledByteBufferFactory;
        this.ED = pooledByteStreams;
        this.EE = readExecutor;
        this.EF = writeExecutor;
        this.EH = imageCacheStatsTracker;
    }

    public boolean l(b key) {
        return this.EG.x(key) || this.EC.g(key);
    }

    public bolts.h<Boolean> m(b key) {
        if (l(key)) {
            return bolts.h.b(Boolean.valueOf(true));
        }
        return n(key);
    }

    private bolts.h<Boolean> n(final b key) {
        try {
            return bolts.h.a(new Callable<Boolean>(this) {
                final /* synthetic */ c EJ;

                public /* synthetic */ Object call() throws Exception {
                    return lu();
                }

                public Boolean lu() throws Exception {
                    return Boolean.valueOf(this.EJ.p(key));
                }
            }, this.EE);
        } catch (Exception exception) {
            HLog.warn(tF, String.format("Failed to schedule disk-cache read for %s", new Object[]{key.getUriString()}), new Object[]{exception});
            return bolts.h.f(exception);
        }
    }

    public boolean o(b key) {
        if (l(key)) {
            return true;
        }
        return p(key);
    }

    public bolts.h<com.MCWorld.image.base.imagepipeline.image.d> a(b key, AtomicBoolean isCancelled) {
        com.MCWorld.image.base.imagepipeline.image.d pinnedImage = this.EG.w(key);
        if (pinnedImage != null) {
            return b(key, pinnedImage);
        }
        return b(key, isCancelled);
    }

    private boolean p(b key) {
        com.MCWorld.image.base.imagepipeline.image.d result = this.EG.w(key);
        if (result != null) {
            result.close();
            HLog.verbose(tF, String.format("Found image for %s in staging area", new Object[]{key.getUriString()}), new Object[0]);
            this.EH.u(key);
            return true;
        }
        HLog.verbose(tF, String.format("Did not find image for %s in staging area", new Object[]{key.getUriString()}), new Object[0]);
        this.EH.lC();
        try {
            return this.EC.h(key);
        } catch (Exception e) {
            return false;
        }
    }

    private bolts.h<com.MCWorld.image.base.imagepipeline.image.d> b(final b key, final AtomicBoolean isCancelled) {
        try {
            return bolts.h.a(new Callable<com.MCWorld.image.base.imagepipeline.image.d>(this) {
                final /* synthetic */ c EJ;

                public /* synthetic */ Object call() throws Exception {
                    return lv();
                }

                public com.MCWorld.image.base.imagepipeline.image.d lv() throws Exception {
                    Throwable th;
                    if (isCancelled.get()) {
                        throw new CancellationException();
                    }
                    com.MCWorld.image.base.imagepipeline.image.d result = this.EJ.EG.w(key);
                    if (result != null) {
                        HLog.verbose(c.tF, String.format("Found image for %s in staging area", new Object[]{key.getUriString()}), new Object[0]);
                        this.EJ.EH.u(key);
                        result.j(key);
                    } else {
                        HLog.verbose(c.tF, String.format("Did not find image for %s in staging area", new Object[]{key.getUriString()}), new Object[0]);
                        this.EJ.EH.lC();
                        try {
                            a<PooledByteBuffer> ref = a.b(this.EJ.r(key));
                            try {
                                com.MCWorld.image.base.imagepipeline.image.d result2 = new com.MCWorld.image.base.imagepipeline.image.d(ref);
                                try {
                                    result2.j(key);
                                    try {
                                        a.c(ref);
                                        result = result2;
                                    } catch (Exception e) {
                                        result = result2;
                                        return null;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    result = result2;
                                    a.c(ref);
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                a.c(ref);
                                throw th;
                            }
                        } catch (Exception e2) {
                            return null;
                        }
                    }
                    if (!Thread.interrupted()) {
                        return result;
                    }
                    HLog.verbose(c.tF, "Host thread was interrupted, decreasing reference count", new Object[0]);
                    if (result != null) {
                        result.close();
                    }
                    throw new InterruptedException();
                }
            }, this.EE);
        } catch (Exception exception) {
            HLog.warn(tF, String.format("Failed to schedule disk-cache read for %s", new Object[]{key.getUriString()}), new Object[]{exception});
            return bolts.h.f(exception);
        }
    }

    public void a(final b key, com.MCWorld.image.base.imagepipeline.image.d encodedImage) {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(com.MCWorld.image.base.imagepipeline.image.d.e(encodedImage));
        this.EG.a(key, encodedImage);
        encodedImage.j(key);
        final com.MCWorld.image.base.imagepipeline.image.d finalEncodedImage = com.MCWorld.image.base.imagepipeline.image.d.a(encodedImage);
        try {
            this.EF.execute(new Runnable(this) {
                final /* synthetic */ c EJ;

                public void run() {
                    try {
                        this.EJ.c(key, finalEncodedImage);
                    } finally {
                        this.EJ.EG.d(key, finalEncodedImage);
                        com.MCWorld.image.base.imagepipeline.image.d.d(finalEncodedImage);
                    }
                }
            });
        } catch (Exception exception) {
            HLog.warn(tF, "Failed to schedule disk-cache write for " + key.getUriString(), new Object[]{exception});
            this.EG.d(key, encodedImage);
            com.MCWorld.image.base.imagepipeline.image.d.d(finalEncodedImage);
        }
    }

    public bolts.h<Void> q(final b key) {
        Preconditions.checkNotNull(key);
        this.EG.v(key);
        try {
            return bolts.h.a(new Callable<Void>(this) {
                final /* synthetic */ c EJ;

                public Void call() throws Exception {
                    this.EJ.EG.v(key);
                    this.EJ.EC.f(key);
                    return null;
                }
            }, this.EF);
        } catch (Exception exception) {
            HLog.warn(tF, "Failed to schedule disk-cache remove for " + key.getUriString(), new Object[]{exception});
            return bolts.h.f(exception);
        }
    }

    public bolts.h<Void> lt() {
        this.EG.clearAll();
        try {
            return bolts.h.a(new Callable<Void>(this) {
                final /* synthetic */ c EJ;

                {
                    this.EJ = this$0;
                }

                public Void call() throws Exception {
                    this.EJ.EG.clearAll();
                    this.EJ.EC.clearAll();
                    return null;
                }
            }, this.EF);
        } catch (Exception exception) {
            HLog.warn(tF, "Failed to schedule disk-cache clear", new Object[]{exception});
            return bolts.h.f(exception);
        }
    }

    private bolts.h<com.MCWorld.image.base.imagepipeline.image.d> b(b key, com.MCWorld.image.base.imagepipeline.image.d pinnedImage) {
        HLog.verbose(tF, String.format("Found image for %s in staging area", new Object[]{key.getUriString()}), new Object[0]);
        this.EH.u(key);
        return bolts.h.b((Object) pinnedImage);
    }

    private PooledByteBuffer r(b key) throws IOException {
        InputStream is;
        try {
            HLog.verbose(tF, String.format("Disk cache read for %s", new Object[]{key.getUriString()}), new Object[0]);
            com.MCWorld.image.base.binaryresource.a diskCacheResource = this.EC.d(key);
            if (diskCacheResource == null) {
                HLog.verbose(tF, String.format("Disk cache miss for %s", new Object[]{key.getUriString()}), new Object[0]);
                this.EH.lE();
                return null;
            }
            HLog.verbose(tF, String.format("Found entry in disk cache for %s", new Object[]{key.getUriString()}), new Object[0]);
            this.EH.lD();
            is = diskCacheResource.openStream();
            PooledByteBuffer byteBuffer = this.Eo.a(is, (int) diskCacheResource.size());
            is.close();
            HLog.verbose(tF, String.format("Successful read from disk cache for %s", new Object[]{key.getUriString()}), new Object[0]);
            return byteBuffer;
        } catch (IOException ioe) {
            HLog.warn(tF, String.format("Exception reading from cache for %s", new Object[]{key.getUriString()}), new Object[]{ioe});
            this.EH.lF();
            throw ioe;
        } catch (Throwable th) {
            is.close();
        }
    }

    private void c(b key, final com.MCWorld.image.base.imagepipeline.image.d encodedImage) {
        HLog.verbose(tF, String.format("About to write to disk-cache for key %s", new Object[]{key.getUriString()}), new Object[0]);
        try {
            this.EC.a(key, new i(this) {
                final /* synthetic */ c EJ;

                public void write(OutputStream os) throws IOException {
                    this.EJ.ED.copy(encodedImage.getInputStream(), os);
                }
            });
            HLog.verbose(tF, String.format("Successful disk-cache write for key %s", new Object[]{key.getUriString()}), new Object[0]);
        } catch (IOException ioe) {
            HLog.warn(tF, String.format("Failed to write to disk-cache for key %s", new Object[]{key.getUriString()}), new Object[]{ioe});
        }
    }
}
