package hlx.ui.resources.fragment;

import android.view.View;
import com.huluxia.ui.base.BaseLoadingLayout.b;

class ResourceRankingFragment$3 implements b {
    final /* synthetic */ ResourceRankingFragment cgk;

    ResourceRankingFragment$3(ResourceRankingFragment this$0) {
        this.cgk = this$0;
    }

    public void onRetryClick(View view) {
        this.cgk.reload();
    }
}
