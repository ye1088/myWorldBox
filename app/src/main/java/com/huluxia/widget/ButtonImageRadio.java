package com.huluxia.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class ButtonImageRadio extends RadioButton {
    private OnCheckedChangeListener brE = new OnCheckedChangeListener(this) {
        final /* synthetic */ ButtonImageRadio brI;

        {
            this.brI = this$0;
        }

        public void onCheckedChanged(CompoundButton btn, boolean checked) {
            this.brI.setCompoundDrawablesWithIntrinsicBounds(this.brI.getResources().getDrawable(checked ? this.brI.brG : this.brI.brH), null, null, null);
        }
    };
    private int brG = 0;
    private int brH = 0;

    public ButtonImageRadio(Context context) {
        super(context);
    }

    public ButtonImageRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonImageRadio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void aI(int trueImage, int falseImage) {
        if (trueImage != 0 && falseImage != 0) {
            this.brG = trueImage;
            this.brH = falseImage;
            setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(this.brH), null, null, null);
            setOnCheckedChangeListener(this.brE);
        }
    }
}
