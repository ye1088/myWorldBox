package com.huluxia.widget.photowall;

import android.content.Context;
import com.huluxia.framework.base.widget.hlistview.AbsHListView;
import com.huluxia.framework.base.widget.hlistview.AbsHListView$OnScrollListener;
import com.huluxia.l;

class PhotoWall2$4 implements AbsHListView$OnScrollListener {
    final /* synthetic */ PhotoWall2 bCP;
    final /* synthetic */ Context val$context;

    PhotoWall2$4(PhotoWall2 this$0, Context context) {
        this.bCP = this$0;
        this.val$context = context;
    }

    public void onScrollStateChanged(AbsHListView view, int scrollState) {
        if (scrollState == 1 || scrollState == 2) {
            l.cb().getImageLoader().pauseTag(this.val$context);
        } else {
            l.cb().getImageLoader().resumeTag(this.val$context);
        }
    }

    public void onScroll(AbsHListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}
