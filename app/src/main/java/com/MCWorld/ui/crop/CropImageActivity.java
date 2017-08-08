package com.MCWorld.ui.crop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.utils.UtilsImage;
import com.MCWorld.module.h;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.widget.cropimage.CropImageView;
import com.MCWorld.widget.dialog.k_dialog_class;

import java.io.File;
import java.io.FileOutputStream;

public class CropImageActivity extends HTBaseActivity {
    private String aRl;
    private String aRm;
    private CropImageView aRn;
    private int aRo = h.arp;
    private int aRp = h.arp;
    private boolean aRq = true;
    private k_dialog_class aRr;

    private class a extends AsyncTask<Bitmap, Integer, String> {
        final /* synthetic */ CropImageActivity aRs;

        private a(CropImageActivity cropImageActivity) {
            this.aRs = cropImageActivity;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((Bitmap[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.aRs.aRr.show();
            this.aRs.aIT.setEnabled(false);
        }

        protected String a(Bitmap... params) {
            try {
                File f = new File(this.aRs.aRm);
                if (f.exists()) {
                    f.delete();
                }
                Bitmap bm = UtilsImage.ScaleBitmap(params[0], this.aRs.aRo, this.aRs.aRp);
                FileOutputStream out = new FileOutputStream(f);
                bm.compress(CompressFormat.JPEG, 85, out);
                out.flush();
                out.close();
                bm.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void ce(String result) {
            this.aRs.aRr.cancel();
            this.aRs.aIT.setEnabled(true);
            Intent bintent = new Intent();
            bintent.putExtra("outputPath", this.aRs.aRm);
            this.aRs.setResult(-1, bintent);
            this.aRs.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_cropimage);
        this.aRr = new k_dialog_class(this);
        setTitle("图片裁剪");
        this.aIs.setVisibility(8);
        this.aIT.setText(hlx.data.localstore.a.bKC_bt_ok);
        this.aIT.setVisibility(0);
        this.aRn = (CropImageView) findViewById(g.CropImageView);
        Intent cropIntent = getIntent();
        this.aRl = cropIntent.getStringExtra("fromPath");
        this.aRm = cropIntent.getStringExtra("outputPath");
        this.aRo = cropIntent.getIntExtra("outputX", h.arp);
        this.aRp = cropIntent.getIntExtra("outputY", h.arp);
        this.aRq = cropIntent.getBooleanExtra("fixedAspectRatio", true);
        this.aRn.setAspectRatio(1, 1);
        this.aRn.setFixedAspectRatio(this.aRq);
        this.aRn.setImageFile(this.aRl);
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CropImageActivity aRs;

            {
                this.aRs = this$0;
            }

            public void onClick(View v) {
                Bitmap bm = this.aRs.aRn.getCroppedImage();
                new a().execute(new Bitmap[]{bm});
            }
        });
    }
}
