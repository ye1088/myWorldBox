package com.huluxia.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.bbs.b.g;
import com.simple.colorful.d;

/* compiled from: GlobalDialogOne */
public class i extends Dialog {
    private TextView boi;
    private TextView bwK;
    private a bxe = null;
    private i bxf;
    private TextView eN;
    private OnClickListener mClickListener = new 1(this);
    private Activity mContext = null;

    /* compiled from: GlobalDialogOne */
    public interface a {
        void ra();

        void rd();
    }

    public i(Activity context, a callback) {
        super(context, d.RD());
        this.mContext = context;
        this.bxe = callback;
        this.bxf = this;
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.huluxia.bbs.b.i.dialog_global_one);
        findViewById(g.cb_tip).setOnClickListener(this.mClickListener);
        findViewById(g.tv_confirm).setOnClickListener(this.mClickListener);
        this.eN = (TextView) findViewById(g.tv_title);
        this.boi = (TextView) findViewById(g.tv_msg);
        this.bwK = (TextView) findViewById(g.tv_confirm);
    }

    public void showDialog() {
    }

    public void dismiss() {
        super.dismiss();
    }

    public void az(String title, String msg) {
        if (title == null) {
            this.eN.setVisibility(8);
        } else {
            this.eN.setText(title);
        }
        if (msg == null) {
            this.boi.setVisibility(8);
        } else {
            this.boi.setText(msg);
        }
    }

    public void a(String title, CharSequence msg) {
        if (title == null) {
            this.eN.setVisibility(8);
        } else {
            this.eN.setText(title);
        }
        if (msg == null) {
            this.boi.setVisibility(8);
        } else {
            this.boi.setText(msg);
        }
    }

    public void gK(String confirm) {
        if (confirm != null) {
            this.bwK.setText(confirm);
        }
    }

    public void Oo() {
        findViewById(g.cb_tip).setVisibility(0);
    }
}
