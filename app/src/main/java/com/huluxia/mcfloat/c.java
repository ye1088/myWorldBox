package com.huluxia.mcfloat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.huluxia.framework.R;
import com.huluxia.mcfloat.animal.PackItemAdapter;
import com.huluxia.mcfloat.animal.b;
import com.huluxia.mcfloat.p.a;
import com.huluxia.mcinterface.h;
import com.huluxia.mcinterface.i;
import com.huluxia.mojang.converter.ItemStack;
import com.huluxia.t;
import com.huluxia.utils.aj;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"InflateParams"})
/* compiled from: FloatLayoutAnimalView */
public class c implements a {
    private static List<ItemStack> PA = null;
    private PackItemAdapter PB;
    private GridView Pq = null;
    private SeekBar Pr = null;
    private b Ps;
    private ViewAnimator Pt;
    private View Pu = null;
    private b.a Pv = new b.a(this) {
        final /* synthetic */ c PC;

        {
            this.PC = this$0;
        }

        public void qS() {
            this.PC.Pt.setDisplayedChild(0);
        }
    };
    private int Pw = 1;
    private OnSeekBarChangeListener Px = new OnSeekBarChangeListener(this) {
        final /* synthetic */ c PC;

        {
            this.PC = this$0;
        }

        public void onStopTrackingTouch(SeekBar bar) {
        }

        public void onStartTrackingTouch(SeekBar bar) {
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            if (progress < this.PC.Pw) {
                bar.setProgress(this.PC.Pw);
            } else {
                ((TextView) this.PC.Pu.findViewById(R.id.floatSeekTextItemCount)).setText(String.valueOf(progress));
            }
        }
    };
    private int Py = -1;
    private OnItemClickListener Pz = new OnItemClickListener(this) {
        final /* synthetic */ c PC;

        {
            this.PC = this$0;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (position < c.PA.size() && ((ItemStack) c.PA.get(position)).getItemId() != -1) {
                c cVar = this.PC;
                if (position == this.PC.PB.ug()) {
                    position = -1;
                }
                cVar.Py = position;
                this.PC.PB.fB(this.PC.Py);
                boolean enable = this.PC.Py != -1;
                this.PC.Pr.setEnabled(enable);
                this.PC.Pu.findViewById(R.id.floatSeekButton1).setEnabled(enable);
                this.PC.Pu.findViewById(R.id.floatSeekButton2).setEnabled(enable);
                this.PC.Pu.findViewById(R.id.floatButtonSelectAll).setEnabled(enable);
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ c PC;

        {
            this.PC = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.floatButtonSelectAll:
                    this.PC.qQ();
                    return;
                case R.id.floatSeekButton1:
                    this.PC.cZ(this.PC.Py);
                    t.show_toast(this.PC.Pu.getContext(), "添加生物完毕");
                    return;
                case R.id.floatSeekButton2:
                    this.PC.da(this.PC.Py);
                    return;
                default:
                    return;
            }
        }
    };

    public void cd(String mapName) {
    }

    public void qN() {
    }

    public void qO() {
        if (this.Pu.getVisibility() == 0 && h.vM()) {
            qP();
        }
    }

    public void az(boolean isShow) {
        this.Pu.setVisibility(isShow ? 0 : 8);
        if (isShow) {
            qP();
        }
    }

    public c(View view, Context context) {
        this.Pu = view;
        view.setVisibility(8);
        this.Pu.findViewById(R.id.floatEditItemSearch).setVisibility(8);
        this.Pu.findViewById(R.id.floatLayoutItemType1).setVisibility(8);
        this.Pu.findViewById(R.id.floatLayoutItemType2).setVisibility(8);
        TextView btn1 = (TextView) this.Pu.findViewById(R.id.floatSeekButton1);
        TextView btn2 = (TextView) this.Pu.findViewById(R.id.floatSeekButton2);
        btn1.setText("添加");
        btn2.setText("删除");
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        if (h.zx() == 3) {
            if (VERSION.SDK_INT < 11) {
                btn2.setEnabled(false);
                btn2.setVisibility(8);
            }
        } else if (h.zx() != 5 && h.zx() == 7) {
        }
        btn1.setOnClickListener(this.mClickListener);
        btn2.setOnClickListener(this.mClickListener);
        this.PB = new PackItemAdapter();
        this.Pq = (GridView) view.findViewById(R.id.floatGridItemList);
        this.Pq.setAdapter(this.PB);
        this.Pq.setOnItemClickListener(this.Pz);
        this.Pr = (SeekBar) view.findViewById(R.id.floatSeekbarItemCount);
        this.Pr.setOnSeekBarChangeListener(this.Px);
        this.Pr.setProgress(this.Pw);
        this.Pr.setEnabled(false);
        int n;
        if (h.zx() == 2 || h.zx() == 3) {
            PA = new ArrayList(26);
            for (n = 0; n < 26; n++) {
                PA.add(0, new ItemStack(0, 0, false, "", 0));
            }
        } else if (h.zx() == 5) {
            PA = new ArrayList(27);
            for (n = 0; n < 27; n++) {
                PA.add(0, new ItemStack(0, 0, false, "", 0));
            }
        } else if (h.zx() == 7) {
            PA = new ArrayList(34);
            for (n = 0; n < 34; n++) {
                PA.add(0, new ItemStack(0, 0, false, "", 0));
            }
        } else {
            PA = new ArrayList(16);
            for (n = 0; n < 16; n++) {
                PA.add(0, new ItemStack(0, 0, false, "", 0));
            }
        }
        this.PB.b(PA, true);
        if (h.zx() == 5 || h.zx() == 7) {
            TextView btnStrengthen = (TextView) this.Pu.findViewById(R.id.floatButtonSelectAll);
            btnStrengthen.setText("强化");
            btnStrengthen.setEnabled(false);
            btnStrengthen.setVisibility(0);
            btnStrengthen.setOnClickListener(this.mClickListener);
            this.Pt = (ViewAnimator) this.Pu.findViewById(R.id.animatorMainviewItem);
            this.Ps = new b(this.Pu.findViewById(R.id.icdeStrengthen), context, this.Pv);
            return;
        }
        this.Pu.findViewById(R.id.floatButtonSelectAll).setVisibility(8);
    }

    private void qP() {
        ArrayList<i> list = h.wQ();
        if (list != null && list.size() == PA.size()) {
            boolean isItemChaged = false;
            for (int n = 0; n < PA.size(); n++) {
                i biont = (i) list.get(n);
                ItemStack old = (ItemStack) PA.get(n);
                if (biont.getId() != old.getItemId() || biont.qm() != old.getItemCount()) {
                    isItemChaged = true;
                    ((ItemStack) PA.get(n)).setItemInfo(aj.ls(biont.getId()));
                    ((ItemStack) PA.get(n)).setItemCount(biont.qm());
                }
            }
            if (isItemChaged) {
                this.PB.notifyDataSetChanged();
            }
        }
    }

    private void cZ(int nListPos) {
        ItemStack item = (ItemStack) this.PB.getItem(this.Py);
        if (item != null) {
            h.P(item.getItemId(), this.Pr.getProgress());
        }
    }

    private void da(int nListPos) {
        ItemStack item = (ItemStack) this.PB.getItem(this.Py);
        if (item != null && item.getItemCount() != 0) {
            h.Q(item.getItemId(), -1);
        }
    }

    private void qQ() {
        if (this.Py >= 0 && this.Py < PA.size()) {
            this.Ps.a((ItemStack) PA.get(this.Py));
            this.Pt.setDisplayedChild(1);
        }
    }
}
