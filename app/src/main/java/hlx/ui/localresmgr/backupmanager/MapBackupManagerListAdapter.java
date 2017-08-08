package hlx.ui.localresmgr.backupmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.utils.UtilsFile;
import hlx.ui.localresmgr.module.a;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MapBackupManagerListAdapter extends BaseAdapter {
    private List<a> aab;
    private int bXv = 0;
    private Context mContext;

    public MapBackupManagerListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<a> data) {
        this.aab = data;
        notifyDataSetChanged();
    }

    public void nB(int showMode) {
        this.bXv = showMode;
        notifyDataSetChanged();
    }

    public int Um() {
        return this.bXv;
    }

    public int getCount() {
        return this.aab == null ? 0 : this.aab.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.itm_local_resmgr_backup, parent, false);
            holder = new a();
            holder.bZu = (PaintView) convertView.findViewById(R.id.rlyLocalResMgrMapItemImg);
            holder.aOj = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemTitle);
            holder.bZv = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemDate);
            holder.bZw = (TextView) convertView.findViewById(R.id.tvLocalResMgrMapItemSize);
            holder.bZx = (RelativeLayout) convertView.findViewById(R.id.rlyResMgrItemDel);
            holder.bZy = (ImageView) convertView.findViewById(R.id.ivResMgrMapItemCheckDel);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, convertView, (a) this.aab.get(position));
        return convertView;
    }

    private void a(a viewHolder, View convertView, a item) {
        viewHolder.aOj.setText(hv(item.UX()));
        viewHolder.bZv.setText(item.UV());
        viewHolder.bZw.setText(String.format("%d个备份", new Object[]{Integer.valueOf(item.UW())}));
        if (item.UU().startsWith("http")) {
            t.a(viewHolder.bZu, item.UU(), 0.0f);
        } else if (item.UU() != null) {
            viewHolder.bZu.setImageBitmap(null);
        } else {
            viewHolder.bZu.setImageBitmap(null);
        }
        if (this.bXv == 1) {
            viewHolder.bZx.setVisibility(0);
            if (item.isDel()) {
                viewHolder.bZy.setVisibility(0);
                return;
            } else {
                viewHolder.bZy.setVisibility(8);
                return;
            }
        }
        viewHolder.bZx.setVisibility(8);
    }

    private String hv(String mapFolderName) {
        String mMCMapPath = UtilsFile.Kq() + mapFolderName;
        if (UtilsFile.isExist(mMCMapPath + File.separator + "level.dat")) {
            String realleveldat = mMCMapPath + File.separator + hlx.data.localstore.a.bKd;
            if (UtilsFile.isExist(realleveldat)) {
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(realleveldat));
                    String fname = bf.readLine();
                    bf.close();
                    if (fname != null) {
                        mapFolderName = fname.trim();
                    }
                } catch (IOException e) {
                }
            }
        }
        return mapFolderName;
    }

    public static Bitmap hw(String url) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
