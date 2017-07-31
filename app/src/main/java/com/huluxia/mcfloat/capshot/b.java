package com.huluxia.mcfloat.capshot;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.mcinterface.h;
import com.huluxia.mcsdk.dtlib.c;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.utils.j;
import com.huluxia.widget.dialog.k;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: MCScreenShotsDlg */
public class b extends Dialog {
    String QD = null;
    private a abp = null;
    private ImageView abq = null;
    private Bitmap abr = null;
    private k abs;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ b abt;

        {
            this.abt = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatCapshotCancel:
                    this.abt.dismiss();
                    r.ck().K(com.huluxia.r.a.lu);
                    return;
                case R.id.floatCapshotSave:
                    this.abt.dismiss();
                    new b().execute(new String[0]);
                    r.ck().K(com.huluxia.r.a.lq);
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: MCScreenShotsDlg */
    public interface a {
        void fC(int i);
    }

    /* compiled from: MCScreenShotsDlg */
    private class b extends AsyncTask<String, Integer, String> {
        final /* synthetic */ b abt;

        private b(b bVar) {
            this.abt = bVar;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            t.m(this.abt.getContext(), "截图成功,正在保存...");
        }

        protected String c(String... params) {
            this.abt.saveBitmap(this.abt.abr, "hlx");
            return null;
        }

        protected void ce(String result) {
            this.abt.abs.cancel();
            UtilsFile.mediaScan(this.abt.getContext(), this.abt.QD);
            t.m(this.abt.getContext(), "截图成功,可在图库的huluxia目录下查看");
        }
    }

    public b(Context context) {
        super(context, R.style.Dialog);
    }

    public void a(a cb) {
        this.abp = cb;
        super.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_dialog_screenshots);
        this.abq = (ImageView) findViewById(R.id.floatImageCapshotView);
        this.abs = new k(getContext());
        this.abs.gL("截图正在保存中,请稍后...");
        uo();
        r.ck().K(com.huluxia.r.a.lr);
        findViewById(R.id.floatCapshotCancel).setOnClickListener(this.mClickListener);
        findViewById(R.id.floatCapshotSave).setOnClickListener(this.mClickListener);
    }

    public void uo() {
        this.abr = h.z(null, 1);
        this.abq.setVisibility(0);
        this.abq.setImageBitmap(this.abr);
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

    public void saveBitmap(Bitmap inputBmp, String prefix) {
        try {
            File f = co(prefix);
            this.QD = f.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(f);
            inputBmp.compress(CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
