package com.MCWorld.framework.base.utils.zip;

import android.os.Environment;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.lingala.zip4j.core.c;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.h;
import net.lingala.zip4j.model.p;
import net.lingala.zip4j.progress.a;

public class ZipQ {
    private static final String META = "meta.txt";
    a mMonitor;
    private ScheduledExecutorService mService;
    private String metaTmpPath;
    private volatile boolean start;

    private static class Singleton {
        public static ZipQ zip = new ZipQ();

        private Singleton() {
        }
    }

    public interface ZipProgress {
        public static final int END_CANCEL = 3;
        public static final int END_ERROR = 2;
        public static final int END_SUCC = 0;
        public static final int STATE_STARTING = 2;
        public static final int STATE_WAITING = 1;

        void onEnd(int i, String str, Object obj);

        void onProgress(long j, long j2, String str);

        void onStateChanged(int i, String str);
    }

    public static ZipQ getInstance() {
        return Singleton.zip;
    }

    private ZipQ() {
        this.mService = Executors.newSingleThreadScheduledExecutor();
        this.metaTmpPath = Environment.getExternalStorageDirectory() + File.separator + "huluxia" + File.separator + "tmp" + File.separator + "zip";
        File metaTmp = new File(this.metaTmpPath);
        if (!metaTmp.exists()) {
            metaTmp.mkdirs();
        }
    }

    private String createTmpMetaFile(ArrayList<Zippee> srcFilePaths) throws IOException {
        File file = new File(this.metaTmpPath, META);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
        Iterator it = srcFilePaths.iterator();
        while (it.hasNext()) {
            Zippee zippee = (Zippee) it.next();
            if (zippee.metadata != null) {
                if (zippee.metadata.desPath != null) {
                    zippee.metadata.desPath.replace(Environment.getExternalStorageDirectory().getAbsolutePath(), "");
                }
                bufWriter.write(zippee.metadata.zipRootName + SimpleComparison.EQUAL_TO_OPERATION + zippee.metadata.desPath);
                bufWriter.newLine();
            }
        }
        bufWriter.flush();
        bufWriter.close();
        return file.getAbsolutePath();
    }

    public void zipFileAndFolderWithMeta(ArrayList<Zippee> srcFilePaths, String destinationFilePath, ZipProgress callback) {
        zipFileAndFolderWithMeta(srcFilePaths, destinationFilePath, callback, null);
    }

