package com.MCWorld.studio.fragment;

import android.widget.ImageView.ScaleType;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.l;
import com.MCWorld.widget.banner.SimpleImageAdapter.a;

class RecommendFragment$3 implements a {
    final /* synthetic */ RecommendFragment aGD;

    RecommendFragment$3(RecommendFragment this$0) {
        this.aGD = this$0;
    }

    public void a(String imageUrl, PaintView imageView) {
        imageView.setUri(UtilUri.getUriOrNull(imageUrl)).scaleType(ScaleType.CENTER_CROP).placeHolder(R.drawable.loading_grey).fadeDuration(0).setImageLoader(l.cb().getImageLoader());
    }
}
