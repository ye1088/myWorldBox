package hlx.ui.resources.fragment;

import com.MCWorld.utils.c.a;

class ResourceRecommendFragment$2 implements a {
    final /* synthetic */ ResourceRecommendFragment cgl;

    ResourceRecommendFragment$2(ResourceRecommendFragment this$0) {
        this.cgl = this$0;
    }

    public void onLoadData() {
        if (ResourceRecommendFragment.a(this.cgl) != null) {
            this.cgl.al(ResourceRecommendFragment.b(this.cgl), ResourceRecommendFragment.a(this.cgl).start, 20);
        }
    }

    public boolean shouldLoadData() {
        if (ResourceRecommendFragment.a(this.cgl) == null) {
            ResourceRecommendFragment.c(this.cgl).onLoadComplete();
            return false;
        } else if (ResourceRecommendFragment.a(this.cgl).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
