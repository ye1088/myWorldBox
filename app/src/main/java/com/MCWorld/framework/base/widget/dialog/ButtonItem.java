package com.MCWorld.framework.base.widget.dialog;

import com.MCWorld.framework.R;

public class ButtonItem {
    public static final int BUTTON_TYPE_CANCEL = 1;
    public static final int BUTTON_TYPE_NORMAL = 0;
    public int mButtonType;
    public OnClickListener mClickListener;
    public String mText;
    public int resourceID;

    public interface OnClickListener {
        void onClick();
    }

    public ButtonItem(String text, OnClickListener l) {
        this(text, 0, l);
    }

    public ButtonItem(String text, int buttonType, OnClickListener l) {
        this.mText = text;
        this.mClickListener = l;
        this.mButtonType = buttonType;
        this.resourceID = R.layout.layout_common_popup_dialog_button;
    }
}
