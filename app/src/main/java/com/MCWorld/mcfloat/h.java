package com.MCWorld.mcfloat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.MCWorld.framework.R;
import com.MCWorld.k;
import com.MCWorld.mcfloat.enchant.c;
import com.MCWorld.mcfloat.enchant.e;
import com.MCWorld.mcfloat.enchant.f;
import com.MCWorld.r;
import java.util.List;

/* compiled from: FloatLayoutEnchantView */
public class h implements OnClickListener, com.MCWorld.mcfloat.p.a {
    private static final int Ru = 20;
    private static final int Rv = 1;
    private static int Rw = 1;
    private List<c> PA = null;
    private Activity PD = null;
    private View Pu = null;
    private int Py = -1;
    private View RA;
    private View RD;
    private ImageView RE;
    private ViewSwitcher RF;
    private int RG = 0;
    private BaseAdapter RH = new BaseAdapter(this) {
        final /* synthetic */ h RK;

        {
            this.RK = this$0;
        }

        public long getItemId(int pos) {
            return 0;
        }

        public int getCount() {
            return this.RK.PA == null ? 0 : this.RK.PA.size();
        }

        public Object getItem(int pos) {
            if (pos == -1 || pos >= this.RK.PA.size()) {
                return null;
            }
            return this.RK.PA.get(pos);
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            b holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floatitem, null);
                holder = new b();
                holder.RZ = (TextView) convertView.findViewById(R.id.floatItemItemName);
                holder.Sa = (ImageView) convertView.findViewById(R.id.floatItemItemLogo);
                convertView.setTag(holder);
            } else {
                holder = (b) convertView.getTag();
            }
            convertView.setOnClickListener(this.RK.RI);
            if (pos == this.RK.Py) {
                convertView.setBackgroundColor(-2008463031);
            }
            if (pos != this.RK.Py) {
                convertView.setBackgroundResource(R.drawable.style_float_grid_itembg);
            }
            c info = (c) getItem(pos);
            if (info.getItemId() == 0) {
                holder.RY = -1;
                holder.RZ.setText("");
                holder.Sa.setVisibility(4);
            } else {
                holder.RY = pos;
                String itemName = info.getItemName();
                if (info.getItemCount() > 0) {
                    itemName = itemName + String.format("x%d", new Object[]{Integer.valueOf(info.getItemCount())});
                }
                holder.RZ.setText(itemName);
                holder.Sa.setVisibility(0);
                Bitmap bp = info.getItemImage();
                if (bp == null) {
                    holder.Sa.setImageResource(info.getItemImgId());
                }
                if (bp != null) {
                    holder.Sa.setImageBitmap(bp);
                }
            }
            return convertView;
        }
    };
    private OnClickListener RI = new OnClickListener(this) {
        final /* synthetic */ h RK;

        {
            this.RK = this$0;
        }

        public void onClick(View v) {
            int i = -1;
            b holder = (b) v.getTag();
            if (holder.RY != -1) {
                h hVar = this.RK;
                if (this.RK.Py != holder.RY) {
                    i = holder.RY;
                }
                hVar.Py = i;
                this.RK.dh(this.RK.Py);
                this.RK.RH.notifyDataSetChanged();
            }
        }
    };
    private a RJ = new a();
    private GridView Rl = null;
    private ListView Rm = null;
    private int Rn = 0;
    private int Ro = 0;
    private final int Rp = 0;
    private final int Rq = 1;
    private final int Rr = 2;
    private final int Rs = 3;
    private final int Rt = 4;
    public int[] Rx = new int[25];
    private View Ry;
    private View Rz;

    /* compiled from: FloatLayoutEnchantView */
    private class a extends BaseAdapter {
        final /* synthetic */ h RK;
        private c RL;
        private String[] RM;
        public int[] RN;
        private int[] RO;
        private boolean[] RQ;

        /* compiled from: FloatLayoutEnchantView */
        class a {
            final /* synthetic */ a RR;
            CheckBox RT;
            View RU;
            View RV;
            TextView RW;
            View RX;

            a(a this$1) {
                this.RR = this$1;
            }
        }

        private a(h hVar) {
            this.RK = hVar;
            this.RM = new String[25];
            this.RN = new int[25];
            this.RO = new int[25];
            this.RQ = new boolean[25];
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchant_attr, parent, false);
                holder = new a(this);
                holder.RT = (CheckBox) convertView.findViewById(R.id.cb_attr_open);
                holder.RV = convertView.findViewById(R.id.btn_attr_lv_down);
                holder.RU = convertView.findViewById(R.id.btn_attr_lv_up);
                holder.RW = (TextView) convertView.findViewById(R.id.tv_attr_level);
                holder.RX = convertView.findViewById(R.id.tv_attr_intro);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            int levelCount = com.MCWorld.mcfloat.enchant.b.fI(this.RO[position]);
            final int currentLevel = this.RN[position];
            holder.RT.setOnCheckedChangeListener(null);
            holder.RT.setChecked(this.RQ[position]);
            holder.RT.setText(this.RM[position]);
            holder.RT.setOnCheckedChangeListener(new OnCheckedChangeListener(this) {
                final /* synthetic */ a RR;

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    this.RR.RQ[position] = isChecked;
                }
            });
            holder.RW.setText(currentLevel + "级");
            if (currentLevel < 1) {
                holder.RV.setEnabled(false);
            } else {
                holder.RV.setEnabled(true);
                holder.RV.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ a RR;

                    public void onClick(View v) {
                        this.RR.RN[position] = currentLevel - (h.Rw * 1);
                        this.RR.notifyDataSetChanged();
                    }
                });
            }
            if (currentLevel == levelCount) {
                holder.RU.setEnabled(false);
            } else {
                holder.RU.setEnabled(true);
                holder.RU.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ a RR;

                    public void onClick(View v) {
                        this.RR.RN[position] = currentLevel + (h.Rw * 1);
                        this.RR.notifyDataSetChanged();
                    }
                });
            }
            holder.RX.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ a RR;

                public void onClick(View v) {
                    this.RR.dj(position);
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
            if (this.RL != null) {
                return this.RL.uv();
            }
            return 0;
        }

        public void a(c item) {
            if (item != null) {
                this.RL = item;
                int[] mTmpSupportAttr = this.RL.uw();
                int count = this.RL.uv();
                for (int i = 0; i < count; i++) {
                    this.RM[i] = com.MCWorld.mcfloat.enchant.b.fG(mTmpSupportAttr[i]);
                    this.RN[i] = 0;
                    this.RQ[i] = false;
                    this.RO[i] = com.MCWorld.mcfloat.enchant.b.fH(mTmpSupportAttr[i]);
                }
                for (int j = count; j < 25; j++) {
                    this.RO[j] = -1;
                }
                notifyDataSetChanged();
            }
        }

        private void dj(int position) {
            new f(this.RK.Pu.getContext(), this.RO[position]).showDialog();
        }

        public void rq() {
            h.Rw = 1;
            for (int i = 0; i < getCount(); i++) {
                this.RQ[i] = true;
                this.RN[i] = h.Rw * com.MCWorld.mcfloat.enchant.b.fI(this.RO[i]);
            }
            notifyDataSetChanged();
        }

        public void aD(boolean bEnabled) {
            int i;
            if (bEnabled) {
                r.ck().K_umengEvent(com.MCWorld.r.a.kU);
            }
            if (bEnabled) {
                i = 20;
            } else {
                i = 1;
            }
            h.Rw = i;
            for (int i2 = 0; i2 < getCount(); i2++) {
                this.RQ[i2] = true;
                this.RN[i2] = h.Rw * com.MCWorld.mcfloat.enchant.b.fI(this.RO[i2]);
            }
            notifyDataSetChanged();
        }

        public void rr() {
            h.Rw = 1;
            for (int i = 0; i < getCount(); i++) {
                this.RQ[i] = false;
                this.RN[i] = 0;
            }
            notifyDataSetChanged();
        }

        public void rs() {
            for (int i = 0; i < getCount(); i++) {
                int level = (int) (Math.random() * ((double) com.MCWorld.mcfloat.enchant.b.fI(this.RO[i])));
                this.RN[i] = h.Rw * level;
                this.RQ[i] = level != 0;
            }
            notifyDataSetChanged();
        }
    }

    /* compiled from: FloatLayoutEnchantView */
    private static class b {
        int RY;
        TextView RZ;
        ImageView Sa;

        private b() {
        }
    }

    public void qN() {
    }

    public void cd(String mapName) {
    }

    public void qO() {
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (isShow && this.RG == 0) {
            u(e.ux());
        }
    }

    public h(View view, Activity c) {
        this.Pu = view;
        this.PD = c;
        view.setVisibility(8);
        this.Pu.findViewById(R.id.floatLayoutItemEnchantType).setVisibility(0);
        this.Rl = (GridView) view.findViewById(R.id.floatGridEnchantItemList);
        this.Rl.setAdapter(this.RH);
        u(e.ux());
        this.Rm = (ListView) view.findViewById(R.id.floatListExchantAttrList);
        this.Rm.setAdapter(this.RJ);
        this.Ry = this.Pu.findViewById(R.id.value_button_random);
        this.Ry.setOnClickListener(this);
        this.Rz = this.Pu.findViewById(R.id.value_button_best);
        this.Rz.setOnClickListener(this);
        this.RA = this.Pu.findViewById(R.id.value_button_top_level);
        this.RA.setOnClickListener(this);
        this.RD = this.Pu.findViewById(R.id.value_button_create);
        this.RD.setOnClickListener(this);
        this.RE = (ImageView) this.Pu.findViewById(R.id.iv_item);
        this.RE.setOnClickListener(this);
        Rw = 1;
        this.RF = (ViewSwitcher) this.Pu.findViewById(R.id.switcher);
        dh(0);
    }

    private void u(List<c> list) {
        this.PA = list;
        this.RH.notifyDataSetInvalidated();
    }

    private void rn() {
        this.RF.setDisplayedChild(0);
        this.Ry.setEnabled(true);
        this.Rz.setEnabled(true);
        this.RD.setEnabled(true);
    }

    private void ro() {
        this.RF.setDisplayedChild(1);
        this.Ry.setEnabled(false);
        this.Rz.setEnabled(false);
        this.RD.setEnabled(false);
    }

    private void d(boolean randomEnabled, boolean bestEnabled) {
        switch (this.Ro) {
            case 1:
                if (randomEnabled) {
                    this.RJ.rs();
                    return;
                }
                return;
            case 2:
                if (bestEnabled) {
                    this.RJ.rq();
                    return;
                }
                return;
            case 3:
                this.RJ.aD(true);
                return;
            case 4:
                this.RJ.rr();
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_item:
                if (this.RF.getDisplayedChild() == 0) {
                    ro();
                    return;
                } else {
                    rn();
                    return;
                }
            case R.id.value_button_random:
                this.Ro = 1;
                d(true, true);
                return;
            case R.id.value_button_best:
                this.Ro = 2;
                d(false, true);
                return;
            case R.id.value_button_top_level:
                this.Ro = 3;
                d(false, true);
                return;
            case R.id.value_button_create:
                for (int i = 0; i < 25; i++) {
                    if (this.RJ.RQ[i]) {
                        this.Rx[i] = this.RJ.RN[i];
                    } else {
                        this.Rx[i] = 0;
                    }
                }
                com.MCWorld.mcinterface.h.d(this.Rn, 0, 1, this.RJ.RO, this.Rx);
                r.ck().K_umengEvent(com.MCWorld.r.a.kT);
                com.MCWorld.mctool.e.Dk().iP(1);
                k.p(this.Pu.getContext(), "附魔完成,已加入背包");
                if (com.MCWorld.mcinterface.h.zx() != 5) {
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void dh(int selectPos) {
        if (selectPos == -1) {
            boolean enable = false;
        }
        if (selectPos != -1) {
            c item = (c) this.RH.getItem(selectPos);
            if (item != null) {
                this.Rn = item.getItemId();
                this.RE.setImageResource(item.getItemImgId());
                this.RJ.a(item);
                rn();
                d(false, false);
            }
        }
    }
}
