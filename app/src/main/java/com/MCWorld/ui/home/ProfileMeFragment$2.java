package com.MCWorld.ui.home;

import android.graphics.Bitmap;
import com.MCWorld.framework.base.image.PipelineView.Callback;

class ProfileMeFragment$2 implements Callback {
    final /* synthetic */ ProfileMeFragment aSu;

    ProfileMeFragment$2(ProfileMeFragment this$0) {
        this.aSu = this$0;
    }

    public void onSucc(Bitmap bitmap) {
        ProfileMeFragment.b(this.aSu);
    }

    public void onFailed() {
        ProfileMeFragment.c(this.aSu);
    }

    public void onProgressUpdate(float progress) {
    }
}
