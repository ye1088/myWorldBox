package com.MCWorld.widget.dialog;

import android.app.Activity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;

/* compiled from: GlobalDialog2 */
public class h {
    private TextView boi;
    private TextView bwJ;
    private TextView bwK;
    private TextView bwS;
    private a bwZ = null;
    private h bxa;
    private WindowManager bxb;
    private LayoutParams bxc;
    private TextView eN;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ h bxd;

        {
            this.bxd = this$0;
        }

        public void onClick(View view) {
            int id = view.getId();
            if (id == g.cb_tip) {
                if (this.bxd.bwZ != null) {
                    this.bxd.bwZ.ra();
                }
            } else if (id == g.tv_cancel) {
                if (!(this.bxd.mContext == null || this.bxd.mContext.isFinishing())) {
                    this.bxd.bxa.dismiss();
                }
                if (this.bxd.bwZ != null) {
                    this.bxd.bwZ.rb();
                }
            } else if (id == g.tv_other) {
                if (!(this.bxd.mContext == null || this.bxd.mContext.isFinishing())) {
                    this.bxd.bxa.dismiss();
                }
                if (this.bxd.bwZ != null) {
                    this.bxd.bwZ.rc();
                }
            } else if (id == g.tv_confirm) {
                if (!(this.bxd.mContext == null || this.bxd.mContext.isFinishing())) {
                    this.bxd.bxa.dismiss();
                }
                if (this.bxd.bwZ != null) {
                    this.bxd.bwZ.rd();
                }
            }
        }
    };
    private Activity mContext = null;
    private View mView;

    /* compiled from: GlobalDialog2 */
    public interface a {
        void ra();

        void rb();

        void rc();

        void rd();
    }

    public h(Activity context, a callback) {
        this.mContext = context;
        this.bwZ = callback;
        this.bxa = this;
        this.bxb = (WindowManager) this.mContext.getSystemService("window");
        this.bxc = new LayoutParams();
        Display display = this.bxb.getDefaultDisplay();
        this.bxc.width = (display.getWidth() * 8) / 10;
        this.bxc.height = -2;
        this.bxc.format = 1;
        this.bxc.type = 2003;
        this.mView = LayoutInflater.from(context).inflate(i.dialog_global, null);
        this.mView.findViewById(g.cb_tip).setOnClickListener(this.mClickListener);
        this.mView.findViewById(g.tv_cancel).setOnClickListener(this.mClickListener);
        this.mView.findViewById(g.tv_other).setOnClickListener(this.mClickListener);
        this.mView.findViewById(g.tv_confirm).setOnClickListener(this.mClickListener);
        this.eN = (TextView) this.mView.findViewById(g.tv_title);
        this.boi = (TextView) this.mView.findViewById(g.tv_msg);
        this.bwJ = (TextView) this.mView.findViewById(g.tv_cancel);
        this.bwS = (TextView) this.mView.findViewById(g.tv_other);
        this.bwK = (TextView) this.mView.findViewById(g.tv_confirm);
    }

    public void showDialog() {
        if (this.mContext != null && !this.mContext.isFinishing()) {
            this.bxb.addView(this.mView, this.bxc);
        }
    }

    public void dismiss() {
        if (!(this.mView == null || this.bxb == null)) {
            this.bxb.removeView(this.mView);
        }
        this.mView = null;
        this.bxb = null;
        this.bxc = null;
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

    public void u(String cancle, String other, String confirm) {
        if (cancle == null) {
            this.bwJ.setVisibility(8);
            this.mView.findViewById(g.split_cancle).setVisibility(8);
        } else {
            this.bwJ.setVisibility(0);
            this.bwJ.setText(cancle);
        }
        if (other == null) {
            this.bwS.setVisibility(8);
            this.mView.findViewById(g.split_other).setVisibility(8);
        } else {
            this.bwS.setVisibility(0);
            this.bwS.setText(other);
        }
        if (confirm != null) {
            this.bwK.setText(confirm);
        }
    }

    public void Oo() {
        this.mView.findViewById(g.cb_tip).setVisibility(0);
    }
}
