package com.MCWorld.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mcinterface.e;
import com.MCWorld.mcinterface.h;
import com.MCWorld.r;
import com.MCWorld.utils.ah;
import com.mojang.minecraftpe.MainActivity;

@SuppressLint({"HandlerLeak"})
/* compiled from: FloatLayoutRoleView */
public class n implements com.MCWorld.mcfloat.p.a {
    private static final String Sd = "Float_GameZoom";
    private static final String Sh = "Float_IsShowPoint";
    private static n UI = null;
    private static long UL = 0;
    private static final long UM = 1000;
    private static final String Us = "Float_IsFlyMode";
    private static final String Ut = "Float_IsInvincible";
    private static final String Uu = "Float_IsDeadNoDrop";
    private static final String Uv = "Float_IsRunSpeed";
    private static final String Uw = "Float_IsFallWithNoDamage";
    private static final String Ux = "Float_ReJump";
    private static final String Uy = "Float_ReJump_X";
    private static final String Uz = "Float_ReJump_Y";
    private Activity PD;
    private View Pu = null;
    private OnCheckedChangeListener QH = new OnCheckedChangeListener(this) {
        final /* synthetic */ n UR;

        {
            this.UR = this$0;
        }

        public void onCheckedChanged(CompoundButton checkbox, boolean checked) {
            int nResult = 1;
            switch (checkbox.getId()) {
                case R.id.floatChkRoleRunSpeed:
                    if (!checked) {
                        nResult = 0;
                    }
                    h.hr(nResult);
                    ah.KZ().k(n.Uv, checked);
                    r.ck().K_umengEvent(com.MCWorld.r.a.kv);
                    return;
                case R.id.floatChkRoleReJump:
                    this.UR.UP.aC(checked);
                    if (!checked) {
                        nResult = 0;
                    }
                    ah.KZ().Q(n.Ux, nResult);
                    r.ck().K_umengEvent(com.MCWorld.r.a.kw);
                    return;
                case R.id.floatChkMainInvincible:
                    h.e(checked ? e.ahz : 20, checked);
                    ah.KZ().k(n.Ut, checked);
                    r.ck().K_umengEvent(com.MCWorld.r.a.kx);
                    return;
                case R.id.floatChkMainDeadNoDrop:
                    b.qy().ax(checked);
                    ah.KZ().k(n.Uu, checked);
                    r.ck().K_umengEvent(com.MCWorld.r.a.ky);
                    return;
                case R.id.floatChkMainFlyMode:
                    h.bR(checked);
                    ah.KZ().k(n.Us, checked);
                    r.ck().K_umengEvent(com.MCWorld.r.a.kz);
                    return;
                case R.id.floatChkMainPerspective:
                    h.e(checked, true);
                    if (checked) {
                        r.ck().K_umengEvent(hlx.data.tongji.a.bOC);
                        return;
                    }
                    return;
                case R.id.floatChkFallWithNoDamage:
                    h.bv(checked);
                    ah.KZ().k(n.Uw, checked);
                    r.ck().K_umengEvent(com.MCWorld.r.a.kA);
                    return;
                case R.id.floatChkMainShowPoint1:
                    com.MCWorld.mcsdk.log.a.verbose("TAG", "DTPrint floatChkMainShowPoint1 000 \n", new Object[0]);
                    this.UR.UQ.aC(checked);
                    ah.KZ().k(n.Sh, checked);
                    r.ck().K_umengEvent(com.MCWorld.r.a.km);
                    return;
                default:
                    return;
            }
        }
    };
    private CheckBox UA;
    private CheckBox UB;
    private CheckBox UC;
    private CheckBox UD;
    private CheckBox UE;
    private CheckBox UF;
    private SeekBar UG;
    private SeekBar UH;
    private OnSeekBarChangeListener UJ = new OnSeekBarChangeListener(this) {
        final /* synthetic */ n UR;

        {
            this.UR = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            int gameZoom = bar.getProgress();
            h.gK(gameZoom);
            ah.KZ().Q(n.Sd, gameZoom);
            r.ck().K_umengEvent(com.MCWorld.r.a.ko);
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            this.UR.dH(progress);
        }
    };
    private int UK = 0;
    private OnSeekBarChangeListener UN = new OnSeekBarChangeListener(this) {
        final /* synthetic */ n UR;

        {
            this.UR = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            this.UR.UK = bar.getProgress();
            h.gP(this.UR.UK);
            r.ck().K_umengEvent(com.MCWorld.r.a.kr);
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            ((TextView) this.UR.Pu.findViewById(R.id.floatTextShowRoleLevel1)).setText(String.valueOf(progress));
        }
    };
    private int UO = 0;
    private a UP = new a();
    private b UQ = new b();

