package hlx.ui.resources.fragment;

import com.MCWorld.utils.c.a;

class ServerRecommendFragment$2 implements a {
    final /* synthetic */ ServerRecommendFragment cgp;

    ServerRecommendFragment$2(ServerRecommendFragment this$0) {
        this.cgp = this$0;
    }

    public void onLoadData() {
        if (ServerRecommendFragment.b(this.cgp) != null) {
            ServerRecommendFragment.c(this.cgp);
        }
    }

    public boolean shouldLoadData() {
        if (ServerRecommendFragment.b(this.cgp) == null) {
            ServerRecommendFragment.d(this.cgp).onLoadComplete();
            return false;
        } else if (ServerRecommendFragment.b(this.cgp).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
