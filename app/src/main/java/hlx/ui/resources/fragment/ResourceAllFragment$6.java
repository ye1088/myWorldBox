package hlx.ui.resources.fragment;

import android.widget.CompoundButton;
import com.huluxia.framework.R;
import com.huluxia.r;
import com.huluxia.widget.a.a;

class ResourceAllFragment$6 implements a {
    final /* synthetic */ ResourceAllFragment cgj;

    ResourceAllFragment$6(ResourceAllFragment this$0) {
        this.cgj = this$0;
    }

    public void a(CompoundButton button, boolean chedked) {
        int id = button.getId();
        if (chedked) {
            ResourceAllFragment.a(this.cgj, id);
            switch (id) {
                case R.id.all_tag:
                    this.cgj.cbD = 1;
                    if (ResourceAllFragment.i(this.cgj) != 1) {
                        ResourceAllFragment.j(this.cgj).setVisibility(0);
                        break;
                    }
                    break;
                default:
                    this.cgj.cbD = 2;
                    hlx.module.resources.a cate = (hlx.module.resources.a) button.getTag();
                    ResourceAllFragment.a(this.cgj, cate.cateid);
                    r.ck().K(hlx.data.tongji.a.bMv + ResourceAllFragment.k(this.cgj) + "_" + String.valueOf(cate.cateid));
                    if (ResourceAllFragment.i(this.cgj) != 1) {
                        ResourceAllFragment.j(this.cgj).setVisibility(0);
                        break;
                    }
                    break;
            }
            ResourceAllFragment.l(this.cgj).setVisibility(0);
            ResourceAllFragment.h(this.cgj).HA();
            ResourceAllFragment.a(this.cgj, null);
            ResourceAllFragment.e(this.cgj).Fy();
            this.cgj.reload();
        }
    }
}
