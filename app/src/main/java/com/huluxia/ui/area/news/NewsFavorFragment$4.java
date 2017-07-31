package com.huluxia.ui.area.news;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.news.k;
import com.huluxia.t;

class NewsFavorFragment$4 extends CallbackHandler {
    final /* synthetic */ NewsFavorFragment aIb;

    NewsFavorFragment$4(NewsFavorFragment this$0) {
        this.aIb = this$0;
    }

    @MessageHandler(message = 1031)
    public void onRecvFavorTopicList(boolean succ, long userId, k info) {
        if (userId == NewsFavorFragment.e(this.aIb)) {
            NewsFavorFragment.f(this.aIb).onRefreshComplete();
            if (!succ || NewsFavorFragment.g(this.aIb) == null) {
                NewsFavorFragment.d(this.aIb).KN();
                if (this.aIb.getCurrentPage() == 0) {
                    this.aIb.FB();
                    return;
                } else {
                    t.n(this.aIb.getActivity(), (info != null ? info.msg : "数据请求失败") + ",请下拉刷新重试");
                    return;
                }
            }
            this.aIb.FC();
            NewsFavorFragment.d(this.aIb).onLoadComplete();
            if (info.start > 20) {
                NewsFavorFragment.c(this.aIb).start = info.start;
                NewsFavorFragment.c(this.aIb).more = info.more;
                NewsFavorFragment.c(this.aIb).list.addAll(info.list);
            } else {
                NewsFavorFragment.a(this.aIb, info);
            }
            NewsFavorFragment.g(this.aIb).a(NewsFavorFragment.c(this.aIb).list, true);
            NewsFavorFragment.g(this.aIb).notifyDataSetChanged();
        }
    }
}
