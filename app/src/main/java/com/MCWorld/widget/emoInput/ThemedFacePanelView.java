package com.MCWorld.widget.emoInput;

import android.content.Context;
import android.util.AttributeSet;
import com.MCWorld.bbs.b;
import com.simple.colorful.c;
import com.simple.colorful.d;

public class ThemedFacePanelView extends FacePanelView implements c {
    public ThemedFacePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemedFacePanelView(Context context) {
        super(context);
    }

    protected void init(Context context) {
        bgColor = d.getColor(context, b.c.backgroundDefault);
        byF = d.getColor(context, b.c.backgroundDim);
        super.init(context);
    }

    public a b(a builder) {
        return builder;
    }

    public void FG() {
        setPanelBackground(d.getColor(getContext(), b.c.backgroundDefault));
        setBottomTabColor(d.getColor(getContext(), b.c.backgroundDim));
    }
}
