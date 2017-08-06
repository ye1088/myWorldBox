package hlx.launch.game;

import android.content.Context;
import com.huluxia.r;
import com.huluxia.widget.dialog.g.a;

/* compiled from: MCPreLaunchCheck */
class d$1 implements a {
    final /* synthetic */ boolean bRd;
    final /* synthetic */ Context val$context;

    d$1(boolean z, Context context) {
        this.bRd = z;
        this.val$context = context;
    }

    public void rc() {
    }

    public void ra() {
    }

    public void rd() {
        r.ck().K_umengEvent(hlx.data.tongji.a.bNS);
        if (!this.bRd) {
            d.e(c.Sg().Sh(), this.val$context);
        }
    }

    public void rb() {
        r.ck().K_umengEvent(hlx.data.tongji.a.bNR);
        hlx.ui.a.e(this.val$context, false);
    }
}
