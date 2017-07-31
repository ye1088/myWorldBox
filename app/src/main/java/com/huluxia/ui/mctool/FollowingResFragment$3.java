package com.huluxia.ui.mctool;

import com.huluxia.data.map.f;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.t;

class FollowingResFragment$3 extends CallbackHandler {
    final /* synthetic */ FollowingResFragment baW;

    FollowingResFragment$3(FollowingResFragment this$0) {
        this.baW = this$0;
    }

    @MessageHandler(message = 803)
    public void onRecvMapListInfo(int tag, boolean succ, f info) {
        FollowingResFragment.e(this.baW).onRefreshComplete();
        FollowingResFragment.d(this.baW).onLoadComplete();
        if (tag == FollowingResFragment.f(this.baW)) {
            if (succ && info != null) {
                if (info.start <= 20) {
                    FollowingResFragment.a(this.baW, info);
                    if (UtilsFunction.empty(info.mapList)) {
                        FollowingResFragment.g(this.baW).setVisibility(0);
                    } else {
                        FollowingResFragment.g(this.baW).setVisibility(8);
                    }
                } else if (FollowingResFragment.b(this.baW) != null) {
                    FollowingResFragment.b(this.baW).start = info.start;
                    FollowingResFragment.b(this.baW).more = info.more;
                    FollowingResFragment.b(this.baW).mapList.addAll(info.mapList);
                } else {
                    return;
                }
                FollowingResFragment.h(this.baW).a(FollowingResFragment.b(this.baW).mapList, true);
                this.baW.FC();
            } else if (info != null) {
                t.n(FollowingResFragment.i(this.baW), info.msg);
            } else if (FollowingResFragment.b(this.baW) == null) {
                this.baW.FB();
            }
        }
    }
}
