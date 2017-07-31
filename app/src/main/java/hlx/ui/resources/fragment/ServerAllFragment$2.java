package hlx.ui.resources.fragment;

import com.huluxia.utils.c.a;

class ServerAllFragment$2 implements a {
    final /* synthetic */ ServerAllFragment cgo;

    ServerAllFragment$2(ServerAllFragment this$0) {
        this.cgo = this$0;
    }

    public void onLoadData() {
        if (ServerAllFragment.b(this.cgo) != null) {
            ServerAllFragment.c(this.cgo);
        }
    }

    public boolean shouldLoadData() {
        if (ServerAllFragment.b(this.cgo) == null) {
            ServerAllFragment.d(this.cgo).onLoadComplete();
            return false;
        } else if (ServerAllFragment.b(this.cgo).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
