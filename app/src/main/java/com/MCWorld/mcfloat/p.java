package com.MCWorld.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.MCWorld.framework.R;
import com.MCWorld.mcinterface.g;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mcinterface.l;
import com.MCWorld.r;
import com.MCWorld.widget.ButtonImageRadio;

@SuppressLint({"HandlerLeak"})
/* compiled from: FloatMainView */
public class p implements g {
    private static final int Vj = 1;
    private static final int Vk = 2;
    private static final int Vl = 3;
    private static final int Vm = 4;
    private OnClickListener QF = null;
    private ButtonImageRadio VA;
    private ButtonImageRadio VB;
    private ButtonImageRadio VC;
    private ButtonImageRadio VD;
    private ButtonImageRadio VE;
    private ButtonImageRadio VF;
    private ButtonImageRadio VG;
    private ButtonImageRadio VH;
    private ButtonImageRadio VI;
    private ButtonImageRadio VJ;
    private ButtonImageRadio VK;
    private ButtonImageRadio VL;
    private OnKeyListener VM = new OnKeyListener(this) {
        final /* synthetic */ p VP;

        {
            this.VP = this$0;
        }

        public boolean onKey(View v, int arg1, KeyEvent event) {
            int key = event.getKeyCode();
            if (event.getAction() == 0 && key == 4) {
                this.VP.QF.onClick(this.VP.Vr);
            }
            return true;
        }
    };
    private OnTouchListener VN = new OnTouchListener(this) {
        final /* synthetic */ p VP;

        {
            this.VP = this$0;
        }

        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() != 0) {
                return false;
            }
            if (v.equals(this.VP.Vr)) {
                this.VP.QF.onClick(this.VP.Vr);
            }
            return true;
        }
    };
    private OnClickListener VO = new OnClickListener(this) {
        final /* synthetic */ p VP;

        {
            this.VP = this$0;
        }

        public void onClick(View v) {
            i.rt().az(false);
            n.rU().az(false);
            l.rF().az(false);
            this.VP.Vu.az(false);
            this.VP.Vx.az(false);
            if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                m.rL().az(false);
            }
            if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                this.VP.Vt.az(false);
            }
            e.rf().az(false);
            this.VP.Vy.az(false);
            this.VP.Vv.az(false);
            this.VP.Vw.az(false);
            f.ri().az(false);
            switch (v.getId()) {
                case R.id.floatBtnMenuRole:
                    r.ck().K_umengEvent(com.MCWorld.r.a.jX);
                    n.rU().az(true);
                    return;
                case R.id.floatBtnMenuFunc:
                    r.ck().K_umengEvent(com.MCWorld.r.a.jW);
                    i.rt().az(true);
                    return;
                case R.id.floatBtnMenuNetPlayer:
                    if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                        r.ck().K_umengEvent(com.MCWorld.r.a.jY);
                        l.rF().az(true);
                        return;
                    }
                    return;
                case R.id.floatBtnMenuInsZones:
                    if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                        r.ck().K_umengEvent(com.MCWorld.r.a.jZ);
                        this.VP.Vt.az(true);
                        return;
                    }
                    return;
                case R.id.floatBtnMenuEnchant:
                    r.ck().K_umengEvent(com.MCWorld.r.a.ki);
                    this.VP.Vx.az(true);
                    return;
                case R.id.floatBtnMenuPotion:
                    if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                        r.ck().K_umengEvent(com.MCWorld.r.a.kh);
                        m.rL().az(true);
                        return;
                    }
                    return;
                case R.id.floatBtnMenuPack:
                    r.ck().K_umengEvent(com.MCWorld.r.a.ka);
                    this.VP.Vu.az(true);
                    return;
                case R.id.floatBtnMenuDeliver:
                    r.ck().K_umengEvent(com.MCWorld.r.a.kb);
                    this.VP.Vv.az(true);
                    return;
                case R.id.floatBtnMenuAnimal:
                    r.ck().K_umengEvent(com.MCWorld.r.a.kc);
                    this.VP.Vy.az(true);
                    return;
                case R.id.floatBtnMenuBuilder:
                    r.ck().K_umengEvent(com.MCWorld.r.a.kd);
                    e.rf().az(true);
                    return;
                case R.id.floatBtnMenuBackup:
                    r.ck().K_umengEvent(com.MCWorld.r.a.ke);
                    this.VP.Vw.az(true);
                    return;
                case R.id.floatBtnMenuCapshot:
                    r.ck().K_umengEvent(com.MCWorld.r.a.kf);
                    f.ri().az(true);
                    return;
                default:
                    return;
            }
        }
    };
    private boolean Vn = false;
    private Handler Vo = new Handler(this) {
        final /* synthetic */ p VP;

        {
            this.VP = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    b.qy().cb((String) msg.obj);
                    this.VP.Vu.cd((String) msg.obj);
                    this.VP.Vx.cd((String) msg.obj);
                    e.rf().cd((String) msg.obj);
                    this.VP.Vv.cd((String) msg.obj);
                    i.rt().cd((String) msg.obj);
                    n.rU().cd((String) msg.obj);
                    this.VP.Vw.cd((String) msg.obj);
                    sendEmptyMessageDelayed(3, 700);
                    this.VP.Vn = true;
                    return;
                case 2:
                    i.rt().qN();
                    n.rU().qN();
                    this.VP.Vw.qN();
                    this.VP.Vt.qN();
                    return;
                case 3:
                    if (this.VP.Vn) {
                        sendEmptyMessageDelayed(3, 700);
                        i.rt().qO();
                        n.rU().qO();
                        this.VP.Vy.qO();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private l Vp = new l(this) {
        final /* synthetic */ p VP;
        @SuppressLint({"HandlerLeak"})
        private Handler handler = new Handler(this) {
            final /* synthetic */ AnonymousClass2 VQ;

            {
                this.VQ = this$1;
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        b.qy().qF();
                        if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                            com.MCWorld.mcfloat.InstanceZones.a.sl().aL(true);
                            return;
                        }
                        return;
                    case 1:
                        if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                            h.zn();
                            return;
                        }
                        n.rU().qH();
                        this.VQ.VP.Vv.qH();
                        b.qy().qH();
                        return;
                    default:
                        return;
                }
            }
        };

        {
            this.VP = this$0;
        }

        public void se() {
            b.qy().qG();
            this.handler.sendEmptyMessage(0);
        }

        public void sf() {
            this.handler.sendEmptyMessageDelayed(1, 400);
        }
    };
    private boolean Vq = false;
    private ViewGroup Vr = null;
    private LayoutParams Vs = null;
    private j Vt;
    private k Vu;
    private g Vv;
    private d Vw;
    private h Vx;
    private a Vy;
    private ButtonImageRadio Vz;
    private WindowManager mWindowManager = null;

    /* compiled from: FloatMainView */
    public interface a {
        void az(boolean z);

        void cd(String str);

        void qN();

        void qO();
    }

    public void cd(String mapName) {
        if (mapName != null) {
            Message msg = this.Vo.obtainMessage(1);
            msg.obj = mapName;
            this.Vo.sendMessageDelayed(msg, 100);
            r.ck().K_umengEvent(com.MCWorld.r.a.jS);
        }
    }

    public void cg(String mapName) {
        if (mapName != null) {
            this.Vn = false;
            this.Vo.sendEmptyMessage(2);
        }
    }

    public void sd() {
        this.Vn = true;
        r.ck().K_umengEvent(com.MCWorld.r.a.jT);
    }

    public boolean bT() {
        return this.Vn;
    }

    public boolean sb() {
        return this.Vq;
    }

    public void aI(boolean bShow) {
        h.aW(bShow);
        if (this.Vq != bShow) {
            if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                if (h.wk() == 1) {
                    this.VA.setVisibility(8);
                    this.Vz.setVisibility(8);
                    this.VB.setVisibility(8);
                    this.VK.setVisibility(8);
                    this.VE.setVisibility(8);
                    this.VD.setVisibility(8);
                    this.VF.setVisibility(8);
                    this.VH.setVisibility(8);
                    this.VL.setVisibility(8);
                    i.rt().az(false);
                    l.rF().az(false);
                } else {
                    this.VA.setVisibility(0);
                    this.Vz.setVisibility(0);
                    this.VL.setVisibility(0);
                    this.VB.setVisibility(0);
                    this.VK.setVisibility(0);
                    this.VE.setVisibility(0);
                    this.VD.setVisibility(0);
                    this.VF.setVisibility(0);
                    this.VH.setVisibility(0);
                    if (VERSION.SDK_INT < 11) {
                        this.VH.setVisibility(8);
                        this.VF.setVisibility(8);
                        this.VB.setVisibility(8);
                        this.VL.setVisibility(8);
                    }
                }
            }
            this.Vq = bShow;
            if (bShow) {
                this.Vs.width = this.mWindowManager.getDefaultDisplay().getWidth();
                this.Vs.height = this.mWindowManager.getDefaultDisplay().getHeight();
                this.mWindowManager.addView(this.Vr, this.Vs);
                this.Vu.qO();
                this.Vx.qO();
                this.Vy.qO();
                i.rt().qO();
                return;
            }
            this.mWindowManager.removeView(this.Vr);
        }
    }

    @SuppressLint({"InflateParams"})
    public p(Activity c, OnClickListener l) {
        this.QF = l;
        this.mWindowManager = (WindowManager) c.getSystemService("window");
        this.Vr = (ViewGroup) LayoutInflater.from(c).inflate(R.layout.floor_mainview, null);
        this.Vr.setId(R.id.float_mainview);
        this.Vr.setOnKeyListener(this.VM);
        this.Vr.setOnTouchListener(this.VN);
        this.Vs = new LayoutParams();
        this.Vs.format = 1;
        this.Vs.flags = 4194304;
        this.Vr.setFocusableInTouchMode(true);
        this.Vr.findViewById(R.id.floatLayoutMenuView).setOnTouchListener(this.VN);
        View view = this.Vr.findViewById(R.id.floatLayoutFuncView);
        view.setOnTouchListener(this.VN);
        i.rt().b(view);
        view = this.Vr.findViewById(R.id.floatLayoutRoleView);
        view.setOnTouchListener(this.VN);
        n.rU().a(c, view);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_instancezones, null);
        this.Vr.addView(view, (int) (a.qu() * 300.0f), -1);
        view.setOnTouchListener(this.VN);
        this.Vt = new j(c, view);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_netplayer, null);
        this.Vr.addView(view, (int) (a.qu() * 250.0f), -1);
        view.setOnTouchListener(this.VN);
        l.rF().a(c, view);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_builder, null);
        this.Vr.addView(view, (int) (a.qu() * 250.0f), -1);
        view.setOnTouchListener(this.VN);
        e.rf().b(view);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_deliver, null);
        this.Vr.addView(view, (int) (a.qu() * 250.0f), -1);
        view.setOnTouchListener(this.VN);
        this.Vv = new g(view);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_backup, null);
        this.Vr.addView(view, (int) (280.0f * a.qu()), -1);
        view.setOnTouchListener(this.VN);
        this.Vw = new d(view, c);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_item, null);
        if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            this.Vr.addView(view, (int) (320.0f * a.qu()), -1);
        } else {
            this.Vr.addView(view, (int) (a.qu() * 300.0f), -1);
        }
        view.setOnTouchListener(this.VN);
        this.Vu = new k(view);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_enchant, null);
        this.Vr.addView(view, (int) (a.qu() * 300.0f), -1);
        view.setOnTouchListener(this.VN);
        this.Vx = new h(view, c);
        if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_potion_ex, null);
            this.Vr.addView(view, (int) (a.qu() * 300.0f), -1);
            view.setOnTouchListener(this.VN);
            m.rL().a(view, c);
        }
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_item, null);
        this.Vr.addView(view, (int) (a.qu() * 300.0f), -1);
        view.setOnTouchListener(this.VN);
        this.Vy = new c(view, c);
        view = LayoutInflater.from(c).inflate(R.layout.floor_mainview_capshot_ex, null);
        this.Vr.addView(view, (int) (220.0f * a.qu()), (int) (220.0f * a.qu()));
        view.setOnTouchListener(this.VN);
        f.ri().a(view, c);
        f.ri().a(this.QF, this.Vr);
        this.VA = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuRole);
        this.VL = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuNetPlayer);
        this.VB = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuInsZones);
        this.Vz = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuFunc);
        this.VC = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuPack);
        this.VD = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuAnimal);
        this.VE = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuDeliver);
        this.VF = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuBuilder);
        this.VG = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuCapshot);
        this.VH = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuBackup);
        this.VJ = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuEnchant);
        this.VK = (ButtonImageRadio) this.Vr.findViewById(R.id.floatBtnMenuPotion);
        this.VA.aI(R.drawable.ic_float_menu_role_true, R.drawable.ic_float_menu_role_false);
        this.VL.aI(R.drawable.ic_float_menu_netplayer_ture, R.drawable.ic_float_menu_netplayer_false);
        this.VB.aI(R.drawable.ic_float_menu_inszones_ture, R.drawable.ic_float_menu_inszones_false);
        this.Vz.aI(R.drawable.ic_float_menu_common_true, R.drawable.ic_float_menu_common_false);
        this.VC.aI(R.drawable.ic_float_menu_packtrue, R.drawable.ic_float_menu_packfalse);
        this.VD.aI(R.drawable.ic_float_menu_animaltrue, R.drawable.ic_float_menu_animalfalse);
        this.VE.aI(R.drawable.ic_float_menu_deliver_true, R.drawable.ic_float_menu_deliver_false);
        this.VF.aI(R.drawable.ic_float_menu_buildtrue, R.drawable.ic_float_menu_buildfalse);
        this.VG.aI(R.drawable.ic_float_menu_capstrue, R.drawable.ic_float_menu_capsfalse);
        this.VH.aI(R.drawable.ic_float_menu_backup_true, R.drawable.ic_float_menu_backup_false);
        this.VJ.aI(R.drawable.ic_float_menu_enchant_true, R.drawable.ic_float_menu_enchant_false);
        this.VK.aI(R.drawable.ic_float_menu_potion_true, R.drawable.ic_float_menu_potion_false);
        this.VA.setOnClickListener(this.VO);
        this.VB.setOnClickListener(this.VO);
        this.VL.setOnClickListener(this.VO);
        this.Vz.setOnClickListener(this.VO);
        this.VC.setOnClickListener(this.VO);
        this.VD.setOnClickListener(this.VO);
        this.VE.setOnClickListener(this.VO);
        this.VF.setOnClickListener(this.VO);
        this.VG.setOnClickListener(this.VO);
        this.VH.setOnClickListener(this.VO);
        this.VJ.setOnClickListener(this.VO);
        this.VK.setOnClickListener(this.VO);
        if (h.zx() == 2) {
            if (VERSION.SDK_INT < 11) {
                this.VH.setVisibility(8);
                this.VG.setVisibility(8);
            }
            this.VB.setVisibility(8);
            this.VL.setVisibility(8);
        } else if (h.zx() == 3) {
            if (VERSION.SDK_INT < 11) {
                this.VH.setVisibility(8);
                this.VF.setVisibility(8);
                this.VB.setVisibility(8);
                this.VL.setVisibility(8);
            }
        } else if (!(h.zx() == 5 || h.zx() == 7)) {
            this.VJ.setVisibility(8);
            this.VK.setVisibility(8);
            this.VB.setVisibility(8);
            this.VL.setVisibility(8);
        }
        this.VA.setChecked(true);
        this.VO.onClick(this.VA);
        h.zM().a(this.Vp);
    }
}
