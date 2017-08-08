package hlx.ui.resources.fragment;

import com.MCWorld.utils.c.a;

class ResourceAllFragment$2 implements a {
    final /* synthetic */ ResourceAllFragment cgj;

    ResourceAllFragment$2(ResourceAllFragment this$0) {
        this.cgj = this$0;
    }

    public void onLoadData() {
        if (ResourceAllFragment.a(this.cgj) != null) {
            this.cgj.al(ResourceAllFragment.b(this.cgj), ResourceAllFragment.a(this.cgj).start, 20);
        }
    }

    public boolean shouldLoadData() {
        if (ResourceAllFragment.a(this.cgj) == null) {
            ResourceAllFragment.c(this.cgj).onLoadComplete();
            return false;
        } else if (ResourceAllFragment.a(this.cgj).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
