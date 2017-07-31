package hlx.ui.resources.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class ResourceRecommendFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ResourceRecommendFragment cgl;

    ResourceRecommendFragment$1(ResourceRecommendFragment this$0) {
        this.cgl = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.cgl.reload();
    }
}
