package hlx.home.fragment;

import android.support.v4.app.FragmentManager;
import com.MCWorld.framework.base.widget.pager.PagerSelectedAdapter;

public abstract class ScrollablePageAdapter extends PagerSelectedAdapter<ScrollableFragment> {
    public ScrollablePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public boolean bk(int position, int direction) {
        ScrollableFragment fragment = (ScrollableFragment) getPosFragment(position);
        return fragment != null ? fragment.canScrollVertically(direction) : false;
    }
}
