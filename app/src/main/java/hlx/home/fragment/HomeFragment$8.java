package hlx.home.fragment;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import com.huluxia.framework.R;
import com.huluxia.framework.base.widget.pager.PagerFragment;

class HomeFragment$8 extends ScrollablePageAdapter {
    final /* synthetic */ HomeFragment bQl;
    String[] bQm = HomeFragment.a(this.bQl).getResources().getStringArray(R.array.home_recommend_list);

    HomeFragment$8(HomeFragment this$0, FragmentManager fm) {
        this.bQl = this$0;
        super(fm);
    }

    public /* synthetic */ PagerFragment getItem(int i) {
        return mE(i);
    }

    public ScrollableFragment mE(int position) {
        switch (position) {
            case 0:
                return MapJsWoodSkinFragment.mF(1);
            case 1:
                return MapJsWoodSkinFragment.mF(2);
            case 2:
                return MapJsWoodSkinFragment.mF(4);
            case 3:
                return MapJsWoodSkinFragment.mF(3);
            case 4:
                return MapJsWoodSkinFragment.mF(5);
            case 5:
                return MapJsWoodSkinFragment.mF(6);
            default:
                return MapJsWoodSkinFragment.mF(7);
        }
    }

    public CharSequence getPageTitle(int position) {
        return this.bQm[position];
    }

    public int getCount() {
        return this.bQm.length;
    }

    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
        if (8 == HomeFragment.d(this.bQl).getVisibility()) {
            HomeFragment.d(this.bQl).setVisibility(0);
        }
    }
}
