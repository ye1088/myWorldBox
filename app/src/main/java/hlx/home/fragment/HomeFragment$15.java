package hlx.home.fragment;

import ru.noties.scrollable.b;

class HomeFragment$15 implements b {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$15(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public boolean canScrollVertically(int direction) {
        if (HomeFragment.e(this.bQl) != null) {
            return HomeFragment.e(this.bQl).bk(HomeFragment.f(this.bQl).getCurrentItem(), direction);
        }
        return false;
    }
}
