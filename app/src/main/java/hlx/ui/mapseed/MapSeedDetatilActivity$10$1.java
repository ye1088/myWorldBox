package hlx.ui.mapseed;

import android.widget.PopupWindow.OnDismissListener;
import com.huluxia.framework.R;
import com.simple.colorful.d;
import hlx.ui.mapseed.MapSeedDetatilActivity.10;

class MapSeedDetatilActivity$10$1 implements OnDismissListener {
    final /* synthetic */ 10 cbs;

    MapSeedDetatilActivity$10$1(10 this$1) {
        this.cbs = this$1;
    }

    public void onDismiss() {
        this.cbs.aNZ.setImageDrawable(d.o(this.cbs.cbr.aMn, R.attr.drawableComplaintDown));
    }
}
