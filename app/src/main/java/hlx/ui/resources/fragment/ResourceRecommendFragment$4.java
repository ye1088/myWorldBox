package hlx.ui.resources.fragment;

import android.view.View;
import com.huluxia.ui.base.BaseLoadingLayout.b;

class ResourceRecommendFragment$4 implements b {
    final /* synthetic */ ResourceRecommendFragment cgl;

    ResourceRecommendFragment$4(ResourceRecommendFragment this$0) {
        this.cgl = this$0;
    }

    public void onRetryClick(View view) {
        this.cgl.reload();
    }
}
