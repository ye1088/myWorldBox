package com.huluxia.widget.photowall;

import android.view.View;
import android.view.View.OnClickListener;

class PhotoWall2$6 implements OnClickListener {
    final /* synthetic */ PhotoWall2 bCP;

    PhotoWall2$6(PhotoWall2 this$0) {
        this.bCP = this$0;
    }

    public void onClick(View v) {
        Object object = v.getTag();
        if (object != null) {
            try {
                PhotoWall2.c(this.bCP, Integer.parseInt(object.toString()));
            } catch (NumberFormatException e) {
            }
        }
    }
}
