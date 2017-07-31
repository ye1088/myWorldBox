package com.tencent.smtt.utils;

import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.TextView;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TbsLogClient {
    static TbsLogClient a = null;
    static File c = null;
    TextView b;
    private SimpleDateFormat d = null;

    private class a implements Runnable {
        String a = null;
        final /* synthetic */ TbsLogClient b;

        a(TbsLogClient tbsLogClient, String str) {
            this.b = tbsLogClient;
            this.a = str;
        }

        public void run() {
            if (this.b.b != null) {
                this.b.b.append(this.a + SpecilApiUtil.LINE_SEP);
            }
        }
    }

    public TbsLogClient() {
        try {
            this.d = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS", Locale.US);
        } catch (Exception e) {
            this.d = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        }
        try {
            if (c != null) {
                return;
            }
            if (Environment.getExternalStorageState().equals("mounted")) {
                c = new File(h.a, "tbslog.txt");
            } else {
                c = null;
            }
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        }
    }

    public static TbsLogClient getIntance() {
        if (a == null) {
            a = new TbsLogClient();
        }
        return a;
    }

    public void d(String str, String str2) {
    }

    public void e(String str, String str2) {
        Log.e(str, str2);
    }

    public void i(String str, String str2) {
    }

    public void setLogView(TextView textView) {
        this.b = textView;
    }

    public void showLog(String str) {
        if (this.b != null) {
            this.b.post(new a(this, str));
        }
    }

    public void v(String str, String str2) {
    }

    public void w(String str, String str2) {
    }

    public void writeLog(String str) {
        if (c != null) {
            LogFileUtils.writeDataToStorage(c, this.d.format(Long.valueOf(System.currentTimeMillis())) + " pid=" + Process.myPid() + " " + str + SpecilApiUtil.LINE_SEP, true);
        }
    }
}
