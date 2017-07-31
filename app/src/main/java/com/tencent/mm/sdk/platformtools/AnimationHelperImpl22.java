package com.tencent.mm.sdk.platformtools;

import android.view.View;
import android.view.animation.Animation;
import com.tencent.mm.sdk.platformtools.BackwardSupportUtil.AnimationHelper.IHelper;

class AnimationHelperImpl22 implements IHelper {
    AnimationHelperImpl22() {
    }

    public void cancelAnimation(View view, Animation animation) {
        animation.cancel();
    }
}
