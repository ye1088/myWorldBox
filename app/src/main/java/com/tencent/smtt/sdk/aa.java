package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import com.MCWorld.module.s;
import com.tencent.smtt.utils.TbsLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class aa {
    private static aa b = null;
    private static final Lock c = new ReentrantLock();
    private static final Lock d = new ReentrantLock();
    private static Handler e = null;
    private static final Long[][] f;
    private static boolean g = false;
    int a = 0;

    static {
        r0 = new Long[7][];
        r0[0] = new Long[]{Long.valueOf(25413), Long.valueOf(11460320)};
        r0[1] = new Long[]{Long.valueOf(25436), Long.valueOf(12009376)};
        r0[2] = new Long[]{Long.valueOf(25437), Long.valueOf(11489180)};
        r0[3] = new Long[]{Long.valueOf(25438), Long.valueOf(11489180)};
        r0[4] = new Long[]{Long.valueOf(25439), Long.valueOf(12013472)};
        r0[5] = new Long[]{Long.valueOf(25440), Long.valueOf(11489180)};
        r0[6] = new Long[]{Long.valueOf(25442), Long.valueOf(11489180)};
        f = r0;
    }

    private aa() {
    }

    static aa a() {
        if (b == null) {
            b = new aa();
        }
        return b;
    }

    static File a(Context context, String str) {
        File g = g(context);
        if (g == null) {
            return null;
        }
        File file = new File(g, str);
        if (file != null && file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static FileLock a(Context context, FileOutputStream fileOutputStream) {
        if (fileOutputStream == null) {
            return null;
        }
        try {
            FileLock tryLock = fileOutputStream.getChannel().tryLock();
            return tryLock.isValid() ? tryLock : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static void a(FileLock fileLock, FileOutputStream fileOutputStream) {
        if (fileLock != null) {
            try {
                fileLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static File g(Context context) {
        File file = new File(context.getDir("tbs", 0), "core_private");
        return file != null ? (file.isDirectory() || file.mkdir()) ? file : null : null;
    }

    static FileOutputStream h(Context context) {
        File a = a(context, "tbslock.txt");
        if (a != null) {
            try {
                return new FileOutputStream(a);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    File a(Context context, Context context2) {
        File file = new File(context2.getDir("tbs", 0), "core_share");
        return file != null ? (file.isDirectory() || ((context != null && TbsShareManager.isThirdPartyApp(context)) || file.mkdir())) ? file : null : null;
    }

    void a(Context context, boolean z) {
    }

    boolean a(Context context) {
        Throwable th;
        Throwable th2;
        boolean z = false;
        File file = new File(e(context), "tbs.conf");
        if (file != null && file.exists()) {
            Properties properties = new Properties();
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    properties.load(fileInputStream);
                    z = Boolean.valueOf(properties.getProperty("tbs_local_installation", "false")).booleanValue();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        th.printStackTrace();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return z;
                    } catch (Throwable th4) {
                        th2 = th4;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                th2 = th5;
                fileInputStream = null;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th2;
            }
        }
        return z;
    }

    boolean a(Context context, int i) {
        return false;
    }

    int b(Context context) {
        FileInputStream fileInputStream;
        Exception e;
        Throwable th;
        int i = 0;
        FileInputStream fileInputStream2 = null;
        try {
            File file = new File(e(context), "tbs.conf");
            if (file == null || !file.exists()) {
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e2) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e2.toString());
                    }
                }
                return i;
            }
            Properties properties = new Properties();
            fileInputStream = new FileInputStream(file);
            try {
                properties.load(fileInputStream);
                fileInputStream.close();
                String property = properties.getProperty("tbs_core_version");
                if (property != null) {
                    i = Integer.parseInt(property);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e22) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e22.toString());
                        }
                    }
                } else if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e222) {
                        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e222.toString());
                    }
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock Exception=" + e.toString());
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e2222) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e2222.toString());
                        }
                    }
                    return i;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e22222) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock IOException=" + e22222.toString());
                        }
                    }
                    throw th;
                }
            }
            return i;
        } catch (Exception e4) {
            e = e4;
            fileInputStream = fileInputStream2;
            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVerInNolock Exception=" + e.toString());
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return i;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = fileInputStream2;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw th;
        }
    }

    boolean c(Context context) {
        File file = new File(e(context), "tbs.conf");
        return file != null && file.exists();
    }

    int d(Context context) {
        FileInputStream fileInputStream;
        Exception e;
        Throwable th;
        FileOutputStream h = h(context);
        if (h == null) {
            return -1;
        }
        FileLock a = a(context, h);
        if (a == null) {
            return -1;
        }
        boolean tryLock = c.tryLock();
        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer locked=" + tryLock);
        if (tryLock) {
            FileInputStream fileInputStream2 = null;
            try {
                File file = new File(e(context), "tbs.conf");
                if (file == null || !file.exists()) {
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e2) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer IOException=" + e2.toString());
                        }
                    }
                    c.unlock();
                    a(a, h);
                    return 0;
                }
                Properties properties = new Properties();
                fileInputStream = new FileInputStream(file);
                try {
                    properties.load(fileInputStream);
                    fileInputStream.close();
                    String property = properties.getProperty("tbs_core_version");
                    if (property == null) {
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e22) {
                                TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer IOException=" + e22.toString());
                            }
                        }
                        c.unlock();
                        a(a, h);
                        return 0;
                    }
                    this.a = Integer.parseInt(property);
                    int i = this.a;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e3) {
                            TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer IOException=" + e3.toString());
                        }
                    }
                    c.unlock();
                    a(a, h);
                    return i;
                } catch (Exception e4) {
                    e = e4;
                    try {
                        TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer Exception=" + e.toString());
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e222) {
                                TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer IOException=" + e222.toString());
                            }
                        }
                        c.unlock();
                        a(a, h);
                        return 0;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e32) {
                                TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer IOException=" + e32.toString());
                            }
                        }
                        c.unlock();
                        a(a, h);
                        throw th;
                    }
                }
            } catch (Exception e5) {
                e = e5;
                fileInputStream = fileInputStream2;
                TbsLog.i("TbsInstaller", "TbsInstaller--getTbsCoreInstalledVer Exception=" + e.toString());
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                c.unlock();
                a(a, h);
                return 0;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                c.unlock();
                a(a, h);
                throw th;
            }
        }
        a(a, h);
        return 0;
    }

    File e(Context context) {
        return a(null, context);
    }

    File f(Context context) {
        File file = new File(context.getDir("tbs", 0), s.SHARE);
        return file != null ? (file.isDirectory() || file.mkdir()) ? file : null : null;
    }
}
