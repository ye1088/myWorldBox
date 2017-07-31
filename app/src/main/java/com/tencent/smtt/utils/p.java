package com.tencent.smtt.utils;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class p {
    private static p e = null;
    public boolean a = false;
    private Context b = null;
    private File c = null;
    private boolean d = false;
    private File f = null;

    private p(Context context) {
        this.b = context.getApplicationContext();
        b();
    }

    public static synchronized p a() {
        p pVar;
        synchronized (p.class) {
            pVar = e;
        }
        return pVar;
    }

    public static synchronized p a(Context context) {
        p pVar;
        synchronized (p.class) {
            if (e == null) {
                e = new p(context);
            }
            pVar = e;
        }
        return pVar;
    }

    private File d() {
        File file;
        try {
            if (this.c == null) {
                this.c = new File(this.b.getDir("tbs", 0), "core_private");
                if (this.c == null || !this.c.isDirectory()) {
                    return null;
                }
            }
            file = new File(this.c, "debug.conf");
            if (!(file == null || file.exists())) {
                file.createNewFile();
            }
        } catch (Throwable th) {
            th.printStackTrace();
            file = null;
        }
        return file;
    }

    public void a(boolean z) {
        this.d = z;
        c();
    }

    public synchronized void b() {
        FileInputStream fileInputStream;
        Throwable th;
        FileInputStream fileInputStream2 = null;
        try {
            if (this.f == null) {
                this.f = d();
            }
            if (this.f == null) {
                try {
                    fileInputStream2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                fileInputStream = new FileInputStream(this.f);
                try {
                    Properties properties = new Properties();
                    properties.load(fileInputStream);
                    String property = properties.getProperty("setting_forceUseSystemWebview", "");
                    if (!"".equals(property)) {
                        this.a = Boolean.parseBoolean(property);
                    }
                    try {
                        fileInputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream.close();
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = fileInputStream2;
            fileInputStream.close();
            throw th;
        }
    }

    public void c() {
        Throwable th;
        OutputStream outputStream;
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        FileInputStream fileInputStream = null;
        try {
            File d = d();
            if (d == null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    fileOutputStream2.close();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            Properties properties;
            OutputStream fileOutputStream3;
            fileInputStream = new FileInputStream(d);
            try {
                properties = new Properties();
                properties.load(fileInputStream);
                properties.setProperty("setting_forceUseSystemWebview", Boolean.toString(this.a));
                properties.setProperty("result_systemWebviewForceUsed", Boolean.toString(this.d));
                fileOutputStream3 = new FileOutputStream(d);
            } catch (Throwable th2) {
                th = th2;
                try {
                    th.printStackTrace();
                    try {
                        fileInputStream.close();
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        fileInputStream.close();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (Exception e32) {
                        e32.printStackTrace();
                    }
                    throw th;
                }
            }
            try {
                properties.store(fileOutputStream3, null);
                try {
                    fileInputStream.close();
                } catch (Exception e2222) {
                    e2222.printStackTrace();
                }
                try {
                    fileOutputStream3.close();
                } catch (Exception e22222) {
                    e22222.printStackTrace();
                }
            } catch (Throwable th4) {
                th = th4;
                outputStream = fileOutputStream3;
                fileInputStream.close();
                fileOutputStream.close();
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            fileInputStream = null;
            fileInputStream.close();
            fileOutputStream.close();
            throw th;
        }
    }
}
