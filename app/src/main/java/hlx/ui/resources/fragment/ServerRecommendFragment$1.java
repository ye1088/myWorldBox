package hlx.ui.resources.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class ServerRecommendFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ServerRecommendFragment cgp;

    ServerRecommendFragment$1(ServerRecommendFragment this$0) {
        this.cgp = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        ServerRecommendFragment.a(this.cgp);
    }
}
