package com.huluxia.controller.resource.handler.impl;

import android.support.annotation.y;
import android.text.TextUtils;
import com.cundong.utils.PatchUtils;
import com.huluxia.controller.c;
import com.huluxia.controller.resource.action.d;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.DownloadMemCache;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord$State;
import com.huluxia.framework.base.http.toolbox.error.ErrorCode;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import java.io.File;

/* compiled from: ApkPatchHandler */
public class b extends d<a> {
    public static final String PATH = (AppConfig.getInstance().getRootDir() + File.separator);
    private static final String TAG = "ApkPatchHandler";
    private static final String nI = ".patch";
    private static final int nJ = 1;
    private static final int nK = -1;
    private static final int nL = -2;
    private static final int nM = -3;
    public static final String nN = (PATH + UtilsApkPackage.getAppPackageName(AppConfig.getInstance().getAppContext()) + "_New.apk");
    private Object LOCK = new Object();
    private d action;

    /* compiled from: ApkPatchHandler */
    public static class a extends ResTaskInfo {
        public String packagename;

        public static a b(ResTaskInfo src) {
            a dest = new a();
            dest.mN = src.mN;
            return dest;
        }
    }

    public b(a info) {
        super(info);
        info.dir = com.huluxia.controller.b.dE().getDownloadPath();
    }

    public boolean prepare() throws Exception {
        super.prepare();
        DownloadRecord record = DownloadMemCache.getInstance().getRecord(((a) getInfo()).url);
        if (record == null) {
            HLog.info(TAG, "hpk handler prepare record null, info %s", new Object[]{getInfo()});
            return false;
        } else if (!new File(new File(record.dir, record.name).getAbsolutePath()).exists()) {
            HLog.error(TAG, "hpk download prepare but file delete before", new Object[0]);
            this.mReporter.deleteRecord(((a) getInfo()).url);
            return false;
        } else if (record.state == DownloadRecord$State.COMPLETION.state) {
            HLog.info(TAG, "patch download complete", new Object[]{getInfo()});
            onResponse(null);
            return true;
        } else {
            if (record.error != -1 && ErrorCode.isRestart(record.error)) {
                HLog.error(TAG, "download prepare, download error before, need to restart", new Object[0]);
                this.mReporter.deleteRecord(((a) getInfo()).url);
            }
            return false;
        }
    }

    public void onDownloadComplete(Object response, @y DownloadRecord record) {
        String path = new File(record.dir, record.name).getAbsolutePath();
        if (path.endsWith(nI)) {
            HLog.info(TAG, "patch file exist not update name twice", new Object[0]);
        } else {
            String newPath = path + nI;
            this.action = new d(path, newPath);
            this.action.run();
            File file = new File(newPath);
            path = file.getAbsolutePath();
            ((a) getInfo()).filename = file.getName();
            this.mReporter.updateName(((a) getInfo()).url, ((a) getInfo()).filename);
        }
        HLog.info(this, "newFileName(%s) packName(%s)", new Object[]{((a) getInfo()).filename, ((a) getInfo()).packagename});
        String oldApkSource = UtilsApkPackage.getSourceApkPath(AppConfig.getInstance().getAppContext(), ((a) getInfo()).packagename);
        if (TextUtils.isEmpty(oldApkSource)) {
            HLog.info(this, "patch fail for no source apk", new Object[0]);
            EventNotifyCenter.notifyEvent(c.class, 270, new Object[]{((a) getInfo()).url});
            return;
        }
        EventNotifyCenter.notifyEvent(c.class, 268, new Object[]{((a) getInfo()).url});
        System.loadLibrary("ApkPatchLibrary");
        if (PatchUtils.patch(oldApkSource, nN, path) == 0) {
            final String signatureNew = UtilsApkPackage.getApkSignatureChar(AppConfig.getInstance().getAppContext(), nN);
            String signatureSource = UtilsApkPackage.getApkSignatureByPackagename(AppConfig.getInstance().getAppContext(), ((a) getInfo()).packagename);
            if (TextUtils.isEmpty(signatureNew) || TextUtils.isEmpty(signatureSource) || !signatureNew.equals(signatureSource)) {
                HLog.info(this, "patch fail for signature err", new Object[0]);
                EventNotifyCenter.notifyEvent(c.class, 270, new Object[]{((a) getInfo()).url});
                return;
            }
            ((a) getInfo()).state = State.SUCC.ordinal();
            HLog.info(this, "patch success", new Object[0]);
            EventNotifyCenter.notifyEvent(c.class, 269, new Object[]{((a) getInfo()).url});
            AppConfig.getInstance().getUiHandler().post(new Runnable(this) {
                final /* synthetic */ b nP;

                public void run() {
                    UtilsApkPackage.runInstallApp(AppConfig.getInstance().getAppContext(), b.nN);
                    EventNotifyCenter.notifyEvent(c.class, 265, new Object[]{((a) this.nP.getInfo()).url, b.nN, signatureNew});
                }
            });
            return;
        }
        HLog.info(this, "patch fail for unknown err", new Object[0]);
        EventNotifyCenter.notifyEvent(c.class, 270, new Object[]{((a) getInfo()).url});
    }
}
