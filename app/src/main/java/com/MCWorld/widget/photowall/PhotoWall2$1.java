package com.MCWorld.widget.photowall;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.MCWorld.module.picture.b;

class PhotoWall2$1 implements OnItemClickListener {
    final /* synthetic */ PhotoWall2 bCP;

    PhotoWall2$1(PhotoWall2 this$0) {
        this.bCP = this$0;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (PhotoWall2.a(this.bCP) && position == 0) {
            if (PhotoWall2.b(this.bCP) != null) {
                PhotoWall2.b(this.bCP).Gq();
            }
        } else if (PhotoWall2.b(this.bCP) != null) {
            PhotoWall2.b(this.bCP).a((b) parent.getAdapter().getItem(position), position - (PhotoWall2.a(this.bCP) ? 1 : 0));
        }
    }
}
