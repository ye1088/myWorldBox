package com.huluxia.ui.bbs;

import android.widget.ImageView.ScaleType;
import com.huluxia.bbs.b.f;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.l;
import com.huluxia.widget.banner.SimpleImageAdapter.a;

class ResTopicDetailCuzTitle$1 implements a {
    final /* synthetic */ ResTopicDetailCuzTitle aOk;

    ResTopicDetailCuzTitle$1(ResTopicDetailCuzTitle this$0) {
        this.aOk = this$0;
    }

    public void a(String imageUrl, PaintView imageView) {
        imageView.setUri(UtilUri.getUriOrNull(imageUrl)).scaleType(ScaleType.CENTER_CROP).placeHolder(f.loading_grey).fadeDuration(0).setImageLoader(l.cb().getImageLoader());
    }
}
