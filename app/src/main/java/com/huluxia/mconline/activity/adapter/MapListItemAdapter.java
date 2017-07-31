package com.huluxia.mconline.activity.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import hlx.ui.localresmgr.adapter.MapResMgrItemAdapter.a;
import java.util.ArrayList;
import java.util.List;

public class MapListItemAdapter extends BaseAdapter {
    private static final String TAG = "MapResMgrItemAdapter";
    private static final int akH = 0;
    private a akE = null;
    private a akF;
    private int akG = 0;
    private int akI = 0;
    private List<Object> akJ = null;
    private DialogManager akK;
    private CommonMenuDialog akL;
    private String akM = null;
    private Drawable akN;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ MapListItemAdapter akO;

        {
            this.akO = this$0;
        }

        public void onClick(View v) {
            com.huluxia.data.map.b item = (com.huluxia.data.map.b) ((b) v.getTag()).name.getTag();
            HLog.verbose(MapListItemAdapter.TAG, "DTPrint 点击地图 => " + item.name, new Object[0]);
            if (this.akO.akE != null) {
                this.akO.akE.g(item);
            }
        }
    };
    private Context mCtx;
    private LayoutInflater mInflater = null;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();
    private List<Object> mObjects;

    static class b {
        RelativeLayout akP;
        TextView name;

        b() {
        }
    }

    public MapListItemAdapter(Context context, List<Object> objects, String inputChooseMapDirName) {
        this.mCtx = context;
        this.mObjects = objects;
        this.akM = inputChooseMapDirName;
        this.mInflater = LayoutInflater.from(context.getApplicationContext());
        this.akN = this.mCtx.getResources().getDrawable(R.drawable.ic_common_check);
        this.akN.setBounds(0, 0, this.akN.getMinimumWidth(), this.akN.getMinimumHeight());
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
            convertView = LayoutInflater.from(this.mCtx).inflate(R.layout.mconline_item_map, null);
            holder = new b();
            holder.akP = (RelativeLayout) convertView.findViewById(R.id.linearLayoutMCOnlineMapIem);
            holder.name = (TextView) convertView.findViewById(R.id.tvMCOnlineMapItemName);
            convertView.setTag(holder);
        } else {
            holder = (b) convertView.getTag();
        }
        a(convertView, holder, (com.huluxia.data.map.b) getItem(position));
        return convertView;
    }

    public void a(View view, b holder, com.huluxia.data.map.b item) {
        holder.name.setText(item.pP);
        holder.name.setTag(item);
        if (item.name.equals(this.akM)) {
            holder.name.setCompoundDrawables(null, null, this.akN, null);
        } else {
            holder.name.setCompoundDrawables(null, null, null, null);
        }
        view.setOnClickListener(this.mClickListener);
    }

    public void a(a mUpdateMapListener) {
        this.akE = mUpdateMapListener;
    }
}
