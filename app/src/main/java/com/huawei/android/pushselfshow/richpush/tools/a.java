package com.huawei.android.pushselfshow.richpush.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.Toast;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushselfshow.utils.a.c;
import com.huawei.android.pushselfshow.utils.d;
import com.tencent.connect.common.Constants;
import java.io.File;
import org.apache.tools.ant.taskdefs.XSLTLiaison;

public class a {
    public Resources a;
    public Activity b;
    private com.huawei.android.pushselfshow.b.a c = null;

    public a(Activity activity) {
        this.b = activity;
        this.a = activity.getResources();
    }

    public void a() {
        try {
            e.a("PushSelfShowLog", "creat shortcut");
            Intent intent = new Intent();
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            Parcelable decodeResource = BitmapFactory.decodeResource(this.b.getResources(), d.f(this.b, "hwpush_main_icon"));
            intent.putExtra("android.intent.extra.shortcut.NAME", this.b.getResources().getString(d.a(this.b, "hwpush_msg_collect")));
            intent.putExtra("android.intent.extra.shortcut.ICON", decodeResource);
            intent.putExtra("duplicate", false);
            Object intent2 = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent2.putExtra("type", "favorite");
            intent2.addFlags(1476395008);
            String str = "com.huawei.android.pushagent";
            if (com.huawei.android.pushselfshow.utils.a.b(this.b, str)) {
                intent2.setPackage(str);
            } else {
                intent2.setPackage(this.b.getPackageName());
            }
            intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
            this.b.sendBroadcast(intent);
        } catch (Throwable e) {
            e.d("PushSelfShowLog", "creat shortcut error", e);
        }
    }

    public void a(com.huawei.android.pushselfshow.b.a aVar) {
        this.c = aVar;
    }

    public void b() {
        try {
            if (this.c == null || this.c.C == null) {
                Toast.makeText(this.b, com.huawei.android.pushselfshow.utils.a.a(this.b, "内容保存失败", "Save Failed"), 0).show();
                return;
            }
            e.e("PushSelfShowLog", "the rpl is " + this.c.C);
            String str = "";
            str = this.c.C.startsWith(XSLTLiaison.FILE_PROTOCOL_PREFIX) ? this.c.C.substring(7) : this.c.C;
            e.e("PushSelfShowLog", "filePath is " + str);
            if ("text/html_local".equals(this.c.E)) {
                File parentFile = new File(str).getParentFile();
                if (parentFile != null && parentFile.isDirectory() && this.c.C.contains("richpush")) {
                    str = parentFile.getAbsolutePath();
                    String replace = str.replace("richpush", "shotcut");
                    e.b("PushSelfShowLog", "srcDir is %s ,destDir is %s", new Object[]{str, replace});
                    if (com.huawei.android.pushselfshow.utils.a.a(str, replace)) {
                        this.c.C = Uri.fromFile(new File(replace + File.separator + "index.html")).toString();
                    } else {
                        Toast.makeText(this.b, com.huawei.android.pushselfshow.utils.a.a(this.b, "内容保存失败", "Save Failed"), 0).show();
                        return;
                    }
                }
            }
            e.a("PushSelfShowLog", "insert data into db");
            a();
            boolean a = c.a(this.b, this.c.q, this.c);
            e.e("PushSelfShowLog", "insert result is " + a);
            if (a) {
                com.huawei.android.pushselfshow.utils.a.a(this.b, Constants.VIA_REPORT_TYPE_MAKE_FRIEND, this.c);
            } else {
                e.d("PushSelfShowLog", "save icon fail");
            }
        } catch (Throwable e) {
            e.c("PushSelfShowLog", "SaveBtnClickListener error ", e);
        }
    }
}
