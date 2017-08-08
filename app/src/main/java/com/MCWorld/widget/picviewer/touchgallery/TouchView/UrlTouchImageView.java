package com.MCWorld.widget.picviewer.touchgallery.TouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b.f;
import com.MCWorld.framework.base.image.Config;
import com.MCWorld.framework.base.image.PipelineView.Callback;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.image.base.imagepipeline.common.d;
import com.MCWorld.utils.y;
import java.io.File;

public class UrlTouchImageView extends RelativeLayout {
    protected TextView bDT;
    protected TouchImageView bDU;
    private OnClickListener bDj;
    protected Context mContext;

    public UrlTouchImageView(Context ctx) {
        super(ctx);
        this.mContext = ctx;
        init();
    }

    public UrlTouchImageView(Context ctx, OnClickListener onClickListener) {
        super(ctx);
        this.mContext = ctx;
        init();
        if (this.bDU != null && onClickListener != null) {
            this.bDU.setOnClickListener(onClickListener);
        }
    }

    public UrlTouchImageView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        this.mContext = ctx;
        init();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public TouchImageView getImageView() {
        return this.bDU;
    }

    protected void init() {
        this.bDU = new TouchImageView(this.mContext);
        this.bDU.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.bDU);
        this.bDT = new TextView(getContext());
        int padding = UtilsScreen.dipToPx(getContext(), 10);
        this.bDT.setPadding(padding, padding, padding, padding);
        this.bDT.setTextSize(30.0f);
        this.bDT.setTextColor(-1);
        this.bDT.setBackgroundResource(f.pop_pic_bg);
        this.bDT.setVisibility(8);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.addRule(13);
        addView(this.bDT, lp);
    }

    public void setUrl(String imageUrl) {
        e(imageUrl, null, 1);
    }

    public void e(String imageUrl, String thumbUrl, int orientation) {
        if (UtilsFunction.empty(imageUrl)) {
            Drawable drawable = this.mContext.getResources().getDrawable(f.err_holder_normal);
            Bitmap bitmap = y.v(drawable);
            if (bitmap != null) {
                this.bDU.setImageBitmap(bitmap);
                return;
            } else {
                this.bDU.setImageDrawable(drawable);
                return;
            }
        }
        Uri uri;
        if (UtilUri.isNetworkUri(UtilUri.parseUriOrNull(imageUrl))) {
            uri = UtilUri.parseUriOrNull(imageUrl);
        } else {
            uri = UtilUri.getLocalFileUriOrNull(new File(imageUrl));
        }
        this.bDT.setVisibility(0);
        this.bDT.setText(" 0 %");
        d options = d.hB();
        Config config = Config.defaultConfig();
        config.highQualityAnimated = true;
        config.autoAnimated = true;
        this.bDU.setUri(uri, UtilUri.parseUriOrNull(thumbUrl), null, options, config, new Callback(this) {
            final /* synthetic */ UrlTouchImageView bDV;

            {
                this.bDV = this$0;
            }

            public void onSucc(Bitmap bitmap) {
                this.bDV.bDT.setVisibility(8);
            }

            public void onFailed() {
                this.bDV.bDT.setVisibility(8);
            }

            public void onProgressUpdate(float progress) {
                this.bDV.bDT.setText(" " + ((int) (100.0f * progress)) + "%");
            }
        });
    }

    public void setScaleType(ScaleType scaleType) {
        this.bDU.setScaleType(scaleType);
    }

    public void setProgressTextColor(int color) {
        this.bDT.setTextColor(color);
    }
}
