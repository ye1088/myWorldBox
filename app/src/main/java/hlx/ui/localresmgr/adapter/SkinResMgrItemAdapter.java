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
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.k;
import com.huluxia.t;
import com.huluxia.utils.ah;
import com.huluxia.widget.dialog.e;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkinResMgrItemAdapter extends BaseAdapter {
    private List<Object> akJ = null;
    private DialogManager akK;
    private boolean bYS = false;
    private com.huluxia.data.map.b bYT;
    private OnCheckedChangeListener bYW = new OnCheckedChangeListener(this) {
        final /* synthetic */ SkinResMgrItemAdapter bZk;

        {
            this.bZk = this$0;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            int id = compoundButton.getId();
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) compoundButton.getTag();
            if (b) {
                for (com.huluxia.data.map.b si : this.bZk.mObjects) {
                    if (b && si.path.equals(item.path)) {
                        si.state = 1;
                        ah.KZ().gb(item.path);
                    } else {
                        si.state = 0;
                    }
                }
            } else {
                ah.KZ().gb(null);
                for (com.huluxia.data.map.b si2 : this.bZk.mObjects) {
                    si2.state = 0;
                }
            }
            this.bZk.notifyDataSetChanged();
            if (this.bZk.bZj != null) {
                this.bZk.bZj.dK(b);
            }
        }
    };
    private CommonMenuDialog bZi;
    private a bZj;
    private Activity bns;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ SkinResMgrItemAdapter bZk;

        {
            this.bZk = this$0;
        }

        public void onClick(View v) {
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) ((c) v.getTag()).bZc.getTag();
            if (this.bZk.bYS) {
                if (this.bZk.akJ.contains(item)) {
                    this.bZk.akJ.remove(item);
                } else {
                    this.bZk.akJ.add(item);
                }
                this.bZk.notifyDataSetChanged();
                return;
            }
            this.bZk.k(item);
        }
    };
    private LayoutInflater mInflater = null;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();
    private List<Object> mObjects;

    public interface a {
        void dK(boolean z);
    }

    private class b implements com.huluxia.widget.dialog.e.a {
        final /* synthetic */ SkinResMgrItemAdapter bZk;

        private b(SkinResMgrItemAdapter skinResMgrItemAdapter) {
            this.bZk = skinResMgrItemAdapter;
        }

        public void rb() {
        }

        public void cf(String msg) {
            if (msg.trim().length() >= 1) {
                hlx.ui.localresmgr.cache.b.Us().a(this.bZk.bYT, this.bZk.bYT.path, this.bZk.bYT.name, msg);
                this.bZk.notifyDataSetChanged();
            }
        }
    }

    static class c {
        PaintView aUN;
        TextView aWl;
        RelativeLayout bYZ;
        TextView bZb;
        CheckBox bZc;
        RelativeLayout bZd;
        ImageView bZe;
        TextView name;

        c() {
        }
    }

    public SkinResMgrItemAdapter(Activity context, List<Object> objects) {
        this.bns = context;
        this.mObjects = objects;
        this.mInflater = LayoutInflater.from(context.getApplicationContext());
        Uf();
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

    public void a(a mLoadSkinListener) {
        this.bZj = mLoadSkinListener;
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
            holder.aUN.setScaleType(ScaleType.FIT_XY);
            holder.bYZ = (RelativeLayout) convertView.findViewById(R.id.rlyLocalResMgrMapItemImgContainer);
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
        com.huluxia.data.map.b item = (com.huluxia.data.map.b) getItem(position);
        holder.name.setText(item.name);
        if (holder.aWl != null) {
            if (item.size == 0) {
                holder.aWl.setText("未知大小");
            } else {
                holder.aWl.setText(item.pS);
            }
        }
        holder.bZb.setText(item.date);
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
        holder.name.setTag(item.path);
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

    public void i(com.huluxia.data.map.b item) {
        hlx.ui.localresmgr.cache.b.Us().n(item);
        String usingSkin = ah.KZ().LI();
        if (usingSkin != null && usingSkin.equalsIgnoreCase(item.path)) {
            ah.KZ().gb(null);
        }
        if (this.bZj != null) {
            this.bZj.dK(false);
        }
    }

    public void Uh() {
        e dialog = new e(this.bns, new b());
        dialog.az("重命名", this.bYT.name);
        dialog.aA(this.bns.getString(R.string.local_resmgr_dlg_cancle), this.bns.getString(R.string.local_resmgr_dlg_save));
        dialog.showDialog();
    }

    private void j(final com.huluxia.data.map.b item) {
        int colorMsg = d.getColor(this.bns, R.attr.dialog_msg_label_color);
        int colorRed = this.bns.getResources().getColor(R.color.dialog_ok_btn_color);
        int colorGray = d.getColor(this.bns, 16842808);
        int colorTitle = d.getColor(this.bns, R.attr.dialog_title_label_color);
        View customView = this.mInflater.inflate(R.layout.dialog_delete_resource, null);
        TextView tip = (TextView) customView.findViewById(R.id.tv_tip);
        tip.setTextColor(colorMsg);
        tip.setText("该删除操作不可恢复，是否继续？");
        this.akK.showOkCancelColorDialog("温馨提示", colorTitle, customView, this.bns.getString(R.string.delete), colorRed, this.bns.getString(R.string.btn_cancel), colorGray, true, new OkCancelDialogListener(this) {
            final /* synthetic */ SkinResMgrItemAdapter bZk;

            public void onCancel() {
                this.bZk.akK.dismissDialog();
            }

            public void onOk() {
                Iterator it = this.bZk.mObjects.iterator();
                while (it.hasNext()) {
                    com.huluxia.data.map.b fileItem = (com.huluxia.data.map.b) it.next();
                    if (fileItem.path.equals(item.path)) {
                        this.bZk.i(fileItem);
                        it.remove();
                    }
                }
                this.bZk.notifyDataSetChanged();
            }
        });
    }

    private void Uf() {
        this.akK = new DialogManager(this.bns);
        CommonMenuDialogListener mSkinMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ SkinResMgrItemAdapter bZk;

            {
                this.bZk = this$0;
            }

            public void pressMenuById(int inIndex, Object object) {
                com.huluxia.data.map.b item = (com.huluxia.data.map.b) object;
                switch (inIndex) {
                    case 1:
                        k.d(this.bZk.bns, item.mapId, item.pQ);
                        this.bZk.bZi.dismissDialog();
                        return;
                    case 3:
                        this.bZk.bYT = item;
                        this.bZk.Uh();
                        this.bZk.bZi.dismissDialog();
                        return;
                    case 6:
                        this.bZk.j(item);
                        this.bZk.bZi.dismissDialog();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuDetail), 1, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuRename), 3, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuDel), 6, R.color.locmgr_menu_res_red_color_day));
        this.bZi = new CommonMenuDialog(this.bns, this.mMenuItemArrayList, mSkinMenuListener, d.RB());
    }

    private void k(com.huluxia.data.map.b item) {
        this.bZi.showMenu(item, item.name);
    }
}
