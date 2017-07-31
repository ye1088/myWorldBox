package com.huluxia.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

/* compiled from: HlxCountTimer */
public class b extends CountDownTimer {
    public static final int btD = 41000;
    private TextView btE;
    private int btF;
    private int btG;
    private int btH;

    public b(long millisInFuture, long countDownInterval, TextView btn, int endStrRid) {
        super(millisInFuture, countDownInterval);
        this.btE = btn;
        this.btF = endStrRid;
    }

    public b(int counttime, TextView btn, int endStrRid) {
        super((long) counttime, 1000);
        this.btE = btn;
        this.btF = endStrRid;
    }

    public b(int counttime, TextView tv_varify, int endStrRid, int normalColor, int timingColor) {
        this(counttime, tv_varify, endStrRid);
        this.btG = normalColor;
        this.btH = timingColor;
    }

    public void onFinish() {
        if (this.btG > 0) {
            this.btE.setTextColor(this.btG);
        }
        this.btE.setText(this.btF);
        this.btE.setEnabled(true);
    }

    public void onTick(long millisUntilFinished) {
        if (this.btH > 0) {
            this.btE.setTextColor(this.btH);
        }
        this.btE.setEnabled(false);
        this.btE.setText("请稍候(" + (millisUntilFinished / 1000) + "s)");
    }
}
