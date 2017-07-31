package com.huluxia.mcfloat;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mcfloat.p.a;
import com.huluxia.mcinterface.h;
import com.huluxia.module.n;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.utils.ah;
import com.huluxia.widget.ButtonImageRadio;
import hlx.mcfairy.b;

/* compiled from: FloatLayoutFuncView */
public class i implements a {
    private static final int Sb = 0;
    private static final int Sc = 1;
    private static final String Sd = "Float_GameZoom";
    private static final String Se = "Float_LockTime";
    private static final String Sf = "Float_IsLockTime";
    private static final String Sg = "Float_IsShowBlood";
    private static final String Sh = "Float_IsShowPoint";
    private static final String Si = "Float_GameGUISizeEx";
    private static i Sq;
    private View Pu = null;
    private OnCheckedChangeListener QH = new OnCheckedChangeListener(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void onCheckedChanged(CompoundButton checkbox, boolean checked) {
            switch (checkbox.getId()) {
                case R.id.floatChkMainLockTime:
                    this.SD.Sz = this.SD.dq(this.SD.Sj.getProgress());
                    this.SD.SB = checked;
                    ah.KZ().Q(i.Se, this.SD.Sz);
                    ah.KZ().k(i.Sf, checked);
                    if (true == checked) {
                        r.ck().K(r.a.kq);
                        return;
                    }
                    return;
                case R.id.floatChkMainShowBlood:
                    h.bT(checked);
                    ah.KZ().k(i.Sg, checked);
                    if (checked) {
                        r.ck().K(r.a.kn);
                        return;
                    }
                    return;
                case R.id.floatChkMainHelpFairy:
                    b.aK(checked);
                    if (checked) {
                        r.ck().K(hlx.data.tongji.a.bOK);
                    }
                    ah.KZ().k(hlx.data.localstore.a.bLv, checked);
                    return;
                case R.id.floatChkMainSmallMap:
                    h.bS(checked);
                    if (true == checked) {
                        r.ck().K(r.a.kp);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private boolean SB = false;
    private OnSeekBarChangeListener SC = new OnSeekBarChangeListener(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            this.SD.Sz = this.SD.dq(bar.getProgress());
            h.hs(this.SD.Sz);
            r.ck().K(r.a.kl);
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            String strText = "";
            if (progress == 0) {
                strText = "清晨";
            }
            if (progress == 1) {
                strText = "白天";
            }
            if (progress == 2) {
                strText = "傍晚";
            }
            if (progress == 3) {
                strText = "黑夜";
            }
            ((TextView) this.SD.Pu.findViewById(R.id.floatTextMainDayTime)).setText(strText);
        }
    };
    private SeekBar Sj;
    private SeekBar Sk;
    private SeekBar Sl;
    private SeekBar Sm;
    private ButtonImageRadio Sn;
    private ButtonImageRadio So;
    private int Sp = -1;
    private int Sr = 0;
    private OnSeekBarChangeListener Ss = new OnSeekBarChangeListener(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            this.SD.Sr = bar.getProgress();
            i.dk(this.SD.Sr);
            ah.KZ().Q(i.Si, this.SD.Sr);
            r.ck().K(r.a.ks);
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            TextView textView = (TextView) this.SD.Pu.findViewById(R.id.floatTextShowGUISize);
            switch (progress) {
                case 0:
                    textView.setText("原始");
                    return;
                case 1:
                    textView.setText("模式1");
                    return;
                case 2:
                    textView.setText("模式2");
                    return;
                default:
                    return;
            }
        }
    };
    private int St = 0;
    private boolean Su = false;
    private OnSeekBarChangeListener Sv = new OnSeekBarChangeListener(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            this.SD.St = bar.getProgress();
            i.dm(this.SD.St);
            if (this.SD.ru()) {
                t.l(this.SD.Pu.getContext(), "进雪地就会下雪");
                this.SD.aE(false);
            }
            r.ck().K(r.a.kt);
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            TextView textView = (TextView) this.SD.Pu.findViewById(R.id.floatTextWheatherRain);
            switch (progress) {
                case 0:
                    textView.setText("雨雪 (关)");
                    return;
                case 1:
                    textView.setText("雨雪 (小)");
                    return;
                case 2:
                    textView.setText("雨雪 (大)");
                    return;
                default:
                    return;
            }
        }
    };
    private int Sw = 0;
    private OnSeekBarChangeListener Sx = new OnSeekBarChangeListener(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            this.SD.Sw = bar.getProgress();
            i.do(this.SD.Sw);
            r.ck().K(r.a.ku);
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            TextView textView = (TextView) this.SD.Pu.findViewById(R.id.floatTextWheatherLightning);
            switch (progress) {
                case 0:
                    textView.setText("闪电 (关)");
                    return;
                case 1:
                    textView.setText("闪电 (小)");
                    return;
                case 2:
                    textView.setText("闪电 (大)");
                    return;
                default:
                    return;
            }
        }
    };
    protected Handler Sy = new Handler(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    h.ht(0);
                    if (h.zx() == 5 || h.zx() == 7) {
                        n.rU().rW();
                        return;
                    }
                    return;
                case 1:
                    h.ht(1);
                    if (h.zx() == 5 || h.zx() == 7) {
                        n.rU().rV();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private int Sz = 0;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ i SD;

        {
            this.SD = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatBtnMainLiveMode:
                    if (this.SD.Sp != 0) {
                        this.SD.Sp = 0;
                        if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                            Message _message = new Message();
                            _message.what = this.SD.Sp;
                            this.SD.Sy.sendMessage(_message);
                        } else {
                            h.ht(this.SD.Sp);
                            b.qy().qI();
                        }
                        r.ck().K(r.a.kk);
                        return;
                    }
                    return;
                case R.id.floatBtnMainCreateMode:
                    if (this.SD.Sp != 1) {
                        this.SD.Sp = 1;
                        if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                            Message _message1 = new Message();
                            _message1.what = this.SD.Sp;
                            this.SD.Sy.sendMessage(_message1);
                        } else {
                            b.qy().ay(true);
                            h.ht(this.SD.Sp);
                        }
                        r.ck().K(r.a.kj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    public void qN() {
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (isShow) {
            qO();
        }
    }

    public void qO() {
        rv();
        if (h.zx() == 2 || h.zx() != 3 || VERSION.SDK_INT >= 11) {
        }
    }

    public void cd(String mapName) {
        boolean checked;
        if (h.zx() == 0 || h.zx() == 1) {
            checked = ah.KZ().j(Sg, false);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setChecked(checked);
            h.bT(checked);
        } else if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            this.Sz = ah.KZ().P(Se, 0);
            this.SB = ah.KZ().j(Sf, false);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainLockTime)).setChecked(this.SB);
            rv();
        } else {
            ah.KZ().Q(Si, 0);
            dl(0);
            checked = ah.KZ().j(Sg, false);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setChecked(checked);
            h.bT(checked);
            this.Sl.setProgress(0);
            this.Sm.setProgress(0);
            ((TextView) this.Pu.findViewById(R.id.floatTextWheatherRain)).setText("雨雪 (关)");
            ((TextView) this.Pu.findViewById(R.id.floatTextWheatherLightning)).setText("闪电 (关)");
            aE(true);
        }
        this.Sp = h.getGameType();
        if (this.Sp == 1) {
            this.Sn.setChecked(true);
        }
        if (this.Sp == 0) {
            this.So.setChecked(true);
        }
        ((CheckBox) this.Pu.findViewById(R.id.floatChkMainSmallMap)).setChecked(false);
        h.bS(false);
        checked = ah.KZ().j(hlx.data.localstore.a.bLv, true);
        if (checked) {
            r.ck().K(hlx.data.tongji.a.bOK);
        }
        ((CheckBox) this.Pu.findViewById(R.id.floatChkMainHelpFairy)).setChecked(checked);
    }

