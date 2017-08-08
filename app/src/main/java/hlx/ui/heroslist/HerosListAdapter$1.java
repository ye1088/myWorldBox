package hlx.ui.heroslist;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.t;

class HerosListAdapter$1 implements OnClickListener {
    final /* synthetic */ c$a bYm;
    final /* synthetic */ HerosListAdapter bYn;

    HerosListAdapter$1(HerosListAdapter this$0, c$a hlx_ui_heroslist_c_a) {
        this.bYn = this$0;
        this.bYm = hlx_ui_heroslist_c_a;
    }

    public void onClick(View v) {
        try {
            t.e(HerosListAdapter.a(this.bYn), this.bYm.userID);
        } catch (Exception e) {
        }
    }
}
