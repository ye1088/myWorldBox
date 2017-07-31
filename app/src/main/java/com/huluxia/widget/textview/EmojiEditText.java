package com.huluxia.widget.textview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class EmojiEditText extends EditText {
    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
