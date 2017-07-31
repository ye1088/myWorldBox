package com.huluxia.widget.photowall;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.huluxia.framework.base.utils.UtilsScreen;

class PhotoWall2$2 implements OnGlobalLayoutListener {
    final /* synthetic */ PhotoWall2 bCP;

    PhotoWall2$2(PhotoWall2 this$0) {
        this.bCP = this$0;
    }

    @TargetApi(16)
    public void onGlobalLayout() {
        if (PhotoWall2.c(this.bCP)) {
            PhotoWall2.a(this.bCP, (this.bCP.getMeasuredWidth() / PhotoWall2.d(this.bCP)) - UtilsScreen.dipToPx(PhotoWall2.e(this.bCP), 12));
            PhotoWall2.b(this.bCP, (int) (((float) PhotoWall2.f(this.bCP)) * PhotoWall2.g(this.bCP)));
            this.bCP.bCy.notifyDataSetChanged();
            if (VERSION.SDK_INT >= 16) {
                this.bCP.bCx.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                this.bCP.bCx.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        }
    }
}
