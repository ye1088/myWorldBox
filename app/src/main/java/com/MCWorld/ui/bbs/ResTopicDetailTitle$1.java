package com.MCWorld.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.data.map.f.a;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;
import com.MCWorld.t;

class ResTopicDetailTitle$1 implements OnClickListener {
    final /* synthetic */ a aOC;
    final /* synthetic */ ResTopicDetailTitle aOD;

    ResTopicDetailTitle$1(ResTopicDetailTitle this$0, a aVar) {
        this.aOD = this$0;
        this.aOC = aVar;
    }

    public void onClick(View v) {
        if (this.aOC.studio != null) {
            EventNotifyCenter.notifyEventUiThread(n.class, n.axl, new Object[]{ResTopicDetailTitle.a(this.aOD), Integer.valueOf(this.aOC.studio.id)});
        } else if (this.aOC.user != null) {
            t.f(ResTopicDetailTitle.a(this.aOD), this.aOC.user.userID);
        }
    }
}
