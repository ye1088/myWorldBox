package com.MCWorld.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.MCWorld.bbs.b.o;
import com.MCWorld.utils.at;
import com.MCWorld.widget.emoInput.d;

public class EmojiTextView extends TextView {
    private float bFm = 1.0f;

    public EmojiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, o.EmojiText);
        if (a.hasValue(o.EmojiText_emojiscale)) {
            this.bFm = a.getFloat(o.EmojiText_emojiscale, 1.0f);
        }
        a.recycle();
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, o.EmojiText);
        if (a.hasValue(o.EmojiText_emojiscale)) {
            this.bFm = a.getFloat(o.EmojiText_emojiscale, 1.0f);
        }
        a.recycle();
    }

    public EmojiTextView(Context context) {
        super(context);
    }

    public void setEmojiText(String text) {
        super.setText(d.Ou().c(getContext(), text, at.dipToPx(getContext(), 22), 0));
    }

    public void setText(String text) {
        if (text != null) {
            super.setText(d.Ou().e(getContext(), text, (int) (getTextSize() * this.bFm)));
        }
    }
}
