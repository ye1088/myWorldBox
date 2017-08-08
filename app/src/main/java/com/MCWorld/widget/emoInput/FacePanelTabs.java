package com.MCWorld.widget.emoInput;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FacePanelTabs extends HorizontalScrollView {
    private LinearLayout byt;
    private a byu = null;
    private List<Button> byv = new ArrayList();
    private int lastIndex = -1;

    public FacePanelTabs(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(FacePanelView.byF);
        this.byt = new LinearLayout(getContext());
        this.byt.setLayoutParams(new LayoutParams(-1, FacePanelView.byC));
        addView(this.byt);
        ac(" 默认 ", 0);
    }

    public void Ov() {
        this.byt.removeAllViews();
        this.byv.clear();
    }

    public void ac(String title, int index) {
        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(FacePanelView.byD, FacePanelView.byC);
        Button defBtn = new Button(getContext());
        defBtn.setLayoutParams(params1);
        defBtn.setText(title);
        defBtn.setPadding(10, 0, 10, 0);
        defBtn.setTag(Integer.valueOf(index));
        this.byt.addView(defBtn);
        this.byv.add(defBtn);
        defBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FacePanelTabs byw;

            {
                this.byw = this$0;
            }

            public void onClick(View v) {
                this.byw.setCurrentTab(((Integer) v.getTag()).intValue());
            }
        });
    }

    public void setCurrentTab(int index) {
        Ow();
        Button v = (Button) this.byv.get(index);
        v.setSelected(v.isSelected());
        v.setBackgroundColor(FacePanelView.bgColor);
        v.setTextColor(FacePanelView.byG);
        Integer tag = (Integer) v.getTag();
        if (this.lastIndex != tag.intValue()) {
            this.lastIndex = tag.intValue();
            if (this.byu != null) {
                this.byu.d(v, this.lastIndex);
            }
        }
    }

    private void Ow() {
        for (Button btn : this.byv) {
            btn.setBackgroundColor(FacePanelView.byF);
        }
    }

    public void Ox() {
        setCurrentTab(this.lastIndex);
    }

    public a getOnTabChangeListener() {
        return this.byu;
    }

    public void setOnTabChangeListener(a onTabChangeListener) {
        this.byu = onTabChangeListener;
    }
}
