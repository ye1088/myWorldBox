package hlx.ui.resources.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class ResourceAllFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ResourceAllFragment cgj;

    ResourceAllFragment$1(ResourceAllFragment this$0) {
        this.cgj = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.cgj.reload();
    }
}
