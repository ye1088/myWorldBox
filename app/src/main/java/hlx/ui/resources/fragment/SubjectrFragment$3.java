package hlx.ui.resources.fragment;

import com.huluxia.utils.c.a;

class SubjectrFragment$3 implements a {
    final /* synthetic */ SubjectrFragment cgs;

    SubjectrFragment$3(SubjectrFragment this$0) {
        this.cgs = this$0;
    }

    public void onLoadData() {
        if (SubjectrFragment.a(this.cgs) != null) {
            this.cgs.al(SubjectrFragment.b(this.cgs), SubjectrFragment.a(this.cgs).start, SubjectrFragment.c(this.cgs));
        }
    }

    public boolean shouldLoadData() {
        if (SubjectrFragment.a(this.cgs) == null) {
            SubjectrFragment.d(this.cgs).onLoadComplete();
            return false;
        } else if (SubjectrFragment.a(this.cgs).more > 0) {
            return true;
        } else {
            return false;
        }
    }
}
