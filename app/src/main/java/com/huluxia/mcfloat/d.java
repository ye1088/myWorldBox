package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.j;
import com.huluxia.utils.p;
import com.huluxia.widget.Constants;
import com.huluxia.widget.dialog.g;
import com.huluxia.widget.dialog.k;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"DefaultLocale"})
/* compiled from: FloatLayoutBackupView */
public class d implements com.huluxia.mcfloat.p.a {
    private static List<com.huluxia.mcfloat.map.a> PG = null;
    public static final int PH = 0;
    public static final int PI = 1;
    public static final int PJ = 2;
    public static final int PK = 3;
    public static final int PL = 4;
    private static int[] PN = new int[]{60, 300, h.arp};
    private static String[] PO = new String[]{"时间间隔:1m", "时间间隔:5m", "时间间隔:10m"};
    public static final String PP = "Save1.png";
    public static final String PQ = "Save2.png";
    public static final String PR = "Save3.png";
    public static final String PS = "Save4.png";
    private Activity PD = null;
    private k PE;
    private String PF = null;
    private hlx.utils.b.a PT = new hlx.utils.b.a(this) {
        final /* synthetic */ d Qo;

        {
            this.Qo = this$0;
        }

        public void qZ() {
            this.Qo.qW();
            this.Qo.PU.nP(d.PN[this.Qo.PZ.getProgress()]);
            this.Qo.PU.Wa();
        }
    };
    private hlx.utils.b PU = new hlx.utils.b(this.PT);
    private CheckBox PV;
    private TextView PW;
    private TextView PX;
    private ImageView PY;
    private SeekBar PZ;
    private View Pu = null;
    private TextView Qa;
    private TextView Qb;
    private TextView Qc;
    private TextView Qd;
    private ImageView Qe;
    private ImageView Qf;
    private ImageView Qg;
    private ImageView Qh;
    private TextView Qi;
    private TextView Qj;
    private TextView Qk;
    private TextView Ql;
    private OnCheckedChangeListener Qm = new OnCheckedChangeListener(this) {
        final /* synthetic */ d Qo;

        {
            this.Qo = this$0;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.floatChkBackupNameAuto:
                    if (isChecked) {
                        this.Qo.PX.setVisibility(0);
                        this.Qo.PZ.setVisibility(0);
                        this.Qo.PU.nP(d.PN[this.Qo.PZ.getProgress()]);
                        this.Qo.PU.Wa();
                        return;
                    }
                    this.Qo.PX.setVisibility(8);
                    this.Qo.PZ.setVisibility(8);
                    this.Qo.PU.Wc();
                    return;
                default:
                    return;
            }
        }
    };
    private OnSeekBarChangeListener Qn = new OnSeekBarChangeListener(this) {
        final /* synthetic */ d Qo;

        {
            this.Qo = this$0;
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            int _progress = seekBar.getProgress();
            this.Qo.PX.setText(d.PO[_progress]);
            this.Qo.PU.nP(d.PN[_progress]);
            this.Qo.PU.Wa();
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ d Qo;

        {
            this.Qo = this$0;
        }

        public void onClick(View v) {
            int tmpChooseIndex = 0;
            switch (v.getId()) {
                case R.id.floatBtnBackupGoto1:
                    tmpChooseIndex = 1;
                    break;
                case R.id.floatBtnBackupGoto2:
                    tmpChooseIndex = 2;
                    break;
                case R.id.floatBtnBackupGoto3:
                    tmpChooseIndex = 3;
                    break;
                case R.id.floatBtnBackupGoto4:
                    tmpChooseIndex = 4;
                    break;
            }
            if (tmpChooseIndex != 0) {
                this.Qo.dc(tmpChooseIndex);
            }
        }
    };

    /* compiled from: FloatLayoutBackupView */
    private class a implements com.huluxia.widget.dialog.g.a {
        final /* synthetic */ d Qo;
        private int index;

        public a(d dVar, int inputIndex) {
            this.Qo = dVar;
            this.index = inputIndex;
        }

        public void ra() {
        }

        public void rb() {
        }

        public void rc() {
        }

        public void rd() {
            if (this.index != 0) {
                this.Qo.dd(this.index);
            }
        }
    }

    /* compiled from: FloatLayoutBackupView */
    private class b extends AsyncTask<String, Integer, String> {
        final /* synthetic */ d Qo;

        private b(d dVar) {
            this.Qo = dVar;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.Qo.PE.show();
        }

        protected String c(String... params) {
            String retVal = null;
            try {
                String mapSrcPath = params[0];
                String mapDstPath = params[1];
                String mapZipName = params[2];
                String mapScreenshotName = params[3];
                retVal = params[4];
                UtilsFile.mkdir(mapDstPath);
                com.huluxia.mcinterface.h.z(mapDstPath + mapScreenshotName, 0);
                p.b(mapSrcPath, mapDstPath, mapZipName, true);
                return retVal;
            } catch (Exception e) {
                e.printStackTrace();
                return retVal;
            }
        }

        protected void ce(String result) {
            this.Qo.PE.cancel();
            this.Qo.db(Integer.parseInt(result));
            t.o(this.Qo.Pu.getContext(), "备份地图完成!");
        }
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
    }

    public void qN() {
        this.PU.Wc();
    }

    public void qO() {
    }

    public void cd(String mapName) {
        qT();
        qU();
        db(0);
        this.PV.setChecked(false);
    }

    public void qT() {
        this.PF = j.cV(true) + com.huluxia.mcinterface.h.zs() + File.separator + Constants.bsk + File.separator;
        UtilsFile.mkdir(this.PF);
    }

    private void qU() {
        if (PG != null) {
            for (int i = 0; i < PG.size() - 1; i++) {
                com.huluxia.mcfloat.map.a tmpBackupViewItem = (com.huluxia.mcfloat.map.a) PG.get(i);
                if (tmpBackupViewItem != null) {
                    j.rename(this.PF + tmpBackupViewItem.uI(), this.PF + tmpBackupViewItem.uC());
                }
            }
        }
    }

    public void qV() {
        if (PG != null) {
            PG.clear();
            PG = null;
        }
        PG = new ArrayList();
        PG.add(new com.huluxia.mcfloat.map.a(1, Constants.bsa, Constants.bsb, PP, null, null, this.Qa, this.Qe, this.Qi));
        PG.add(new com.huluxia.mcfloat.map.a(2, Constants.bsc, Constants.bsd, PQ, null, null, this.Qb, this.Qf, this.Qj));
        PG.add(new com.huluxia.mcfloat.map.a(3, Constants.bse, Constants.bsf, PR, null, null, this.Qc, this.Qg, this.Qk));
        PG.add(new com.huluxia.mcfloat.map.a(4, Constants.bsg, Constants.bsh, PS, null, null, this.Qd, this.Qh, this.Ql));
        PG.add(new com.huluxia.mcfloat.map.a(5, Constants.bsi, Constants.bsj, null, null, null, this.PW, this.PY, null));
    }

    public boolean db(int inputIndex) {
        if (PG == null) {
            return false;
        }
        qT();
        for (int i = 0; i < PG.size(); i++) {
            com.huluxia.mcfloat.map.a tmpBackupViewItem = (com.huluxia.mcfloat.map.a) PG.get(i);
            if (tmpBackupViewItem.uA() == inputIndex || inputIndex == 0) {
                tmpBackupViewItem.a(this.Pu, this.PF);
                if (tmpBackupViewItem.uA() == inputIndex) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean dc(int inputIndex) {
        com.huluxia.mcfloat.map.a tmpBackupViewItem = null;
        if (PG == null) {
            return false;
        }
        for (int i = 0; i < PG.size(); i++) {
            tmpBackupViewItem = (com.huluxia.mcfloat.map.a) PG.get(i);
            if (tmpBackupViewItem.uA() == inputIndex) {
                break;
            }
            tmpBackupViewItem = null;
        }
        if (!(tmpBackupViewItem == null || tmpBackupViewItem.uH() == null)) {
            if (tmpBackupViewItem.uH().getText() == com.huluxia.mcfloat.map.a.acp) {
                new b().execute(new String[]{j.Kq() + com.huluxia.mcinterface.h.zs(), this.PF, tmpBackupViewItem.uB(), tmpBackupViewItem.uC(), Integer.toString(inputIndex)});
            } else if (tmpBackupViewItem.uH().getText() == "删除") {
                g dia = new g(this.PD, new a(this, inputIndex));
                dia.az(hlx.data.localstore.a.bKA, "删除备份地图将无法恢复,确定要删除吗？");
                dia.u(hlx.data.localstore.a.bKB, null, hlx.data.localstore.a.bKC);
                dia.showDialog();
            }
        }
        return true;
    }

    public boolean dd(int inputIndex) {
        if (PG == null) {
            return false;
        }
        for (int i = 0; i < PG.size(); i++) {
            com.huluxia.mcfloat.map.a tmpBackupViewItem = (com.huluxia.mcfloat.map.a) PG.get(i);
            if (tmpBackupViewItem.uA() == inputIndex || inputIndex == 0) {
                tmpBackupViewItem.cq(this.PF);
                tmpBackupViewItem.a(this.Pu, this.PF);
                if (tmpBackupViewItem.uA() == inputIndex) {
                    return true;
                }
            }
        }
        return true;
    }

    public d(View view, Activity inputActivity) {
        this.Pu = view;
        this.Pu.setVisibility(8);
        this.PD = inputActivity;
        this.PV = (CheckBox) view.findViewById(R.id.floatChkBackupNameAuto);
        this.PW = (TextView) view.findViewById(R.id.floatTextBackupTimeAuto);
        this.PY = (ImageView) view.findViewById(R.id.floatBtnBackupImageAuto);
        this.PX = (TextView) view.findViewById(R.id.floatTextBackupTimeInterval);
        this.PX.setVisibility(8);
        this.PZ = (SeekBar) view.findViewById(R.id.floatSeekBackupTimeInterVal);
        this.PV.setOnCheckedChangeListener(this.Qm);
        this.PZ.setOnSeekBarChangeListener(this.Qn);
        this.PZ.setVisibility(8);
        this.Qa = (TextView) view.findViewById(R.id.floatTextBackupTime1);
        this.Qb = (TextView) view.findViewById(R.id.floatTextBackupTime2);
        this.Qc = (TextView) view.findViewById(R.id.floatTextBackupTime3);
        this.Qd = (TextView) view.findViewById(R.id.floatTextBackupTime4);
        this.Qe = (ImageView) view.findViewById(R.id.floatBtnBackupImage1);
        this.Qf = (ImageView) view.findViewById(R.id.floatBtnBackupImage2);
        this.Qg = (ImageView) view.findViewById(R.id.floatBtnBackupImage3);
        this.Qh = (ImageView) view.findViewById(R.id.floatBtnBackupImage4);
        this.Qi = (TextView) view.findViewById(R.id.floatBtnBackupGoto1);
        this.Qj = (TextView) view.findViewById(R.id.floatBtnBackupGoto2);
        this.Qk = (TextView) view.findViewById(R.id.floatBtnBackupGoto3);
        this.Ql = (TextView) view.findViewById(R.id.floatBtnBackupGoto4);
        this.Qi.setOnClickListener(this.mClickListener);
        this.Qj.setOnClickListener(this.mClickListener);
        this.Qk.setOnClickListener(this.mClickListener);
        this.Ql.setOnClickListener(this.mClickListener);
        qV();
        this.PE = new k(view.getContext());
        this.PE.gL("地图正在保存中,请稍后...");
    }

    private void qW() {
        int inputIndex = ((com.huluxia.mcfloat.map.a) PG.get(PG.size() - 1)).uA();
        new b().execute(new String[]{j.Kq() + com.huluxia.mcinterface.h.zs(), this.PF, tmpBackupViewItem.uB(), tmpBackupViewItem.uC(), Integer.toString(inputIndex)});
    }
}
