package com.MCWorld.widget;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ImageRadioGroup */
public class d {
    private List<RadioButton> brB;
    private OnCheckedChangeListener brE = new OnCheckedChangeListener(this) {
        final /* synthetic */ d btK;

        {
            this.btK = this$0;
        }

        public void onCheckedChanged(CompoundButton button, boolean checked) {
            if (checked) {
                for (RadioButton btn : this.btK.brB) {
                    if (btn.getId() != button.getId()) {
                        btn.setChecked(false);
                    }
                }
                this.btK.btJ.a(button, checked);
            }
        }
    };
    private a btJ;

    /* compiled from: ImageRadioGroup */
    public interface a {
        void a(CompoundButton compoundButton, boolean z);
    }

    public d(a callback) {
        this.btJ = callback;
        this.brB = new ArrayList();
    }

    public void a(RadioButton btn1) {
        this.brB.add(btn1);
        btn1.setOnCheckedChangeListener(this.brE);
    }

    public void a(RadioButton btn1, RadioButton btn2) {
        this.brB.add(btn1);
        this.brB.add(btn2);
        btn1.setOnCheckedChangeListener(this.brE);
        btn2.setOnCheckedChangeListener(this.brE);
    }

    public void a(RadioButton btn1, RadioButton btn2, RadioButton btn3) {
        this.brB.add(btn1);
        this.brB.add(btn2);
        this.brB.add(btn3);
        btn1.setOnCheckedChangeListener(this.brE);
        btn2.setOnCheckedChangeListener(this.brE);
        btn3.setOnCheckedChangeListener(this.brE);
    }
}
