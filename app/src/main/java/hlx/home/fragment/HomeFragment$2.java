package hlx.home.fragment;

import ru.noties.scrollable.j;

class HomeFragment$2 implements j {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$2(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void f(int y, long duration) {
        if (HomeFragment.e(this.bQl) != null) {
            ((ScrollableFragment) HomeFragment.e(this.bQl).getPosFragment(HomeFragment.f(this.bQl).getCurrentItem())).f(y, duration);
        }
    }
}
