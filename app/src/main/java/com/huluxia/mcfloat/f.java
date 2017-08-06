package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.huluxia.framework.R;
import com.huluxia.mcinterface.h;
import com.huluxia.r;
import com.mojang.minecraftpe.MainActivity;

/* compiled from: FloatLayoutCapshotView */
public class f implements com.huluxia.mcfloat.p.a {
    private static f QG;
    private View Pu = null;
    private a QA = new a();
    private CheckBox QB;
    private CheckBox QC;
    String QD = null;
    private View QE = null;
    private OnClickListener QF = null;
    private OnCheckedChangeListener QH = new OnCheckedChangeListener(this) {
        final /* synthetic */ f QI;

        {
            this.QI = this$0;
        }

        public void onCheckedChanged(CompoundButton checkbox, boolean checked) {
            switch (checkbox.getId()) {
                case R.id.BtnOpenQuickScreenShots:
                    this.QI.aA(checked);
                    return;
                case R.id.chkOpenFloatLogo:
                    this.QI.aB(checked);
                    return;
                default:
                    return;
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ f QI;

        {
            this.QI = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatBtnCapshotStart:
                    com.huluxia.mcfloat.capshot.a.un().aP(this.QI.Pu.getContext());
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatLayoutCapshotView */
    private class a {
        final /* synthetic */ f QI;
        private boolean QJ;
        private boolean QK;
        private int QL;
        private int QM;
        private b QN;
        private MainActivity QO;
        @SuppressLint({"ClickableViewAccessibility"})
        private OnTouchListener QP;

        private a(f fVar) {
            this.QI = fVar;
            this.QJ = false;
            this.QK = false;
            this.QL = 0;
            this.QM = 0;
            this.QN = null;
            this.QO = null;
            this.QP = new OnTouchListener(this) {
                final /* synthetic */ a QQ;

                {
                    this.QQ = this$1;
                }

                public boolean onTouch(View v, MotionEvent event) {
                    if (!this.QQ.QK) {
                        return false;
                    }
                    switch (event.getAction()) {
                        case 1:
                            com.huluxia.mcfloat.capshot.a.un().aQ(this.QQ.QI.Pu.getContext());
                            r.ck().K_umengEvent(com.huluxia.r.a.lt);
                            break;
                    }
                    return true;
                }
            };
        }

        public void aC(boolean bShow) {
            if (bShow != this.QK) {
                if (!this.QJ && bShow) {
                    rj();
                }
                if (this.QJ) {
                    this.QK = bShow;
                    if (bShow) {
                        this.QN.showAtLocation(this.QO.getWindow().getDecorView(), 53, this.QL, this.QM);
                    } else {
                        this.QN.dismiss();
                    }
                }
            }
        }

        private void rj() {
            if (h.zu() != null) {
                this.QO = (MainActivity) h.zu().get();
                if (this.QO != null) {
                    RelativeLayout layout = new RelativeLayout(this.QO);
                    layout.setBackgroundResource(R.drawable.ic_float_screenshots_normal);
                    layout.setOnTouchListener(this.QP);
                    int width = this.QO.getWindow().getDecorView().getWidth();
                    if (width > 500) {
                        if (width > com.huluxia.module.h.asn) {
                            if (width > 1000) {
                                if (width > com.huluxia.video.recorder.a.boW) {
                                    if (width <= 2000) {
                                    }
                                }
                            }
                        }
                    }
                    this.QN = new b(this.QI, this.QO);
                    this.QN.setContentView(layout);
                    this.QN.setWidth(a.cX(40));
                    this.QN.setHeight(a.cX(40));
                    this.QN.setBackgroundDrawable(new ColorDrawable(0));
                    this.QL = 80;
                    this.QM = 80;
                    this.QJ = true;
                }
            }
        }
    }

    /* compiled from: FloatLayoutCapshotView */
    private class b extends PopupWindow {
        final /* synthetic */ f QI;

        public b(f fVar, Context c) {
            this.QI = fVar;
            super(c);
        }
    }

    private void aA(boolean inputEnabled) {
        this.QA.aC(inputEnabled);
    }

    private void aB(boolean inputEnabled) {
        q.aJ(inputEnabled);
        r.ck().K_umengEvent(hlx.data.tongji.a.bOJ);
    }

    public void qO() {
    }

    public void qN() {
        this.QA.aC(false);
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
    }

    public void cd(String mapName) {
    }

    public void a(OnClickListener l, View v) {
        this.QF = l;
        this.QE = v;
    }

    public static synchronized f ri() {
        f fVar;
        synchronized (f.class) {
            if (QG == null) {
                QG = new f();
            }
            fVar = QG;
        }
        return fVar;
    }

    public void a(View view, Activity inputActivity) {
        this.Pu = view;
        this.Pu.setVisibility(8);
        view.findViewById(R.id.floatBtnCapshotStart).setOnClickListener(this.mClickListener);
        this.QB = (CheckBox) this.Pu.findViewById(R.id.BtnOpenQuickScreenShots);
        this.QB.setOnCheckedChangeListener(this.QH);
        this.QC = (CheckBox) this.Pu.findViewById(R.id.chkOpenFloatLogo);
        this.QC.setOnCheckedChangeListener(this.QH);
    }
}
