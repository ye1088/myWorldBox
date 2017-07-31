package hlx.ui.resources.fragment;

import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

class SubjectrFragment$2 implements OnRefreshListener<ListView> {
    final /* synthetic */ SubjectrFragment cgs;

    SubjectrFragment$2(SubjectrFragment this$0) {
        this.cgs = this$0;
    }

    public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.cgs.reload();
    }
}
