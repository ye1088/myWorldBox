package com.huluxia.widget.emoInput;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.huluxia.bbs.b.c;
import com.huluxia.utils.at;
import com.simple.colorful.d;
import org.mozilla.classfile.ByteCode;

public class FacePanelView extends RelativeLayout implements FacePanelTabs$a {
    public static int bgColor = -921103;
    public static int byA = 0;
    public static int byB = 0;
    public static int byC = 0;
    public static int byD = 0;
    public static int byE = 0;
    public static int byF = -2500135;
    public static int byG = -10395295;
    public static int byz = 0;
    private a byH = null;
    private FaceView byx;
    private FacePanelTabs byy;

    public interface a {
        void a(FaceItem faceItem);
    }

    public FacePanelView(Context context) {
        super(context);
        init(context);
    }

    public FacePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FacePanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void bG(Context context) {
        byz = at.dipToPx(context, ByteCode.MONITOREXIT);
        byA = at.dipToPx(context, 145);
        byB = at.dipToPx(context, 15);
        byC = at.dipToPx(context, 35);
        byD = at.dipToPx(context, 70);
        byE = at.dipToPx(context, 5);
    }

    protected void init(Context context) {
        bG(context);
        bgColor = d.getColor(context, c.backgroundDefault);
        byG = d.getColor(context, c.colorDownButtonGrey);
        setBackgroundColor(bgColor);
        this.byx = new FaceView(getContext(), this);
        this.byx.setLayoutParams(new LayoutParams(-1, byA + byB));
        addView(this.byx);
        this.byy = new FacePanelTabs(getContext());
        LayoutParams params = new LayoutParams(-1, byC);
        params.addRule(12);
        this.byy.setHorizontalScrollBarEnabled(false);
        this.byy.setHorizontalFadingEdgeEnabled(false);
        this.byy.setLayoutParams(params);
        this.byy.setOnTabChangeListener(this);
        addView(this.byy);
        if (!isInEditMode()) {
            this.byy.setCurrentTab(0);
        }
    }

    private void HD() {
        if (!isInEditMode()) {
            this.byx.setData((FaceItem[]) FacePanelData.getInstance().get(0));
        }
    }

    public void d(View v, int index) {
        this.byx.setData((FaceItem[]) FacePanelData.getInstance().get(index));
    }

    public void setOnlyDefaultFace(boolean isOnly) {
        if (isOnly) {
            this.byy.Ov();
            this.byy.ac(" 默认 ", 0);
            this.byy.setCurrentTab(0);
            return;
        }
        this.byy.Ov();
        this.byy.ac(" 默认 ", 0);
        this.byy.ac("泡泡兵", 1);
        this.byy.setCurrentTab(0);
    }

    public a getOnItemFaceClick() {
        return this.byH;
    }

    public void setOnItemFaceClick(a onItemFaceClick) {
        this.byH = onItemFaceClick;
    }

    public void setPanelBackground(int color) {
        bgColor = color;
        setBackgroundColor(bgColor);
    }

    public void setBottomTabColor(int color) {
        byF = color;
        this.byy.setBackgroundColor(byF);
        this.byy.Ox();
    }
}
