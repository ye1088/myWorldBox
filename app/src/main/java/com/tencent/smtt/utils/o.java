package com.tencent.smtt.utils;

import android.annotation.TargetApi;
import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

public class o {
    private static o c = null;
    private Context a = null;
    private File b = null;
    private String d = "http://log.tbs.qq.com/ajax?c=pu&v=2&k_dialog_class=";
    private String e = "http://wup.imtt.qq.com:8080";
    private String f = "http://log.tbs.qq.com/ajax?c=dl&k_dialog_class=";
    private String g = "http://cfg.imtt.qq.com/tbs?v=2&mk=";
    private String h = "http://log.tbs.qq.com/ajax?c=ul&v=2&k_dialog_class=";
    private String i = "http://mqqad.html5.qq.com/adjs";
    private String j = "http://log.tbs.qq.com/ajax?c=ucfu&k_dialog_class=";

    @TargetApi(11)
    private o(Context context) {
        TbsLog.w("TbsCommonConfig", "TbsCommonConfig constructing...");
        this.a = context.getApplicationContext();
        e();
    }

    public static synchronized o a() {
        o oVar;
        synchronized (o.class) {
            oVar = c;
        }
        return oVar;
    }

    public static synchronized o a(Context context) {
        o oVar;
        synchronized (o.class) {
            if (c == null) {
                c = new o(context);
            }
            oVar = c;
        }
        return oVar;
    }

    private synchronized void e() {
        try {
            File f = f();
            if (f == null) {
                TbsLog.e("TbsCommonConfig", "Config file is null, default values will be applied");
            } else {
                InputStream fileInputStream = new FileInputStream(f);
                Properties properties = new Properties();
                properties.load(fileInputStream);
                String property = properties.getProperty("pv_post_url", "");
                if (!"".equals(property)) {
                    this.d = property;
                }
                property = properties.getProperty("wup_proxy_domain", "");
                if (!"".equals(property)) {
                    this.e = property;
                }
                property = properties.getProperty("tbs_download_stat_post_url", "");
                if (!"".equals(property)) {
                    this.f = property;
                }
                property = properties.getProperty("tbs_downloader_post_url", "");
                if (!"".equals(property)) {
                    this.g = property;
                }
                property = properties.getProperty("tbs_log_post_url", "");
                if (!"".equals(property)) {
                    this.h = property;
                }
                property = properties.getProperty("tips_url", "");
                if (!"".equals(property)) {
                    this.i = property;
                }
                String property2 = properties.getProperty("tbs_cmd_post_url", "");
                if (!"".equals(property2)) {
                    this.j = property2;
                }
                fileInputStream.close();
            }
        } catch (Throwable th) {
            Writer stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            TbsLog.e("TbsCommonConfig", "exceptions occurred1:" + stringWriter.toString());
        }
    }

    private File f() {
        Throwable th;
        Writer stringWriter;
        File file;
        try {
            if (this.b == null) {
                this.b = new File(h.a(this.a, 4));
                if (this.b == null || !this.b.isDirectory()) {
                    return null;
                }
            }
            file = new File(this.b, "tbsnet.conf");
            if (file.exists()) {
                try {
                    TbsLog.w("TbsCommonConfig", "pathc:" + file.getCanonicalPath());
                    return file;
                } catch (Throwable th2) {
                    th = th2;
                    stringWriter = new StringWriter();
                    th.printStackTrace(new PrintWriter(stringWriter));
                    TbsLog.e("TbsCommonConfig", "exceptions occurred2:" + stringWriter.toString());
                    return file;
                }
            }
            TbsLog.e("TbsCommonConfig", "Get file(" + file.getCanonicalPath() + ") failed!");
            return null;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            file = null;
            th = th4;
            stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            TbsLog.e("TbsCommonConfig", "exceptions occurred2:" + stringWriter.toString());
            return file;
        }
    }

    public String b() {
        return this.d;
    }

    public String c() {
        return this.g;
    }

    public String d() {
        return this.i;
    }
}
