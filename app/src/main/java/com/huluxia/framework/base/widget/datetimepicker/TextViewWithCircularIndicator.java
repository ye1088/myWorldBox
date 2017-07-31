package com.huluxia.framework.base.widget.datetimepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.R$dimen;
import com.huluxia.framework.R$string;

public class TextViewWithCircularIndicator extends TextView {
    private final int mCircleColor;
    private Paint mCirclePaint = new Paint();
    private boolean mDrawCircle;
    private final String mItemIsSelectedText;
    private final int mRadius;

    public TextViewWithCircularIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources res = context.getResources();
        this.mCircleColor = res.getColor(R.color.blue);
        this.mRadius = res.getDimensionPixelOffset(R$dimen.month_select_circle_radius);
        this.mItemIsSelectedText = context.getResources().getString(R$string.item_is_selected);
        init();
    }

    private void init() {
        this.mCirclePaint.setFakeBoldText(true);
        this.mCirclePaint.setAntiAlias(true);
        this.mCirclePaint.setColor(this.mCircleColor);
        this.mCirclePaint.setTextAlign(Align.CENTER);
        this.mCirclePaint.setStyle(Style.FILL);
        this.mCirclePaint.setAlpha(60);
    }

    public void drawIndicator(boolean drawIndicator) {
        this.mDrawCircle = drawIndicator;
    }

    public CharSequence getContentDescription() {
        CharSequence text = getText();
        if (!this.mDrawCircle) {
            return text;
        }
        return String.format(this.mItemIsSelectedText, new Object[]{text});
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDrawCircle) {
            int width = getWidth();
            int heigth = getHeight();
            canvas.drawCircle((float) (width / 2), (float) (heigth / 2), (float) (Math.min(width, heigth) / 2), this.mCirclePaint);
        }
    }
}
