package com.huluxia.ui.mctool;

import com.huluxia.data.map.MapProfileInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.t;

class ProfileResFragment$1 extends CallbackHandler {
    final /* synthetic */ ProfileResFragment bbE;

    ProfileResFragment$1(ProfileResFragment this$0) {
        this.bbE = this$0;
    }

    @MessageHandler(message = 528)
    public void onRecvMapProfileData(boolean succ, int resType, int state, MapProfileInfo info, String msg, Object ctx) {
        if (ctx == ProfileResFragment.a(this.bbE)) {
            ProfileResFragment.b(this.bbE).onRefreshComplete();
            ProfileResFragment.c(this.bbE).onLoadComplete();
            this.bbE.cs(false);
            if (resType != ProfileResFragment.d(this.bbE)) {
                return;
            }
            if (ProfileResFragment.e(this.bbE) == null || !succ) {
                ProfileResFragment.c(this.bbE).KN();
                t.n(this.bbE.getActivity(), msg);
                return;
            }
            if (info.start <= 20) {
                ProfileResFragment.a(this.bbE, info);
            } else if (ProfileResFragment.f(this.bbE) != null) {
                ProfileResFragment.f(this.bbE).start = info.start;
                ProfileResFragment.f(this.bbE).more = info.more;
                ProfileResFragment.f(this.bbE).mapList.addAll(info.mapList);
            } else {
                return;
            }
            ProfileResFragment.e(this.bbE).a(ProfileResFragment.f(this.bbE).mapList, state, true);
        }
    }
}
