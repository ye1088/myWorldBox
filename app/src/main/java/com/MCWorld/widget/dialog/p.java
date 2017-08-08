package com.MCWorld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.utils.ah;
import com.MCWorld.utils.ah.b;
import com.simple.colorful.d;

/* compiled from: NetModDialog */
public class p extends Dialog {
    private Drawable akN;
    private TextView bxG;
    private a bxH = null;
    private p bxI;
    private int bxJ;
    private TextView bxK;
    private TextView bxL;
    private TextView bxM;
    private TextView eN;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ p bxN;

        {
            this.bxN = this$0;
        }

        public void onClick(View view) {
            int id = view.getId();
            if (id == g.mod_all) {
                this.bxN.hH(com.MCWorld.utils.ah.a.ALL);
                if (this.bxN.mType == b.bme) {
                    ah.KZ().lh(com.MCWorld.utils.ah.a.ALL);
                } else {
                    ah.KZ().li(com.MCWorld.utils.ah.a.ALL);
                }
                if (this.bxN.bxH != null) {
                    this.bxN.bxH.kE(com.MCWorld.utils.ah.a.ALL);
                }
                this.bxN.AQ();
            } else if (id == g.mod_wifi) {
                this.bxN.hH(com.MCWorld.utils.ah.a.bmc);
                if (this.bxN.mType == b.bme) {
                    ah.KZ().lh(com.MCWorld.utils.ah.a.bmc);
                } else {
                    ah.KZ().li(com.MCWorld.utils.ah.a.bmc);
                }
                if (this.bxN.bxH != null) {
                    this.bxN.bxH.kE(com.MCWorld.utils.ah.a.bmc);
                }
                this.bxN.AQ();
            } else if (id == g.mod_none) {
                this.bxN.hH(com.MCWorld.utils.ah.a.bmd);
                if (this.bxN.mType == b.bme) {
                    ah.KZ().lh(com.MCWorld.utils.ah.a.bmd);
                } else {
                    ah.KZ().li(com.MCWorld.utils.ah.a.bmd);
                }
                if (this.bxN.bxH != null) {
                    this.bxN.bxH.kE(com.MCWorld.utils.ah.a.bmd);
                }
                this.bxN.AQ();
            } else if (id == g.tv_cancle) {
                this.bxN.AQ();
            }
        }
    };
    private Activity mContext = null;
    private int mType;

    /* compiled from: NetModDialog */
    public interface a {
        void kE(int i);

        void rb();
    }

    public p(Activity context, int mod, int type, a callback) {
        super(context, d.RD());
        this.mContext = context;
        this.bxH = callback;
        this.bxI = this;
        this.bxJ = mod;
        this.mType = type;
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.dialog_netmod);
        findViewById(g.tv_cancle).setOnClickListener(this.mClickListener);
        this.eN = (TextView) findViewById(g.tv_title);
        this.bxG = (TextView) findViewById(g.tv_dest);
        this.bxL = (TextView) findViewById(g.mod_all);
        this.bxK = (TextView) findViewById(g.mod_wifi);
        this.bxM = (TextView) findViewById(g.mod_none);
        this.bxL.setOnClickListener(this.mClickListener);
        this.bxK.setOnClickListener(this.mClickListener);
        this.bxM.setOnClickListener(this.mClickListener);
        this.akN = this.mContext.getResources().getDrawable(d.r(this.mContext, c.setting_net_check));
        this.akN.setBounds(0, 0, this.akN.getMinimumWidth(), this.akN.getMinimumHeight());
        lW(this.mType);
        hH(this.bxJ);
    }

    private void lW(int type) {
        if (type == b.bme) {
            this.eN.setText("帖子列表缩略图");
            this.bxG.setText("显示缩略图");
            return;
        }
        this.eN.setText("帖子详情视频");
        this.bxG.setText("自动播放视频");
    }

    public void showDialog() {
    }

    public void dismiss() {
        super.dismiss();
    }

    private void AQ() {
        if (this.mContext != null && !this.mContext.isFinishing()) {
            this.bxI.dismiss();
        }
    }

    private void hH(int mod) {
        if (com.MCWorld.utils.ah.a.ALL == mod) {
            this.bxL.setCompoundDrawables(null, null, this.akN, null);
            this.bxK.setCompoundDrawables(null, null, null, null);
            this.bxM.setCompoundDrawables(null, null, null, null);
        } else if (com.MCWorld.utils.ah.a.bmc == mod) {
            this.bxL.setCompoundDrawables(null, null, null, null);
            this.bxK.setCompoundDrawables(null, null, this.akN, null);
            this.bxM.setCompoundDrawables(null, null, null, null);
        } else if (com.MCWorld.utils.ah.a.bmd == mod) {
            this.bxL.setCompoundDrawables(null, null, null, null);
            this.bxK.setCompoundDrawables(null, null, null, null);
            this.bxM.setCompoundDrawables(null, null, this.akN, null);
        }
    }
}
