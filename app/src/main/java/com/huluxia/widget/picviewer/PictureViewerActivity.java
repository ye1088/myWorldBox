package com.huluxia.widget.picviewer;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.image.base.binaryresource.a;
import com.huluxia.image.base.binaryresource.c;
import com.huluxia.image.pipeline.core.e;
import com.huluxia.image.pipeline.core.h;
import com.huluxia.image.pipeline.request.ImageRequest;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.UtilsFile;
import com.huluxia.widget.picviewer.touchgallery.GalleryWidget.GalleryViewPager;
import com.huluxia.widget.picviewer.touchgallery.GalleryWidget.UrlPagerAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PictureViewerActivity extends HTBaseActivity implements OnPageChangeListener, OnClickListener {
    private GalleryViewPager bCY;
    private UrlPagerAdapter bCZ;
    private String[] bDa = null;
    private List<String> items = new ArrayList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_picture_viewer);
        this.aIs.setVisibility(8);
        this.bCY = (GalleryViewPager) findViewById(g.vpBody);
        this.bDa = getIntent().getStringArrayExtra("urlArray");
        Integer index = Integer.valueOf(getIntent().getIntExtra("index", 0));
        if (this.bDa != null) {
            Collections.addAll(this.items, this.bDa);
            this.bCZ = new UrlPagerAdapter(this, this.items);
            this.bCY.setAdapter(this.bCZ);
            this.bCY.setCurrentItem(index.intValue());
            this.bCY.addOnPageChangeListener(this);
            ej(String.format(Locale.getDefault(), "%d / %d", new Object[]{Integer.valueOf(this.bCY.getCurrentItem() + 1), Integer.valueOf(this.bCZ.getCount())}));
        }
        this.aIQ.setImageResource(f.btn_main_save_selector);
        this.aIQ.setVisibility(0);
        this.aIQ.setOnClickListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        String tip = "图片已经保存到本地\n " + UtilsFile.KP() + "目录下，可在图库的SaveImage目录下直接查看";
        try {
            String url = this.bDa[this.bCY.getCurrentItem()];
            e imagePipeline = h.mz().lj();
            a resource = h.mz().mG().d(imagePipeline.lN().c(ImageRequest.bL(url), null));
            if (resource == null) {
                t.n(this, "图片还没有打开");
                return;
            }
            File file = ((c) resource).getFile();
            if (file == null || !file.exists()) {
                t.n(this, "图片保存失败");
                return;
            }
            com.huluxia.framework.base.utils.UtilsFile.copyFile(file.getAbsolutePath(), UtilsFile.KU() + System.currentTimeMillis() + ".jpeg");
            t.o(this, tip);
        } catch (Exception e) {
            HLog.error("PictureViewerActivity", "save to MediaStore images " + e, new Object[0]);
            t.n(this, "图片保存失败");
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (state == 0) {
            ej(String.format(Locale.getDefault(), "%d / %d", new Object[]{Integer.valueOf(this.bCY.getCurrentItem() + 1), Integer.valueOf(this.bCZ.getCount())}));
        }
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    public void onPageSelected(int arg0) {
    }
}
