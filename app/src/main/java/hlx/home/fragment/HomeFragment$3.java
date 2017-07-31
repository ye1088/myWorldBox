package hlx.home.fragment;

import android.os.Build.VERSION;
import ru.noties.scrollable.k;

class HomeFragment$3 implements k {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$3(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void ad(int y, int oldY, int maxY) {
        float tabsTranslationY;
        if (y < maxY) {
            tabsTranslationY = 0.0f;
        } else {
            tabsTranslationY = (float) (y - maxY);
        }
        if (VERSION.SDK_INT >= 11) {
            HomeFragment.d(this.bQl).setTranslationY(tabsTranslationY);
            HomeFragment.g(this.bQl).setTranslationY(tabsTranslationY);
        }
    }
}
