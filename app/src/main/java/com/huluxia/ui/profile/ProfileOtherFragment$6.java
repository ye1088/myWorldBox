package com.huluxia.ui.profile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.FrameLayout.LayoutParams;
import com.huluxia.framework.base.image.PipelineView.Callback;

class ProfileOtherFragment$6 implements Callback {
    final /* synthetic */ ProfileOtherFragment bgR;
    final /* synthetic */ int bgT;
    final /* synthetic */ int bgU;

    ProfileOtherFragment$6(ProfileOtherFragment this$0, int i, int i2) {
        this.bgR = this$0;
        this.bgT = i;
        this.bgU = i2;
    }

    public void onSucc(Bitmap bitmap) {
        Activity activity = this.bgR.getActivity();
        if (activity != null && !activity.isFinishing()) {
            this.bgR.Vo.sendMessageDelayed(this.bgR.Vo.obtainMessage(1), 500);
            LayoutParams llp = (LayoutParams) ProfileOtherFragment.c(this.bgR).getLayoutParams();
            llp.width = this.bgT;
            llp.height = this.bgU;
            ProfileOtherFragment.c(this.bgR).setLayoutParams(llp);
            ProfileOtherFragment.c(this.bgR).setVisibility(0);
        }
    }

    public void onFailed() {
    }

    public void onProgressUpdate(float progress) {
    }
}
