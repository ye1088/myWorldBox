package com.huluxia.mcfloat;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.huluxia.framework.R;
import com.huluxia.mcfloat.build.b;
import com.huluxia.mcfloat.build.c;
import com.huluxia.mcfloat.build.d;
import com.huluxia.mcfloat.p.a;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.j;
import com.huluxia.r;
import com.huluxia.utils.aj;
import java.util.ArrayList;
import java.util.Stack;

/* compiled from: FloatLayoutBuilderView */
public class e implements a {
    private static e Qv;
    private View Pu = null;
    private com.huluxia.mcfloat.build.e Qp;
    private c Qq;
    private d Qr;
    private b Qs;
    private com.huluxia.widget.a Qt = null;
    private Stack<ArrayList<j>> Qu = new Stack();
    private OnCheckedChangeListener Qw = new OnCheckedChangeListener(this) {
        final /* synthetic */ e Qz;

        {
            this.Qz = this$0;
        }

        public void onCheckedChanged(CompoundButton button, boolean checked) {
            if (button.getId() == R.id.floatBuildChkAutoTips) {
                h.bO(checked);
            }
        }
    };
    private com.huluxia.widget.a.a Qx = new com.huluxia.widget.a.a(this) {
        final /* synthetic */ e Qz;

        {
            this.Qz = this$0;
        }

        public void a(CompoundButton button, boolean chedked) {
            boolean z = true;
            switch (button.getId()) {
                case R.id.floatBuildChkLineSet:
                    this.Qz.Qq.aO(chedked);
                    break;
                case R.id.floatBuildChkRectAdd:
                    this.Qz.Qr.aO(chedked);
                    break;
                case R.id.floatBuildChkEllipseAdd:
                    this.Qz.Qs.aO(chedked);
                    break;
            }
            boolean tmpCheck = false;
            for (CompoundButton tmpButton : this.Qz.Qt.NQ()) {
                if (tmpButton.isChecked()) {
                    tmpCheck = true;
                    com.huluxia.mcfloat.build.e g;
                    switch (tmpButton.getId()) {
                        case R.id.floatBuildChkLineSet:
                            g = this.Qz.Qp;
                            if (h.getGameType() != 1) {
                                z = false;
                            }
                            g.b(z, this.Qz.Qq.uk());
                            break;
                        case R.id.floatBuildChkRectAdd:
                            g = this.Qz.Qp;
                            if (h.getGameType() != 1) {
                                z = false;
                            }
                            g.b(z, this.Qz.Qr.uk());
                            break;
                        case R.id.floatBuildChkEllipseAdd:
                            g = this.Qz.Qp;
                            if (h.getGameType() != 1) {
                                z = false;
                            }
                            g.b(z, this.Qz.Qs.uk());
                            break;
                    }
                    if (!tmpCheck) {
                        this.Qz.Qp.b(false, " ");
                    }
                }
            }
            if (!tmpCheck) {
                this.Qz.Qp.b(false, " ");
            }
        }
    };
    private com.huluxia.mcinterface.d Qy = new com.huluxia.mcinterface.d(this) {
        final /* synthetic */ e Qz;

        {
            this.Qz = this$0;
        }

        public void b(int id, int dmg, int x, int y, int z) {
            if (aj.aC(id, dmg).getItemBuild()) {
                ArrayList<j> oldList = null;
                ArrayList<j> newList = null;
                if (this.Qz.Qs.uh()) {
                    this.Qz.Qs.d(id, dmg, x, y, z);
                    oldList = this.Qz.Qs.ui();
                    newList = this.Qz.Qs.uj();
                }
                if (this.Qz.Qr.uh()) {
                    this.Qz.Qr.d(id, dmg, x, y, z);
                    oldList = this.Qz.Qr.ui();
                    newList = this.Qz.Qr.uj();
                }
                if (this.Qz.Qq.uh()) {
                    this.Qz.Qq.d(id, dmg, x, y, z);
                    oldList = this.Qz.Qq.ui();
                    newList = this.Qz.Qq.uj();
                    if (oldList != null) {
                        this.Qz.Qq.um();
                    }
                }
                if (newList != null && oldList != null) {
                    this.Qz.i(oldList);
                    h.D(newList);
                    r.ck().du();
                }
            }
        }

        public void c(int id, int dmg, int x, int y, int z) {
            ArrayList<j> oldList = null;
            ArrayList<j> newList = null;
            if (this.Qz.Qq.uh()) {
                this.Qz.Qq.e(id, dmg, x, y, z);
                oldList = this.Qz.Qq.ui();
                newList = this.Qz.Qq.uj();
                if (oldList != null) {
                    this.Qz.Qq.um();
                }
            }
            if (newList != null && oldList != null) {
                this.Qz.i(oldList);
                h.D(newList);
                r.ck().du();
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ e Qz;

        {
            this.Qz = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatBtnBuildUndo:
                    this.Qz.rh();
                    return;
                case R.id.floatBuildBtnLineSet:
                    this.Qz.Qq.aO(this.Qz.Pu.getContext());
                    r.ck().K(r.a.lm);
                    return;
                case R.id.floatBuildBtnRectAddSet:
                    this.Qz.Qr.aO(this.Qz.Pu.getContext());
                    r.ck().K(r.a.ln);
                    return;
                case R.id.floatBuildBtnEllipseAddSet:
                    this.Qz.Qs.aO(this.Qz.Pu.getContext());
                    r.ck().K(r.a.lo);
                    return;
                default:
                    return;
            }
        }
    };

    public void qO() {
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
    }

    public void qN() {
    }

    public void cd(String mapName) {
        this.Qu.clear();
        this.Qt.dh(false);
        this.Pu.findViewById(R.id.floatBtnBuildUndo).setEnabled(false);
        h.bO(((CheckBox) this.Pu.findViewById(R.id.floatBuildChkAutoTips)).isChecked());
    }

    public static synchronized e rf() {
        e eVar;
        synchronized (e.class) {
            if (Qv == null) {
                Qv = new e();
            }
            eVar = Qv;
        }
        return eVar;
    }

    public void rg() {
        if (this.Pu != null) {
            this.Qt.dh(false);
        }
    }

    public void b(View view) {
        this.Pu = view;
        this.Pu.setVisibility(8);
        this.Qp = new com.huluxia.mcfloat.build.e();
        this.Qr = new d(this.Qp);
        this.Qq = new c(this.Qp);
        this.Qs = new b(this.Qp);
        CheckBox lineSet = (CheckBox) view.findViewById(R.id.floatBuildChkLineSet);
        CheckBox rectAdd = (CheckBox) view.findViewById(R.id.floatBuildChkRectAdd);
        CheckBox ellipseAdd = (CheckBox) view.findViewById(R.id.floatBuildChkEllipseAdd);
        this.Qt = new com.huluxia.widget.a(this.Qx);
        this.Qt.a(lineSet, rectAdd, ellipseAdd);
        ((CheckBox) view.findViewById(R.id.floatBuildChkAutoTips)).setOnCheckedChangeListener(this.Qw);
        view.findViewById(R.id.floatBtnBuildUndo).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBuildBtnLineSet).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBuildBtnRectAddSet).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBuildBtnEllipseAddSet).setOnClickListener(this.mClickListener);
        h.zM().a(this.Qy);
    }

    private void i(ArrayList<j> oldList) {
        if (oldList != null && !oldList.isEmpty()) {
            this.Qu.push(oldList);
            if (this.Qu.size() > 5) {
                this.Qu.remove(0);
            }
            this.Pu.post(new Runnable(this) {
                final /* synthetic */ e Qz;

                {
                    this.Qz = this$0;
                }

                public void run() {
                    this.Qz.Pu.findViewById(R.id.floatBtnBuildUndo).setEnabled(true);
                }
            });
        }
    }

    private void rh() {
        if (this.Qu != null && !this.Qu.empty()) {
            h.D((ArrayList) this.Qu.pop());
            if (this.Qu.isEmpty()) {
                this.Pu.findViewById(R.id.floatBtnBuildUndo).setEnabled(false);
            }
        }
    }
}
