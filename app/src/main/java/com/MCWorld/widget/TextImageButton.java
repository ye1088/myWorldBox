package com.MCWorld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextImageButton extends TextView {
    private int buD = 0;
    private String mText = "";

    public TextImageButton(Context context) {
        super(context);
    }

    public TextImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void ab(String text, int image) {
        if (text != null) {
            this.mText = text;
            setText(this.mText);
            if (image != 0) {
                this.buD = image;
                setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(this.buD), null);
            }
        }
    }
}
