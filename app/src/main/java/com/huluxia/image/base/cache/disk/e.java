package com.huluxia.image.base.cache.disk;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.binaryresource.a;
import com.huluxia.image.base.cache.common.CacheErrorLogger;
import com.huluxia.image.base.cache.common.CacheErrorLogger.CacheErrorCategory;
import com.huluxia.image.base.cache.disk.c.c;
import com.huluxia.image.base.cache.disk.c.d;
import com.huluxia.image.core.common.file.FileUtils;
import com.huluxia.image.core.common.file.FileUtils.CreateDirectoryException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/* compiled from: DynamicDefaultDiskStorage */
public class e implements c {
    private static final Class<?> tF = e.class;
    private final CacheErrorLogger tN;
    private final int tY;
    private final String tZ;
    @VisibleForTesting
    volatile a uI = new a(null, null);
    private final Supplier<File> ua;

    public e(int version, Supplier<File> baseDirectoryPathSupplier, String baseDirectoryName, CacheErrorLogger cacheErrorLogger) {
        this.tY = version;
        this.tN = cacheErrorLogger;
        this.ua = baseDirectoryPathSupplier;
        this.tZ = baseDirectoryName;
    }

    public boolean isEnabled() {
        try {
            return gU().isEnabled();
        } catch (IOException e) {
            return false;
        }
    }

    public boolean go() {
        try {
            return gU().go();
        } catch (IOException e) {
            return false;
        }
    }

    public String gp() {
        try {
            return gU().gp();
        } catch (IOException e) {
            return "";
        }
    }

    public a d(String resourceId, Object debugInfo) throws IOException {
        return gU().d(resourceId, debugInfo);
    }

    public boolean e(String resourceId, Object debugInfo) throws IOException {
        return gU().e(resourceId, debugInfo);
    }

    public boolean f(String resourceId, Object debugInfo) throws IOException {
        return gU().f(resourceId, debugInfo);
    }

    public void gr() {
        try {
            gU().gr();
        } catch (IOException ioe) {
            HLog.error(tF, "purgeUnexpectedResources", ioe, new Object[0]);
        }
    }

    public d c(String resourceId, Object debugInfo) throws IOException {
        return gU().c(resourceId, debugInfo);
    }

    public Collection<c> gu() throws IOException {
        return gU().gu();
    }

    public long a(c entry) throws IOException {
        return gU().a(entry);
    }

    public long bf(String resourceId) throws IOException {
        return gU().bf(resourceId);
    }

    public void clearAll() throws IOException {
        gU().clearAll();
    }

    public c.a gs() throws IOException {
        return gU().gs();
    }

    @VisibleForTesting
    synchronized c gU() throws IOException {
        if (gV()) {
            gW();
            gX();
        }
        return (c) Preconditions.checkNotNull(this.uI.uJ);
    }

    private boolean gV() {
        a currentState = this.uI;
        return currentState.uJ == null || currentState.rootDirectory == null || !currentState.rootDirectory.exists();
    }

    @VisibleForTesting
    void gW() {
        if (this.uI.uJ != null && this.uI.rootDirectory != null) {
            com.huluxia.image.core.common.file.a.t(this.uI.rootDirectory);
        }
    }

    private void gX() throws IOException {
        File rootDirectory = new File((File) this.ua.get(), this.tZ);
        s(rootDirectory);
        this.uI = new a(rootDirectory, new DefaultDiskStorage(rootDirectory, this.tY, this.tN));
    }

    @VisibleForTesting
    void s(File rootDirectory) throws IOException {
        try {
            FileUtils.u(rootDirectory);
            HLog.debug(tF, String.format("Created cache directory %s", new Object[]{rootDirectory.getAbsolutePath()}), new Object[0]);
        } catch (CreateDirectoryException cde) {
            this.tN.a(CacheErrorCategory.WRITE_CREATE_DIR, tF, "createRootDirectoryIfNecessary", cde);
            throw cde;
        }
    }
}
