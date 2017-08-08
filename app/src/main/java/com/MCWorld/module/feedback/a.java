package com.MCWorld.module.feedback;

import android.os.Handler;
import android.os.HandlerThread;
import com.MCWorld.framework.AppConstant;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.log.HLog.LogOutputPaths;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: FeedbackModule */
public class a {
    private static final String TAG = "FeedbackModule";
    private static a aBR = null;
    public static final String aBS = "logsZip.zip";
    private static final String eI = "http://upload.huluxia.net/upload/file";
    private Handler mHandler;
    private HandlerThread thread;

    public static synchronized a DZ() {
        a aVar;
        synchronized (a.class) {
            if (aBR == null) {
                aBR = new a();
            }
            aVar = aBR;
        }
        return aVar;
    }

    public void as(Object context) {
        if (this.thread == null) {
            this.thread = new HandlerThread("feedback");
            this.thread.start();
            this.mHandler = new Handler(this.thread.getLooper());
        }
        this.mHandler.post(new 1(this, context));
    }

    private String getLogPath() {
        try {
            File crashLogs;
            LogOutputPaths outputPaths = HLog.getLogOutputPaths();
            String logsPath = outputPaths.currentLogFile;
            String preLogsPath = outputPaths.latestBackupFile;
            CharSequence uncaughtLogPath = new File(logsPath).getParent() + File.separator + AppConstant.CRASH_FILE;
            CharSequence uncaughtPushLogPath = new File(logsPath).getParent() + File.separator + "push_crash.txt";
            HLog.info(this, "logsPath = " + logsPath + ", preLogsPath = " + preLogsPath, new Object[0]);
            List<File> files = new ArrayList();
            if (!UtilsFunction.empty(uncaughtLogPath)) {
                crashLogs = new File(uncaughtLogPath);
                if (crashLogs != null && crashLogs.exists() && ((double) crashLogs.length()) < 3500000.0d) {
                    files.add(crashLogs);
                }
            }
            if (!UtilsFunction.empty(uncaughtPushLogPath)) {
                crashLogs = new File(uncaughtPushLogPath);
                if (crashLogs != null && crashLogs.exists() && ((double) crashLogs.length()) < 3500000.0d) {
                    files.add(crashLogs);
                }
            }
            if (!(logsPath == null || logsPath.length() == 0)) {
                File currentLogs = new File(logsPath);
                if (currentLogs != null && currentLogs.exists()) {
                    if (((double) currentLogs.length()) < 1500000.0d) {
                        files.add(currentLogs);
                        if (!(preLogsPath == null || preLogsPath.length() == 0)) {
                            File preLogs = new File(preLogsPath);
                            if (preLogs != null && preLogs.exists() && ((double) preLogs.length()) < 6500000.0d) {
                                files.add(preLogs);
                            }
                        }
                    } else if (((double) currentLogs.length()) >= 1500000.0d && ((double) currentLogs.length()) < 6500000.0d) {
                        files.add(currentLogs);
                    }
                }
            }
            return I(files);
        } catch (Exception e) {
            HLog.error(this, "compress logs file error = " + e, new Object[0]);
            return null;
        }
    }

    private String I(List<File> files) {
        String str = null;
        if (files.size() > 0) {
            byte[] buffer = new byte[1024];
            try {
                String zipPath = HLog.getLogOutputPaths().dir + File.separator + aBS;
                HLog.verbose(this, "zipPath = " + zipPath, new Object[0]);
                File zipFile = new File(zipPath);
                if (zipFile.exists()) {
                    zipFile.delete();
                }
                zipFile.createNewFile();
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
                for (File file : files) {
                    if (file != null && file.exists()) {
                        zos.putNextEntry(new ZipEntry(file.getName()));
                        FileInputStream in = new FileInputStream(file);
                        while (true) {
                            int len = in.read(buffer);
                            if (len <= 0) {
                                break;
                            }
                            zos.write(buffer, 0, len);
                        }
                        in.close();
                    }
                }
                zos.closeEntry();
                zos.close();
                str = zipFile.getAbsolutePath();
            } catch (Exception ex) {
                HLog.error(this, "compress logs file error = " + ex.getMessage(), new Object[0]);
            }
        }
        return str;
    }
}
