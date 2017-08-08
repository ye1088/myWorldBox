package hlx.home.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.r;
import hlx.ui.a;

class HomeFragment$10 implements OnClickListener {
    final /* synthetic */ HomeFragment bQl;

    HomeFragment$10(HomeFragment this$0) {
        this.bQl = this$0;
    }

    public void onClick(View v) {
        a.cc(HomeFragment.a(this.bQl));
        r.ck().K_umengEvent(hlx.data.tongji.a.bMd);
    }
}
