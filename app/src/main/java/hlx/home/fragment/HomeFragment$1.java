package hlx.home.fragment;

import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.framework.base.widget.PagerSlidingIndicator.IndicatorTextSizeChange;

class HomeFragment$1 implements IndicatorTextSizeChange {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$1(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public int getTextSizePx() {
        return UtilsScreen.sp2px(HomeFragment.a(this.bQl), 16.0f);
    }
}
