package com.huluxia.framework.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.huluxia.framework.base.widget.ResizeRelativeLayout.OnSizeChangedListener;
import java.util.ArrayList;
import java.util.List;

public class KeyboardResizeLayout extends ResizeRelativeLayout {
    private static final int HEIGHT_THRESHOLD = 30;
    private List<OnKeyboardShowListener> keyboardShowListener = new ArrayList();
    private OnSizeChangedListener sizeChangedListener = new 1(this);

    public interface OnKeyboardShowListener {
        void onShow(boolean z);
    }

    public KeyboardResizeLayout(Context context) {
        super(context);
        setListener(this.sizeChangedListener);
    }

    public KeyboardResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListener(this.sizeChangedListener);
    }

    public KeyboardResizeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setListener(this.sizeChangedListener);
    }

    public void addOnKeyboardShowListener(OnKeyboardShowListener l) {
        if (l != null && !this.keyboardShowListener.contains(l)) {
            this.keyboardShowListener.add(l);
        }
    }

    public void removeOnKeyboardShowListener(OnKeyboardShowListener l) {
        this.keyboardShowListener.remove(l);
    }
}
