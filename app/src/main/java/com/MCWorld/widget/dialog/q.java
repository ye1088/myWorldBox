package com.MCWorld.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.widget.progressbar.ProgressBarRect;

/* compiled from: RotateProgressDialog */
public class q extends Dialog {
    private q bxT;
    private ProgressBarRect bxs;
    private TextView eN;
    private TextView eO;
    private Context mContext = null;

    public q(Context context) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.bxT = this;
        show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rotate_progress);
        this.eN = (TextView) findViewById(R.id.MyViewTip);
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
}
