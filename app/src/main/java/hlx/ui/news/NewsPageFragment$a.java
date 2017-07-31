package hlx.ui.news;

import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.t;
import hlx.module.news.a;
import hlx.module.news.b;
import hlx.module.news.c;
import java.lang.ref.WeakReference;

class NewsPageFragment$a extends CallbackHandler {
    WeakReference<NewsPageFragment> aSc;

    NewsPageFragment$a(NewsPageFragment fragment) {
        this.aSc = new WeakReference(fragment);
    }

    @MessageHandler(message = 2821)
    public void onRecvMcNews(boolean succ, c info) {
        NewsPageFragment fragment = (NewsPageFragment) this.aSc.get();
        if (fragment != null) {
            NewsPageFragment.a(fragment).onRefreshComplete();
            NewsPageFragment.b(fragment).onLoadComplete();
            if (!succ || info == null) {
                b headerInfo = new b();
                headerInfo.bigList.add(new a("local:story", 1, 1, ""));
                headerInfo.bigList.add(new a("local:story", 1, 1, ""));
                headerInfo.topList.add(new a("local:story", 1, 1, ""));
                headerInfo.topList.add(new a("local:story", 1, 1, ""));
                headerInfo.bottomList.add(new a("local:story", 1, 1, ""));
                headerInfo.bottomList.add(new a("local:story", 1, 1, ""));
                NewsPageFragment.a(fragment, headerInfo);
                fragment.FC();
                t.n(fragment.getActivity(), "网络不给力,请下拉刷新");
                return;
            }
            if (info.start <= 20) {
                NewsPageFragment.a(fragment, info);
            } else if (NewsPageFragment.c(fragment) != null) {
                NewsPageFragment.c(fragment).start = info.start;
                NewsPageFragment.c(fragment).more = info.more;
                NewsPageFragment.c(fragment).newsList.addAll(info.newsList);
            } else {
                return;
            }
            NewsPageFragment.d(fragment).setData(NewsPageFragment.c(fragment).newsList);
            fragment.FC();
        }
    }

    @MessageHandler(message = 2822)
    public void onRecvNewsHeader(boolean succ, b info) {
        NewsPageFragment fragment = (NewsPageFragment) this.aSc.get();
        if (fragment != null && succ && info != null) {
            NewsPageFragment.a(fragment, info);
        }
    }
}
