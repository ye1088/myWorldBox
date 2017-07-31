package com.huluxia.framework.base.widget.datetimepicker;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

public class Utils {
    public static final int PULSE_ANIMATOR_DURATION = 544;

    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return year % 4 == 0 ? 29 : 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    public static ObjectAnimator getPulseAnimator(View labelToAnimate, float decreaseRatio, float increaseRatio) {
        Keyframe k0 = Keyframe.ofFloat(0.0f, 1.0f);
        Keyframe k1 = Keyframe.ofFloat(0.275f, decreaseRatio);
        Keyframe k2 = Keyframe.ofFloat(0.69f, increaseRatio);
        Keyframe k3 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3);
        ObjectAnimator pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, scaleX, scaleY);
        pulseAnimator.setDuration(544);
        return pulseAnimator;
    }

    public static boolean isJellybeanOrLater() {
        return VERSION.SDK_INT >= 16;
    }

    @TargetApi(16)
    public static void tryAccessibilityAnnounce(View view, CharSequence text) {
        if (isJellybeanOrLater() && view != null && text != null) {
            view.announceForAccessibility(text);
        }
    }

    @TargetApi(14)
    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        if (VERSION.SDK_INT >= 14) {
            return accessibilityManager.isTouchExplorationEnabled();
        }
        return false;
    }
}
