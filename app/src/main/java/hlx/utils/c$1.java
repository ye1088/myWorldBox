package hlx.utils;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.R;

/* compiled from: DoubleBtnDialog */
class c$1 implements OnClickListener {
    final /* synthetic */ c cgM;

    c$1(c this$0) {
        this.cgM = this$0;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cb_tip) {
            if (c.a(this.cgM) != null) {
                c.a(this.cgM).ra();
            }
        } else if (id == R.id.tv_cancel) {
            this.cgM.Op();
        } else if (id == R.id.tv_confirm) {
            this.cgM.sK();
        }
    }
}
