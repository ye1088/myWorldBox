package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.k;
import com.huluxia.mcfloat.p.a;
import com.huluxia.mcgame.netuser.b;
import com.huluxia.mcinterface.h;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.r;

@SuppressLint({"InflateParams"})
/* compiled from: FloatLayoutPlayerView */
public class l implements a {
    private static l TP;
    private View Pu = null;
    private TextView TB;
    private TextView TC;
    private TextView TD;
    private TextView TF;
    private TextView TG;
    private RelativeLayout TH;
    private RelativeLayout TI;
    private RelativeLayout TJ;
    private RelativeLayout TK;
    private TextView TL;
    private TextView TM;
    private TextView TN;
    private TextView TO;
    private TextView Tv;
    private TextView Tw;
    private TextView Tx;
    private TextView Ty;
    private TextView Tz;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ l TQ;

        {
            this.TQ = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatBtnTrace02:
                    this.TQ.dD(0);
                    return;
                case R.id.floatBtnTrace03:
                    this.TQ.dD(1);
                    return;
                case R.id.floatBtnTrace04:
                    this.TQ.dD(2);
                    return;
                case R.id.floatBtnTrace05:
                    this.TQ.dD(3);
                    return;
                default:
                    return;
            }
        }
    };

    public static synchronized l rF() {
        l lVar;
        synchronized (l.class) {
            if (TP == null) {
                TP = new l();
            }
            lVar = TP;
        }
        return lVar;
    }

    public void a(Activity inputActivity, View inputView) {
        this.Pu = inputView;
        this.Pu.setVisibility(8);
        this.Tv = (TextView) this.Pu.findViewById(R.id.floatTextNetPlayerCnt);
        this.Tw = (TextView) this.Pu.findViewById(R.id.floatTextNetPlayer01);
        this.Tx = (TextView) this.Pu.findViewById(R.id.floatTextNetPlayer02);
        this.Ty = (TextView) this.Pu.findViewById(R.id.floatBtnTrace02);
        this.Tz = (TextView) this.Pu.findViewById(R.id.floatTextNetPlayer03);
        this.TB = (TextView) this.Pu.findViewById(R.id.floatBtnTrace03);
        this.TC = (TextView) this.Pu.findViewById(R.id.floatTextNetPlayer04);
        this.TD = (TextView) this.Pu.findViewById(R.id.floatBtnTrace04);
        this.TF = (TextView) this.Pu.findViewById(R.id.floatTextNetPlayer05);
        this.TG = (TextView) this.Pu.findViewById(R.id.floatBtnTrace05);
        this.TH = (RelativeLayout) this.Pu.findViewById(R.id.floatRelativeLayoutNetPlayer02);
        this.TL = (TextView) this.Pu.findViewById(R.id.floatLayoutLineNetPlayer02);
        this.TI = (RelativeLayout) this.Pu.findViewById(R.id.floatRelativeLayoutNetPlayer03);
        this.TM = (TextView) this.Pu.findViewById(R.id.floatLayoutLineNetPlayer03);
        this.TJ = (RelativeLayout) this.Pu.findViewById(R.id.floatRelativeLayoutNetPlayer04);
        this.TN = (TextView) this.Pu.findViewById(R.id.floatLayoutLineNetPlayer04);
        this.TK = (RelativeLayout) this.Pu.findViewById(R.id.floatRelativeLayoutNetPlayer05);
        this.TO = (TextView) this.Pu.findViewById(R.id.floatLayoutLineNetPlayer05);
        this.Ty.setOnClickListener(this.mClickListener);
        this.TB.setOnClickListener(this.mClickListener);
        this.TD.setOnClickListener(this.mClickListener);
        this.TG.setOnClickListener(this.mClickListener);
    }

    private void dC(int index) {
        rH();
        b _tmpMCNetUser = com.huluxia.mcgame.netuser.a.yQ().hf(index);
        if (_tmpMCNetUser.isValid()) {
            DTSDKManagerEx.a(_tmpMCNetUser.yX(), h.zF(), h.zG(), h.zH());
        } else {
            k.l(this.Pu.getContext(), "玩家已经离开!");
        }
    }

    private void dD(int index) {
        rH();
        b _tmpMCNetUser = com.huluxia.mcgame.netuser.a.yQ().hf(index);
        if (_tmpMCNetUser.isValid()) {
            h.b(_tmpMCNetUser.yj(), _tmpMCNetUser.yk(), _tmpMCNetUser.yl());
            r.ck().K(r.a.kB);
            return;
        }
        k.l(this.Pu.getContext(), "玩家已经离开!");
    }

    private void rG() {
        this.Tv.setText("房间成员(" + String.valueOf(com.huluxia.mcgame.netuser.a.yQ().yU() + 1) + "/5)");
        this.Tw.setText(com.huluxia.mcgame.netuser.a.yQ().yW() + "(自己)");
        b _tmpMCNetUser = com.huluxia.mcgame.netuser.a.yQ().hf(0);
        if (_tmpMCNetUser.isValid()) {
            this.TH.setVisibility(0);
            this.TL.setVisibility(0);
            this.Tx.setText(_tmpMCNetUser.getName());
        } else {
            this.TH.setVisibility(8);
            this.TL.setVisibility(8);
        }
        _tmpMCNetUser = com.huluxia.mcgame.netuser.a.yQ().hf(1);
        if (_tmpMCNetUser.isValid()) {
            this.TI.setVisibility(0);
            this.TM.setVisibility(0);
            this.Tz.setText(_tmpMCNetUser.getName());
        } else {
            this.TI.setVisibility(8);
            this.TM.setVisibility(8);
        }
        _tmpMCNetUser = com.huluxia.mcgame.netuser.a.yQ().hf(2);
        if (_tmpMCNetUser.isValid()) {
            this.TJ.setVisibility(0);
            this.TN.setVisibility(0);
            this.TC.setText(_tmpMCNetUser.getName());
        } else {
            this.TJ.setVisibility(8);
            this.TN.setVisibility(8);
        }
        _tmpMCNetUser = com.huluxia.mcgame.netuser.a.yQ().hf(3);
        if (_tmpMCNetUser.isValid()) {
            this.TK.setVisibility(0);
            this.TO.setVisibility(0);
            this.TF.setText(_tmpMCNetUser.getName());
            return;
        }
        this.TK.setVisibility(8);
        this.TO.setVisibility(8);
    }

    private void rH() {
        h.zy();
        rG();
    }

    public void qN() {
    }

    public void cd(String mapName) {
    }

    public void qO() {
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (isShow) {
            rH();
        }
    }
}
