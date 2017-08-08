package com.MCWorld.ui.profile;

import com.MCWorld.bbs.b.m;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.profile.i;
import com.MCWorld.t;

class SpaceRecommendStyleFragment$4 extends CallbackHandler {
    final /* synthetic */ SpaceRecommendStyleFragment bhz;

    SpaceRecommendStyleFragment$4(SpaceRecommendStyleFragment this$0) {
        this.bhz = this$0;
    }

    @MessageHandler(message = 625)
    public void onRecvSpaceStyleList(boolean succ, i info) {
        SpaceRecommendStyleFragment.c(this.bhz).onRefreshComplete();
        if (succ) {
            if (info.start > 20) {
                SpaceRecommendStyleFragment.d(this.bhz).start = info.start;
                SpaceRecommendStyleFragment.d(this.bhz).more = info.more;
                SpaceRecommendStyleFragment.d(this.bhz).spacelist.addAll(info.spacelist);
            } else {
                SpaceRecommendStyleFragment.a(this.bhz, info);
            }
            SpaceRecommendStyleFragment.b(this.bhz).setData(SpaceRecommendStyleFragment.d(this.bhz).spacelist);
            SpaceRecommendStyleFragment.f(this.bhz).FC();
        } else if (SpaceRecommendStyleFragment.f(this.bhz).getCurrentPage() == 0) {
            SpaceRecommendStyleFragment.f(this.bhz).FB();
        } else {
            t.n(this.bhz.getActivity(), this.bhz.getResources().getString(m.loading_failed_please_retry));
        }
    }
}
