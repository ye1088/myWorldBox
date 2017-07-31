package com.huluxia.ui.profile;

import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.topic.b;
import com.huluxia.t;

class TopicFavorFragment$4 extends CallbackHandler {
    final /* synthetic */ TopicFavorFragment biY;

    TopicFavorFragment$4(TopicFavorFragment this$0) {
        this.biY = this$0;
    }

    @MessageHandler(message = 615)
    public void onRecvFavorTopicList(boolean succ, String start, b info, long userId) {
        if (userId == TopicFavorFragment.d(this.biY)) {
            TopicFavorFragment.e(this.biY).onRefreshComplete();
            if (succ && TopicFavorFragment.f(this.biY) != null && info != null && info.isSucc()) {
                this.biY.FC();
                this.biY.aHb.onLoadComplete();
                TopicFavorFragment.c(this.biY).start = info.start;
                TopicFavorFragment.c(this.biY).more = info.more;
                if (start == null || start.equals("0")) {
                    TopicFavorFragment.c(this.biY).posts.clear();
                    TopicFavorFragment.c(this.biY).posts.addAll(info.posts);
                } else {
                    TopicFavorFragment.c(this.biY).posts.addAll(info.posts);
                }
                TopicFavorFragment.f(this.biY).notifyDataSetChanged();
            } else if (this.biY.getCurrentPage() == 0) {
                this.biY.FB();
            } else {
                this.biY.aHb.KN();
                t.n(this.biY.getActivity(), info == null ? this.biY.getResources().getString(m.loading_failed_please_retry) : info.msg);
            }
        }
    }
}
