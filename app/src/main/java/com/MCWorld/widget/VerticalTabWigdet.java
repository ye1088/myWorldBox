package com.MCWorld.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TabWidget;
import com.MCWorld.framework.R;

public class VerticalTabWigdet extends TabWidget {
    Resources buH;

    public VerticalTabWigdet(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.buH = context.getResources();
        setOrientation(1);
    }

    public void addView(View child) {
        LayoutParams lp = new LayoutParams(-1, -2, 1.0f);
        lp.setMargins(0, 0, 0, 0);
        child.setLayoutParams(lp);
        super.addView(child);
        child.setBackgroundDrawable(this.buH.getDrawable(R.color.transparent));
    }
}
