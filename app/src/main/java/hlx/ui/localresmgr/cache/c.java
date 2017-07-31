package hlx.ui.localresmgr.cache;

import com.huluxia.data.map.b;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ai;
import com.huluxia.utils.bc;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.ReStartSoftFlag;
import hlx.data.localstore.a;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* compiled from: LocResFileOper */
public class c {
    private static final String TAG = "LocResFileOper";
    private static c caa;

    public static synchronized c Uw() {
        c cVar;
        synchronized (c.class) {
            if (caa == null) {
                caa = new c();
            }
            cVar = caa;
        }
        return cVar;
    }

    public void r(final b item) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                j.deleteFile(j.Ku() + item.name + ".zip");
                j.deleteFile(j.cU(true) + item.name + a.bKa);
                j.deleteFile(j.cU(true) + item.name + a.bKb);
            }
        });
    }

    public void d(b item, final String srcPath, final String srcName, final String dstName) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                if (UtilsFile.rename(srcPath, UtilsFile.cU(true) + dstName + a.bKa)) {
                    UtilsFile.rename(UtilsFile.cU(true) + srcName + a.bKb, UtilsFile.cU(true) + dstName + a.bKb);
                }
            }
        });
    }

    private void aJ(String version, String resourcePack) {
        if (resourcePack.length() > 1 && version.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0150.Value()))) {
            UtilsFile.deleteFile(j.Ks() + resourcePack);
        }
    }

    public void s(final b item) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                a.Un().hA(item.pU);
                this.cab.aJ(item.ver, item.pU);
                j.deleteFile(j.Kv() + item.name + ".zip");
                j.deleteFile(j.Kv() + item.name + ".zip" + a.bKb);
            }
        });
    }

    public void aK(final String zipPath, final String unzipPath) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                File zipFile = new File(zipPath);
                if (!zipFile.exists()) {
                    HLog.error(c.TAG, "DTPrint [%s] file not exist !", new Object[]{zipPath});
                } else if (zipPath.endsWith(".zip")) {
                    File dir = new File(unzipPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    try {
                        bc.a(zipFile.getAbsolutePath(), unzipPath, null);
                    } catch (Exception e) {
                    }
                } else {
                    HLog.error(c.TAG, "DTPrint [%s] file tail is error !", new Object[]{zipPath});
                }
            }
        });
    }

    public void e(b item, final String srcPath, final String srcName, final String dstName) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                if (UtilsFile.rename(srcPath, UtilsFile.Kv() + dstName + ".zip")) {
                    UtilsFile.rename(UtilsFile.Kv() + srcName + ".zip" + a.bKb, UtilsFile.Kv() + dstName + ".zip" + a.bKb);
                }
            }
        });
    }

    public void t(final b item) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                String zipPath = j.Kt() + item.name + ".zip";
                String filePath = j.cT(true) + item.name + a.bJY;
                String filePathIni = j.cT(true) + item.name + a.bKb;
                j.deleteFile(filePath);
                j.deleteFile(zipPath);
                j.deleteFile(filePathIni);
            }
        });
    }

    public void f(b item, final String srcPath, final String srcName, final String dstName) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                if (UtilsFile.rename(srcPath, UtilsFile.cT(true) + dstName + a.bJY)) {
                    UtilsFile.rename(UtilsFile.cT(true) + srcName + a.bKb, UtilsFile.cT(true) + dstName + a.bKb);
                }
            }
        });
    }

    public void u(final b item) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                UtilsFile.deleteFile(UtilsFile.Kq() + item.name);
                UtilsFile.deleteFile(ai.Md() + File.separator + item.name + ".zip");
                UtilsFile.deleteFile(ai.Md() + File.separator + item.name);
            }
        });
    }

    public void b(final b item, final String dstMapName) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                String from = item.path;
                String to = UtilsFile.Kq() + dstMapName;
                String realleveldat = to + File.separator + a.bKd;
                if (!UtilsFile.isExist(to) && UtilsFile.copyFolder(from, to) && UtilsFile.isExist(realleveldat)) {
                    try {
                        FileWriter f = new FileWriter(realleveldat);
                        f.write(dstMapName);
                        f.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    public void b(final b item, final String srcMapPath, final String dstMapName) {
        AsyncTaskCenter.getInstance().execute(new Runnable(this) {
            final /* synthetic */ c cab;

            public void run() {
                String from = srcMapPath;
                String to = UtilsFile.Kq() + dstMapName;
                if (UtilsFile.rename(from, to)) {
                    String realleveldat = to + File.separator + a.bKd;
                    if (UtilsFile.isExist(realleveldat)) {
                        try {
                            FileWriter f = new FileWriter(realleveldat);
                            f.write(dstMapName);
                            f.close();
                            UtilsFile.rename(ai.Md() + File.separator + item.name, ai.Md() + File.separator + dstMapName);
                        } catch (IOException e) {
                        }
                    }
                }
            }
        });
    }
}
