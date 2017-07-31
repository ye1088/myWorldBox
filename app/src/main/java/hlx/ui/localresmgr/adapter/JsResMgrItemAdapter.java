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
import com.huluxia.framework.R;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.k;
import com.huluxia.t;
import com.huluxia.utils.ai;
import com.huluxia.widget.dialog.e;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsResMgrItemAdapter extends BaseAdapter {
    private List<Object> akJ = null;
    private DialogManager akK;
    private boolean bYS = false;
    private com.huluxia.data.map.b bYT;
    private CommonMenuDialog bYU;
    private a bYV;
    private OnCheckedChangeListener bYW = new OnCheckedChangeListener(this) {
        final /* synthetic */ JsResMgrItemAdapter bYX;

        {
            this.bYX = this$0;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) compoundButton.getTag();
            item.state = b ? 1 : 0;
            ai.f("js", item.name, ai.bmi, String.valueOf(item.state));
            if (this.bYX.bYV != null) {
                this.bYX.bYV.dJ(b);
            }
        }
    };
    private Activity bns;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ JsResMgrItemAdapter bYX;

        {
            this.bYX = this$0;
        }

        public void onClick(View v) {
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) ((c) v.getTag()).bZc.getTag();
            if (this.bYX.bYS) {
                if (this.bYX.akJ.contains(item)) {
                    this.bYX.akJ.remove(item);
                } else {
                    this.bYX.akJ.add(item);
                }
                this.bYX.notifyDataSetChanged();
                return;
            }
            this.bYX.k(item);
        }
    };
    private LayoutInflater mInflater = null;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();
    private List<Object> mObjects;

    public interface a {
        void dJ(boolean z);
    }

    private class b implements com.huluxia.widget.dialog.e.a {
        final /* synthetic */ JsResMgrItemAdapter bYX;

        private b(JsResMgrItemAdapter jsResMgrItemAdapter) {
            this.bYX = jsResMgrItemAdapter;
        }

        public void rb() {
        }

        public void cf(String msg) {
            if (msg.trim().length() >= 1) {
                hlx.ui.localresmgr.cache.b.Us().c(this.bYX.bYT, this.bYX.bYT.path, this.bYX.bYT.name, msg);
                this.bYX.notifyDataSetChanged();
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

    public JsResMgrItemAdapter(Activity context, List<Object> objects) {
        this.bns = context;
        this.mObjects = objects;
        this.mInflater = LayoutInflater.from(context.getApplicationContext());
        Uf();
    }

    public void a(a mLoadJSListener) {
        this.bYV = mLoadJSListener;
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
        com.huluxia.data.map.b item = (com.huluxia.data.map.b) getItem(position);
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

    public void i(com.huluxia.data.map.b item) {
        hlx.ui.localresmgr.cache.b.Us().p(item);
        if (this.bYV != null) {
            this.bYV.dJ(false);
        }
    }

    public void Ue() {
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
            final /* synthetic */ JsResMgrItemAdapter bYX;

            public void onCancel() {
                this.bYX.akK.dismissDialog();
            }

            public void onOk() {
                Iterator it = this.bYX.mObjects.iterator();
                while (it.hasNext()) {
                    com.huluxia.data.map.b fileItem = (com.huluxia.data.map.b) it.next();
                    if (fileItem.path.equals(item.path)) {
                        this.bYX.i(fileItem);
                        it.remove();
                    }
                }
                this.bYX.notifyDataSetChanged();
            }
        });
    }

    private void Uf() {
        this.akK = new DialogManager(this.bns);
        CommonMenuDialogListener mJSMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ JsResMgrItemAdapter bYX;

            {
                this.bYX = this$0;
            }

            public void pressMenuById(int inIndex, Object object) {
                com.huluxia.data.map.b item = (com.huluxia.data.map.b) object;
                switch (inIndex) {
                    case 1:
                        k.c(this.bYX.bns, item.mapId, item.pQ);
                        this.bYX.bYU.dismissDialog();
                        return;
                    case 3:
                        this.bYX.bYT = item;
                        this.bYX.Ue();
                        this.bYX.bYU.dismissDialog();
                        return;
                    case 6:
                        this.bYX.j(item);
                        this.bYX.bYU.dismissDialog();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuDetail), 1, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuRename), 3, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.bns.getString(R.string.MenuDel), 6, R.color.locmgr_menu_res_red_color_day));
        this.bYU = new CommonMenuDialog(this.bns, this.mMenuItemArrayList, mJSMenuListener, d.RB());
    }

    private void k(com.huluxia.data.map.b item) {
        this.bYU.showMenu(item, item.name);
    }
}
