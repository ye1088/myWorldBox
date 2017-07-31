package com.huluxia.widget.banner;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import com.huluxia.framework.base.utils.UtilsFunction;

class BannerGallery$1 implements OnItemSelectedListener {
    final /* synthetic */ BannerGallery bvu;

    BannerGallery$1(BannerGallery this$0) {
        this.bvu = this$0;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (UtilsFunction.empty(BannerGallery.a(this.bvu).getData()) || BannerGallery.b(this.bvu) != null) {
            View vChild = BannerGallery.b(this.bvu).getChildAt(position % BannerGallery.a(this.bvu).getData().size());
            if (vChild != null) {
                BannerGallery.b(this.bvu).check(vChild.getId());
            }
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
