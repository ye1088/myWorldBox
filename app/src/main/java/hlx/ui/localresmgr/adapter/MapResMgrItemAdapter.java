package hlx.ui.localresmgr.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.huluxia.r;
import com.huluxia.t;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapResMgrItemAdapter extends BaseAdapter {
    private static final String TAG = "MapResMgrItemAdapter";
    private static final int akH = 0;
    private a akF;
    private int akG = 0;
    private int akI = 0;
    private List<Object> akJ = null;
    private DialogManager akK;
    private CommonMenuDialog akL;
    private OnClickListener bZf = new OnClickListener(this) {
        final /* synthetic */ MapResMgrItemAdapter bZg;

        {
            this.bZg = this$0;
        }

        public void onClick(View v) {
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) ((b) v.getTag()).name.getTag();
            this.bZg.akF.aI(item.pP, item.path);
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ MapResMgrItemAdapter bZg;

        {
            this.bZg = this$0;
        }

        public void onClick(View v) {
            b holder = (b) v.getTag();
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) holder.name.getTag();
            if (this.bZg.akI == 0) {
                this.bZg.k(item);
                return;
            }
            if (this.bZg.akJ.contains(item)) {
                holder.bZe.setVisibility(8);
                this.bZg.akJ.remove(item);
            } else {
                holder.bZe.setVisibility(0);
                this.bZg.akJ.add(item);
            }
            this.bZg.notifyDataSetChanged();
        }
    };
    private Context mCtx;
    private LayoutInflater mInflater = null;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();
    private List<Object> mObjects;

    public interface a {
        void Ug();

        void aH(String str, String str2);

        void aI(String str, String str2);

        void ht(String str);

        int hu(String str);

        void l(long j, long j2);

        void l(com.huluxia.data.map.b bVar);

        void m(com.huluxia.data.map.b bVar);
    }

    static class b {
        PaintView aUN;
        TextView aWl;
        RelativeLayout bYZ;
        TextView bZb;
        RelativeLayout bZd;
        ImageView bZe;
        RelativeLayout bZh;
        TextView name;

        b() {
        }
    }

    public MapResMgrItemAdapter(Context context, List<Object> objects) {
        this.mCtx = context;
        this.mObjects = objects;
        this.mInflater = LayoutInflater.from(context.getApplicationContext());
        Uf();
    }

    public void hG(int ltype) {
        this.akG = ltype;
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
        b holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mCtx).inflate(R.layout.itm_local_resmgr_map, null);
            holder = new b();
            holder.aUN = (PaintView) convertView.findViewById(R.id.rlyLocalResMgrMapItemImg);
            holder.bYZ = (RelativeLayout) convertView.findViewById(R.id.rlyLocalResMgrMapItemImgContainer);
            holder.name = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemTitle);
            holder.bZb = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemDate);
            holder.aWl = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemSize);
            holder.bZh = (RelativeLayout) convertView.findViewById(R.id.rlyChkSwitch);
            holder.bZd = (RelativeLayout) convertView.findViewById(R.id.rlyResMgrItemDel);
            holder.bZe = (ImageView) convertView.findViewById(R.id.ivResMgrMapItemCheckDel);
            convertView.findViewById(R.id.rlyChkSwitch).setVisibility(8);
            convertView.findViewById(R.id.rlyResMgrItemDel).setVisibility(8);
            holder.bZh.setVisibility(8);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        a(convertView, holder, (com.huluxia.data.map.b) getItem(position));
        return convertView;
    }

    public void a(View view, b holder, com.huluxia.data.map.b item) {
        if (this.akG == 0) {
            holder.name.setText(item.pP == null ? item.name : item.pP);
            view.setOnClickListener(this.mClickListener);
        } else {
            holder.name.setText(item.name);
            view.setOnClickListener(this.bZf);
        }
        holder.name.setTag(item);
        holder.bZb.setText(item.pT);
        holder.aWl.setText(item.size == 0 ? "未知大小" : item.pS);
        holder.aUN.setVisibility(0);
        holder.bYZ.setVisibility(0);
        if (item.pR.startsWith("http")) {
            t.a(holder.aUN, item.pR, 0.0f);
        } else {
            holder.aUN.setImageDrawable(d.o(this.mCtx, R.attr.ic_default_loc_res));
        }
        if (this.akI == 0) {
            holder.bZd.setVisibility(8);
            return;
        }
        holder.bZd.setVisibility(0);
        holder.bZe.setVisibility(this.akJ.contains(item) ? 0 : 8);
    }

    public void nB(int lvShowMode) {
        this.akI = lvShowMode;
        this.akJ.clear();
        notifyDataSetChanged();
    }

    public void S(List<Object> checkList) {
        this.akJ = checkList;
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

    public void a(a mLoadMapListener) {
        this.akF = mLoadMapListener;
    }

    private void i(com.huluxia.data.map.b fileItem) {
        hlx.ui.localresmgr.cache.b.Us().q(fileItem);
        Iterator it = this.mObjects.iterator();
        while (it.hasNext()) {
            if (fileItem.name.equals(((com.huluxia.data.map.b) it.next()).name)) {
                it.remove();
            }
        }
        if (this.akF != null) {
            this.akF.Ug();
        }
        notifyDataSetChanged();
    }

    private void j(final com.huluxia.data.map.b item) {
        int colorMsg = d.getColor(this.mCtx, R.attr.dialog_msg_label_color);
        int colorRed = this.mCtx.getResources().getColor(R.color.dialog_ok_btn_color);
        int colorGray = d.getColor(this.mCtx, 16842808);
        int colorTitle = d.getColor(this.mCtx, R.attr.dialog_title_label_color);
        View customView = this.mInflater.inflate(R.layout.dialog_delete_resource, null);
        TextView tip = (TextView) customView.findViewById(R.id.tv_tip);
        tip.setTextColor(colorMsg);
        tip.setText("该删除操作不可恢复，是否继续？");
        this.akK.showOkCancelColorDialog("温馨提示", colorTitle, customView, this.mCtx.getString(R.string.delete), colorRed, this.mCtx.getString(R.string.btn_cancel), colorGray, true, new OkCancelDialogListener(this) {
            final /* synthetic */ MapResMgrItemAdapter bZg;

            public void onCancel() {
                this.bZg.akK.dismissDialog();
            }

            public void onOk() {
                this.bZg.i(item);
            }
        });
    }

    private void Uf() {
        this.akK = new DialogManager(this.mCtx);
        CommonMenuDialogListener mMapMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ MapResMgrItemAdapter bZg;

            {
                this.bZg = this$0;
            }

            public void pressMenuById(int inIndex, Object object) {
                com.huluxia.data.map.b item = (com.huluxia.data.map.b) object;
                switch (inIndex) {
                    case 1:
                        if (this.bZg.akF != null) {
                            this.bZg.akF.l(item.pQ, item.mapId);
                        }
                        this.bZg.akL.dismissDialog();
                        return;
                    case 2:
                        r.ck().K_umengEvent(hlx.data.tongji.a.bNa);
                        if (this.bZg.akF != null) {
                            this.bZg.akF.aH(item.name, item.path);
                            this.bZg.akL.dismissDialog();
                            return;
                        }
                        return;
                    case 3:
                        r.ck().K_umengEvent(hlx.data.tongji.a.bNb);
                        if (this.bZg.akF != null) {
                            this.bZg.akF.l(item);
                        }
                        this.bZg.akL.dismissDialog();
                        return;
                    case 4:
                        r.ck().K_umengEvent(hlx.data.tongji.a.bNc);
                        if (this.bZg.akF != null) {
                            this.bZg.akF.m(item);
                        }
                        this.bZg.akL.dismissDialog();
                        return;
                    case 5:
                        r.ck().K_umengEvent(hlx.data.tongji.a.bNd);
                        if (this.bZg.akF != null) {
                            this.bZg.akF.ht(item.name);
                        }
                        this.bZg.akL.dismissDialog();
                        return;
                    case 6:
                        this.bZg.j(item);
                        this.bZg.akL.dismissDialog();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mMenuItemArrayList.add(new ResMenuItem(this.mCtx.getString(R.string.MenuDetail), 1, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.mCtx.getString(R.string.MenuRecover), 2, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.mCtx.getString(R.string.MenuRename), 3, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.mCtx.getString(R.string.MenuBackupMap), 4, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.mCtx.getString(R.string.MenuExportMapToZip), 5, 0));
        this.mMenuItemArrayList.add(new ResMenuItem(this.mCtx.getString(R.string.MenuDel), 6, R.color.locmgr_menu_res_red_color_day));
        this.akL = new CommonMenuDialog(this.mCtx, this.mMenuItemArrayList, mMapMenuListener, d.RB());
    }

    private void k(com.huluxia.data.map.b item) {
        this.akL.showMenu(item, item.name);
    }
}
