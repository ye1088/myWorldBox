package com.huluxia.mcfloat.build;

import android.graphics.drawable.ColorDrawable;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.huluxia.mcfloat.a;
import com.huluxia.mcinterface.h;
import com.mojang.minecraftpe.MainActivity;

/* compiled from: BuildStatusShow */
public class e {
    private boolean QJ = false;
    private boolean QK = false;
    private MainActivity QO = null;
    private PopupWindow Xi = null;
    private TextView abk = null;

    public void b(boolean bShow, String inputStatusStr) {
        if (this.QK || bShow) {
            if (!this.QK && bShow) {
                rj();
            }
            if (this.QJ) {
                this.QK = bShow;
                if (bShow) {
                    this.Xi.showAtLocation(this.QO.getWindow().getDecorView(), 51, a.cX(5), 0);
                    cn(inputStatusStr);
                    return;
                }
                this.Xi.dismiss();
            }
        }
    }

    public void cm(String content) {
        cn(content);
    }

    public void cn(String inputStatusStr) {
        if (this.QK && inputStatusStr != null) {
            this.abk.setText(inputStatusStr);
        }
    }

    private void rj() {
        if (h.zu() != null) {
            this.QO = (MainActivity) h.zu().get();
            if (this.QO != null) {
                this.abk = new TextView(this.QO);
                this.abk.setGravity(17);
                this.abk.setTextColor(-1);
                this.abk.setTextSize(13.0f);
                this.abk.setBackgroundColor(-2004318072);
                RelativeLayout layout = new RelativeLayout(this.QO);
                layout.addView(this.abk, new LayoutParams(-2, a.cX(30)));
                this.Xi = new PopupWindow(this.QO);
                this.Xi.setBackgroundDrawable(new ColorDrawable(0));
                this.Xi.setContentView(layout);
                this.Xi.setWidth(-2);
                this.Xi.setHeight(-2);
                this.QJ = true;
            }
        }
    }
}
