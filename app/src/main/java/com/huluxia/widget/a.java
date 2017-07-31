package com.huluxia.widget;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ButtonCheckGroup */
public class a {
    private List<CompoundButton> brB;
    private a brC;
    private CompoundButton brD;
    private OnCheckedChangeListener brE = new OnCheckedChangeListener(this) {
        final /* synthetic */ a brF;

        {
            this.brF = this$0;
        }

        public void onCheckedChanged(CompoundButton button, boolean checked) {
            if (checked) {
                this.brF.brD = button;
                this.brF.brC.a(button, true);
                for (CompoundButton btn : this.brF.brB) {
                    if (btn != button && btn.isChecked()) {
                        btn.setChecked(false);
                    }
                }
                return;
            }
            this.brF.brC.a(button, false);
        }
    };

    /* compiled from: ButtonCheckGroup */
    public interface a {
        void a(CompoundButton compoundButton, boolean z);
    }

    public a(a callback) {
        this.brC = callback;
        this.brB = new ArrayList();
    }

    public void a(CompoundButton btn1) {
        this.brB.add(btn1);
        btn1.setOnCheckedChangeListener(this.brE);
    }

    public void a(CompoundButton btn1, CompoundButton btn2) {
        a(btn1);
        a(btn2);
    }

    public void a(CompoundButton btn1, CompoundButton btn2, CompoundButton btn3) {
        a(btn1);
        a(btn2);
        a(btn3);
    }

    public void dh(boolean checked) {
        for (CompoundButton btn : this.brB) {
            btn.setChecked(checked);
        }
        if (!checked) {
            this.brD = null;
        }
    }

    public CompoundButton NP() {
        return this.brD;
    }

    public List<CompoundButton> NQ() {
        return this.brB;
    }
}
