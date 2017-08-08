package hlx.ui.resources.fragment;

import android.widget.CompoundButton;
import com.MCWorld.framework.R;
import com.MCWorld.widget.a.a;
import hlx.module.resources.f;

class ServerAllFragment$6 implements a {
    final /* synthetic */ ServerAllFragment cgo;

    ServerAllFragment$6(ServerAllFragment this$0) {
        this.cgo = this$0;
    }

    public void a(CompoundButton button, boolean chedked) {
        int id = button.getId();
        if (chedked) {
            ServerAllFragment.a(this.cgo, id);
            switch (id) {
                case R.id.all_tag:
                    this.cgo.cbD = 0;
                    break;
                default:
                    this.cgo.cbD = 1;
                    ServerAllFragment.a(this.cgo, ((f) button.getTag()).id);
                    break;
            }
            ServerAllFragment.l(this.cgo).setVisibility(0);
            ServerAllFragment.k(this.cgo).HA();
            ServerAllFragment.a(this.cgo, null);
            ServerAllFragment.f(this.cgo).Fy();
            ServerAllFragment.a(this.cgo);
        }
    }
}
