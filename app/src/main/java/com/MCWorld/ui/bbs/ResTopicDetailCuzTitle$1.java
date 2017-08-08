package com.MCWorld.ui.bbs;

import android.widget.ImageView.ScaleType;
import com.MCWorld.bbs.b.f;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.l;
import com.MCWorld.widget.banner.SimpleImageAdapter.a;

class ResTopicDetailCuzTitle$1 implements a {
    final /* synthetic */ ResTopicDetailCuzTitle aOk;

    ResTopicDetailCuzTitle$1(ResTopicDetailCuzTitle this$0) {
        this.aOk = this$0;
    }

    public void a(String imageUrl, PaintView imageView) {
        imageView.setUri(UtilUri.getUriOrNull(imageUrl)).scaleType(ScaleType.CENTER_CROP).placeHolder(f.loading_grey).fadeDuration(0).setImageLoader(l.cb().getImageLoader());
    }
}
