package com.huluxia.controller.resource.handler.impl;

import com.huluxia.controller.b;
import com.huluxia.controller.c;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsApkPackage;
import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.bytedeco.javacpp.avcodec;

/* compiled from: ApkHandler */
public class a extends p {
    private static final String nF = "apk";

    public a(ResTaskInfo info) {
        super(info);
        if (UtilsFunction.empty(info.dir)) {
            info.dir = b.dE().getDownloadPath();
        }
    }

    public String getSuffix() {
        return nF;
    }

    public void aj(final String path) {
        String signature;
        if (((ResTaskInfo) getInfo()).nb) {
            signature = UtilsApkPackage.getApkSignatureChar(AppConfig.getInstance().getAppContext(), path);
        } else {
            signature = null;
        }
        AppConfig.getInstance().getUiHandler().post(new Runnable(this) {
            final /* synthetic */ a nH;

            public void run() {
                if (((ResTaskInfo) this.nH.getInfo()).na) {
                    UtilsApkPackage.runInstallApp(AppConfig.getInstance().getAppContext(), path);
                }
                EventNotifyCenter.notifyEvent(c.class, 265, new Object[]{((ResTaskInfo) this.nH.getInfo()).url, path, signature});
            }
        });
    }

    private String ak(String apkPath) throws IOException {
        String destPath = "";
        int index = apkPath.lastIndexOf(".apk");
        if (index != -1) {
            destPath = apkPath.substring(0, index) + "_new" + ".apk";
        } else {
            destPath = apkPath + ".apk";
        }
        File srcFile = new File(apkPath);
        if (!srcFile.exists()) {
            return apkPath;
        }
        FileInputStream fis = new FileInputStream(apkPath);
        FileOutputStream fos = new FileOutputStream(destPath);
        byte[] buffer = new byte[1024];
        byte[] cache = new byte[4];
        int count = 0;
        while (true) {
            int len = fis.read(buffer);
            if (len != -1) {
                if (count < 8) {
                    for (int i = 0; i < 1024; i += 4) {
                        int j;
                        for (j = 0; j < 4; j++) {
                            cache[j] = buffer[i + j];
                        }
                        cache[0] = (byte) (cache[0] ^ 0);
                        cache[1] = (byte) (cache[1] ^ 0);
                        cache[2] = (byte) (cache[2] ^ avcodec.AV_CODEC_ID_HQX);
                        cache[3] = (byte) (cache[3] ^ 252);
                        for (j = 0; j < 4; j++) {
                            buffer[i + j] = cache[j];
                        }
                    }
                    count++;
                }
                fos.write(buffer, 0, len);
                fos.flush();
            } else {
                fis.close();
                fos.close();
                srcFile.delete();
                return new File(destPath).getAbsolutePath();
            }
        }
    }
}
