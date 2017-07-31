package hlx.home.fragment;

import android.widget.ImageView.ScaleType;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.l;
import com.huluxia.widget.banner.SimpleImageAdapter.a;

class HomeFragment$4 implements a {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$4(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void a(String imageUrl, PaintView imageView) {
        imageView.setUri(UtilUri.getUriOrNull(imageUrl)).scaleType(ScaleType.CENTER_CROP).placeHolder(R.drawable.loading_grey).fadeDuration(0).setImageLoader(l.cb().getImageLoader());
    }
}
