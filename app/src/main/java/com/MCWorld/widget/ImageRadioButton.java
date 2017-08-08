package com.MCWorld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class ImageRadioButton extends RadioButton {
    private OnCheckedChangeListener brE = new OnCheckedChangeListener(this) {
        final /* synthetic */ ImageRadioButton btI;

        {
            this.btI = this$0;
        }

        public void onCheckedChanged(CompoundButton btn, boolean checked) {
            this.btI.setCompoundDrawablesWithIntrinsicBounds(this.btI.getResources().getDrawable(checked ? this.btI.brG : this.btI.brH), null, null, null);
        }
    };
    private int brG = 0;
    private int brH = 0;

    public ImageRadioButton(Context context) {
        super(context);
    }

    public ImageRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageRadioButton(Context context, AttributeSet attrs, int defStyle) {
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
