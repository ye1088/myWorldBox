package com.huawei.android.pushselfshow.richpush.tools;

import android.content.Context;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a;
import java.io.File;
import java.io.FileOutputStream;

public class c {
    private String a;
    private Context b;

    public c(Context context, String str) {
        this.a = str;
        this.b = context;
    }

    private String b() {
        return "﻿<!DOCTYPE html>\t\t<html>\t\t   <head>\t\t     <meta charset=\"utf-8\">\t\t     <title></title>\t\t     <style type=\"text/css\">\t\t\t\t html { height:100%;}\t\t\t\t body { height:100%; text-align:center;}\t    \t    .centerDiv { display:inline-block; zoom:1; *display:inline; vertical-align:top; text-align:left; width:200px; padding:10px;margin-top:100px;}\t\t\t   .hiddenDiv { height:100%; overflow:hidden; display:inline-block; width:1px; overflow:hidden; margin-left:-1px; zoom:1; *display:inline; *margin-top:-1px; _margin-top:0; vertical-align:middle;}\t\t  \t</style>    \t  </head>\t\t <body>\t\t\t<div id =\"container\" class=\"centerDiv\">";
    }

    private String c() {
        return "﻿\t\t</div>  \t\t<div class=\"hiddenDiv\"></div>\t  </body>   </html>";
    }

    public String a() {
        Throwable e;
        FileOutputStream fileOutputStream;
        Throwable e2;
        FileOutputStream fileOutputStream2 = null;
        if (this.b == null) {
            e.d("PushSelfShowLog", "CreateHtmlFile fail ,context is null");
            return null;
        }
        String str = b() + this.a + c();
        String str2 = this.b.getFilesDir().getPath() + File.separator + "PushService" + File.separator + "richpush";
        String str3 = "error.html";
        File file = new File(str2);
        File file2 = new File(str2 + File.separator + str3);
        try {
            if (!file.exists()) {
                e.a("PushSelfShowLog", "Create the path:" + str2);
                if (!file.mkdirs()) {
                    e.a("PushSelfShowLog", "!path.mkdirs()");
                    if (null == null) {
                        return null;
                    }
                    try {
                        fileOutputStream2.close();
                        return null;
                    } catch (Throwable e3) {
                        e.a("PushSelfShowLog", "stream.close() error ", e3);
                        return null;
                    }
                }
            }
            if (file2.exists()) {
                a.a(file2);
            }
            e.a("PushSelfShowLog", "Create the file:" + str3);
            if (file2.createNewFile()) {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    fileOutputStream.write(str.getBytes("UTF-8"));
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable e22) {
                            e.a("PushSelfShowLog", "stream.close() error ", e22);
                        }
                    }
                    return file2.getAbsolutePath();
                } catch (Exception e4) {
                    e3 = e4;
                    try {
                        e.a("PushSelfShowLog", "Create html error ", e3);
                        if (fileOutputStream != null) {
                            return null;
                        }
                        try {
                            fileOutputStream.close();
                            return null;
                        } catch (Throwable e32) {
                            e.a("PushSelfShowLog", "stream.close() error ", e32);
                            return null;
                        }
                    } catch (Throwable th) {
                        e22 = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable e322) {
                                e.a("PushSelfShowLog", "stream.close() error ", e322);
                            }
                        }
                        throw e22;
                    }
                }
            }
            e.a("PushSelfShowLog", "!file.createNewFile()");
            if (null == null) {
                return null;
            }
            try {
                fileOutputStream2.close();
                return null;
            } catch (Throwable e3222) {
                e.a("PushSelfShowLog", "stream.close() error ", e3222);
                return null;
            }
        } catch (Exception e5) {
            e3222 = e5;
            fileOutputStream = null;
            e.a("PushSelfShowLog", "Create html error ", e3222);
            if (fileOutputStream != null) {
                return null;
            }
            fileOutputStream.close();
            return null;
        } catch (Throwable e32222) {
            fileOutputStream = null;
            e22 = e32222;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw e22;
        }
    }
}
