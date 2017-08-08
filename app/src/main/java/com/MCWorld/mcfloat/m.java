package com.MCWorld.mcfloat;

import android.app.Activity;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.mcfloat.potion.c;
import com.MCWorld.mcinterface.h;
import com.MCWorld.mcinterface.k;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.widget.ButtonImageRadio;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FloatLayoutPotionView */
public class m implements OnClickListener, com.MCWorld.mcfloat.p.a {
    private static final boolean DEBUG = false;
    public static final int TZ = 30;
    private static List<k> Ua = new ArrayList(23);
    private static m Ud;
    private Activity PD = null;
    private View Pu = null;
    private View RD;
    private ListView Rm = null;
    private int Rn = 0;
    private View Ry;
    private View Rz;
    private SeekBar TR;
    private ButtonImageRadio TS;
    private ButtonImageRadio TT;
    private ButtonImageRadio TU;
    private CheckBox TV;
    private int TW = 0;
    private boolean TX = false;
    private boolean TY = false;
    private int Ub = 0;
    private OnSeekBarChangeListener Uc = new OnSeekBarChangeListener(this) {
        final /* synthetic */ m Uh;

        {
            this.Uh = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
            this.Uh.Ub = bar.getProgress();
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            ((TextView) this.Uh.Pu.findViewById(R.id.floatTextShowPotionTime)).setText(String.valueOf(progress) + this.Uh.Pu.getResources().getString(R.string.minute));
        }
    };
    private OnCheckedChangeListener Ue = new OnCheckedChangeListener(this) {
        final /* synthetic */ m Uh;

        {
            this.Uh = this$0;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                this.Uh.TX = true;
                this.Uh.TU.setText(this.Uh.Pu.getResources().getString(R.string.potion_attack_on));
                t.show_toast(this.Uh.PD, this.Uh.Pu.getResources().getString(R.string.potion_attack_on_tips));
                return;
            }
            this.Uh.TX = false;
            this.Uh.TU.setText(this.Uh.Pu.getResources().getString(R.string.potion_attack_off));
            m.Ua.clear();
            h.a(false, null);
            this.Uh.Ug.notifyDataSetChanged();
            t.show_toast(this.Uh.PD, this.Uh.Pu.getResources().getString(R.string.potion_attack_off_tips));
        }
    };
    private a Uf = new a();
    private b Ug = new b();
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ m Uh;

        {
            this.Uh = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatBtnMainPotionBuffMode:
                    this.Uh.rM();
                    return;
                case R.id.floatBtnMainPotionDebuffMode:
                    this.Uh.rN();
                    return;
                case R.id.floatBtnMainPotionAttack:
                    this.Uh.rO();
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatLayoutPotionView */
    private class a extends BaseAdapter {
        private String[] RM;
        public int[] RN;
        private int[] RO;
        final /* synthetic */ m Uh;
        private com.MCWorld.mcfloat.potion.b Ui;
        private int[] Uj;
        private boolean[] Uk;
        private OnSeekBarChangeListener Ul;

        /* compiled from: FloatLayoutPotionView */
        class a {
            CheckBox RT;
            View RX;
            final /* synthetic */ a Um;
            SeekBar Un;
            TextView Uo;
            int index;

            a(a this$1) {
                this.Um = this$1;
            }
        }

        private a(m mVar) {
            this.Uh = mVar;
            this.RM = new String[14];
            this.RO = new int[25];
            this.Uj = new int[25];
            this.RN = new int[14];
            this.Uk = new boolean[14];
            this.Ul = new OnSeekBarChangeListener(this) {
                final /* synthetic */ a Um;

                {
                    this.Um = this$1;
                }

                public void onStopTrackingTouch(SeekBar bar) {
                    this.Um.RN[((a) bar.getTag()).index] = bar.getProgress();
                }

                public void onStartTrackingTouch(SeekBar bar) {
                }

                public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                    ((a) bar.getTag()).Uo.setText(String.valueOf(progress));
                }
            };
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potion_attr, parent, false);
                holder = new a(this);
                holder.RT = (CheckBox) convertView.findViewById(R.id.potion_attr_open);
                holder.Un = (SeekBar) convertView.findViewById(R.id.floatSeekPotionLevelEx);
                holder.Uo = (TextView) convertView.findViewById(R.id.floatTextShowPotionLevelEx);
                holder.RX = convertView.findViewById(R.id.potion_attr_intro);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            holder.RT.setOnCheckedChangeListener(null);
            holder.RT.setChecked(this.Uk[position]);
            holder.RT.setText(this.RM[position]);
            holder.RT.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
                final /* synthetic */ a Um;

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    this.Um.Uk[position] = isChecked;
                }
            });
            holder.index = position;
            holder.Un.setMax(this.Uj[position]);
            holder.Un.setOnSeekBarChangeListener(this.Ul);
            holder.Un.setTag(holder);
            holder.Un.setProgress(this.RN[position]);
            holder.Uo.setText(String.valueOf(holder.Un.getProgress()));
            holder.RX.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ a Um;

                public void onClick(View v) {
                    this.Um.dj(position);
                }
            });
            return convertView;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public Object getItem(int position) {
            return this.RM[position];
        }

        public int getCount() {
            if (this.Uh.TW == 0) {
                return 14;
            }
            if (this.Uh.TW == 1) {
                return 9;
            }
            return 0;
        }

        public void a(com.MCWorld.mcfloat.potion.b item) {
            int count;
            List<com.MCWorld.mcfloat.potion.b> mTmpPotionItemList;
            if (this.Uh.TW == 0) {
                count = 14;
                mTmpPotionItemList = c.uO();
            } else if (this.Uh.TW == 1) {
                count = 9;
                mTmpPotionItemList = c.uP();
            } else {
                count = 0;
                mTmpPotionItemList = null;
            }
            for (int i = 0; i < count; i++) {
                com.MCWorld.mcfloat.potion.b mTmpPotionItem = (com.MCWorld.mcfloat.potion.b) mTmpPotionItemList.get(i);
                this.RM[i] = mTmpPotionItem.uL();
                this.RN[i] = 1;
                this.Uk[i] = false;
                this.RO[i] = mTmpPotionItem.uJ();
                this.Uj[i] = mTmpPotionItem.ur();
            }
            this.Uh.TY = false;
            this.Uh.dG(1);
            this.Uh.TR.setProgress(1);
            notifyDataSetChanged();
        }

        private void dj(int position) {
            com.MCWorld.mcfloat.potion.b mTmpPotionItem = c.fT(this.RO[position]);
            if (mTmpPotionItem != null) {
                new com.MCWorld.mcfloat.potion.a(this.Uh.Pu.getContext(), mTmpPotionItem.uL(), mTmpPotionItem.uM()).showDialog();
            }
        }

        public void rS() {
            boolean z = false;
            for (int i = 0; i < getCount(); i++) {
                boolean z2;
                boolean[] zArr = this.Uk;
                if (this.Uh.TY) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                zArr[i] = z2;
            }
            m mVar = this.Uh;
            if (!this.Uh.TY) {
                z = true;
            }
            mVar.TY = z;
            if (this.Uh.TW == 0) {
                r.ck().K_umengEvent(com.MCWorld.r.a.lc);
            } else if (this.Uh.TW == 1) {
                r.ck().K_umengEvent(com.MCWorld.r.a.ld);
            }
            notifyDataSetChanged();
        }

        public void rT() {
            for (int i = 0; i < getCount(); i++) {
                this.Uk[i] = true;
                this.RN[i] = this.Uj[i];
            }
            this.Uh.TY = true;
            this.Uh.dG(30);
            this.Uh.TR.setProgress(30);
            if (this.Uh.TW == 0) {
                r.ck().K_umengEvent(com.MCWorld.r.a.le);
            } else if (this.Uh.TW == 1) {
                r.ck().K_umengEvent(com.MCWorld.r.a.lf);
            }
            notifyDataSetChanged();
        }
    }

    /* compiled from: FloatLayoutPotionView */
    private class b extends BaseAdapter {
        final /* synthetic */ m Uh;

        /* compiled from: FloatLayoutPotionView */
        class a {
            TextView Up;
            TextView Uq;
            final /* synthetic */ b Ur;

            a(b this$1) {
                this.Ur = this$1;
            }
        }

        private b(m mVar) {
            this.Uh = mVar;
        }

        public int getCount() {
            return m.Ua.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.float_potion_attack_item, parent, false);
                holder = new a(this);
                holder.Up = (TextView) convertView.findViewById(R.id.tvFloatAttackItemName);
                holder.Uq = (TextView) convertView.findViewById(R.id.tvFloatAttackItemLevel);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            holder.Up.setText(((k) m.Ua.get(position)).getName());
            holder.Uq.setText(((k) m.Ua.get(position)).getLevel() + this.Uh.Pu.getResources().getString(R.string.level));
            return convertView;
        }
    }

    public int rI() {
        return this.TW;
    }

    public void dE(int mPotionMode) {
        this.TW = mPotionMode;
    }

    public int rJ() {
        return this.Rn;
    }

    public void dF(int mSelectItemID) {
        this.Rn = mSelectItemID;
    }

    public void qN() {
    }

    public void cd(String mapName) {
    }

    public void qO() {
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (!isShow) {
        }
    }

    public int rK() {
        return this.Ub;
    }

    public void dG(int mLocalPotionTime) {
        this.Ub = mLocalPotionTime;
    }

    public static synchronized m rL() {
        m mVar;
        synchronized (m.class) {
            if (Ud == null) {
                Ud = new m();
            }
            mVar = Ud;
        }
        return mVar;
    }

    public void rg() {
        if (this.Pu != null) {
            this.TV.setChecked(false);
        }
    }

    public void a(View view, Activity c) {
        this.Pu = view;
        this.PD = c;
        view.setVisibility(8);
        this.Pu.findViewById(R.id.floatLayoutItemPotionTypeEx).setVisibility(0);
        this.TS = (ButtonImageRadio) view.findViewById(R.id.floatBtnMainPotionBuffMode);
        this.TT = (ButtonImageRadio) view.findViewById(R.id.floatBtnMainPotionDebuffMode);
        this.TU = (ButtonImageRadio) view.findViewById(R.id.floatBtnMainPotionAttack);
        if (h.zx() == 3) {
            if (VERSION.SDK_INT < 11) {
                this.TU.setVisibility(8);
            }
        } else if (h.zx() != 5 && h.zx() == 7) {
        }
        this.TT.aI(R.drawable.ic_float_role_livetrue, R.drawable.ic_float_role_livefalse);
        this.TS.aI(R.drawable.ic_float_role_createtrue, R.drawable.ic_float_role_createfalse);
        this.TU.aI(R.drawable.ic_float_role_attacktrue, R.drawable.ic_float_role_attackfalse);
        this.TT.setOnClickListener(this.mClickListener);
        this.TS.setOnClickListener(this.mClickListener);
        this.TU.setOnClickListener(this.mClickListener);
        this.TV = (CheckBox) view.findViewById(R.id.floatChkMainAttackOption);
        this.TV.setOnCheckedChangeListener(this.Ue);
        this.TR = (SeekBar) view.findViewById(R.id.floatSeekPotionTime);
        this.TR.setOnSeekBarChangeListener(this.Uc);
        this.TR.setMax(30);
        this.Rm = (ListView) view.findViewById(R.id.floatListPotionAttrList);
        this.Rm.setAdapter(this.Uf);
        this.Ry = this.Pu.findViewById(R.id.value_button_cancel_potion);
        this.Ry.setOnClickListener(this);
        this.Rz = this.Pu.findViewById(R.id.value_button_best_potion);
        this.Rz.setOnClickListener(this);
        this.RD = this.Pu.findViewById(R.id.value_button_eat_potion);
        this.RD.setOnClickListener(this);
        this.Pu.findViewById(R.id.value_button_all_potion).setOnClickListener(this);
        this.TY = false;
        dG(1);
        this.TR.setProgress(1);
        if (this.TW == 0) {
            this.TS.setChecked(true);
        }
        if (this.TW == 1) {
            this.TT.setChecked(true);
        }
        this.Uf.a(null);
    }

    private void rM() {
        this.Pu.findViewById(R.id.floatLayoutItemPotionType).setVisibility(0);
        this.Pu.findViewById(R.id.floatLinearLayoutPotionTime).setVisibility(0);
        this.Pu.findViewById(R.id.floatLinearLayoutOption).setVisibility(8);
        this.TW = 0;
        this.Uf.a(null);
        this.Rm.setAdapter(this.Uf);
        r.ck().K_umengEvent(com.MCWorld.r.a.kV);
    }

    private void rN() {
        this.Pu.findViewById(R.id.floatLayoutItemPotionType).setVisibility(0);
        this.Pu.findViewById(R.id.floatLinearLayoutPotionTime).setVisibility(0);
        this.Pu.findViewById(R.id.floatLinearLayoutOption).setVisibility(8);
        this.TW = 1;
        this.Uf.a(null);
        this.Rm.setAdapter(this.Uf);
        r.ck().K_umengEvent(com.MCWorld.r.a.kW);
    }

    private void rO() {
        this.Pu.findViewById(R.id.floatLayoutItemPotionType).setVisibility(8);
        this.Pu.findViewById(R.id.floatLinearLayoutPotionTime).setVisibility(8);
        this.Pu.findViewById(R.id.floatLinearLayoutOption).setVisibility(0);
        this.TW = 2;
        this.Rm.setAdapter(this.Ug);
        r.ck().K_umengEvent(com.MCWorld.r.a.lg);
    }

    private void rP() {
        int tmpPotionCnt;
        if (this.TW == 0) {
            tmpPotionCnt = 14;
        } else if (this.TW == 1) {
            tmpPotionCnt = 9;
        } else {
            tmpPotionCnt = 0;
        }
        if (this.TX) {
            Ua.clear();
            this.Ug.notifyDataSetChanged();
            h.a(false, null);
        } else {
            for (int i = 0; i < tmpPotionCnt; i++) {
                h.hm(this.Uf.RO[i]);
            }
        }
        if (this.TX) {
            r.ck().K_umengEvent(com.MCWorld.r.a.li);
        } else if (this.TW == 0) {
            r.ck().K_umengEvent(com.MCWorld.r.a.kX);
        } else if (this.TW == 1) {
            r.ck().K_umengEvent(com.MCWorld.r.a.kY);
        }
        com.MCWorld.k.p(this.Pu.getContext(), this.Pu.getResources().getString(R.string.cancel_potion_tips));
    }

    private void rQ() {
        int tmpPotionCnt;
        boolean tmpEatPotionFlag = false;
        if (this.TW == 0) {
            tmpPotionCnt = 14;
        } else if (this.TW == 1) {
            tmpPotionCnt = 9;
        } else {
            tmpPotionCnt = 0;
        }
        int tmpTime = rK() * 60;
        if (tmpTime > 0) {
            for (int i = 0; i < tmpPotionCnt; i++) {
                if (true == this.Uf.Uk[i]) {
                    int tmpId = this.Uf.RO[i];
                    int tmpLevel = this.Uf.RN[i];
                    if (tmpLevel > 0) {
                        if (this.TX) {
                            f(tmpId, tmpTime, tmpLevel);
                        } else {
                            h.v(tmpId, tmpTime, tmpLevel);
                            tmpEatPotionFlag = true;
                        }
                    }
                }
            }
            if (this.TX) {
                h.a(true, Ua);
                rO();
                this.TU.setChecked(true);
            }
        } else {
            com.MCWorld.k.p(this.Pu.getContext(), this.Pu.getResources().getString(R.string.set_time_tips));
        }
        if (tmpEatPotionFlag) {
            if (this.TX) {
                r.ck().K_umengEvent(com.MCWorld.r.a.lh);
            } else if (this.TW == 0) {
                r.ck().K_umengEvent(com.MCWorld.r.a.kZ);
            } else if (this.TW == 1) {
                r.ck().K_umengEvent(com.MCWorld.r.a.la);
            }
            com.MCWorld.k.p(this.Pu.getContext(), this.Pu.getResources().getString(R.string.eat_over_potion_tips));
        }
    }

    private void f(int id, int time, int level) {
        boolean _bIsExist = false;
        for (int i = Ua.size() - 1; i >= 0; i--) {
            if (((k) Ua.get(i)).Ab() == id) {
                ((k) Ua.get(i)).setLevel(level);
                ((k) Ua.get(i)).setTime(time);
                _bIsExist = true;
                break;
            }
        }
        if (!_bIsExist) {
            Ua.add(new k(id, time, level, c.fT(id).uL()));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.value_button_all_potion:
                this.Uf.rS();
                return;
            case R.id.value_button_best_potion:
                this.Uf.rT();
                return;
            case R.id.value_button_eat_potion:
                rQ();
                return;
            case R.id.value_button_cancel_potion:
                rP();
                return;
            default:
                return;
        }
    }
}
