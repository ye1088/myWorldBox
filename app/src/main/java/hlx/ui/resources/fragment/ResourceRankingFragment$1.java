package hlx.ui.resources.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class ResourceRankingFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ResourceRankingFragment cgk;

    ResourceRankingFragment$1(ResourceRankingFragment this$0) {
        this.cgk = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.cgk.reload();
    }
}
