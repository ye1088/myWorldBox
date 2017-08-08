package hlx.ui.resources.fragment;

import com.MCWorld.utils.c.a;

class ResourceRankingFragment$2 implements a {
    final /* synthetic */ ResourceRankingFragment cgk;

    ResourceRankingFragment$2(ResourceRankingFragment this$0) {
        this.cgk = this$0;
    }

    public void onLoadData() {
        if (ResourceRankingFragment.a(this.cgk) != null) {
            this.cgk.al(ResourceRankingFragment.b(this.cgk), ResourceRankingFragment.a(this.cgk).start, 20);
        }
    }

    public boolean shouldLoadData() {
        if (ResourceRankingFragment.a(this.cgk) == null) {
            ResourceRankingFragment.c(this.cgk).onLoadComplete();
            return false;
        } else if (ResourceRankingFragment.a(this.cgk).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
