package hlx.ui.resources.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class ServerAllFragment$1 implements OnRefreshListener<ListView> {
    final /* synthetic */ ServerAllFragment cgo;

    ServerAllFragment$1(ServerAllFragment this$0) {
        this.cgo = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        ServerAllFragment.a(this.cgo);
    }
}
