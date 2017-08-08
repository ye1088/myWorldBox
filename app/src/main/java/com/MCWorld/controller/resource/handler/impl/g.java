package com.MCWorld.controller.resource.handler.impl;

import android.widget.Toast;
import com.MCWorld.controller.b;
import com.MCWorld.controller.c;
import com.MCWorld.controller.resource.action.e;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.controller.resource.zip.d;
import com.MCWorld.controller.resource.zip.e.a;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilsApkPackage;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsFunction;
import java.io.File;
import java.util.List;

/* compiled from: HpkHandler */
public class g extends p implements a {
    private static final String TAG = "HpkHandler";
    private Object LOCK = new Object();
    public e nQ;

    public g(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().getDownloadPath();
        }
    }

    public String getSuffix() {
        return "hpk";
    }

    public void aj(String path) {
        File desFile = new File(d.at(((ResTaskInfo) getInfo()).url));
        if (desFile.exists()) {
            desFile.delete();
        }
        desFile.mkdirs();
        HLog.info(TAG, "unzip begin check space, avail %d, unzip %d", new Object[]{Long.valueOf(UtilsFile.availableSpace(desFile.getAbsolutePath())), Long.valueOf((long) (((float) new File(path).length()) * 1.3f))});
        if (UtilsFile.availableSpace(desFile.getAbsolutePath()) < ((long) (((float) new File(path).length()) * 1.3f))) {
            ((ResTaskInfo) getInfo()).state = State.UNZIP_NOT_START.ordinal();
            AppConfig.getInstance().getUiHandler().post(new Runnable(this) {
                final /* synthetic */ g nR;

                {
                    this.nR = this$0;
                }

                public void run() {
                    Toast.makeText(AppConfig.getInstance().getAppContext(), "解压文件（" + ((ResTaskInfo) this.nR.getInfo()).filename + "）空间不足，请清理空间后重试", 0).show();
                }
            });
            EventNotifyCenter.notifyEvent(c.class, 267, new Object[]{((ResTaskInfo) getInfo()).url});
            return;
        }
        this.nQ = new e(new File(path), desFile.getAbsolutePath(), this);
        this.nQ.run();
        ((ResTaskInfo) getInfo()).mR = new File(desFile, "app.apk").getAbsolutePath();
        ((ResTaskInfo) getInfo()).state = State.UNZIP_START.ordinal();
        EventNotifyCenter.notifyEventUiThread(c.class, 260, new Object[]{((ResTaskInfo) getInfo()).url});
        com.MCWorld.controller.resource.zip.b hpkFileList = com.MCWorld.controller.resource.zip.c.ea().aq(((ResTaskInfo) getInfo()).url);
        if (hpkFileList == null) {
            hpkFileList = new com.MCWorld.controller.resource.zip.b();
            hpkFileList.hpkFile = path;
            hpkFileList.apkFile = ((ResTaskInfo) getInfo()).mR;
        }
        hpkFileList.url = ((ResTaskInfo) getInfo()).url;
        com.MCWorld.controller.resource.zip.c.ea().a(((ResTaskInfo) getInfo()).url, hpkFileList);
        synchronized (this.LOCK) {
            try {
                HLog.info(TAG, "hpk run wait...", new Object[0]);
                this.LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (((ResTaskInfo) getInfo()).state == State.UNZIP_COMPLETE.ordinal()) {
            String signature;
            ((ResTaskInfo) getInfo()).state = State.SUCC.ordinal();
            hpkFileList = com.MCWorld.controller.resource.zip.c.ea().aq(((ResTaskInfo) getInfo()).url);
            boolean fileExists = true;
            for (String file : hpkFileList.getFiles()) {
                fileExists = fileExists && new File(file).exists();
                hpkFileList.fileExists = fileExists;
                if (!fileExists) {
                    break;
                }
            }
            hpkFileList.setFileExists(fileExists);
            final String apk = ((ResTaskInfo) getInfo()).mR;
            if (((ResTaskInfo) getInfo()).nb) {
                signature = UtilsApkPackage.getApkSignatureChar(AppConfig.getInstance().getAppContext(), apk);
            } else {
                signature = null;
            }
            AppConfig.getInstance().getUiHandler().post(new Runnable(this) {
                final /* synthetic */ g nR;

                public void run() {
                    HLog.debug(g.TAG, "unzip complete run install, info %s", new Object[]{this.nR.getInfo()});
                    if (((ResTaskInfo) this.nR.getInfo()).na) {
                        UtilsApkPackage.runInstallApp(AppConfig.getInstance().getAppContext(), apk);
                    }
                    EventNotifyCenter.notifyEvent(c.class, 265, new Object[]{((ResTaskInfo) this.nR.getInfo()).url, apk, signature});
                }
            });
        }
    }

    public void d(String srcFile, int progress, int length) {
        HLog.debug(TAG, "unzip progress src %s , progress %d, length %d", new Object[]{srcFile, Integer.valueOf(progress), Integer.valueOf(length)});
        ((ResTaskInfo) getInfo()).mP = new ProgressInfo((long) length, (long) progress, 0.0f);
        ((ResTaskInfo) getInfo()).state = State.UNZIP_PROGRESSING.ordinal();
        EventNotifyCenter.notifyEventUiThread(c.class, 261, new Object[]{((ResTaskInfo) getInfo()).url});
    }

    public void r(String srcFile, int result) {
        HLog.info(TAG, "unzip result src %s , result %d", new Object[]{srcFile, Integer.valueOf(result)});
        if (result < 0) {
            ((ResTaskInfo) getInfo()).state = State.UNZIP_ERROR.ordinal();
        } else {
            ((ResTaskInfo) getInfo()).state = State.UNZIP_COMPLETE.ordinal();
        }
        EventNotifyCenter.notifyEvent(c.class, 262, new Object[]{((ResTaskInfo) getInfo()).url});
        synchronized (this.LOCK) {
            this.LOCK.notifyAll();
        }
    }

    public void al(String innerFile) {
        HLog.debug(TAG, "unzip hpk inner file %s", new Object[]{innerFile});
        if (!UtilsFunction.empty(innerFile)) {
            com.MCWorld.controller.resource.zip.b hpkFileList = com.MCWorld.controller.resource.zip.c.ea().aq(((ResTaskInfo) getInfo()).url);
            if (hpkFileList == null) {
                HLog.error(this, "onRecvUnzipInnerFile file but hpkFileList null", new Object[0]);
                return;
            }
            List<String> files = hpkFileList.getFiles();
            files.add(innerFile);
            hpkFileList.setFiles(files);
            if (innerFile.endsWith("app.apk")) {
                hpkFileList.apkFile = innerFile;
            }
            com.MCWorld.controller.resource.zip.c.ea().a(((ResTaskInfo) getInfo()).url, hpkFileList);
        }
    }
}
