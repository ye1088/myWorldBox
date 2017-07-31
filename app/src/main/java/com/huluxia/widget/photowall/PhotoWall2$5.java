package com.huluxia.widget.photowall;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout.LayoutParams;
import com.huluxia.framework.base.utils.UtilsScreen;

class PhotoWall2$5 implements OnGlobalLayoutListener {
    final /* synthetic */ PhotoWall2 bCP;

    PhotoWall2$5(PhotoWall2 this$0) {
        this.bCP = this$0;
    }

    @TargetApi(16)
    public void onGlobalLayout() {
        LayoutParams lp = (LayoutParams) this.bCP.bCv.getLayoutParams();
        lp.height = UtilsScreen.dipToPx(this.bCP.getContext(), 16) + PhotoWall2.h(this.bCP);
        this.bCP.bCv.setLayoutParams(lp);
        if (VERSION.SDK_INT >= 16) {
            this.bCP.bCv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            this.bCP.bCv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
