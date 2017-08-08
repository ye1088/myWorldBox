package com.MCWorld.ui.itemadapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.TagInfo;

class TagAdapter$1 implements OnClickListener {
    final /* synthetic */ TagAdapter aSy;

    TagAdapter$1(TagAdapter this$0) {
        this.aSy = this$0;
    }

    public void onClick(View view) {
        T data = view.getTag();
        if (data instanceof TagInfo) {
            TagInfo info = (TagInfo) data;
            if (TagAdapter.a(this.aSy) != null) {
                TagAdapter.a(this.aSy).j(info.getID(), info.getName());
            }
            TagAdapter.a(this.aSy, info.getID());
        } else if (data instanceof MyMapCateItem) {
            MyMapCateItem info2 = (MyMapCateItem) data;
            if (TagAdapter.a(this.aSy) != null) {
                TagAdapter.a(this.aSy).j(info2.id, info2.title);
            }
            TagAdapter.a(this.aSy, info2.id);
        }
        this.aSy.notifyDataSetChanged();
    }
}
