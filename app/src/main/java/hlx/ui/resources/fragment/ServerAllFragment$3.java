package hlx.ui.resources.fragment;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class ServerAllFragment$3 implements OnGlobalLayoutListener {
    final /* synthetic */ ServerAllFragment cgo;

    ServerAllFragment$3(ServerAllFragment this$0) {
        this.cgo = this$0;
    }

    @SuppressLint({"NewApi"})
    public void onGlobalLayout() {
        ServerAllFragment.f(this.cgo).setPadding(0, ServerAllFragment.e(this.cgo).getHeight(), 0, 0);
        if (VERSION.SDK_INT >= 16) {
            ServerAllFragment.f(this.cgo).getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            ServerAllFragment.f(this.cgo).getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
