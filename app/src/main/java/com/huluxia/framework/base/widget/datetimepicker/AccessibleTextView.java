package com.huluxia.framework.base.widget.datetimepicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

public class AccessibleTextView extends TextView {
    public AccessibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(event);
            event.setClassName(Button.class.getName());
        }
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(info);
            info.setClassName(Button.class.getName());
        }
    }
}
