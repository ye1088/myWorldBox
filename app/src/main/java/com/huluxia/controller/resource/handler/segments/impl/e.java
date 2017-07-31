package com.huluxia.controller.resource.handler.segments.impl;

import android.widget.Toast;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.bean.ResTaskInfo.State;
import com.huluxia.controller.resource.handler.segments.c;
import com.huluxia.controller.resource.zip.b;
import com.huluxia.controller.resource.zip.d;
import com.huluxia.controller.resource.zip.e.a;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.File;
import java.util.List;

/* compiled from: HpkHandler */
public class e extends c implements a {
    private static final String TAG = "HpkHandler";
    private Object LOCK = new Object();
    public com.huluxia.controller.resource.action.e nQ;

    public e(ResTaskInfo info) {
        super(info);
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
                final /* synthetic */ e oF;

                {
                    this.oF = this$0;
                }

                public void run() {
                    Toast.makeText(AppConfig.getInstance().getAppContext(), "解压文件（" + ((ResTaskInfo) this.oF.getInfo()).filename + "）空间不足，请清理空间后重试", 0).show();
                }
            });
            EventNotifyCenter.notifyEvent(com.huluxia.controller.c.class, 267, new Object[]{((ResTaskInfo) getInfo()).url});
            return;
        }
        this.nQ = new com.huluxia.controller.resource.action.e(new File(path), desFile.getAbsolutePath(), this);
        this.nQ.run();
        ((ResTaskInfo) getInfo()).mR = new File(desFile, "app.apk").getAbsolutePath();
        ((ResTaskInfo) getInfo()).state = State.UNZIP_START.ordinal();
        EventNotifyCenter.notifyEventUiThread(com.huluxia.controller.c.class, 260, new Object[]{((ResTaskInfo) getInfo()).url});
        b hpkFileList = com.huluxia.controller.resource.zip.c.ea().aq(((ResTaskInfo) getInfo()).url);
        if (hpkFileList == null) {
            hpkFileList = new b();
            hpkFileList.hpkFile = path;
            hpkFileList.apkFile = ((ResTaskInfo) getInfo()).mR;
        }
        hpkFileList.url = ((ResTaskInfo) getInfo()).url;
        com.huluxia.controller.resource.zip.c.ea().a(((ResTaskInfo) getInfo()).url, hpkFileList);
        synchronized (this.LOCK) {
            try {
                HLog.info(TAG, "hpk run wait...", new Object[0]);
                this.LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HLog.info(TAG, "hpk run succ " + ((ResTaskInfo) getInfo()).state, new Object[0]);
        if (((ResTaskInfo) getInfo()).state == State.UNZIP_COMPLETE.ordinal()) {
            String signature;
            ((ResTaskInfo) getInfo()).state = State.SUCC.ordinal();
            hpkFileList = com.huluxia.controller.resource.zip.c.ea().aq(((ResTaskInfo) getInfo()).url);
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
                final /* synthetic */ e oF;

                public void run() {
                    HLog.debug(e.TAG, "unzip complete run install, info %s", new Object[]{this.oF.getInfo()});
                    if (((ResTaskInfo) this.oF.getInfo()).na) {
                        UtilsApkPackage.runInstallApp(AppConfig.getInstance().getAppContext(), apk);
                    }
                    EventNotifyCenter.notifyEvent(com.huluxia.controller.c.class, 265, new Object[]{((ResTaskInfo) this.oF.getInfo()).url, apk, signature});
                }
            });
        }
    }

    public void d(String srcFile, int progress, int length) {
        HLog.debug(TAG, "unzip progress src %s , progress %d, length %d", new Object[]{srcFile, Integer.valueOf(progress), Integer.valueOf(length)});
        ((ResTaskInfo) getInfo()).mP = new ProgressInfo((long) length, (long) progress, 0.0f);
        ((ResTaskInfo) getInfo()).state = State.UNZIP_PROGRESSING.ordinal();
        EventNotifyCenter.notifyEventUiThread(com.huluxia.controller.c.class, 261, new Object[]{((ResTaskInfo) getInfo()).url});
    }

    public void r(String srcFile, int result) {
        HLog.info(TAG, "unzip result src %s , result %d", new Object[]{srcFile, Integer.valueOf(result)});
        if (result < 0) {
            ((ResTaskInfo) getInfo()).state = State.UNZIP_ERROR.ordinal();
        } else {
            ((ResTaskInfo) getInfo()).state = State.UNZIP_COMPLETE.ordinal();
        }
        EventNotifyCenter.notifyEvent(com.huluxia.controller.c.class, 262, new Object[]{((ResTaskInfo) getInfo()).url});
        synchronized (this.LOCK) {
            this.LOCK.notifyAll();
        }
    }

    public void al(String innerFile) {
        HLog.debug(TAG, "unzip hpk inner file %s", new Object[]{innerFile});
        if (!UtilsFunction.empty(innerFile)) {
            b hpkFileList = com.huluxia.controller.resource.zip.c.ea().aq(((ResTaskInfo) getInfo()).url);
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
            com.huluxia.controller.resource.zip.c.ea().a(((ResTaskInfo) getInfo()).url, hpkFileList);
        }
    }
}
