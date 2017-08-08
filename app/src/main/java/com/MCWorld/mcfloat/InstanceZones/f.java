package com.MCWorld.mcfloat.InstanceZones;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.mcinterface.h;
import com.mojang.minecraftpe.MainActivity;
import hlx.utils.d;

/* compiled from: InsZonesDisplayBoard */
public class f {
    private boolean QJ = false;
    private boolean QK = false;
    private MainActivity QO = null;
    private TextView Xd;
    private TextView Xe;
    private TextView Xf;
    private TextView Xg;
    private TextView Xh;
    private PopupWindow Xi = null;
    private f Xj = null;
    private a Xk;

    /* compiled from: InsZonesDisplayBoard */
    public interface a {
        void sm();
    }

    public f(a cb) {
        this.Xk = cb;
    }

    public void aC(boolean bShow) {
        if (bShow != this.QK) {
            if (!this.QJ && bShow) {
                rj();
            }
            if (this.QJ) {
                this.QK = bShow;
                if (bShow) {
                    this.Xi.showAtLocation(this.QO.getWindow().getDecorView(), 49, 0, 0);
                } else {
                    this.Xi.dismiss();
                }
            }
        }
    }

    public void ch(String time) {
        this.Xd.setText(time);
    }

    public String sF() {
        return this.Xd.getText().toString();
    }

    public void ci(String score) {
        this.Xe.setText(score);
    }

    public String sG() {
        return this.Xe.getText().toString();
    }

    public void cj(String killMonsterNumber) {
        this.Xf.setText(killMonsterNumber);
    }

    public String sH() {
        return this.Xf.getText().toString();
    }

    public void ck(String quitText) {
        this.Xg.setText(quitText);
    }

    public void b(String score, String killMonster, String time, String quitText) {
        ci(score);
        cj(killMonster);
        ch(time);
        ck(quitText);
    }

    public void g(int score, int killMonsterSum, int countDownTime) {
        ci(String.valueOf(score));
        cj(String.valueOf(killMonsterSum));
        ch(d.nQ(countDownTime));
    }

    public void dP(int countDonw) {
        this.Xh.setVisibility(0);
        this.Xh.setText(String.valueOf(countDonw));
    }

    public void sI() {
        this.Xh.setVisibility(8);
    }

    private void rj() {
        if (h.zu() != null) {
            this.QO = (MainActivity) h.zu().get();
            if (this.QO != null) {
                this.Xj = this;
                View view = LayoutInflater.from(this.QO).inflate(R.layout.lyt_float_chalenge_show, null);
                this.Xh = (TextView) view.findViewById(R.id.tvChalengeCountDownShow);
                this.Xd = (TextView) view.findViewById(R.id.tvFloatChalengeTime);
                this.Xe = (TextView) view.findViewById(R.id.tvFloatChalengeScore);
                this.Xf = (TextView) view.findViewById(R.id.tvFloatChalengeKillMonster);
                this.Xg = (TextView) view.findViewById(R.id.tvFloatChalengeQuit);
                this.Xg.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ f Xl;

                    {
                        this.Xl = this$0;
                    }

                    public void onClick(View v) {
                        this.Xl.Xk.sm();
                    }
                });
                LinearLayout layout = new LinearLayout(this.QO);
                layout.addView(view, new LayoutParams(-2, -2));
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
