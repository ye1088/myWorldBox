package com.MCWorld.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.MCWorld.bbs.b.f;

public class EditTextWithDelete extends EditText {
    private TextWatcher btB = new TextWatcher(this) {
        final /* synthetic */ EditTextWithDelete btC;

        {
            this.btC = this$0;
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable arg0) {
            this.btC.NS();
        }
    };

    public EditTextWithDelete(Context context) {
        super(context);
        addTextChangedListener(this.btB);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this.btB);
    }

    public EditTextWithDelete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addTextChangedListener(this.btB);
    }

    public int lL(int defVal) {
        String value = getText().toString();
        if (!(value == null || value.length() == 0)) {
            try {
                defVal = Integer.valueOf(value).intValue();
            } catch (NumberFormatException e) {
            }
        }
        return defVal;
    }

    private void NS() {
        Drawable imageAble = null;
        if (length() > 0) {
            imageAble = getResources().getDrawable(f.icon_btn_clear);
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, imageAble, null);
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (length() != 0 && super.isEnabled() && event.getAction() == 1) {
            int eventX = (int) event.getRawX();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            if (rect.right - eventX <= 120) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
