package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mcinterface.h;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ae;
import com.huluxia.utils.ah;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants;
import com.huluxia.widget.dialog.e;
import java.io.File;

@SuppressLint({"DefaultLocale"})
/* compiled from: FloatLayoutDeliverView */
public class g implements com.huluxia.mcfloat.p.a {
    private static int QR;
    public static final int QS = 0;
    private static final String[] QT = new String[]{"deliver1.ht", "deliver2.ht", "deliver3.ht", "deliver4.ht"};
    private String PF = null;
    private View Pu = null;
    private String QU = "";
    private b QV;
    private TextView QW;
    private b QX;
    private b QY;
    private b QZ;
    private b Ra;
    private TextView Rb;
    private TextView Rc;
    private TextView Rd;
    private TextView Re;
    private TextView Rf;
    private TextView Rg;
    private TextView Rh;
    private TextView Ri;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ g Rj;

        {
            this.Rj = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatBtnSetSpawn:
                    if (this.Rj.a(this.Rj.rm())) {
                        r.ck().K_umengEvent(com.huluxia.r.a.lj);
                        return;
                    } else {
                        t.n(this.Rj.Pu.getContext(), this.Rj.Pu.getContext().getString(R.string.set_spawn_failure));
                        return;
                    }
                case R.id.floatBtnKillSelf:
                    h.e(-26, false);
                    r.ck().K_umengEvent(com.huluxia.r.a.lk);
                    return;
                case R.id.floatBtnBackToDeathPoint:
                    this.Rj.rl();
                    return;
                case R.id.floatImgDeliverGoto1:
                case R.id.floatBtnDeliverGoto1:
                    h.b(this.Rj.QX.x, this.Rj.QX.y, this.Rj.QX.z);
                    return;
                case R.id.floatBtnDeliverSave1:
                    this.Rj.dg(1);
                    return;
                case R.id.floatImgDeliverGoto2:
                case R.id.floatBtnDeliverGoto2:
                    h.b(this.Rj.QY.x, this.Rj.QY.y, this.Rj.QY.z);
                    return;
                case R.id.floatBtnDeliverSave2:
                    this.Rj.dg(2);
                    return;
                case R.id.floatImgDeliverGoto3:
                case R.id.floatBtnDeliverGoto3:
                    h.b(this.Rj.QZ.x, this.Rj.QZ.y, this.Rj.QZ.z);
                    return;
                case R.id.floatBtnDeliverSave3:
                    this.Rj.dg(3);
                    return;
                case R.id.floatImgDeliverGoto4:
                case R.id.floatBtnDeliverGoto4:
                    h.b(this.Rj.Ra.x, this.Rj.Ra.y, this.Rj.Ra.z);
                    return;
                case R.id.floatBtnDeliverSave4:
                    this.Rj.dg(4);
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatLayoutDeliverView */
    private class a implements com.huluxia.widget.dialog.e.a {
        final /* synthetic */ g Rj;

        private a(g gVar) {
            this.Rj = gVar;
        }

        public void rb() {
        }

        public void cf(String msg) {
            if (msg.trim().length() >= 1) {
                this.Rj.b(g.rk(), msg);
            }
        }
    }

    /* compiled from: FloatLayoutDeliverView */
    private class b {
        final /* synthetic */ g Rj;
        boolean Rk;
        String name;
        float x;
        float y;
        float z;

        private b(g gVar) {
            this.Rj = gVar;
            this.Rk = false;
        }
    }

    public void qO() {
    }

    public void qN() {
    }

    public static int rk() {
        return QR;
    }

    public static void de(int mSelectPostIndex) {
        QR = mSelectPostIndex;
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
    }

    public void cd(String mapName) {
        this.QU = "MAP" + mapName.hashCode();
        this.QV = a(1, this.QW, "未保存坐标");
        this.QX = a(2, this.Rb, "");
        this.QY = a(3, this.Rc, "");
        this.QZ = a(4, this.Rd, "");
        this.Ra = a(5, this.Re, "");
        this.Rf.setText(this.QX.name);
        this.Rg.setText(this.QY.name);
        this.Rh.setText(this.QZ.name);
        this.Ri.setText(this.Ra.name);
        if (a(this.QV)) {
            this.Pu.findViewById(R.id.floatImgDeliverGoto1).setEnabled(this.QX.Rk);
            this.Pu.findViewById(R.id.floatImgDeliverGoto2).setEnabled(this.QY.Rk);
            this.Pu.findViewById(R.id.floatImgDeliverGoto3).setEnabled(this.QZ.Rk);
            this.Pu.findViewById(R.id.floatImgDeliverGoto4).setEnabled(this.Ra.Rk);
            this.Pu.findViewById(R.id.floatBtnDeliverGoto1).setEnabled(this.QX.Rk);
            this.Pu.findViewById(R.id.floatBtnDeliverGoto2).setEnabled(this.QY.Rk);
            this.Pu.findViewById(R.id.floatBtnDeliverGoto3).setEnabled(this.QZ.Rk);
            this.Pu.findViewById(R.id.floatBtnDeliverGoto4).setEnabled(this.Ra.Rk);
            df(0);
            return;
        }
        t.n(this.Pu.getContext(), this.Pu.getContext().getString(R.string.set_spawn_failure));
    }

    public boolean df(int inputIndex) {
        qT();
        switch (inputIndex) {
            case 0:
                a(this.QX.Rk, this.PF + QT[0], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto1));
                a(this.QY.Rk, this.PF + QT[1], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto2));
                a(this.QZ.Rk, this.PF + QT[2], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto3));
                a(this.Ra.Rk, this.PF + QT[3], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto4));
                break;
            case 1:
                a(this.QX.Rk, this.PF + QT[0], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto1));
                break;
            case 2:
                a(this.QY.Rk, this.PF + QT[1], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto2));
                break;
            case 3:
                a(this.QZ.Rk, this.PF + QT[2], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto3));
                break;
            case 4:
                a(this.Ra.Rk, this.PF + QT[3], (ImageView) this.Pu.findViewById(R.id.floatImgDeliverGoto4));
                break;
        }
        return true;
    }

    private void a(boolean enable, String inputImgPath, ImageView imgView) {
        Bitmap tmpBitMap;
        if (j.isExist(inputImgPath) && enable) {
            tmpBitMap = ae.getLocalBitMap(inputImgPath);
            imgView.setImageBitmap(tmpBitMap);
        } else {
            tmpBitMap = BitmapFactory.decodeResource(this.Pu.getContext().getResources(), R.drawable.backup_shot);
        }
        if (tmpBitMap != null) {
            imgView.setImageBitmap(tmpBitMap);
        }
    }

    public void qH() {
        if (this.QV != null && this.QV.Rk) {
            if (this.QV.x != 0.0f || this.QV.y != 0.0f || this.QV.z != 0.0f) {
                h.b(this.QV.x, this.QV.y, this.QV.z);
            }
        }
    }

    public g(View view) {
        this.Pu = view;
        this.Pu.setVisibility(8);
        this.QW = (TextView) view.findViewById(R.id.floatTextSpawnPos);
        this.Rf = (TextView) view.findViewById(R.id.floatTextDeliverName1);
        this.Rb = (TextView) view.findViewById(R.id.floatTextDeliverPos1);
        this.Rg = (TextView) view.findViewById(R.id.floatTextDeliverName2);
        this.Rc = (TextView) view.findViewById(R.id.floatTextDeliverPos2);
        this.Rh = (TextView) view.findViewById(R.id.floatTextDeliverName3);
        this.Rd = (TextView) view.findViewById(R.id.floatTextDeliverPos3);
        this.Ri = (TextView) view.findViewById(R.id.floatTextDeliverName4);
        this.Re = (TextView) view.findViewById(R.id.floatTextDeliverPos4);
        view.findViewById(R.id.floatImgDeliverGoto1).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatImgDeliverGoto2).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatImgDeliverGoto3).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatImgDeliverGoto4).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverGoto1).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverGoto2).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverGoto3).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverGoto4).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverSave1).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverSave2).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverSave3).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnDeliverSave4).setOnClickListener(this.mClickListener);
        if (VERSION.SDK_INT < 11 && h.zx() == 3) {
            ((RelativeLayout) view.findViewById(R.id.rlfloatSpawnLayout)).setVisibility(8);
            ((TextView) view.findViewById(R.id.tvfloatSpawnLayoutLine)).setVisibility(8);
        }
        view.findViewById(R.id.floatBtnSetSpawn).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnKillSelf).setOnClickListener(this.mClickListener);
        view.findViewById(R.id.floatBtnBackToDeathPoint).setOnClickListener(this.mClickListener);
    }

    private void b(int inputIndex, String inputPosName) {
        switch (inputIndex) {
            case 1:
                a(this.QX, inputPosName, rm(), this.Rb);
                a(this.QX, 2);
                this.Pu.findViewById(R.id.floatImgDeliverGoto1).setEnabled(this.QX.Rk);
                this.Pu.findViewById(R.id.floatBtnDeliverGoto1).setEnabled(this.QX.Rk);
                this.Rf.setText(inputPosName);
                break;
            case 2:
                a(this.QY, inputPosName, rm(), this.Rc);
                a(this.QY, 3);
                this.Pu.findViewById(R.id.floatImgDeliverGoto2).setEnabled(this.QY.Rk);
                this.Pu.findViewById(R.id.floatBtnDeliverGoto2).setEnabled(this.QY.Rk);
                this.Rg.setText(inputPosName);
                break;
            case 3:
                a(this.QZ, inputPosName, rm(), this.Rd);
                a(this.QZ, 4);
                this.Pu.findViewById(R.id.floatImgDeliverGoto3).setEnabled(this.QZ.Rk);
                this.Pu.findViewById(R.id.floatBtnDeliverGoto3).setEnabled(this.QZ.Rk);
                this.Rh.setText(inputPosName);
                break;
            case 4:
                a(this.Ra, inputPosName, rm(), this.Re);
                a(this.Ra, 5);
                this.Pu.findViewById(R.id.floatImgDeliverGoto4).setEnabled(this.Ra.Rk);
                this.Pu.findViewById(R.id.floatBtnDeliverGoto4).setEnabled(this.Ra.Rk);
                this.Ri.setText(inputPosName);
                break;
        }
        df(inputIndex);
    }

    private boolean a(b newPos) {
        a(this.QV, "重生点", newPos, this.QW);
        if (this.QV == null) {
            return false;
        }
        h.e(0, (int) this.QV.x, (int) this.QV.y, (int) this.QV.z);
        a(this.QV, 1);
        return true;
    }

    public void qT() {
        this.PF = j.cV(true) + h.zs() + File.separator + Constants.bsk + File.separator;
        UtilsFile.mkdir(this.PF);
    }

    private void dg(int inputIndex) {
        h.z(this.PF + QT[inputIndex - 1], 0);
        String _tmpText = "传送点" + String.valueOf(inputIndex);
        de(inputIndex);
        e dialog = new e((Activity) this.Pu.getContext(), new a());
        dialog.aA(hlx.data.localstore.a.bKB_bt_cancel, "保存");
        dialog.az("坐标名称", _tmpText);
        dialog.gJ("请输入坐标点名称");
        dialog.showDialog();
    }

    private void rl() {
        h.b(h.zI(), h.zJ(), h.zK());
        r.ck().K_umengEvent(com.huluxia.r.a.ll);
    }

    private void b(b pos) {
        pos.z = 0.0f;
        pos.y = 0.0f;
        pos.x = 0.0f;
        pos.Rk = false;
    }

    private void a(b tar, b src) {
        if (tar != null && src != null) {
            tar.x = src.x;
            tar.y = src.y;
            tar.z = src.z;
            tar.Rk = src.Rk;
            tar.name = src.name;
        }
    }

    private b rm() {
        b pos = new b();
        pos.x = h.zF();
        pos.y = h.zG();
        pos.z = h.zH();
        return pos;
    }

    private boolean b(b pos1, b pos2) {
        if (((double) Math.abs(pos1.x - pos2.x)) <= 0.1d && ((double) Math.abs(pos1.y - pos2.y)) <= 0.1d && ((double) Math.abs(pos1.z - pos2.z)) <= 0.1d) {
            return true;
        }
        return false;
    }

    private void a(b oldPos, String inputPosName, b newPos, TextView txtView) {
        String showText = "未保存坐标";
        if (oldPos != null && newPos != null) {
            a(oldPos, newPos);
            oldPos.Rk = true;
            oldPos.name = inputPosName;
            txtView.setText(String.format("x:%.0f,  y:%.0f,  z:%.0f", new Object[]{Float.valueOf(oldPos.x), Float.valueOf(oldPos.y), Float.valueOf(oldPos.z)}));
        }
    }

    private b a(int index, TextView txtView, String defaultString) {
        txtView.setText(defaultString);
        b pos = new b();
        pos.Rk = ah.KZ().j(String.format("%s_posEn%d", new Object[]{this.QU, Integer.valueOf(index)}), false);
        if (pos.Rk) {
            pos.x = ah.KZ().b(String.format("%s_posX%d", new Object[]{this.QU, Integer.valueOf(index)}), 0.0f);
            pos.y = ah.KZ().b(String.format("%s_posY%d", new Object[]{this.QU, Integer.valueOf(index)}), 0.0f);
            pos.z = ah.KZ().b(String.format("%s_posZ%d", new Object[]{this.QU, Integer.valueOf(index)}), 0.0f);
            pos.name = ah.KZ().aj(String.format("%s_posname%d", new Object[]{this.QU, Integer.valueOf(index)}), "传送点" + String.valueOf(index - 1));
            txtView.setText(String.format("x:%.0f,  y:%.0f,  z:%.0f", new Object[]{Float.valueOf(pos.x), Float.valueOf(pos.y), Float.valueOf(pos.z)}));
        } else {
            pos.name = "未保存坐标";
        }
        return pos;
    }

    private void a(b pos, int index) {
        if (pos != null) {
            ah.KZ().k(String.format("%s_posEn%d", new Object[]{this.QU, Integer.valueOf(index)}), pos.Rk);
            ah.KZ().c(String.format("%s_posX%d", new Object[]{this.QU, Integer.valueOf(index)}), pos.x);
            ah.KZ().c(String.format("%s_posY%d", new Object[]{this.QU, Integer.valueOf(index)}), pos.y);
            ah.KZ().c(String.format("%s_posZ%d", new Object[]{this.QU, Integer.valueOf(index)}), pos.z);
            ah.KZ().ak(String.format("%s_posname%d", new Object[]{this.QU, Integer.valueOf(index)}), pos.name);
        }
    }
}
