package com.huluxia.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.n;

/* compiled from: GlobalDialog */
public class g extends Dialog {
    private TextView boi;
    private TextView bwJ;
    private TextView bwK;
    private a bwQ = null;
    private g bwR;
    private TextView bwS;
    private String bwT;
    private String bwU;
    private String bwV;
    private String bwW;
    private CharSequence bwX;
    private TextView eN;
    private OnClickListener mClickListener = new 1(this);
    private Activity mContext = null;

    /* compiled from: GlobalDialog */
    public interface a {
        void ra();

        void rb();

        void rc();

        void rd();
    }

    public g(Activity context, a callback) {
        super(context, n.theme_dialog_normal);
        this.mContext = context;
        this.bwQ = callback;
        this.bwR = this;
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.dialog_global);
        findViewById(com.huluxia.bbs.b.g.cb_tip).setOnClickListener(this.mClickListener);
        findViewById(com.huluxia.bbs.b.g.tv_cancel).setOnClickListener(this.mClickListener);
        findViewById(com.huluxia.bbs.b.g.tv_other).setOnClickListener(this.mClickListener);
        findViewById(com.huluxia.bbs.b.g.tv_confirm).setOnClickListener(this.mClickListener);
        this.eN = (TextView) findViewById(com.huluxia.bbs.b.g.tv_title);
        this.boi = (TextView) findViewById(com.huluxia.bbs.b.g.tv_msg);
        this.bwJ = (TextView) findViewById(com.huluxia.bbs.b.g.tv_cancel);
        this.bwS = (TextView) findViewById(com.huluxia.bbs.b.g.tv_other);
        this.bwK = (TextView) findViewById(com.huluxia.bbs.b.g.tv_confirm);
        a(this.bwT, this.bwX);
        u(this.bwU, this.bwV, this.bwW);
    }

    public void showDialog() {
    }

    public void dismiss() {
        super.dismiss();
    }

    public void az(String title, String msg) {
        this.bwT = title;
        this.bwX = msg;
        if (this.eN != null && this.boi != null) {
            if (title == null) {
                this.eN.setVisibility(8);
            } else {
                this.eN.setVisibility(0);
                this.eN.setText(title);
            }
            if (msg == null) {
                this.boi.setVisibility(8);
                return;
            }
            this.boi.setVisibility(0);
            this.boi.setText(msg);
        }
    }

    public void a(String title, CharSequence msg) {
        this.bwT = title;
        this.bwX = msg;
        if (this.eN != null && this.boi != null) {
            if (title == null) {
                this.eN.setVisibility(8);
            } else {
                this.eN.setVisibility(0);
                this.eN.setText(title);
            }
            if (msg == null) {
                this.boi.setVisibility(8);
                return;
            }
            this.boi.setVisibility(0);
            this.boi.setText(msg);
        }
    }

    public void u(String cancle, String other, String confirm) {
        this.bwU = cancle;
        this.bwV = other;
        this.bwW = confirm;
        if (this.bwJ != null && this.bwS != null && this.bwK != null) {
            if (cancle == null) {
                this.bwJ.setVisibility(8);
                findViewById(com.huluxia.bbs.b.g.split_cancle).setVisibility(8);
            } else {
                this.bwJ.setVisibility(0);
                findViewById(com.huluxia.bbs.b.g.split_cancle).setVisibility(0);
                this.bwJ.setText(cancle);
            }
            if (other == null) {
                this.bwS.setVisibility(8);
                findViewById(com.huluxia.bbs.b.g.split_other).setVisibility(8);
            } else {
                this.bwS.setVisibility(0);
                findViewById(com.huluxia.bbs.b.g.split_other).setVisibility(0);
                this.bwS.setText(other);
            }
            if (confirm != null) {
                this.bwK.setText(confirm);
            }
        }
    }

    public void Oo() {
        findViewById(com.huluxia.bbs.b.g.cb_tip).setVisibility(0);
    }

    protected void Op() {
        if (!(this.mContext == null || this.mContext.isFinishing())) {
            this.bwR.dismiss();
        }
        if (this.bwQ != null) {
            this.bwQ.rb();
        }
    }

    protected void Oq() {
        if (!(this.mContext == null || this.mContext.isFinishing())) {
            this.bwR.dismiss();
        }
        if (this.bwQ != null) {
            this.bwQ.rc();
        }
    }

    protected void sK() {
        if (!(this.mContext == null || this.mContext.isFinishing())) {
            this.bwR.dismiss();
        }
        if (this.bwQ != null) {
            this.bwQ.rd();
        }
    }
}
