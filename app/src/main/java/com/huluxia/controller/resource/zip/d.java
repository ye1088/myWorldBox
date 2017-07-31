package com.huluxia.controller.resource.zip;

import android.os.Environment;
import com.huluxia.controller.resource.zip.e.c;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.jni.InstallerJni.ZipCallback;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: HpkZipper */
public class d extends a implements ZipCallback {
    private static final String TAG = "HpkZipper";
    private static final String oL = "info.txt";
    public static final String oM = "app.apk";
    private String desPath;
    private com.huluxia.controller.resource.zip.e.a mI;
    private String oH;
    c oN;
    private int state = 0;

    /* compiled from: HpkZipper */
    public static class a {
        public String name;
        public String oO;
        public String oP;

        public String toString() {
            return "InfoTxt{name='" + this.name + '\'' + ", sdcardPath='" + this.oO + '\'' + ", internalPath='" + this.oP + '\'' + '}';
        }
    }

    public d(String srcFile, String desPath) {
        this.oH = srcFile;
        this.desPath = desPath;
    }

    public void a(com.huluxia.controller.resource.zip.e.a callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback should not be null.......");
        }
        this.mI = callback;
        this.state = 1;
        e.eb().a(this.oH, "info.txt", this.desPath, this);
    }

    public void OnRecvZipResult(int taskId, int result) {
        if (this.state == 1) {
            if (result < 0) {
                this.mI.r(this.oH, result);
                HLog.error(TAG, "unzip info txt failed, result %d, info %s", new Object[]{Integer.valueOf(result), this.oH});
                return;
            }
            a infoTxt = as(new File(this.desPath, "info.txt").getAbsolutePath());
            if (infoTxt == null) {
                HLog.error(TAG, "info txt is INVALID, result %d info %s", new Object[]{Integer.valueOf(result), this.oH});
                this.mI.r(this.oH, Integer.MIN_VALUE);
                return;
            }
            this.state = 2;
            this.mI.d(this.oH, 5, 100);
            HLog.info(TAG, "unzip info txt result , begin unzip game file info %s, infotxt %s, des path %s", new Object[]{this.oH, infoTxt, new File(Environment.getExternalStorageDirectory().getAbsolutePath(), infoTxt.oO).getAbsolutePath()});
            e.eb().a(this.oH, infoTxt.oP, absPath, this);
        } else if (this.state == 2) {
            if (result < 0) {
                this.mI.r(this.oH, result);
                HLog.error(TAG, "unzip obb, result %d info %s", new Object[]{Integer.valueOf(result), this.oH});
                return;
            }
            this.state = 3;
            HLog.info(TAG, "unzip obb result, info %s", new Object[]{this.oH});
            this.mI.d(this.oH, 50, 100);
            e.eb().a(this.oH, "app.apk", this.desPath, this);
        } else if (this.state != 3) {
        } else {
            if (result < 0) {
                this.mI.r(this.oH, result);
                HLog.error(TAG, "unzip apkfailed, info %s", new Object[]{this.oH});
                return;
            }
            HLog.info(TAG, "unzip apk succ info %s", new Object[]{this.oH});
            this.mI.r(this.oH, 0);
        }
    }

    public void OnRecvZipProgress(int taskId, int progress, int length) {
        float curr = (float) progress;
        float total = (float) length;
        if (this.oN == null) {
            this.oN = new c();
        }
        if (this.state == 1) {
            float infoPorgress = ((curr / total) * 0.05f) * 100.0f;
            this.oN.oZ = taskId;
            this.oN.pa = infoPorgress;
            this.oN.length = 100.0f;
        } else if (this.state == 2) {
            float obbPorgress = (((curr / total) * 0.45f) * 100.0f) + 5.0f;
            this.oN.oZ = taskId;
            this.oN.pa = obbPorgress;
            this.oN.length = 100.0f;
        } else if (this.state == 3) {
            float apkPorgress = (((curr / total) * 0.5f) * 100.0f) + 50.0f;
            this.oN.oZ = taskId;
            this.oN.pa = apkPorgress;
            this.oN.length = 100.0f;
        }
        if (System.currentTimeMillis() - this.oN.pb > 500) {
            this.oN.pb = System.currentTimeMillis();
            this.mI.d(this.oH, (int) this.oN.pa, (int) this.oN.length);
        }
    }

    public void onRecvUnzipInternalFile(int taskId, String filePath) {
        HLog.info(this, "onRecvUnzipInternalFile task id %d, path %s", new Object[]{Integer.valueOf(taskId), filePath});
        this.mI.al(filePath);
    }

    public static a as(String path) {
        File infoFile = new File(path);
        if (infoFile.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(infoFile)));
                a infoTxt = new a();
                int line = 0;
                String lineText = bufferedReader.readLine();
                while (line <= 2) {
                    if (lineText == null) {
                        throw new IllegalArgumentException("illegal argument lineText");
                    }
                    if (line == 0) {
                        infoTxt.name = lineText.trim();
                    } else if (line == 1) {
                        infoTxt.oO = lineText.trim();
                    } else if (line == 2) {
                        infoTxt.oP = lineText.trim();
                        if (infoTxt.oP.equals("android")) {
                            infoTxt.oP = "Android";
                        }
                    } else {
                        continue;
                    }
                    line++;
                    lineText = bufferedReader.readLine();
                }
                return infoTxt;
            } catch (FileNotFoundException e) {
                HLog.error(TAG, "hpkzipper FileNotFoundException ex " + e, new Object[0]);
            } catch (IOException e2) {
                HLog.error(TAG, "hpkzipper io ex " + e2, new Object[0]);
            } catch (Exception e3) {
                HLog.error(TAG, "hpkzipper ex " + e3, new Object[0]);
            }
        }
        return null;
    }

    public static String at(String url) {
        return new File(Environment.getExternalStorageDirectory(), "huluxia" + File.separator + "tmp" + File.separator + "hpk" + File.separator + UtilsMD5.getMD5String(url)).getAbsolutePath();
    }

    public static String au(String url) {
        return new File(at(url), "app.apk").getAbsolutePath();
    }

    public static String av(String url) {
        return new File(at(url), "app.apk").getAbsolutePath();
    }
}
