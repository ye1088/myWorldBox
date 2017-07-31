package com.huluxia.widget;

import android.content.Context;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.service.i;
import com.huluxia.utils.p;
import java.io.File;
import java.util.Vector;

/* compiled from: Zipper */
public class h {
    public static final int buI = 0;
    public static final int buJ = 1;
    public static final int buK = 0;
    public static final int buL = 1;
    private static h buN = null;
    private Thread btR;
    private Vector<a> buM;
    private Context mContext;

    public static synchronized h NV() {
        h hVar;
        synchronized (h.class) {
            if (buN == null) {
                buN = new h();
            }
            hVar = buN;
        }
        return hVar;
    }

    private h() {
        this.mContext = null;
        this.btR = null;
        this.buM = null;
        this.buM = new Vector(20);
        this.btR = new Thread(new 1(this));
        this.btR.start();
    }

    private void NU() {
        while (true) {
            synchronized (this.buM) {
                int nLastIdx = this.buM.size();
                if (nLastIdx == 0) {
                    try {
                        this.buM.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (nLastIdx == 0) {
                } else {
                    a info = (a) this.buM.get(nLastIdx - 1);
                    this.buM.remove(nLastIdx - 1);
                    if (!(info == null || info.buP == null)) {
                        if (1 == info.fileType && (info.buS == null || info.buT == null || info.buU == null)) {
                            i.e(info.btW, 1, 0);
                        } else if (info.buQ == null || info.buP == null) {
                            HLog.error(this, "zip file param invalid, dirpath = " + info.buQ + ", zippath = " + info.buP, new Object[0]);
                        } else {
                            boolean ret;
                            int lastIndex = info.buP.lastIndexOf(File.separator);
                            if (lastIndex < 0) {
                                lastIndex = 0;
                            }
                            String dir = info.buP.substring(0, lastIndex);
                            String name = info.buP.substring(lastIndex);
                            if (info.tf == 0) {
                                ret = ay(info.buP, info.buQ);
                            } else {
                                HLog.error(this, "DoZip task(%s) flag(%d) zipPath(%s) dirPath(%s)", info.btW, Integer.valueOf(info.tf), info.buP, info.buQ);
                                ret = s(info.buQ, dir, name);
                            }
                            if (ret) {
                                i.e(info.btW, 1, info.tf);
                            } else {
                                i.e(info.btW, 0, info.tf);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean s(String srcPath, String desPath, String desName) {
        try {
            p.b(srcPath, desPath, desName, true);
            return true;
        } catch (Exception e) {
            HLog.error(this, "do zip error", e, new Object[0]);
            return false;
        }
    }

    private boolean ay(String zipFile, String filePath) {
        try {
            p.ah(zipFile, filePath);
            return true;
        } catch (Exception e) {
            HLog.error(this, "do unzip error", e, new Object[0]);
            return false;
        }
    }

    public void t(String taskId, String zipPath, String dirPath) {
        a info = new a(taskId, zipPath, dirPath, null, 0, 0);
        synchronized (this.buM) {
            this.buM.add(info);
            this.buM.notify();
        }
    }

    public void g(String taskId, String zipPath, String dirPath, String curPath) {
        a info = new a(taskId, zipPath, dirPath, curPath, 1, 0);
        synchronized (this.buM) {
            this.buM.add(info);
            this.buM.notify();
        }
    }

    public void b(String taskId, String zipPath, String tmpPath, String infoPath, String sdCard) {
        a info = new a(taskId, zipPath, null, null, 0, 1);
        info.buS = tmpPath;
        info.buT = infoPath;
        info.buU = sdCard;
        synchronized (this.buM) {
            this.buM.add(info);
            this.buM.notify();
        }
    }
}
