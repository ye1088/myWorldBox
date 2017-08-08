package com.MCWorld.widget.photowall;

import android.view.View;
import com.MCWorld.framework.base.widget.hlistview.AdapterView;
import com.MCWorld.framework.base.widget.hlistview.AdapterView.OnItemClickListener;
import com.MCWorld.module.picture.b;

class PhotoWall2$3 implements OnItemClickListener {
    final /* synthetic */ PhotoWall2 bCP;

    PhotoWall2$3(PhotoWall2 this$0) {
        this.bCP = this$0;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (PhotoWall2.a(this.bCP) && position == this.bCP.bCw.getCount() - 1) {
            if (PhotoWall2.b(this.bCP) != null) {
                PhotoWall2.b(this.bCP).Gq();
            }
        } else if (PhotoWall2.b(this.bCP) != null) {
            PhotoWall2.b(this.bCP).a((b) parent.getAdapter().getItem(position), position);
        }
    }
}
