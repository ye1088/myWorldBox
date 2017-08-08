package com.MCWorld.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.image.Config;
import com.MCWorld.framework.base.image.PipelineView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.List;

public class SliceWallpaper extends LinearLayout {
    private static final int bup = 5000;
    private int buq;
    private int bur;
    private List<String> bus;
    private List<PipelineView> but;
    private Runnable buu;
    private a buv;
    private OnClickListener mClickListener;
    private Handler mHandler;

    public interface a {
        void a(int i, List<String> list);
    }

    public SliceWallpaper(Context context) {
        this(context, null);
    }

    public SliceWallpaper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.buu = new Runnable(this) {
            final /* synthetic */ SliceWallpaper buw;

            {
                this.buw = this$0;
            }

            public void run() {
                if (!UtilsFunction.empty(this.buw.bus) && this.buw.bus.size() >= 4 && this.buw.bur < this.buw.bus.size()) {
                    PipelineView imageView = (PipelineView) this.buw.but.get(this.buw.buq);
                    imageView.setUri(UtilUri.getUriOrNull((String) this.buw.bus.get(this.buw.bur)), Config.defaultConfig(), null);
                    imageView.setTag(Integer.valueOf(this.buw.bur));
                    this.buw.buq = (this.buw.buq + 1) % 3;
                    this.buw.bur = (this.buw.bur + 1) % this.buw.bus.size();
                    this.buw.mHandler.postDelayed(this, 5000);
                }
            }
        };
        this.mClickListener = new OnClickListener(this) {
            final /* synthetic */ SliceWallpaper buw;

            {
                this.buw = this$0;
            }

            public void onClick(View v) {
                if (this.buw.buv != null) {
                    this.buw.buv.a(((Integer) v.getTag()).intValue(), this.buw.bus);
                }
            }
        };
        init(context);
    }

    private void init(Context context) {
        setOrientation(0);
        this.mHandler = new Handler();
        LayoutInflater.from(context).inflate(i.slice_wallpaper, this, true);
        this.but = new ArrayList();
        this.but.add((PipelineView) findViewById(g.iv_slice_1));
        this.but.add((PipelineView) findViewById(g.iv_slice_2));
        this.but.add((PipelineView) findViewById(g.iv_slice_3));
        if (this.but != null) {
            for (View view : this.but) {
                if (view != null) {
                    view.setOnClickListener(this.mClickListener);
                }
            }
        }
    }

    public void c(List<String> urls, boolean restartAnimation) {
        Gi();
        this.bus = urls;
        int i = 0;
        while (i < this.but.size()) {
            PipelineView imageView = (PipelineView) this.but.get(i);
            if (UtilsFunction.empty(this.bus) || i >= this.bus.size()) {
                imageView.setImageResource(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal);
                imageView.setTag(Integer.valueOf(0));
            } else {
                imageView.setTag(Integer.valueOf(i));
                imageView.setUri(UtilUri.getUriOrNull((String) this.bus.get(i)), Config.defaultConfig(), null);
            }
            i++;
        }
        if (restartAnimation) {
            startAnimation();
        }
    }

    public void startAnimation() {
        if (!UtilsFunction.empty(this.bus) && this.bus.size() >= 4) {
            this.buq = 0;
            this.bur = 3;
            this.mHandler.removeCallbacks(this.buu);
            this.mHandler.postDelayed(this.buu, 5000);
        }
    }

    public void Gi() {
        this.mHandler.removeCallbacks(this.buu);
    }

    public void setImageClickListener(a listener) {
        this.buv = listener;
    }
}
