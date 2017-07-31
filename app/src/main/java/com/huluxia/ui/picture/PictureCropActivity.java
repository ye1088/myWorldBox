package com.huluxia.ui.picture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsFile;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsImage;
import com.huluxia.framework.base.widget.cropimage.CropImageView;
import com.huluxia.image.core.datasource.c;
import com.huluxia.image.pipeline.core.e;
import com.huluxia.image.pipeline.request.ImageRequestBuilder;
import com.huluxia.module.h;
import com.huluxia.module.picture.b;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import com.huluxia.widget.dialog.k;
import com.huluxia.widget.photowall.PhotoWall2;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PictureCropActivity extends HTBaseActivity {
    private static final String TAG = "PictureCropActivity";
    public static final String beh = "EXTRA_PHOTOS";
    public static final String bei = "EXTRA_SAVED_PATHS";
    public static final String bej = "EXTRA_OUTPUT_X";
    public static final String bek = "EXTRA_OUTPUT_Y";
    public static final String bel = "EXTRA_ASPECT_X";
    public static final String bem = "EXTRA_ASPECT_Y";
    public static final String ben = "EXTRA_CURRENT_IDX";
    private int aRo = h.arp;
    private int aRp = h.arp;
    private k aRr;
    private CropImageView beA;
    private CropImageView beB;
    private Set<Integer> beC = new HashSet();
    private PhotoWall2 beo;
    private List<b> bep = new ArrayList();
    private int beq;
    private int ber;
    private int bes = -1;
    private CropImageView bet;
    private CropImageView beu;
    private CropImageView bev;
    private CropImageView bew;
    private CropImageView bex;
    private CropImageView bey;
    private CropImageView bez;
    private c<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> mDataSource;
    private com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b> mImage;

    private class a extends AsyncTask<Integer, Integer, String> {
        final /* synthetic */ PictureCropActivity beD;
        private ArrayList<String> beG;

        private a(PictureCropActivity pictureCropActivity) {
            this.beD = pictureCropActivity;
            this.beG = new ArrayList();
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return b((Integer[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.beD.aRr.show();
            this.beD.aIT.setEnabled(false);
        }

        protected String b(Integer... params) {
            int i = 0;
            while (i < this.beD.bep.size()) {
                try {
                    Bitmap src = null;
                    if (i == 0) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.bet.isRectChanged()) {
                            src = this.beD.bet.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 1) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.beu.isRectChanged()) {
                            src = this.beD.beu.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 2) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.bev.isRectChanged()) {
                            src = this.beD.bev.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 3) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.bew.isRectChanged()) {
                            src = this.beD.bew.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 4) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.bex.isRectChanged()) {
                            src = this.beD.bex.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 5) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.bey.isRectChanged()) {
                            src = this.beD.bey.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 6) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.bez.isRectChanged()) {
                            src = this.beD.bez.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 7) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.beA.isRectChanged()) {
                            src = this.beD.beA.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    if (i == 8) {
                        if (this.beD.beC.contains(Integer.valueOf(i)) && this.beD.beB.isRectChanged()) {
                            src = this.beD.beB.getCroppedImage();
                        } else {
                            this.beG.add(((b) this.beD.bep.get(i)).localPath);
                            i++;
                        }
                    }
                    String outputPath = UtilsFile.getTempFileName(i);
                    this.beG.add(outputPath);
                    File f = new File(outputPath);
                    if (f.exists()) {
                        f.delete();
                    }
                    Bitmap dest = UtilsImage.ScaleBitmap(src, this.beD.aRo, this.beD.aRp);
                    FileOutputStream out = new FileOutputStream(f);
                    dest.compress(CompressFormat.JPEG, 85, out);
                    out.flush();
                    out.close();
                    dest.recycle();
                    src.recycle();
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void ce(String result) {
            this.beD.aRr.cancel();
            this.beD.aIT.setEnabled(true);
            Intent intent = new Intent();
            intent.putExtra(PictureCropActivity.bei, this.beG);
            this.beD.setResult(-1, intent);
            this.beD.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_picture_crop);
        Intent intent = getIntent();
        this.bep = intent.getParcelableArrayListExtra("EXTRA_PHOTOS");
        this.aRo = intent.getIntExtra(bej, h.arp);
        this.aRp = intent.getIntExtra(bek, h.arp);
        this.beq = intent.getIntExtra(bel, 0);
        this.ber = intent.getIntExtra(bem, 0);
        this.bes = intent.getIntExtra(ben, 0);
        EP();
        this.beo = (PhotoWall2) findViewById(g.photowall);
        this.beo.e(this.bep, true);
        this.beo.setItemClickListener(new PhotoWall2.b(this) {
            final /* synthetic */ PictureCropActivity beD;

            {
                this.beD = this$0;
            }

            public void a(b unit, int position) {
                HLog.info(PictureCropActivity.TAG, "unit id(%d) position(%d)", Long.valueOf(unit.id), Integer.valueOf(position));
                this.beD.b(unit, position);
                this.beD.bes = position;
                this.beD.ej(this.beD.getString(m.photo_index, new Object[]{Integer.valueOf(this.beD.bes + 1), Integer.valueOf(this.beD.bep.size())}));
            }

            public void Gq() {
            }
        });
        this.beo.dp(false);
        this.aRr = new k(this);
        int minWidth = (int) (((double) at.getScreenWidth(this)) * 0.8d);
        this.bet = (CropImageView) findViewById(g.CropImageView0);
        this.bet.setAspectRatio(1, 1);
        this.bet.setFixedAspectRatio(false);
        this.bet.setMinSize(minWidth);
        this.beu = (CropImageView) findViewById(g.CropImageView1);
        this.beu.setAspectRatio(1, 1);
        this.beu.setFixedAspectRatio(false);
        this.beu.setMinSize(minWidth);
        this.bev = (CropImageView) findViewById(g.CropImageView2);
        this.bev.setAspectRatio(1, 1);
        this.bev.setFixedAspectRatio(false);
        this.bev.setMinSize(minWidth);
        this.bew = (CropImageView) findViewById(g.CropImageView3);
        this.bew.setAspectRatio(1, 1);
        this.bew.setFixedAspectRatio(false);
        this.bew.setMinSize(minWidth);
        this.bex = (CropImageView) findViewById(g.CropImageView4);
        this.bex.setAspectRatio(1, 1);
        this.bex.setFixedAspectRatio(false);
        this.bex.setMinSize(minWidth);
        this.bey = (CropImageView) findViewById(g.CropImageView5);
        this.bey.setAspectRatio(1, 1);
        this.bey.setFixedAspectRatio(false);
        this.bey.setMinSize(minWidth);
        this.bez = (CropImageView) findViewById(g.CropImageView6);
        this.bez.setAspectRatio(1, 1);
        this.bez.setFixedAspectRatio(false);
        this.bez.setMinSize(minWidth);
        this.beA = (CropImageView) findViewById(g.CropImageView7);
        this.beA.setAspectRatio(1, 1);
        this.beA.setFixedAspectRatio(false);
        this.beA.setMinSize(minWidth);
        this.beB = (CropImageView) findViewById(g.CropImageView8);
        this.beB.setAspectRatio(1, 1);
        this.beB.setFixedAspectRatio(false);
        this.beB.setMinSize(minWidth);
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PictureCropActivity beD;

            {
                this.beD = this$0;
            }

            public void onClick(View v) {
                this.beD.Jf();
            }
        });
        b((b) this.bep.get(this.bes), this.bes);
    }

    protected void onDestroy() {
        super.onDestroy();
        closeDataSource();
        closeImage();
    }

    private void EP() {
        this.aIs.setVisibility(8);
        this.aIT.setText(hlx.data.localstore.a.bKC);
        this.aIT.setVisibility(0);
        if (UtilsFunction.empty(this.bep)) {
            this.aIT.setEnabled(false);
            return;
        }
        ej(getString(m.photo_index, new Object[]{Integer.valueOf(this.bes + 1), Integer.valueOf(this.bep.size())}));
    }

    private void closeDataSource() {
        if (this.mDataSource != null) {
            this.mDataSource.close();
            this.mDataSource = null;
        }
    }

    private void closeImage() {
        if (this.mImage != null) {
            this.mImage.close();
            this.mImage = null;
        }
    }

    private void b(b unit, int position) {
        this.bet.setVisibility(8);
        this.beu.setVisibility(8);
        this.bev.setVisibility(8);
        this.bew.setVisibility(8);
        this.bex.setVisibility(8);
        this.bey.setVisibility(8);
        this.bez.setVisibility(8);
        this.beA.setVisibility(8);
        this.beB.setVisibility(8);
        switch (position) {
            case 0:
                a(unit, position, this.bet);
                return;
            case 1:
                a(unit, position, this.beu);
                return;
            case 2:
                a(unit, position, this.bev);
                return;
            case 3:
                a(unit, position, this.bew);
                return;
            case 4:
                a(unit, position, this.bex);
                return;
            case 5:
                a(unit, position, this.bey);
                return;
            case 6:
                a(unit, position, this.bez);
                return;
            case 7:
                a(unit, position, this.beA);
                return;
            case 8:
                a(unit, position, this.beB);
                return;
            default:
                return;
        }
    }

    private void a(b unit, int position, CropImageView imageView) {
        if (!UtilsFunction.empty(unit.localPath)) {
            b(unit, position, imageView);
        } else if (!UtilsFunction.empty(unit.url)) {
            a(unit.url, position, imageView);
        }
    }

    private void a(String url, final int position, final CropImageView imageView) {
        this.aRr.show();
        final boolean contain = this.beC.contains(Integer.valueOf(position));
        e imagePipeline = com.huluxia.image.pipeline.core.h.mz().lj();
        ImageRequestBuilder builder = ImageRequestBuilder.w(UtilUri.parseUriOrNull(url));
        closeDataSource();
        this.mDataSource = imagePipeline.f(builder.pM(), null);
        this.mDataSource.a(new com.huluxia.image.core.datasource.b<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>>(this) {
            final /* synthetic */ PictureCropActivity beD;

            protected void onNewResultImpl(c<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> dataSource) {
                this.beD.mImage = (com.huluxia.image.core.common.references.a) dataSource.getResult();
                if (!(this.beD.mImage == null || this.beD.mImage.get() == null)) {
                    imageView.setImageBitmap(((com.huluxia.image.base.imagepipeline.image.a) this.beD.mImage.get()).hM(), null, !contain);
                    imageView.setVisibility(0);
                    if (!contain) {
                        this.beD.beC.add(Integer.valueOf(position));
                    }
                }
                this.beD.aRr.cancel();
            }

            protected void onFailureImpl(c<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> cVar) {
                this.beD.aRr.cancel();
            }

            public void onProgressUpdate(c<com.huluxia.image.core.common.references.a<com.huluxia.image.base.imagepipeline.image.b>> cVar) {
            }
        }, com.huluxia.image.core.common.executors.g.ir());
    }

    private void b(b unit, int position, CropImageView imageView) {
        if (this.beC.contains(Integer.valueOf(position))) {
            imageView.setImageFile(unit.localPath, false);
        } else {
            this.beC.add(Integer.valueOf(position));
            imageView.setImageFile(unit.localPath, true);
        }
        imageView.setVisibility(0);
    }

    private void Jf() {
        new a().execute(new Integer[]{Integer.valueOf(0)});
    }
}