    /* compiled from: FloatLayoutRoleView */
    private class a {
        private boolean QJ;
        private boolean QK;
        private int QL;
        private int QM;
        private MainActivity QO;
        @SuppressLint({"ClickableViewAccessibility"})
        private OnTouchListener QP;
        final /* synthetic */ n UR;
        private c US;

        private a(n nVar) {
            this.UR = nVar;
            this.QJ = false;
            this.QK = false;
            this.QL = 0;
            this.QM = 0;
            this.US = null;
            this.QO = null;
            this.QP = new OnTouchListener(this) {
                float UT;
                float UV;
                float UW;
                float UX;
                final /* synthetic */ a UY;

                {
                    this.UY = this$1;
                }

                public boolean onTouch(View v, MotionEvent event) {
                    if (!this.UY.QK) {
                        return false;
                    }
                    float x = event.getRawX();
                    float y = event.getRawY();
                    switch (event.getAction()) {
                        case 0:
                            this.UW = x;
                            this.UT = x;
                            this.UX = y;
                            this.UV = y;
                            v.setBackgroundResource(R.drawable.ic_float_rolejmp_pushed);
                            if (h.xw()) {
                                return true;
                            }
                            h.zz();
                            return true;
                        case 1:
                            v.setBackgroundResource(R.drawable.ic_float_rolejmp_normal);
                            int minMove = (int) (9.0f * a.qu());
                            if (Math.abs(x - this.UT) <= ((float) minMove) && Math.abs(y - this.UV) <= ((float) minMove)) {
                                return true;
                            }
                            ah.KZ().Q(n.Uy, this.UY.QL);
                            ah.KZ().Q(n.Uy, this.UY.QM);
                            return true;
                        case 2:
                            this.UY.QL = (int) (((float) this.UY.QL) - (x - this.UW));
                            this.UY.QM = (int) (((float) this.UY.QM) - (y - this.UX));
                            this.UY.US.update(this.UY.QL, this.UY.QM, -1, -1, true);
                            this.UW = x;
                            this.UX = y;
                            return true;
                        default:
                            return true;
                    }
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
                        this.US.showAtLocation(this.QO.getWindow().getDecorView(), 85, this.QL, this.QM);
                    } else {
                        this.US.dismiss();
                    }
                }
            }
        }

