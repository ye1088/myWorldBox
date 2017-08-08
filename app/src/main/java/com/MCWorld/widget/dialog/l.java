package com.MCWorld.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.n;
import com.MCWorld.widget.progressbar.ProgressBarRect;
import java.text.NumberFormat;

/* compiled from: HlxRootProgressDialog */
public class l extends Dialog {
    private l bxr;
    private ProgressBarRect bxs;
    private TextView eN;
    private TextView eO;
    private Context mContext = null;

    public l(Context context) {
        super(context, n.theme_dialog_normal);
        this.mContext = context;
        this.bxr = this;
        show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.dialog_progress);
        this.eN = (TextView) findViewById(g.MyViewTip);
        this.eO = (TextView) findViewById(g.MyViewProgress);
        this.bxs = (ProgressBarRect) findViewById(g.MyViewBar);
    }

    public void showDialog() {
    }

    public void dismiss() {
        super.dismiss();
    }

    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    public void setTitle(String title) {
        this.eN.setText(title);
    }

    public void setProgress(int cur) {
        this.bxs.setProgress(cur);
        this.bxs.setMax(100);
        this.eO.setText(aK(cur, 100));
    }

    private String aK(int cur, int max) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format((double) ((((float) cur) / ((float) max)) * 100.0f)) + "%";
    }
}
