package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mcinterface.c;
import com.huluxia.mcinterface.h;
import com.huluxia.mojang.converter.ItemStack;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.utils.aj;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SuppressLint({"InflateParams"})
/* compiled from: FloatLayoutItemView */
public class k implements com.huluxia.mcfloat.p.a {
    private static final int SO = 0;
    private static final int SQ = 1;
    private static final int SR = 2;
    private static Set<Integer> SS = new HashSet();
    private static Set<Integer> ST = new HashSet();
    private List<ItemStack> PA = null;
    private GridView Pq = null;
    private SeekBar Pr = null;
    private View Pu = null;
    private int Pw = 0;
    private OnSeekBarChangeListener Px = new OnSeekBarChangeListener(this) {
        final /* synthetic */ k Tt;

        {
            this.Tt = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            if (progress < this.Tt.Pw) {
                bar.setProgress(this.Tt.Pw);
            } else {
                ((TextView) this.Tt.Pu.findViewById(R.id.floatSeekTextItemCount)).setText(String.valueOf(progress));
            }
        }
    };
    private int Py = -1;
    private int RG = 0;
    private BaseAdapter RH = new BaseAdapter(this) {
        final /* synthetic */ k Tt;

        {
            this.Tt = this$0;
        }

        public long getItemId(int pos) {
            return 0;
        }

        public int getCount() {
            return this.Tt.PA == null ? 0 : this.Tt.PA.size();
        }

        public Object getItem(int pos) {
            if (pos == -1 || pos >= this.Tt.PA.size()) {
                return null;
            }
            return this.Tt.PA.get(pos);
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floatitem_exex, null);
                holder = new a();
                holder.RZ = (TextView) convertView.findViewById(R.id.floatItemItemName);
                holder.Sa = (ImageView) convertView.findViewById(R.id.floatItemItemLogo);
                holder.Tu = (ImageView) convertView.findViewById(R.id.floatItemItemSelectedFlag);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            convertView.setOnClickListener(this.Tt.Tp);
            if (k.dx(pos)) {
                holder.Tu.setVisibility(0);
            } else {
                holder.Tu.setVisibility(8);
            }
            ItemStack info = (ItemStack) getItem(pos);
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
    private EditText SU = null;
    private TextView SV = null;
    private TextView SW = null;
    private TextView SX = null;
    private boolean SY = false;
    private com.huluxia.widget.a SZ;
    private RadioButton Ta;
    private RadioButton Tb;
    private RadioButton Tc;
    private RadioButton Td;
    private RadioButton Te;
    private RadioButton Tf;
    private RadioButton Tg;
    private RadioButton Th;
    private RadioButton Ti;
    private RadioButton Tj;
    private RadioButton Tk;
    private RadioButton Tl;
    private RadioButton Tm;
    private RadioButton Tn;
    private com.huluxia.widget.a.a To = new com.huluxia.widget.a.a(this) {
        final /* synthetic */ k Tt;

        {
            this.Tt = this$0;
        }

        public void a(CompoundButton button, boolean chedked) {
            if (chedked) {
                switch (button.getId()) {
                    case R.id.floatBtnItemTypePack:
                        this.Tt.SY = false;
                        k.rw();
                        this.Tt.rC();
                        return;
                    case R.id.floatBtnItemTypeV121New:
                        r.ck().K_umengEvent(com.huluxia.r.a.kR);
                        this.Tt.v(aj.Mm());
                        return;
                    case R.id.floatBtnItemTypeV131RedStone:
                        this.Tt.v(aj.Mo());
                        return;
                    case R.id.floatBtnItemTypeBlock:
                        this.Tt.v(aj.Mi());
                        return;
                    case R.id.floatBtnItemTypeStone:
                        this.Tt.v(aj.Mj());
                        return;
                    case R.id.floatBtnItemTypeTool:
                        this.Tt.v(aj.Mg());
                        return;
                    case R.id.floatBtnItemTypeFood:
                        this.Tt.v(aj.Mf());
                        return;
                    case R.id.floatBtnItemTypeMedicine:
                        r.ck().K_umengEvent(com.huluxia.r.a.kS);
                        this.Tt.v(aj.Mn());
                        return;
                    case R.id.floatBtnItemTypeTree:
                        this.Tt.v(aj.Mh());
                        return;
                    case R.id.floatBtnItemTypeColor:
                        this.Tt.v(aj.Mk());
                        return;
                    case R.id.floatBtnItemTypeOther:
                        this.Tt.v(aj.Ml());
                        return;
                    case R.id.floatBtnItemTypeEgg:
                        this.Tt.v(aj.Mp());
                        return;
                    case R.id.floatBtnItemTypeSave:
                        r.ck().K_umengEvent(com.huluxia.r.a.kQ);
                        this.Tt.v(b.qy().qz());
                        return;
                    case R.id.floatBtnItemTypeJs:
                        this.Tt.v(this.Tt.Tq);
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private OnClickListener Tp = new OnClickListener(this) {
        final /* synthetic */ k Tt;

        {
            this.Tt = this$0;
        }

        public void onClick(View v) {
            int i = -1;
            a holder = (a) v.getTag();
            if (holder.RY != -1) {
                if (k.dx(holder.RY)) {
                    this.Tt.Py = -1;
                    k.dw(holder.RY);
                    k kVar = this.Tt;
                    if (k.rx() > 0) {
                        i = 0;
                    }
                    kVar.Py = i;
                } else {
                    this.Tt.Py = holder.RY;
                    k.dv(this.Tt.Py);
                }
                if (this.Tt.RG == 2) {
                    int nSeekShow = k.rx() >= 0 ? 0 : 8;
                    this.Tt.SU.setVisibility(8);
                    this.Tt.Pu.findViewById(R.id.floatSeekbarItemCount).setVisibility(nSeekShow);
                    this.Tt.Pu.findViewById(R.id.floatSeekTextItemCount).setVisibility(nSeekShow);
                }
                this.Tt.dy(this.Tt.Py);
                this.Tt.RH.notifyDataSetChanged();
            }
        }
    };
    private List<ItemStack> Tq = new ArrayList();
    private List<ItemStack> Tr = new ArrayList();
    private TextWatcher Ts = new TextWatcher(this) {
        final /* synthetic */ k Tt;

        {
            this.Tt = this$0;
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().length() != 0) {
                aj.d(s.toString(), this.Tt.Tr);
                this.Tt.PA = this.Tt.Tr;
                this.Tt.RH.notifyDataSetChanged();
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ k Tt;

        {
            this.Tt = this$0;
        }

        public void onClick(View v) {
            boolean z = false;
            switch (v.getId()) {
                case R.id.floatButtonSelectAll:
                    k kVar = this.Tt;
                    if (!this.Tt.SY) {
                        z = true;
                    }
                    kVar.SY = z;
                    if (this.Tt.RG == 1) {
                        this.Tt.aG(this.Tt.SY);
                        return;
                    }
                    return;
                case R.id.floatSeekButton1:
                    if (this.Tt.RG == 1) {
                        this.Tt.aH(false);
                    }
                    if (this.Tt.RG == 2) {
                        this.Tt.rz();
                        return;
                    }
                    return;
                case R.id.floatSeekButton2:
                    if (this.Tt.RG == 1) {
                        this.Tt.aH(true);
                    }
                    if (this.Tt.RG == 2) {
                        this.Tt.rA();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatLayoutItemView */
    private static class a {
        int RY;
        TextView RZ;
        ImageView Sa;
        ImageView Tu;

        private a() {
        }
    }

    private static void rw() {
        ST.clear();
    }

    private static void dv(int id) {
        ST.add(Integer.valueOf(id));
    }

    private static void dw(int id) {
        ST.remove(Integer.valueOf(id));
    }

    private static boolean dx(int id) {
        return ST.contains(Integer.valueOf(id));
    }

    private static int rx() {
        return ST.size();
    }

    public boolean ry() {
        return this.SY;
    }

    public void aF(boolean mSelectAllMyBagFlag) {
        this.SY = mSelectAllMyBagFlag;
    }

    public void qN() {
    }

    public void qO() {
        if (this.Pu.getVisibility() == 0 && this.RG == 1) {
            rB();
        }
    }

    public void cd(String mapName) {
        this.Py = -1;
        if (this.Tq.size() <= 0) {
            ArrayList<c> list = h.zA();
            if (list != null && list.size() != 0) {
                this.Tq.clear();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    c item = (c) it.next();
                    if (item.getId() != 0) {
                        ItemStack newItem = new ItemStack(item.getId(), item.ql(), false, item.zh(), 0);
                        newItem.setItemBitmap(item.zj());
                        aj.a(this.Tq, newItem);
                    }
                }
            }
        }
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (!isShow) {
            return;
        }
        if (this.RG == 1) {
            rC();
        } else if (this.RG == 0) {
            this.Tf.setChecked(true);
        }
    }

    public k(View view) {
        this.Pu = view;
        view.setVisibility(8);
        this.Pu.findViewById(R.id.floatLayoutItemType1).setVisibility(0);
        this.Pu.findViewById(R.id.floatLayoutItemType2).setVisibility(0);
        this.Pq = (GridView) view.findViewById(R.id.floatGridItemList);
        this.Pq.setAdapter(this.RH);
        this.SX = (TextView) this.Pu.findViewById(R.id.floatButtonSelectAll);
        this.SV = (TextView) this.Pu.findViewById(R.id.floatSeekButton1);
        this.SW = (TextView) this.Pu.findViewById(R.id.floatSeekButton2);
        this.SX.setOnClickListener(this.mClickListener);
        this.SV.setOnClickListener(this.mClickListener);
        this.SW.setOnClickListener(this.mClickListener);
        this.SU = (EditText) view.findViewById(R.id.floatEditItemSearch);
        this.SU.addTextChangedListener(this.Ts);
        this.Pr = (SeekBar) view.findViewById(R.id.floatSeekbarItemCount);
        this.Pr.setOnSeekBarChangeListener(this.Px);
        this.Tg = (RadioButton) view.findViewById(R.id.floatBtnItemTypeJs);
        this.Ta = (RadioButton) view.findViewById(R.id.floatBtnItemTypeSave);
        this.Tf = (RadioButton) view.findViewById(R.id.floatBtnItemTypePack);
        this.Tj = (RadioButton) view.findViewById(R.id.floatBtnItemTypeTool);
        this.Tc = (RadioButton) view.findViewById(R.id.floatBtnItemTypeFood);
        this.Tb = (RadioButton) view.findViewById(R.id.floatBtnItemTypeTree);
        this.Th = (RadioButton) view.findViewById(R.id.floatBtnItemTypeBlock);
        this.Ti = (RadioButton) view.findViewById(R.id.floatBtnItemTypeStone);
        this.Td = (RadioButton) view.findViewById(R.id.floatBtnItemTypeColor);
        this.Te = (RadioButton) view.findViewById(R.id.floatBtnItemTypeOther);
        this.Tk = (RadioButton) view.findViewById(R.id.floatBtnItemTypeV121New);
        this.Tl = (RadioButton) view.findViewById(R.id.floatBtnItemTypeMedicine);
        this.Tm = (RadioButton) view.findViewById(R.id.floatBtnItemTypeV131RedStone);
        this.Tn = (RadioButton) view.findViewById(R.id.floatBtnItemTypeEgg);
        this.SZ = new com.huluxia.widget.a(this.To);
        this.SZ.a(this.Ta);
        this.SZ.a(this.Tc, this.Tb, this.Th);
        this.SZ.a(this.Ti, this.Td, this.Te);
        this.SZ.a(this.Tg, this.Tf, this.Tj);
        if (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
            this.SZ.a(this.Tk, this.Tl);
            if (h.zx() == 3 || h.zx() == 5 || h.zx() == 7) {
                this.SZ.a(this.Tm, this.Tn);
                return;
            }
            this.Tm.setVisibility(8);
            this.Tn.setVisibility(8);
            return;
        }
        this.Tk.setVisibility(8);
        this.Tl.setVisibility(8);
        this.Tm.setVisibility(8);
        this.Tn.setVisibility(8);
        this.Tg.setVisibility(0);
        this.Tf.setVisibility(0);
    }

    private void dy(int selectPos) {
        boolean enable = selectPos != -1;
        this.SV.setEnabled(enable);
        this.SW.setEnabled(enable);
        this.Pr.setEnabled(enable);
        if (selectPos != -1) {
            ItemStack item = (ItemStack) this.RH.getItem(selectPos);
            if (item != null && item.getItemCount() != 0) {
                this.Pr.setProgress(item.getItemCount());
            }
        }
    }

    private void v(List<ItemStack> list) {
        this.SU.setText("");
        this.SU.setVisibility(0);
        this.Pu.findViewById(R.id.floatSeekbarItemCount).setVisibility(8);
        this.Pu.findViewById(R.id.floatSeekTextItemCount).setVisibility(8);
        this.SV.setText("添加");
        this.SW.setText("收藏");
        this.SX.setVisibility(8);
        this.SV.setVisibility(0);
        if (this.Ta.isChecked()) {
            this.SW.setText("删除");
        }
        this.Pw = 1;
        this.Py = -1;
        rw();
        dy(this.Py);
        this.Pr.setProgress(this.Pw);
        if (this.RG != 2) {
            this.Pr.setProgress(this.Pw);
        }
        this.RG = 2;
        this.PA = list;
        this.RH.notifyDataSetInvalidated();
    }

    private void rz() {
        if (h.getGameType() == 1) {
            com.huluxia.k.p(this.Pu.getContext(), "创造模式无法添加物品");
        } else if (rx() != 0) {
            for (Integer _itemIndex : ST) {
                ItemStack _item = (ItemStack) this.RH.getItem(_itemIndex.intValue());
                if (_item != null) {
                    h.x(_item.getItemId(), this.Pr.getProgress(), _item.getItemDmg());
                }
            }
            com.huluxia.k.p(this.Pu.getContext(), "添加物品完成");
            this.Py = -1;
            rw();
            this.RH.notifyDataSetChanged();
        }
    }

    private void rA() {
        boolean mDelItemFlag = false;
        String strTipsText = "";
        if (ST != null) {
            if (this.Tg.isChecked()) {
                t.p(this.Pu.getContext(), "收藏失败");
                return;
            }
            b.qy().qA();
            for (Integer _itemIndex : ST) {
                mDelItemFlag = true;
                ItemStack _item = (ItemStack) this.RH.getItem(_itemIndex.intValue());
                if (!(_item == null || _item.getItemId() == 0)) {
                    if (this.Ta.isChecked()) {
                        b.qy().v(_item.getItemId(), _item.getItemDmg());
                        strTipsText = "删除收藏物品成功";
                    } else {
                        strTipsText = b.qy().w(_item.getItemId(), _item.getItemDmg()) ? "物品添加收藏成功" : "收藏中已经存在物品";
                    }
                }
            }
            b.qy().qB();
            this.Py = -1;
            rw();
            this.RH.notifyDataSetChanged();
            if (mDelItemFlag) {
                t.p(this.Pu.getContext(), strTipsText);
            }
        }
    }

    private void rB() {
        this.PA = b.qy().qE();
        if (this.Py >= this.PA.size()) {
            this.Py = -1;
        }
        this.RH.notifyDataSetChanged();
    }

    private void aG(boolean bSelectAll) {
        int _itemCnt = this.RH.getCount();
        if (bSelectAll) {
            for (int _i = 0; _i < _itemCnt; _i++) {
                if (((ItemStack) this.RH.getItem(_i)).getItemId() > 0) {
                    dv(_i);
                }
            }
            if (rx() > 0) {
                dy(0);
            }
        } else {
            rw();
        }
        this.RH.notifyDataSetChanged();
    }

    private void aH(boolean bDelete) {
        boolean _bDelMultipleItemsFlag = false;
        if (!bDelete && (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7)) {
            t.n(this.Pu.getContext(), "12版本暂时不支持修改背包物品数量!");
        } else if (ST != null && rx() != 0) {
            int _newCount = this.Pr.getProgress();
            SS.clear();
            for (Integer _itemIndex : ST) {
                ItemStack _item = (ItemStack) this.RH.getItem(_itemIndex.intValue());
                if (!(_item == null || _item.getItemId() == 0 || _item.getItemBagIdx() == -1)) {
                    if (bDelete) {
                        _newCount = 0;
                    }
                    if (_item.getItemCount() != _newCount) {
                        if (true == bDelete && (h.zx() == 2 || h.zx() == 3 || h.zx() == 5 || h.zx() == 7)) {
                            _bDelMultipleItemsFlag = true;
                            SS.add(Integer.valueOf(_item.getmInGameBagIndex()));
                        } else if (_newCount == 0) {
                            _bDelMultipleItemsFlag = true;
                            SS.add(Integer.valueOf(_item.getmInGameBagIndex()));
                        } else {
                            h.R(_item.getItemBagIdx(), _newCount);
                        }
                    }
                }
            }
            if (_bDelMultipleItemsFlag) {
                h.f(SS);
            }
            this.Py = -1;
            rw();
            this.RH.notifyDataSetChanged();
            dy(this.Py);
            com.huluxia.k.p(this.Pu.getContext(), _newCount == 0 ? "删除物品完成" : "修改数量完成");
            rB();
        }
    }

    private void rC() {
        this.RG = 1;
        this.SU.setText("");
        this.SU.setVisibility(8);
        this.Pu.findViewById(R.id.floatSeekbarItemCount).setVisibility(0);
        this.Pu.findViewById(R.id.floatSeekTextItemCount).setVisibility(0);
        this.SV.setText("修改");
        this.SW.setText("删除");
        this.SX.setVisibility(0);
        this.SV.setVisibility(8);
        this.Pw = 0;
        this.Py = -1;
        this.Pr.setProgress(this.Pw);
        dy(this.Py);
        rB();
    }
}
