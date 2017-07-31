package hlx.ui.resources.fragment;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class ResourceAllFragment$3 implements OnGlobalLayoutListener {
    final /* synthetic */ ResourceAllFragment cgj;

    ResourceAllFragment$3(ResourceAllFragment this$0) {
        this.cgj = this$0;
    }

    @SuppressLint({"NewApi"})
    public void onGlobalLayout() {
        ResourceAllFragment.e(this.cgj).setPadding(0, ResourceAllFragment.d(this.cgj).getHeight(), 0, 0);
        if (VERSION.SDK_INT >= 16) {
            ResourceAllFragment.e(this.cgj).getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            ResourceAllFragment.e(this.cgj).getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
