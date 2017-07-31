package com.huluxia.ui.itemadapter.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.FileType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class FileSelectItemAdapter extends BaseAdapter {
    HashMap<String, Boolean> aTZ = new HashMap();
    private int aUc;
    private Context mCtx;
    private List<Object> mObjects;

    public class a {
        TextView aRh;
        RelativeLayout aUa;
        ImageView aUb;
        final /* synthetic */ FileSelectItemAdapter aUe;
        RadioButton aUf;

        public a(FileSelectItemAdapter this$0) {
            this.aUe = this$0;
        }
    }

    public FileSelectItemAdapter(Context context, List<Object> objects, int fileType) {
        this.mCtx = context;
        this.mObjects = objects;
        this.aUc = fileType;
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
        a holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mCtx).inflate(R.layout.item_map_import, null);
            holder = new a(this);
            holder.aUa = (RelativeLayout) convertView.findViewById(R.id.rl_cate);
            holder.aUb = (ImageView) convertView.findViewById(R.id.logo);
            holder.aRh = (TextView) convertView.findViewById(R.id.title);
            holder.aUf = (RadioButton) convertView.findViewById(R.id.rbtn);
            convertView.setTag(holder);
        } else {
            holder = (a) convertView.getTag();
        }
        a(holder, position);
        return convertView;
    }

    public void a(final a viewHolder, int position) {
        MapPathItem item = (MapPathItem) getItem(position);
        if (item.fileType == FileType.PARENTDIR.Value()) {
            viewHolder.aUb.setImageResource(R.drawable.ic_back);
        } else if (item.fileType == FileType.SUBDIR.Value()) {
            viewHolder.aUb.setImageResource(R.drawable.ic_folder);
        } else if (item.fileType == FileType.ZIP.Value() || item.fileType == FileType.JS.Value() || item.fileType == FileType.PNG.Value()) {
            viewHolder.aUb.setImageResource(R.drawable.ic_file);
        }
        viewHolder.aUf.setVisibility(8);
        final String path = item.path + File.separator + item.name;
        File dat = new File(path + File.separator + "level.dat");
        String _tmpLevelName = ey(path + File.separator + hlx.data.localstore.a.bKd);
        if (this.aUc != 1 || _tmpLevelName == null) {
            viewHolder.aRh.setText(item.name);
        } else {
            viewHolder.aRh.setText(_tmpLevelName);
        }
        if ((j.eS(item.name) && this.aUc == 1) || ((this.aUc == 2 && item.fileType == FileType.JS.Value()) || ((this.aUc == 4 && item.fileType == FileType.ZIP.Value()) || (this.aUc == 3 && item.fileType == FileType.PNG.Value())))) {
            viewHolder.aUf.setVisibility(0);
            viewHolder.aUf.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ FileSelectItemAdapter aUe;

                public void onClick(View v) {
                    for (String key : this.aUe.aTZ.keySet()) {
                        this.aUe.aTZ.put(key, Boolean.valueOf(false));
                    }
                    this.aUe.aTZ.put(path, Boolean.valueOf(viewHolder.aUf.isChecked()));
                    this.aUe.notifyDataSetChanged();
                }
            });
            boolean res = false;
            if (this.aTZ.get(path) == null || !((Boolean) this.aTZ.get(path)).booleanValue()) {
                this.aTZ.put(String.valueOf(position), Boolean.valueOf(false));
            } else {
                res = true;
            }
            viewHolder.aUf.setChecked(res);
        }
    }

    public String ey(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.isFile() || !file.exists()) {
                return null;
            }
            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            String _tmpString = new BufferedReader(read).readLine();
            read.close();
            return _tmpString;
        } catch (Exception e) {
            HLog.verbose("TAG", "读取文件内容出错", new Object[0]);
            return null;
        }
    }

    public String getPath() {
        for (String key : this.aTZ.keySet()) {
            if (((Boolean) this.aTZ.get(key)).booleanValue()) {
                return key;
            }
        }
        return null;
    }
}
