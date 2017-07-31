package hlx.ui.resources.fragment;

import android.view.View;
import com.huluxia.ui.base.BaseLoadingLayout.b;

class SubjectrFragment$1 implements b {
    final /* synthetic */ SubjectrFragment cgs;

    SubjectrFragment$1(SubjectrFragment this$0) {
        this.cgs = this$0;
    }

    public void onRetryClick(View view) {
        this.cgs.reload();
    }
}