    public void zipFileAndFolderWithMeta(ArrayList<Zippee> srcFilePaths, String destinationFilePath, ZipProgress callback, String password) {
        if (!UtilsFunction.empty(srcFilePaths)) {
            if (callback != null) {
                callback.onStateChanged(1, destinationFilePath);
            }
            final String str = destinationFilePath;
            final ArrayList<Zippee> arrayList = srcFilePaths;
            final ZipProgress zipProgress = callback;
            final String str2 = password;
            this.mService.schedule(new Runnable() {
                public void run() {
                    try {
                        Zippee path;
                        c cVar = new c(str);
                        ZipQ.this.mMonitor = cVar.Ws();
                        File metaFile = new File(ZipQ.this.createTmpMetaFile(arrayList));
                        Zippee zippee = new Zippee();
                        zippee.file = metaFile.getAbsolutePath();
                        ZipMetadata metadata = new ZipMetadata();
                        metadata.zipRootName = ZipQ.META;
                        metadata.secondaryDir = null;
                        metadata.desPath = "";
                        zippee.metadata = metadata;
                        arrayList.add(zippee);
                        final Map<String, Long> fileSize = new HashMap();
                        long length = 0;
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            File file = new File(((Zippee) it.next()).file);
                            long size = UtilsFile.getSize(file);
                            length += size;
                            fileSize.put(file.getAbsolutePath(), Long.valueOf(size));
                            HLog.debug(this, "File zip path %s, length %d", new Object[]{path, Long.valueOf(length)});
                        }
                        final long j = length;
                        new Thread(new Runnable() {
                            List<String> completeZipfiles = new ArrayList();
                            String lastFileName;
                            long progress = 0;

                            public void run() {
                                while (ZipQ.this.start) {
                                    try {
                                        Thread.sleep(50);
                                        if (ZipQ.this.mMonitor != null) {
                                            String filename = ZipQ.this.mMonitor.getFileName();
                                            if (filename != null) {
                                                if (this.lastFileName != null && filename.indexOf(this.lastFileName) < 0) {
                                                    this.completeZipfiles.add(this.lastFileName);
                                                }
                                                long zipCompleteFilesSize = 0;
                                                for (String key : fileSize.keySet()) {
                                                    for (String file : this.completeZipfiles) {
                                                        if (file.indexOf(key) >= 0) {
                                                            zipCompleteFilesSize += ((Long) fileSize.get(key)).longValue();
                                                            break;
                                                        }
                                                    }
                                                    if (filename.indexOf(key) >= 0) {
                                                        filename = key;
                                                    }
                                                }
                                                this.lastFileName = filename;
                                                this.progress = ZipQ.this.mMonitor.YG() + zipCompleteFilesSize;
                                                HLog.debug(this, "File zip progress %d, file name %s, current %d, complete %d", new Object[]{Long.valueOf(this.progress), ZipQ.this.mMonitor.getFileName(), Long.valueOf(ZipQ.this.mMonitor.YG()), Long.valueOf(zipCompleteFilesSize)});
                                                if (ZipQ.this.mMonitor.getState() == 1 && zipProgress != null) {
                                                    zipProgress.onProgress(this.progress, j, str);
                                                }
                                            }
                                        } else {
                                            return;
                                        }
                                    } catch (InterruptedException e) {
                                        HLog.error(this, "progress zip interrupt %s", e, new Object[0]);
                                        ZipQ.this.start = false;
                                        ZipQ.this.mMonitor = null;
                                    }
                                }
                            }
                        }).start();
                        if (zipProgress != null) {
                            zipProgress.onStateChanged(2, str);
                        }
                        it = arrayList.iterator();
                        while (it.hasNext()) {
                            path = (Zippee) it.next();
                            ZipQ.this.start = true;
                            p parameters = new p();
                            parameters.od(8);
                            parameters.setCompressionLevel(5);
                            if (!UtilsFunction.empty(str2)) {
                                parameters.eg(true);
                                parameters.ox(99);
                                parameters.oB(3);
                                parameters.setPassword(str2);
                            }
                            File targetFile = new File(path.file);
                            parameters.ig(path.metadata.secondaryDir);
                            if (targetFile.isFile()) {
                                cVar.b(targetFile, parameters);
                            } else if (targetFile.isDirectory()) {
                                cVar.c(targetFile, parameters);
                            }
                        }
                        if (zipProgress != null) {
                            zipProgress.onEnd(ZipQ.this.mMonitor.getResult(), str, null);
                        }
                        ZipQ.this.start = false;
                        ZipQ.this.mMonitor = null;
                    } catch (ZipException e) {
                        HLog.error(this, "zip failed ,ex %s", e, new Object[0]);
                        if (zipProgress != null) {
                            zipProgress.onEnd(2, str, null);
                        }
                        ZipQ.this.start = false;
                        ZipQ.this.mMonitor = null;
                    } catch (IOException e2) {
                        HLog.error(this, "zip meta failed ,ex %s", e2, new Object[0]);
                        if (zipProgress != null) {
                            zipProgress.onEnd(2, str, null);
                        }
                        ZipQ.this.start = false;
                        ZipQ.this.mMonitor = null;
                    } catch (Throwable th) {
                        ZipQ.this.start = false;
                        ZipQ.this.mMonitor = null;
                    }
                }
            }, 0, TimeUnit.SECONDS);
        }
    }

    public void unzipWithMeta(String targetZipFilePath, ZipProgress callback) {
        unzipWithMeta(targetZipFilePath, callback, null);
    }

    public void unzipWithMeta(final String targetZipFilePath, final ZipProgress callback, final String password) {
        if (callback != null) {
            callback.onStateChanged(1, targetZipFilePath);
        }
        this.mService.schedule(new Runnable() {
            public void run() {
                try {
                    ZipQ.this.start = true;
                    c cVar = new c(targetZipFilePath);
                    if (!UtilsFunction.empty(password)) {
                        cVar.setPassword(password);
                    }
                    List<h> fileHeaders = cVar.Wl();
                    final Map<String, Long> fileSize = new HashMap();
                    long length = 0;
                    for (h fileHeader : fileHeaders) {
                        long size = fileHeader.Xw();
                        fileSize.put(fileHeader.getFileName(), Long.valueOf(size));
                        length += size;
                    }
                    long total = length;
                    ZipQ.this.mMonitor = cVar.Ws();
                    final long j = total;
                    new Thread(new Runnable() {
                        List<String> completeUnzipfiles = new ArrayList();
                        String lastFileName;

                        public void run() {
                            while (ZipQ.this.start) {
                                try {
                                    Thread.sleep(50);
                                    if (ZipQ.this.mMonitor != null) {
                                        String filename = ZipQ.this.mMonitor.getFileName();
                                        if (filename != null) {
                                            if (!(this.lastFileName == null || filename.equals(this.lastFileName) || !fileSize.keySet().contains(filename))) {
                                                this.completeUnzipfiles.add(this.lastFileName);
                                            }
                                            long curFileCompleteSize = 0;
                                            if (fileSize.keySet().contains(filename)) {
                                                curFileCompleteSize = (long) (((float) ((Long) fileSize.get(filename)).longValue()) * (((float) ZipQ.this.mMonitor.YH()) / 100.0f));
                                            }
                                            long progress = 0;
                                            for (String completeUnzipfile : this.completeUnzipfiles) {
                                                if (!UtilsFunction.empty(completeUnzipfile)) {
                                                    progress += ((Long) fileSize.get(completeUnzipfile)).longValue();
                                                }
                                            }
                                            progress += curFileCompleteSize;
                                            this.lastFileName = filename;
                                            if (ZipQ.this.mMonitor.getState() == 1 && callback != null) {
                                                callback.onProgress(progress, j, targetZipFilePath);
                                            }
                                        }
                                    } else {
                                        return;
                                    }
                                } catch (InterruptedException e) {
                                    HLog.error(this, "progress unzip interrupt %s", e, new Object[0]);
                                    ZipQ.this.start = false;
                                    ZipQ.this.mMonitor = null;
                                }
                            }
                        }
                    }).start();
                    if (callback != null) {
                        callback.onStateChanged(2, targetZipFilePath);
                    }
                    File file = new File(ZipQ.this.metaTmpPath, ZipQ.META);
                    if (file.exists()) {
                        file.delete();
                    }
                    cVar.aQ(ZipQ.META, ZipQ.this.metaTmpPath);
                    List<ZipMetadata> metadatas = ZipQ.this.readMeta(file);
                    for (h fileHeader2 : fileHeaders) {
                        String file2 = fileHeader2.getFileName();
                        for (ZipMetadata metadata : metadatas) {
                            if (file2.startsWith(metadata.zipRootName)) {
                                cVar.a(fileHeader2, new File(Environment.getExternalStorageDirectory(), metadata.desPath).getAbsolutePath());
                            }
                        }
                    }
                    if (callback != null) {
                        callback.onEnd(ZipQ.this.mMonitor.getResult(), targetZipFilePath, metadatas);
                    }
                    ZipQ.this.start = false;
                    ZipQ.this.mMonitor = null;
                } catch (ZipException e) {
                    HLog.error(this, "unzip failed ,ex %s", e, new Object[0]);
                    if (callback != null) {
                        callback.onEnd(2, targetZipFilePath, null);
                    }
                    ZipQ.this.start = false;
                    ZipQ.this.mMonitor = null;
                } catch (IOException e2) {
                    HLog.error(this, "unzip read meta failed ,ex %s", e2, new Object[0]);
                    if (callback != null) {
                        callback.onEnd(2, targetZipFilePath, null);
                    }
                    ZipQ.this.start = false;
                    ZipQ.this.mMonitor = null;
                } catch (Throwable th) {
                    ZipQ.this.start = false;
                    ZipQ.this.mMonitor = null;
                }
            }
        }, 0, TimeUnit.SECONDS);
    }

    private List<ZipMetadata> readMeta(File file) throws IOException {
        List<ZipMetadata> zipMetadatas = new ArrayList();
        if (file != null && file.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String metaStr = bufferedReader.readLine();
                if (metaStr == null) {
                    break;
                }
                String[] strread = metaStr.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (strread.length >= 2) {
                    ZipMetadata metadata = new ZipMetadata();
                    metadata.zipRootName = strread[0];
                    metadata.desPath = strread[1];
                    zipMetadatas.add(metadata);
                }
            }
            bufferedReader.close();
        }
        return zipMetadatas;
    }

    public void stop() {
        if (this.mMonitor != null) {
            this.mMonitor.YM();
        }
        this.start = false;
        this.mMonitor = null;
    }
}
