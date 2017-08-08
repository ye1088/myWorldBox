package com.MCWorld.ui.picture;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.image.base.binaryresource.c;
import com.MCWorld.image.pipeline.core.e;
import com.MCWorld.image.pipeline.core.h;
import com.MCWorld.image.pipeline.request.ImageRequest;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.widget.picviewer.touchgallery.GalleryWidget.GalleryViewPager;
import com.MCWorld.widget.picviewer.touchgallery.GalleryWidget.UrlPagerAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewerActivity extends HTBaseThemeActivity implements OnPageChangeListener, OnClickListener {
    public static final String bdr = "EXTRA_PICTURES";
    public static final String bds = "EXTRA_THUMNAILS";
    public static final String bdt = "EXTRA_ORIENTATION";
    public static final String bdu = "EXTRA_INDEX";
    private TextView bdA;
    private a bdB = new a(this);
    private GalleryViewPager bdv;
    private UrlPagerAdapter bdw;
    private List<String> bdx = new ArrayList();
    private List<String> bdy = new ArrayList();
    private TextView bdz;
    private int mOrientation = 1;

    static class a implements OnClickListener {
        Activity bdC;

        a(Activity _activity) {
            this.bdC = _activity;
        }

        public void onClick(View view) {
            if (this.bdC != null) {
                this.bdC.finish();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(i.activity_picture_viewer2);
        this.bdz = (TextView) findViewById(g.photo_index);
        this.bdA = (TextView) findViewById(g.save_photo);
        this.bdA.setOnClickListener(this);
        this.bdv = (GalleryViewPager) findViewById(g.gallery_pager);
        this.bdx = getIntent().getStringArrayListExtra(bdr);
        this.bdy = getIntent().getStringArrayListExtra(bds);
        Integer index = Integer.valueOf(getIntent().getIntExtra(bdu, 0));
        if (savedInstanceState == null) {
            this.mOrientation = getIntent().getIntExtra(bdt, 1);
        } else {
            this.mOrientation = savedInstanceState.getInt(bdt);
        }
        if (!UtilsFunction.empty(this.bdx)) {
            this.bdw = new UrlPagerAdapter(this, IQ(), this.mOrientation, this.bdB);
            this.bdv.setAdapter(this.bdw);
            this.bdv.setCurrentItem(index.intValue());
            this.bdv.addOnPageChangeListener(this);
            IR();
        }
    }

    private List<Pair<String, String>> IQ() {
        int pSize = UtilsFunction.size(this.bdx);
        int tSize = UtilsFunction.size(this.bdy);
        int length = Math.max(pSize, tSize);
        List<Pair<String, String>> urls = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            String picture = null;
            String thumbnail = null;
            if (i < pSize) {
                picture = (String) this.bdx.get(i);
            }
            if (i < tSize) {
                thumbnail = (String) this.bdy.get(i);
            }
            urls.add(new Pair(picture, thumbnail));
        }
        return urls;
    }

    private void IR() {
        this.bdz.setText(getResources().getString(m.photo_index, new Object[]{Integer.valueOf(this.bdv.getCurrentItem() + 1), Integer.valueOf(this.bdw.getCount())}));
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(bdt, this.mOrientation);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        String tip = "图片已经保存到本地\n " + UtilsFile.KP() + "目录下，可在图库的SaveImage目录下直接查看";
        try {
            String url = (String) this.bdx.get(this.bdv.getCurrentItem());
            e imagePipeline = h.mz().lj();
            com.MCWorld.image.base.binaryresource.a resource = h.mz().mG().d(imagePipeline.lN().c(ImageRequest.bL(url), null));
            if (resource == null) {
                t.n(this, "图片还没有打开");
                return;
            }
            File file = ((c) resource).getFile();
            if (file == null || !file.exists()) {
                t.n(this, "图片保存失败");
                return;
            }
            com.MCWorld.framework.base.utils.UtilsFile.copyFile(file.getAbsolutePath(), UtilsFile.KU() + System.currentTimeMillis() + ".jpeg");
            t.o(this, tip);
        } catch (Exception e) {
            HLog.error("PictureViewerActivity", "save to MediaStore images: " + e, new Object[0]);
            t.n(this, "图片保存失败");
        }
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageSelected(int arg0) {
        IR();
    }
}
