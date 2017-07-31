package hlx.ui.resources.fragment;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class ResourceRecommendFragment$3 implements OnGlobalLayoutListener {
    final /* synthetic */ ResourceRecommendFragment cgl;

    ResourceRecommendFragment$3(ResourceRecommendFragment this$0) {
        this.cgl = this$0;
    }

    @SuppressLint({"NewApi"})
    public void onGlobalLayout() {
        ResourceRecommendFragment.e(this.cgl).setPadding(0, ResourceRecommendFragment.d(this.cgl).getHeight(), 0, 0);
        if (VERSION.SDK_INT >= 16) {
            ResourceRecommendFragment.e(this.cgl).getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            ResourceRecommendFragment.e(this.cgl).getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
