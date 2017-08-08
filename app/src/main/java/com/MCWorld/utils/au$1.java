package com.MCWorld.utils;

import android.graphics.Bitmap;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.widget.dialog.CommonShareDialog.CommonShareDialogAdapter.CommonShareDialogListener;
import com.MCWorld.service.j;
import com.MCWorld.service.k;

/* compiled from: UtilsShare */
class au$1 implements CommonShareDialogListener {
    final /* synthetic */ au bmZ;

    au$1(au this$0) {
        this.bmZ = this$0;
    }

    public void pressMenuById(int inIndex, Object object, Object param) {
        TopicItem topicItem = (TopicItem) object;
        String shareurl = (String) param;
        this.bmZ.bmW.dismissDialog();
        switch (inIndex) {
            case 1:
                Bitmap bitmap;
                if (ad.empty(topicItem.getImages())) {
                    bitmap = null;
                } else {
                    bitmap = au.O(this.bmZ.aTg, (String) topicItem.getImages().get(0));
                }
                k.bc(this.bmZ.aTg).b(topicItem.getTitle(), aw.W(topicItem.getDetail(), 30), shareurl, bitmap, false);
                return;
            case 2:
                j.m(this.bmZ.aTg).a(topicItem, shareurl);
                return;
            case 3:
                j.m(this.bmZ.aTg).b(topicItem, shareurl);
                return;
            default:
                return;
        }
    }
}
