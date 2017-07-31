package hlx.home.fragment;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import ru.noties.scrollable.ScrollableLayout;

class HomeFragment$13 implements OnRefreshListener<ScrollableLayout> {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$13(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void onRefresh(PullToRefreshBase<ScrollableLayout> pullToRefreshBase) {
        HomeFragment.c(this.bQl);
    }
}
