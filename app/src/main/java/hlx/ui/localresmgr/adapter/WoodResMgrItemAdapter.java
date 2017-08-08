package hlx.ui.localresmgr.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.k;
import com.MCWorld.t;
import com.MCWorld.utils.ah;
import com.MCWorld.widget.Constants.ReStartSoftFlag;
import com.MCWorld.widget.dialog.e;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WoodResMgrItemAdapter extends BaseAdapter {
    private static final String TAG = "WoodResMgrItemAdapter";
    private List<Object> akJ = null;
    private DialogManager akK;
    private boolean bYS = false;
    private com.MCWorld.data.map.b bYT;
    private OnCheckedChangeListener bYW = new OnCheckedChangeListener(this) {
        final /* synthetic */ WoodResMgrItemAdapter bZn;

        {
            this.bZn = this$0;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int id = compoundButton.getId();
            com.MCWorld.data.map.b item = (com.MCWorld.data.map.b) compoundButton.getTag();
            if (b) {
                for (com.MCWorld.data.map.b si : this.bZn.mObjects) {
                    if (b && si.path.equals(item.path)) {
                        si.state = 1;
                        ah.KZ().gc(item.path);
                        hlx.ui.localresmgr.cache.a.Un().hy(item.pU);
                    } else {
                        si.state = 0;
                    }
                }
            } else {
                ah.KZ().gc(null);
                for (com.MCWorld.data.map.b si2 : this.bZn.mObjects) {
                    si2.state = 0;
                    hlx.ui.localresmgr.cache.a.Un().hA(item.pU);
                }
            }
            this.bZn.notifyDataSetChanged();
            if (this.bZn.bZm != null) {
                this.bZn.bZm.dL(b);
            }
        }
    };
    private CommonMenuDialog bZl;
    private a bZm;
    private Activity bns;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ WoodResMgrItemAdapter bZn;

        {
            this.bZn = this$0;
        }

        public void onClick(View v) {
            com.MCWorld.data.map.b item = (com.MCWorld.data.map.b) ((c) v.getTag()).bZc.getTag();
            if (this.bZn.bYS) {
                if (this.bZn.akJ.contains(item)) {
                    this.bZn.akJ.remove(item);
                } else {
                    this.bZn.akJ.add(item);
                }
                this.bZn.notifyDataSetChanged();
                return;
            }
            this.bZn.k(item);
        }
    };
    private LayoutInflater mInflater = null;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();
    private List<Object> mObjects;

    public interface a {
        void dL(boolean z);
    }

    private class b implements com.MCWorld.widget.dialog.e.a {
        final /* synthetic */ WoodResMgrItemAdapter bZn;

        private b(WoodResMgrItemAdapter woodResMgrItemAdapter) {
            this.bZn = woodResMgrItemAdapter;
        }

        public void rb() {
        }

        public void cf(String msg) {
            if (msg.trim().length() >= 1) {
                hlx.ui.localresmgr.cache.b.Us().b(this.bZn.bYT, this.bZn.bYT.path, this.bZn.bYT.name, msg);
                this.bZn.notifyDataSetChanged();
            }
        }
    }

    static class c {
        PaintView aUN;
        TextView aWl;
        RelativeLayout bYZ;
        TextView bZa;
        TextView bZb;
        CheckBox bZc;
        RelativeLayout bZd;
        ImageView bZe;
        TextView name;

        c() {
        }
    }

    public WoodResMgrItemAdapter(Activity context, List<Object> objects) {
        this.bns = context;
        this.mObjects = objects;
        this.mInflater = LayoutInflater.from(context.getApplicationContext());
        Uf();
    }

    private void T(List<Object> objects) {
        if (!UtilsFunction.empty(objects)) {
            for (int i = objects.size() - 1; i >= 0; i--) {
                if (((com.MCWorld.data.map.b) objects.get(i)).ver.contains(String.valueOf(ReStartSoftFlag.MC_RESTART_V0150.Value()))) {
                    objects.remove(i);
                }
            }
        }
    }

    public void Ud() {
        if (this.akJ.size() == this.mObjects.size()) {
            this.akJ.clear();
        } else {
            this.akJ.clear();
            for (int i = 0; i < this.mObjects.size(); i++) {
                this.akJ.add(this.mObjects.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public void a(a mLoadWoodListener) {
        this.bZm = mLoadWoodListener;
    }

    public int getCount() {
        return this.mObjects.size();
    }

    public Object getItem(int i) {
        return this.mObjects.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        c holder;
        boolean z;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.bns).inflate(R.layout.itm_local_resmgr_map, null);
            holder = new c();
            holder.aUN = (PaintView) convertView.findViewById(R.id.rlyLocalResMgrMapItemImg);
            holder.bYZ = (RelativeLayout) convertView.findViewById(R.id.rlyLocalResMgrMapItemImgContainer);
            holder.bZa = (TextView) convertView.findViewById(R.id.tvResVersion);
            holder.name = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemTitle);
            holder.aWl = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemSize);
            holder.bZb = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemDate);
            holder.bZc = (CheckBox) convertView.findViewById(R.id.JsChkSwitch);
            holder.bZd = (RelativeLayout) convertView.findViewById(R.id.rlyResMgrItemDel);
            holder.bZe = (ImageView) convertView.findViewById(R.id.ivResMgrMapItemCheckDel);
            convertView.setTag(holder);
        } else {
            holder = (c) convertView.getTag();
        }
        com.MCWorld.data.map.b item = (com.MCWorld.data.map.b) getItem(position);
        holder.name.setText(item.name);
        if (holder.aWl != null) {
            if (item.size == 0) {
                holder.aWl.setText("未知大小");
            } else {
                holder.aWl.setText(item.pS);
            }
        }
        holder.bZb.setText(item.pT);
        holder.bZc.setTag(item);
        holder.bZc.setOnCheckedChangeListener(null);
        CheckBox checkBox = holder.bZc;
        if (item.state == 1) {
            z = true;
        } else {
            z = false;
        }
        checkBox.setChecked(z);
        holder.bZc.setOnCheckedChangeListener(this.bYW);
        holder.aUN.setVisibility(0);
        holder.bYZ.setVisibility(0);
        if (item.pR.startsWith("http")) {
            t.a(holder.aUN, item.pR, 0.0f);
        } else {
            holder.aUN.setImageDrawable(d.o(this.bns, R.attr.ic_default_loc_res));
        }
        if (item.ver != null) {
            holder.bZa.setVisibility(0);
            holder.bZa.setText(hlx.ui.localresmgr.utils.a.hK(item.ver));
        } else {
            holder.bZa.setVisibility(8);
        }
        if (this.bYS) {
            holder.bZc.setVisibility(8);
            holder.bZd.setVisibility(0);
            if (this.akJ.contains(item)) {
                holder.bZe.setVisibility(0);
            } else {
                holder.bZe.setVisibility(8);
            }
        } else {
            holder.bZc.setVisibility(0);
            holder.bZd.setVisibility(8);
        }
        convertView.setOnClickListener(this.mClickListener);
        return convertView;
    }

    public void dI(boolean isDelMode) {
        this.bYS = isDelMode;
        if (!this.bYS) {
            this.akJ.clear();
        }
        notifyDataSetChanged();
    }

    public void S(List<Object> checkList) {
        this.akJ = checkList;
    }

    public void i(com.MCWorld.data.map.b item) {
        hlx.ui.localresmgr.cache.b.Us().o(item);
        String usingWood = ah.KZ().LJ();
        if (usingWood != null && usingWood.equalsIgnoreCase(item.path)) {
            ah.KZ().gc(null);
        }
        if (this.bZm != null) {
            this.bZm.dL(false);
        }
    }

    public void Ui() {
        e dialog = new e(this.bns, new b());
        dialog.az("重命名", this.bYT.name);
        dialog.aA(this.bns.getString(R.string.local_resmgr_dlg_cancle), this.bns.getString(R.string.local_resmgr_dlg_save));
        dialog.showDialog();
    }

    private void j(final com.MCWorld.data.map.b item) {
        int colorMsg = d.getColor(this.bns, R.attr.dialog_msg_label_color);
        int colorRed = this.bns.getResources().getColor(R.color.dialog_ok_btn_color);
        int colorGray = d.getColor(this.bns, 16842808);
        int colorTitle = d.getColor(this.bns, R.attr.dialog_title_label_color);
        View customView = this.mInflater.inflate(R.layout.dialog_delete_resource, null);
        TextView tip = (TextView) customView.findViewById(R.id.tv_tip);
        tip.setTextColor(colorMsg);
        tip.setText("该删除操作不可恢复，是否继续？");
        this.akK.showOkCancelColorDialog("温馨提示", colorTitle, customView, this.bns.getString(R.string.delete), colorRed, this.bns.getString(R.string.btn_cancel), colorGray, true, new OkCancelDialogListener(this) {
            final /* synthetic */ WoodResMgrItemAdapter bZn;

            public void onCancel() {
                this.bZn.akK.dismissDialog();
            }

            public void onOk() {
                Iterator it = this.bZn.mObjects.iterator();
                while (it.hasNext()) {
                    com.MCWorld.data.map.b fileItem = (com.MCWorld.data.map.b) it.next();
                    if (fileItem.path.equals(item.path)) {
                        this.bZn.i(fileItem);
                        it.remove();
                    }
                }
                this.bZn.notifyDataSetChanged();
            }
        });
    }

    private void Uf() {
        this.akK = new DialogManager(this.bns);
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuDetail), 1, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuRename), 3, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuDel), 6, R.color.locmgr_menu_res_red_color_day));
        this.bZl = new CommonMenuDialog(this.bns, this.mMenuItemArrayList, new CommonMenuDialogListener(this) {
            final /* synthetic */ WoodResMgrItemAdapter bZn;

            {
                this.bZn = this$0;
            }

            public void pressMenuById(int inIndex, Object inObject) {
                com.MCWorld.data.map.b item = (com.MCWorld.data.map.b) inObject;
                switch (inIndex) {
                    case 1:
                        k.e(this.bZn.bns, item.mapId, item.pQ);
                        this.bZn.bZl.dismissDialog();
                        return;
                    case 3:
                        this.bZn.bYT = item;
                        this.bZn.Ui();
                        this.bZn.bZl.dismissDialog();
                        return;
                    case 6:
                        this.bZn.j(item);
                        this.bZn.bZl.dismissDialog();
                        return;
                    default:
                        return;
                }
            }
        }, d.RB());
    }

    private void k(com.MCWorld.data.map.b item) {
        this.bZl.showMenu(item, item.name);
    }
}
