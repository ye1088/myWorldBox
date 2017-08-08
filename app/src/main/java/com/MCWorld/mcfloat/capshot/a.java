package com.MCWorld.mcfloat.capshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.view.WindowManager.LayoutParams;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mcsdk.dtlib.c;
import com.MCWorld.module.n;
import com.MCWorld.t;
import com.MCWorld.utils.j;
import com.mojang.minecraftpe.MainActivity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: MCScreenShots */
public class a {
    private static a abl;
    private String QD;
    private MainActivity QO = null;
    private Bitmap abm;
    private com.MCWorld.mcfloat.capshot.b.a abn = new com.MCWorld.mcfloat.capshot.b.a(this) {
        final /* synthetic */ a abo;

        {
            this.abo = this$0;
        }

        public void fC(int inputVal) {
        }
    };

    /* compiled from: MCScreenShots */
    private class a extends AsyncTask<String, Integer, String> {
        final /* synthetic */ a abo;

        private a(a aVar) {
            this.abo = aVar;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            t.m(this.abo.QO, "正在保存...");
        }

        protected String c(String... params) {
            this.abo.saveBitmap(this.abo.abm, "hlx");
            return null;
        }

        protected void ce(String result) {
            UtilsFile.mediaScan(this.abo.QO, this.abo.QD);
            t.m(this.abo.QO, "截图成功,可在图库的huluxia目录下查看");
        }
    }

    public static synchronized a un() {
        a aVar;
        synchronized (a.class) {
            if (abl == null) {
                abl = new a();
            }
            aVar = abl;
        }
        return aVar;
    }

    public void aP(Context context) {
        b dlg = new b(context);
        dlg.a(this.abn);
        this.QO = (MainActivity) h.zu().get();
        if (this.QO != null) {
            int screenWidth = this.QO.getWindow().getDecorView().getWidth();
            int screenHeight = this.QO.getWindow().getDecorView().getHeight();
            if (screenWidth <= n.avC) {
                LayoutParams params = dlg.getWindow().getAttributes();
                params.width = (int) (((float) ((int) ((500.0f * ((float) screenWidth)) / 1280.0f))) * com.MCWorld.mcfloat.a.qu());
                params.height = (int) (((float) ((int) ((360.0f * ((float) screenHeight)) / 720.0f))) * com.MCWorld.mcfloat.a.qu());
                dlg.getWindow().setAttributes(params);
            }
        }
    }

    public void aQ(Context context) {
        this.QO = (MainActivity) h.zu().get();
        this.abm = h.z(null, 1);
        new a().execute(new String[0]);
    }

    public void saveBitmap(Bitmap inputBmp, String prefix) {
        if (inputBmp != null) {
            try {
                File f = co(prefix);
                this.QD = f.getAbsolutePath();
                FileOutputStream out = new FileOutputStream(f);
                if (out != null) {
                    inputBmp.compress(CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private File co(String prefix) {
        String picturesFolder = j.KV();
        c.dP(picturesFolder);
        String currentTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.US).format(new Date());
        File retFile = new File(picturesFolder, prefix + "-" + currentTime + hlx.data.localstore.a.bKa);
        int postFix = 1;
        while (retFile.exists()) {
            postFix++;
            retFile = new File(picturesFolder, prefix + "-" + currentTime + "_" + postFix + hlx.data.localstore.a.bKa);
        }
        return retFile;
    }
}