    public static synchronized i rt() {
        i iVar;
        synchronized (i.class) {
            if (Sq == null) {
                Sq = new i();
            }
            iVar = Sq;
        }
        return iVar;
    }

    public void rg() {
        if (this.Pu != null) {
            this.So.setChecked(true);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setChecked(true);
        }
    }

    public void b(View inputView) {
        this.Pu = inputView;
        this.Pu.setVisibility(8);
        this.So = (ButtonImageRadio) this.Pu.findViewById(R.id.floatBtnMainLiveMode);
        this.Sn = (ButtonImageRadio) this.Pu.findViewById(R.id.floatBtnMainCreateMode);
        this.So.aI(R.drawable.ic_float_role_livetrue, R.drawable.ic_float_role_livefalse);
        this.Sn.aI(R.drawable.ic_float_role_createtrue, R.drawable.ic_float_role_createfalse);
        this.So.setOnClickListener(this.mClickListener);
        this.Sn.setOnClickListener(this.mClickListener);
        if (h.zx() == 2) {
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainSmallMap)).setOnCheckedChangeListener(this.QH);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setOnCheckedChangeListener(this.QH);
            if (VERSION.SDK_INT < 11) {
                ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutGUISize)).setVisibility(8);
            }
            this.Sk = (SeekBar) this.Pu.findViewById(R.id.floatSeekMainGUISize);
            this.Sk.setOnSeekBarChangeListener(this.Ss);
            this.Sk.setMax(2);
            this.Sl = (SeekBar) this.Pu.findViewById(R.id.floatSeekWheatherRain);
            this.Sl.setOnSeekBarChangeListener(this.Sv);
            this.Sl.setMax(2);
            this.Sm = (SeekBar) this.Pu.findViewById(R.id.floatSeekWheatherLightning);
            this.Sm.setOnSeekBarChangeListener(this.Sx);
            this.Sm.setMax(2);
        } else if (h.zx() == 3) {
            if (VERSION.SDK_INT < 11) {
                ((CheckBox) this.Pu.findViewById(R.id.floatChkMainSmallMap)).setVisibility(8);
                ((TextView) this.Pu.findViewById(R.id.floatLineMainSmallMap)).setVisibility(8);
                ((LinearLayout) this.Pu.findViewById(R.id.floatWheatherRainLinearLayout)).setVisibility(8);
                ((TextView) this.Pu.findViewById(R.id.floatWheatherRainGap)).setVisibility(8);
                ((LinearLayout) this.Pu.findViewById(R.id.floatWheatherLightningLinearLayout)).setVisibility(8);
                ((TextView) this.Pu.findViewById(R.id.floatWheatherLightningGap)).setVisibility(8);
                ((CheckBox) this.Pu.findViewById(R.id.floatChkMainLockTime)).setVisibility(8);
            } else {
                ((CheckBox) this.Pu.findViewById(R.id.floatChkMainSmallMap)).setOnCheckedChangeListener(this.QH);
                ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setOnCheckedChangeListener(this.QH);
                this.Sl = (SeekBar) this.Pu.findViewById(R.id.floatSeekWheatherRain);
                this.Sl.setOnSeekBarChangeListener(this.Sv);
                this.Sl.setMax(2);
                this.Sm = (SeekBar) this.Pu.findViewById(R.id.floatSeekWheatherLightning);
                this.Sm.setOnSeekBarChangeListener(this.Sx);
                this.Sm.setMax(2);
            }
            ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutGUISize)).setVisibility(8);
        } else if (h.zx() == 5 || h.zx() == 7) {
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainSmallMap)).setOnCheckedChangeListener(this.QH);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setOnCheckedChangeListener(this.QH);
            this.Sl = (SeekBar) this.Pu.findViewById(R.id.floatSeekWheatherRain);
            this.Sl.setOnSeekBarChangeListener(this.Sv);
            this.Sl.setMax(2);
            this.Sm = (SeekBar) this.Pu.findViewById(R.id.floatSeekWheatherLightning);
            this.Sm.setOnSeekBarChangeListener(this.Sx);
            this.Sm.setMax(2);
            ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutGUISize)).setVisibility(8);
        } else {
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainSmallMap)).setOnCheckedChangeListener(this.QH);
            ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowBlood)).setOnCheckedChangeListener(this.QH);
            ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutGUISize)).setVisibility(8);
            ((LinearLayout) this.Pu.findViewById(R.id.floatWheatherRainLinearLayout)).setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatWheatherRainGap)).setVisibility(8);
            ((LinearLayout) this.Pu.findViewById(R.id.floatWheatherLightningLinearLayout)).setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatWheatherLightningGap)).setVisibility(8);
        }
        ((CheckBox) this.Pu.findViewById(R.id.floatChkMainLockTime)).setOnCheckedChangeListener(this.QH);
        this.Sj = (SeekBar) this.Pu.findViewById(R.id.floatSeekMainDayTime);
        this.Sj.setOnSeekBarChangeListener(this.SC);
        this.Sj.setMax(3);
        this.Pu.findViewById(R.id.floatLineMainHelpFairy).setVisibility(0);
        this.Pu.findViewById(R.id.floatChkMainHelpFairy).setVisibility(0);
        ((CheckBox) this.Pu.findViewById(R.id.floatChkMainHelpFairy)).setOnCheckedChangeListener(this.QH);
    }

    private static void dk(int progress) {
        if (progress == 0) {
            h.setmGameGUISize(0.0f);
        } else if (1 == progress) {
            h.setmGameGUISize(2.0f);
        } else if (2 == progress) {
            h.setmGameGUISize(3.0f);
        }
    }

    private void dl(int progress) {
        this.Sk.setProgress(progress);
    }

    private static void dm(int progress) {
        if (progress == 0) {
            h.d(0, 0.0f);
        } else if (1 == progress) {
            h.d(0, 0.5f);
        } else if (2 == progress) {
            h.d(0, 1.0f);
        }
    }

    private void dn(int progress) {
        this.Sl.setProgress(progress);
    }

    public boolean ru() {
        return this.Su;
    }

    public void aE(boolean bMarkWordsFlag) {
        this.Su = bMarkWordsFlag;
    }

    private static void do(int progress) {
        if (progress == 0) {
            h.d(1, 0.0f);
        } else if (1 == progress) {
            h.d(1, 0.7f);
        } else if (2 == progress) {
            h.d(1, 1.0f);
        }
    }

    private void dp(int progress) {
        this.Sm.setProgress(progress);
    }

    private void rv() {
        if (this.SB) {
            int gameTime = h.wx();
            if (this.Sz != 0 && Math.abs(gameTime - this.Sz) >= 300) {
                h.hs(this.Sz);
            } else if (this.Pu.getVisibility() == 0) {
                this.Sj.setProgress(dr(gameTime));
            }
        }
    }

    private int dq(int progress) {
        if (h.zx() == 2) {
            if (progress == 0) {
                return 100;
            }
            if (progress == 1) {
                return 5000;
            }
            if (progress == 2) {
                return 12000;
            }
            if (progress == 3) {
                return 14000;
            }
        } else if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            if (VERSION.SDK_INT < 11) {
                if (progress == 0) {
                    return 1944;
                }
                if (progress == 1) {
                    return n.awR;
                }
                if (progress == 2) {
                    return 4005;
                }
                if (progress == 3) {
                    return 5486;
                }
            } else if (progress == 0) {
                return 100;
            } else {
                if (progress == 1) {
                    return 5000;
                }
                if (progress == 2) {
                    return 12000;
                }
                if (progress == 3) {
                    return 14000;
                }
            }
        } else if (progress == 0) {
            return 19100;
        } else {
            if (progress == 1) {
                return 3000;
            }
            if (progress == 2) {
                return 9700;
            }
            if (progress == 3) {
                return 11000;
            }
        }
        return 0;
    }

    private int dr(int time) {
        if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            if (time > 0 && time <= com.huluxia.module.h.arp) {
                return 0;
            }
            if (time > 11800 && time <= 12600) {
                return 2;
            }
            if (time > 12600 && time <= 22500) {
                return 3;
            }
        } else if (time > 8500 && time <= 10500) {
            return 2;
        } else {
            if (time > 10500 && time <= 19000) {
                return 3;
            }
            if (time > 19000 && time <= 20000) {
                return 0;
            }
        }
        return 1;
    }
}