        private void rj() {
            if (h.zu() != null) {
                this.QO = (MainActivity) h.zu().get();
                if (this.QO != null) {
                    RelativeLayout layout = new RelativeLayout(this.QO);
                    layout.setBackgroundResource(R.drawable.ic_float_rolejmp_normal);
                    layout.setOnTouchListener(this.QP);
                    int width = this.QO.getWindow().getDecorView().getWidth();
                    if (width > 500) {
                        if (width > com.MCWorld.module.h.asn) {
                            if (width > 1000) {
                                if (width > com.MCWorld.video.recorder.a.boW) {
                                    if (width <= 2000) {
                                    }
                                }
                            }
                        }
                    }
                    this.US = new c(this.UR, this.QO);
                    this.US.setContentView(layout);
                    this.US.setWidth(a.cX(80));
                    this.US.setHeight(a.cX(80));
                    this.US.setBackgroundDrawable(new ColorDrawable(0));
                    this.QL = ah.KZ().get_config_sp_intVal(n.Uy, 40);
                    this.QM = ah.KZ().get_config_sp_intVal(n.Uz, 20);
                    this.QJ = true;
                }
            }
        }
    }

    /* compiled from: FloatLayoutRoleView */
    private class b {
        private boolean QJ;
        private boolean QK;
        private MainActivity QO;
        final /* synthetic */ n UR;
        private TextView UZ;
        private d Va;

        private b(n nVar) {
            this.UR = nVar;
            this.UZ = null;
            this.Va = null;
            this.QO = null;
            this.QJ = false;
            this.QK = false;
        }

        public void aC(boolean bShow) {
            if (bShow != this.QK) {
                if (!this.QJ && bShow) {
                    rj();
                }
                if (this.QJ) {
                    this.QK = bShow;
                    if (bShow) {
                        this.Va.showAtLocation(this.QO.getWindow().getDecorView(), 49, 0, (a.qv() / 4) * 3);
                        sa();
                        return;
                    }
                    this.Va.dismiss();
                }
            }
        }

        public void sa() {
            String _tmpShowText = "";
            if (this.QK) {
                float x = h.zF();
                float y = h.zG();
                float z = h.zH();
                try {
                    _tmpShowText = String.format("X:%.0f | Z:%.0f | Y:%.0f", new Object[]{Float.valueOf(x), Float.valueOf(z), Float.valueOf(y)});
                } catch (Exception e) {
                    HLog.info("TAG", "DTPrint String.format Error " + e, new Object[0]);
                }
                this.UZ.setText(_tmpShowText);
            }
        }

        private void rj() {
            if (h.zu() != null) {
                this.QO = (MainActivity) h.zu().get();
                if (this.QO != null) {
                    this.UZ = new TextView(this.QO);
                    this.UZ.setGravity(17);
                    this.UZ.setTextColor(-1);
                    this.UZ.setTextSize(13.0f);
                    RelativeLayout layout = new RelativeLayout(this.QO);
                    layout.addView(this.UZ, new LayoutParams(-2, -2));
                    this.Va = new d(this.UR, this.QO);
                    this.Va.setBackgroundDrawable(new ColorDrawable(0));
                    this.Va.setContentView(layout);
                    this.Va.setWidth(-2);
                    this.Va.setHeight(-2);
                    this.QJ = true;
                }
            }
        }
    }

    /* compiled from: FloatLayoutRoleView */
    private class c extends PopupWindow {
        final /* synthetic */ n UR;

        public c(n nVar, Context c) {
            this.UR = nVar;
            super(c);
        }
    }

    /* compiled from: FloatLayoutRoleView */
    private class d extends PopupWindow {
        final /* synthetic */ n UR;

        public d(n nVar, Context c) {
            this.UR = nVar;
            super(c);
        }
    }

    public void qO() {
        this.UQ.sa();
        if ((h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) && VERSION.SDK_INT >= 11) {
            rZ();
        }
    }

    public void qN() {
        this.UP.aC(false);
        this.UQ.aC(false);
    }

    public void cd(String mapName) {
        int i = 1;
        boolean checked = ah.KZ().j(Ut, false);
        this.UB.setChecked(checked);
        if (checked) {
            h.e(e.ahz, true);
        }
        checked = ah.KZ().j(Uu, true);
        this.UC.setChecked(checked);
        b.qy().ax(checked);
        checked = ah.KZ().j(Us, false);
        this.UA.setChecked(checked);
        h.bR(checked);
        if (h.zx() == 0 || h.zx() == 1) {
            boolean z;
            int nResult = ah.KZ().get_config_sp_intVal(Ux, 0);
            CheckBox checkBox = this.UD;
            if (nResult == 0) {
                z = false;
            } else {
                z = true;
            }
            checkBox.setChecked(z);
            a aVar = this.UP;
            if (nResult == 0) {
                z = false;
            } else {
                z = true;
            }
            aVar.aC(z);
            checked = ah.KZ().j(Uv, false);
            this.UE.setChecked(checked);
            if (!checked) {
                i = 0;
            }
            h.hr(i);
            checked = ah.KZ().j(Uw, false);
            this.UF.setChecked(checked);
            h.bv(checked);
            int gameZoom = ah.KZ().get_config_sp_intVal(Sd, 2);
            h.gK(gameZoom);
            this.UG.setProgress(gameZoom);
            dH(gameZoom);
        }
        boolean checkedex = ah.KZ().j(Sh, false);
        ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowPoint1)).setChecked(checkedex);
        this.UQ.aC(checkedex);
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
    }

    public void qH() {
        if (this.UB.isChecked()) {
            h.e(e.ahz, true);
        }
    }

    public static synchronized n rU() {
        n nVar;
        synchronized (n.class) {
            if (UI == null) {
                UI = new n();
            }
            nVar = UI;
        }
        return nVar;
    }

    public void rV() {
        this.UA.setChecked(true);
    }

    public void rW() {
        this.UA.setChecked(false);
    }

    public void rg() {
        if (this.Pu != null) {
            this.UB.setChecked(false);
            this.UE.setChecked(false);
            this.UA.setChecked(false);
        }
    }

    public void rX() {
        this.UA.setChecked(true);
    }

    private void dH(int progress) {
        String strText = "";
        switch (progress) {
            case 0:
                strText = "最近";
                break;
            case 1:
                strText = "很近";
                break;
            case 2:
                strText = "正常";
                break;
            case 3:
                strText = "很远";
                break;
            case 4:
                strText = "最远";
                break;
        }
        ((TextView) this.Pu.findViewById(R.id.floatTextGameZoom1)).setText(strText);
    }

    public void a(Activity inputActivity, View inputView) {
        this.PD = inputActivity;
        this.Pu = inputView;
        this.Pu.setVisibility(8);
        this.UD = (CheckBox) this.Pu.findViewById(R.id.floatChkRoleReJump);
        this.UA = (CheckBox) this.Pu.findViewById(R.id.floatChkMainFlyMode);
        this.UB = (CheckBox) this.Pu.findViewById(R.id.floatChkMainInvincible);
        this.UC = (CheckBox) this.Pu.findViewById(R.id.floatChkMainDeadNoDrop);
        this.UE = (CheckBox) this.Pu.findViewById(R.id.floatChkRoleRunSpeed);
        this.UF = (CheckBox) this.Pu.findViewById(R.id.floatChkFallWithNoDamage);
        this.UD.setOnCheckedChangeListener(this.QH);
        this.UA.setOnCheckedChangeListener(this.QH);
        this.UB.setOnCheckedChangeListener(this.QH);
        this.UC.setOnCheckedChangeListener(this.QH);
        this.UE.setOnCheckedChangeListener(this.QH);
        this.UF.setOnCheckedChangeListener(this.QH);
        this.UG = (SeekBar) this.Pu.findViewById(R.id.floatSeekGameZoom1);
        this.UG.setOnSeekBarChangeListener(this.UJ);
        this.UG.setMax(4);
        if (h.zx() == 2) {
            this.UE.setText("疾跑");
            this.UD.setVisibility(8);
            this.UF.setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatChkMainLineInvincible)).setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatChkMainLineNoDamage)).setVisibility(8);
            this.UH = (SeekBar) this.Pu.findViewById(R.id.floatSeekMainRoleLevel1);
            this.UH.setOnSeekBarChangeListener(this.UN);
            this.UH.setMax(3000);
            ((TextView) this.Pu.findViewById(R.id.floatTextShowRoleLevel1)).setText("0");
        } else if (h.zx() == 3) {
            this.UC.setOnCheckedChangeListener(this.QH);
            if (VERSION.SDK_INT < 11) {
                this.UE.setVisibility(8);
                ((TextView) this.Pu.findViewById(R.id.floatLineViewDistance)).setVisibility(8);
                ((TextView) this.Pu.findViewById(R.id.floatTextViewDistance1)).setVisibility(8);
                ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutGameZoom1)).setVisibility(8);
                ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutRoleLevel1)).setVisibility(8);
            } else {
                this.UE.setText("疾跑");
            }
            this.UD.setVisibility(8);
            this.UF.setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatChkMainLineInvincible)).setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatChkMainLineNoDamage)).setVisibility(8);
            this.UH = (SeekBar) this.Pu.findViewById(R.id.floatSeekMainRoleLevel1);
            this.UH.setOnSeekBarChangeListener(this.UN);
            this.UH.setMax(3000);
            ((TextView) this.Pu.findViewById(R.id.floatTextShowRoleLevel1)).setText("0");
        } else if (h.zx() == 5 || h.zx() == 7) {
            this.UC.setOnCheckedChangeListener(this.QH);
            this.UE.setText("疾跑");
            this.UD.setVisibility(8);
            this.UF.setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatChkMainLineInvincible)).setVisibility(8);
            ((TextView) this.Pu.findViewById(R.id.floatChkMainLineNoDamage)).setVisibility(8);
            this.UH = (SeekBar) this.Pu.findViewById(R.id.floatSeekMainRoleLevel1);
            this.UH.setOnSeekBarChangeListener(this.UN);
            this.UH.setMax(3000);
            ((TextView) this.Pu.findViewById(R.id.floatTextShowRoleLevel1)).setText("0");
        } else {
            ((LinearLayout) this.Pu.findViewById(R.id.floatLinearLayoutRoleLevel1)).setVisibility(8);
        }
        ((CheckBox) this.Pu.findViewById(R.id.floatChkMainShowPoint1)).setOnCheckedChangeListener(this.QH);
    }

    public static long rY() {
        return UL;
    }

    public static void T(long nPerMirTickCallBack) {
        UL = nPerMirTickCallBack;
    }

    private void rZ() {
        long curTimeMillis = System.currentTimeMillis();
        if (curTimeMillis > rY() + 1000) {
            T(curTimeMillis);
            int mPlayerLevel = h.zo();
            if (this.Pu.getVisibility() == 0) {
                this.UH.setProgress(mPlayerLevel);
            }
        }
    }
}
