package com.tencent.mm.sdk.platformtools;

import android.view.View;
import android.view.animation.Animation;
import com.tencent.mm.sdk.platformtools.BackwardSupportUtil.AnimationHelper.IHelper;

class AnimationHelperImpl21below implements IHelper {
    AnimationHelperImpl21below() {
    }

    public void cancelAnimation(View view, Animation animation) {
        if (view != null) {
            view.setAnimation(null);
        }
    }
}
