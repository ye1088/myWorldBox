package com.huluxia.image.base.cache.disk;

import android.os.Environment;
import com.huluxia.framework.base.utils.CountingOutputStream;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.cache.common.CacheErrorLogger;
import com.huluxia.image.base.cache.common.CacheErrorLogger.CacheErrorCategory;
import com.huluxia.image.base.cache.common.i;
import com.huluxia.image.base.cache.disk.c.c;
import com.huluxia.image.core.common.file.FileUtils;
import com.huluxia.image.core.common.file.FileUtils.CreateDirectoryException;
import com.huluxia.image.core.common.file.FileUtils.ParentDirNotFoundException;
import com.huluxia.image.core.common.file.FileUtils.RenameException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DefaultDiskStorage implements c {
    private static final Class<?> tF = DefaultDiskStorage.class;
    private static final String tG = ".cnt";
    private static final String tH = ".tmp";
    private static final String tI = "v2";
    private static final int tJ = 100;
    static final long tK = TimeUnit.MINUTES.toMillis(30);
    private final File mRootDirectory;
    private final boolean tL;
    private final File tM;
    private final CacheErrorLogger tN;
    private final com.huluxia.image.core.common.time.a tO = com.huluxia.image.core.common.time.d.iK();

    private enum FileType {
        CONTENT(DefaultDiskStorage.tG),
        TEMP(".tmp");
        
        public final String extension;

        private FileType(String extension) {
            this.extension = extension;
        }

        public static FileType fromExtension(String extension) {
            if (DefaultDiskStorage.tG.equals(extension)) {
                return CONTENT;
            }
            if (".tmp".equals(extension)) {
                return TEMP;
            }
            return null;
        }
    }

    private static class IncompleteFileException extends IOException {
        public final long actual;
        public final long expected;

        public IncompleteFileException(long expected, long actual) {
            super("File was not written completely. Expected: " + expected + ", found: " + actual);
            this.expected = expected;
            this.actual = actual;
        }
    }

    private class a implements com.huluxia.image.core.common.file.b {
        private final List<c> tP;
        final /* synthetic */ DefaultDiskStorage tQ;

        private a(DefaultDiskStorage defaultDiskStorage) {
            this.tQ = defaultDiskStorage;
            this.tP = new ArrayList();
        }

        public void k(File directory) {
        }

        public void l(File file) {
            c info = this.tQ.j(file);
            if (info != null && info.tS == FileType.CONTENT) {
                this.tP.add(new b(info.tT, file));
            }
        }

        public void m(File directory) {
        }

        public List<c> gt() {
            return Collections.unmodifiableList(this.tP);
        }
    }

    @VisibleForTesting
    static class b implements c {
        private final String id;
        private long size;
        private final com.huluxia.image.base.binaryresource.c tR;
        private long timestamp;

        public /* synthetic */ com.huluxia.image.base.binaryresource.a gx() {
            return gw();
        }

        private b(String id, File cachedFile) {
            Preconditions.checkNotNull(cachedFile);
            this.id = (String) Preconditions.checkNotNull(id);
            this.tR = com.huluxia.image.base.binaryresource.c.h(cachedFile);
            this.size = -1;
            this.timestamp = -1;
        }

        public String getId() {
            return this.id;
        }

        public long getTimestamp() {
            if (this.timestamp < 0) {
                this.timestamp = this.tR.getFile().lastModified();
            }
            return this.timestamp;
        }

        public com.huluxia.image.base.binaryresource.c gw() {
            return this.tR;
        }

        public long getSize() {
            if (this.size < 0) {
                this.size = this.tR.size();
            }
            return this.size;
        }
    }

    @VisibleForTesting
    class d implements com.huluxia.image.base.cache.disk.c.d {
        final /* synthetic */ DefaultDiskStorage tQ;
        private final String tU;
        @VisibleForTesting
        final File tV;

        public d(DefaultDiskStorage this$0, String resourceId, File temporaryFile) {
            this.tQ = this$0;
            this.tU = resourceId;
            this.tV = temporaryFile;
        }

        public void a(i callback, Object debugInfo) throws IOException {
            try {
                FileOutputStream fileStream = new FileOutputStream(this.tV);
                try {
                    CountingOutputStream countingStream = new CountingOutputStream(fileStream);
                    callback.write(countingStream);
                    countingStream.flush();
                    long length = countingStream.getCount();
                    if (this.tV.length() != length) {
                        throw new IncompleteFileException(length, this.tV.length());
                    }
                } finally {
                    fileStream.close();
                }
            } catch (FileNotFoundException fne) {
                this.tQ.tN.a(CacheErrorCategory.WRITE_UPDATE_FILE_NOT_FOUND, DefaultDiskStorage.tF, "updateResource", fne);
                throw fne;
            }
        }

        public com.huluxia.image.base.binaryresource.a h(Object debugInfo) throws IOException {
            File targetFile = this.tQ.bb(this.tU);
            try {
                FileUtils.rename(this.tV, targetFile);
                if (targetFile.exists()) {
                    targetFile.setLastModified(this.tQ.tO.now());
                }
                return com.huluxia.image.base.binaryresource.c.h(targetFile);
            } catch (RenameException re) {
                CacheErrorCategory category;
                Throwable cause = re.getCause();
                if (cause == null) {
                    category = CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                } else if (cause instanceof ParentDirNotFoundException) {
                    category = CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_PARENT_NOT_FOUND;
                } else if (cause instanceof FileNotFoundException) {
                    category = CacheErrorCategory.WRITE_RENAME_FILE_TEMPFILE_NOT_FOUND;
                } else {
                    category = CacheErrorCategory.WRITE_RENAME_FILE_OTHER;
                }
                this.tQ.tN.a(category, DefaultDiskStorage.tF, "commit", re);
                throw re;
            }
        }

        public boolean gy() {
            return !this.tV.exists() || this.tV.delete();
        }
    }

    private class e implements com.huluxia.image.core.common.file.b {
        final /* synthetic */ DefaultDiskStorage tQ;
        private boolean tW;

        private e(DefaultDiskStorage defaultDiskStorage) {
            this.tQ = defaultDiskStorage;
        }

        public void k(File directory) {
            if (!this.tW && directory.equals(this.tQ.tM)) {
                this.tW = true;
            }
        }

        public void l(File file) {
            if (!this.tW || !p(file)) {
                file.delete();
            }
        }

        public void m(File directory) {
            if (!(this.tQ.mRootDirectory.equals(directory) || this.tW)) {
                directory.delete();
            }
            if (this.tW && directory.equals(this.tQ.tM)) {
                this.tW = false;
            }
        }

        private boolean p(File file) {
            boolean z = false;
            c info = this.tQ.j(file);
            if (info == null) {
                return false;
            }
            if (info.tS == FileType.TEMP) {
                return q(file);
            }
            if (info.tS == FileType.CONTENT) {
                z = true;
            }
            Preconditions.checkState(z);
            return true;
        }

        private boolean q(File file) {
            return file.lastModified() > this.tQ.tO.now() - DefaultDiskStorage.tK;
        }
    }

    public /* synthetic */ Collection gu() throws IOException {
        return gt();
    }

    public DefaultDiskStorage(File rootDirectory, int version, CacheErrorLogger cacheErrorLogger) {
        Preconditions.checkNotNull(rootDirectory);
        this.mRootDirectory = rootDirectory;
        this.tL = a(rootDirectory, cacheErrorLogger);
        this.tM = new File(this.mRootDirectory, bk(version));
        this.tN = cacheErrorLogger;
        gq();
    }

    private static boolean a(File directory, CacheErrorLogger cacheErrorLogger) {
        String appCacheDirPath = null;
        File extStoragePath = Environment.getExternalStorageDirectory();
        if (extStoragePath == null) {
            return false;
        }
        String cacheDirPath = extStoragePath.toString();
        try {
            appCacheDirPath = directory.getCanonicalPath();
            if (appCacheDirPath.contains(cacheDirPath)) {
                return true;
            }
            return false;
        } catch (IOException e) {
            cacheErrorLogger.a(CacheErrorCategory.OTHER, tF, "failed to read folder to check if external: " + appCacheDirPath, e);
            return false;
        }
    }

    @VisibleForTesting
    static String bk(int version) {
        return String.format((Locale) null, "%s.ols%d.%d", new Object[]{tI, Integer.valueOf(100), Integer.valueOf(version)});
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean go() {
        return this.tL;
    }

    public String gp() {
        String directoryName = this.mRootDirectory.getAbsolutePath();
        return "_" + directoryName.substring(directoryName.lastIndexOf(47) + 1, directoryName.length()) + "_" + directoryName.hashCode();
    }

    private void gq() {
        boolean recreateBase = false;
        if (!this.mRootDirectory.exists()) {
            recreateBase = true;
        } else if (!this.tM.exists()) {
            recreateBase = true;
            com.huluxia.image.core.common.file.a.t(this.mRootDirectory);
        }
        if (recreateBase) {
            try {
                FileUtils.u(this.tM);
            } catch (CreateDirectoryException e) {
                this.tN.a(CacheErrorCategory.WRITE_CREATE_DIR, tF, "version directory could not be created: " + this.tM, null);
            }
        }
    }

    @VisibleForTesting
    File bb(String resourceId) {
        return new File(be(resourceId));
    }

    private String bc(String resourceId) {
        return this.tM + File.separator + String.valueOf(Math.abs(resourceId.hashCode() % 100));
    }

    private File bd(String resourceId) {
        return new File(bc(resourceId));
    }

    public void gr() {
        com.huluxia.image.core.common.file.a.a(this.mRootDirectory, new e());
    }

    private void d(File directory, String message) throws IOException {
        try {
            FileUtils.u(directory);
        } catch (CreateDirectoryException cde) {
            this.tN.a(CacheErrorCategory.WRITE_CREATE_DIR, tF, message, cde);
            throw cde;
        }
    }

    public com.huluxia.image.base.cache.disk.c.d c(String resourceId, Object debugInfo) throws IOException {
        c info = new c(FileType.TEMP, resourceId, null);
        File parent = bd(info.tT);
        if (!parent.exists()) {
            d(parent, "insert");
        }
        try {
            return new d(this, resourceId, info.n(parent));
        } catch (IOException ioe) {
            this.tN.a(CacheErrorCategory.WRITE_CREATE_TEMPFILE, tF, "insert", ioe);
            throw ioe;
        }
    }

    public com.huluxia.image.base.binaryresource.a d(String resourceId, Object debugInfo) {
        File file = bb(resourceId);
        if (!file.exists()) {
            return null;
        }
        file.setLastModified(this.tO.now());
        return com.huluxia.image.base.binaryresource.c.h(file);
    }

    private String be(String resourceId) {
        c fileInfo = new c(FileType.CONTENT, resourceId, null);
        return fileInfo.bg(bc(fileInfo.tT));
    }

    public boolean e(String resourceId, Object debugInfo) {
        return b(resourceId, false);
    }

    public boolean f(String resourceId, Object debugInfo) {
        return b(resourceId, true);
    }

    private boolean b(String resourceId, boolean touch) {
        File contentFile = bb(resourceId);
        boolean exists = contentFile.exists();
        if (touch && exists) {
            contentFile.setLastModified(this.tO.now());
        }
        return exists;
    }

    public long a(c entry) {
        return i(((b) entry).gw().getFile());
    }

    public long bf(String resourceId) {
        return i(bb(resourceId));
    }

    private long i(File contentFile) {
        if (!contentFile.exists()) {
            return 0;
        }
        return !contentFile.delete() ? -1 : contentFile.length();
    }

    public void clearAll() {
        com.huluxia.image.core.common.file.a.deleteContents(this.mRootDirectory);
    }

    public com.huluxia.image.base.cache.disk.c.a gs() throws IOException {
        List<c> entries = gt();
        com.huluxia.image.base.cache.disk.c.a dumpInfo = new com.huluxia.image.base.cache.disk.c.a();
        for (c entry : entries) {
            com.huluxia.image.base.cache.disk.c.b infoEntry = b(entry);
            String type = infoEntry.type;
            if (!dumpInfo.um.containsKey(type)) {
                dumpInfo.um.put(type, Integer.valueOf(0));
            }
            dumpInfo.um.put(type, Integer.valueOf(((Integer) dumpInfo.um.get(type)).intValue() + 1));
            dumpInfo.entries.add(infoEntry);
        }
        return dumpInfo;
    }

    private com.huluxia.image.base.cache.disk.c.b b(c entry) throws IOException {
        b entryImpl = (b) entry;
        String firstBits = "";
        byte[] bytes = entryImpl.gw().ga();
        String type = i(bytes);
        if (type.equals("undefined") && bytes.length >= 4) {
            firstBits = String.format((Locale) null, "0x%02X 0x%02X 0x%02X 0x%02X", new Object[]{Byte.valueOf(bytes[0]), Byte.valueOf(bytes[1]), Byte.valueOf(bytes[2]), Byte.valueOf(bytes[3])});
        }
        return new com.huluxia.image.base.cache.disk.c.b(entryImpl.gw().getFile().getPath(), type, (float) entryImpl.getSize(), firstBits);
    }

    private String i(byte[] bytes) {
        if (bytes.length >= 2) {
            if (bytes[0] == (byte) -1 && bytes[1] == (byte) -40) {
                return "jpg";
            }
            if (bytes[0] == (byte) -119 && bytes[1] == (byte) 80) {
                return "png";
            }
            if (bytes[0] == (byte) 82 && bytes[1] == (byte) 73) {
                return "webp";
            }
            if (bytes[0] == (byte) 71 && bytes[1] == (byte) 73) {
                return "gif";
            }
        }
        return "undefined";
    }

    public List<c> gt() throws IOException {
        a collector = new a();
        com.huluxia.image.core.common.file.a.a(this.tM, collector);
        return collector.gt();
    }

    private c j(File file) {
        c info = c.o(file);
        if (info == null) {
            return null;
        }
        if (!bd(info.tT).equals(file.getParentFile())) {
            info = null;
        }
        return info;
    }
}
