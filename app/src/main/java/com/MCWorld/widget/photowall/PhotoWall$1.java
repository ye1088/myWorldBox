package com.MCWorld.widget.photowall;

import com.MCWorld.widget.dialog.n;
import com.MCWorld.widget.dialog.n.a;
import com.MCWorld.widget.dialog.o;

class PhotoWall$1 implements a {
    final /* synthetic */ n aYQ;
    final /* synthetic */ PhotoWall bCq;
    final /* synthetic */ int val$index;

    PhotoWall$1(PhotoWall this$0, n nVar, int i) {
        this.bCq = this$0;
        this.aYQ = nVar;
        this.val$index = i;
    }

    public void a(o m) {
        if (((Integer) m.getTag()).intValue() == 1) {
            this.aYQ.dismiss();
            PhotoWall.a(this.bCq).remove(this.val$index);
            this.bCq.PE();
        }
    }
}
