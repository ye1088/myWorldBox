package com.huluxia.ui.picture;

import com.huluxia.framework.base.utils.SpeedScrollListener;
import com.huluxia.l;

class PictureChooserFragment$4 extends SpeedScrollListener {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$4(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    public void onFling() {
        l.cb().getImageLoader().pauseTag(this.bea.getActivity());
    }

    public void onFlingStop() {
        l.cb().getImageLoader().resumeTag(this.bea.getActivity());
    }
}
