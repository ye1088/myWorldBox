package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.framework.R;
import com.huluxia.k;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.map.PreExportMapItemAdapter;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.FileType;
import hlx.ui.online.CreateRoomAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapPreExportActivity extends HTBaseActivity implements OnItemClickListener {
    private Activity aMn;
    private ListView aZh;
    private ArrayList<Object> arrayList = new ArrayList();
    private TextView baJ;
    private TextView baK;
    private PreExportMapItemAdapter bbt = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_select);
        ej(CreateRoomAdapter.ccj);
        this.aMn = this;
        this.baJ = (TextView) findViewById(R.id.tv_tip);
        this.baK = (TextView) findViewById(R.id.tv_path);
        this.baK.setVisibility(8);
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.bbt = new PreExportMapItemAdapter(this.aMn, this.arrayList);
        this.aZh.setAdapter(this.bbt);
        this.aZh.setOnItemClickListener(this);
        this.arrayList.addAll(eF(j.Kq()));
        this.bbt.notifyDataSetChanged();
        this.baJ.setText("请选择要导出的地图");
    }

    private List<MapPathItem> eF(String filePath) {
        List<MapPathItem> items = new ArrayList();
        File f = new File(filePath);
        File[] files = f.listFiles();
        for (File file : files) {
            FileType type = x(file);
            if (type == FileType.SUBDIR) {
                MapPathItem item = new MapPathItem(file.getName(), f.getPath());
                item.fileType = type.Value();
                items.add(item);
            }
        }
        return items;
    }

    public FileType x(File file) {
        String fileNameString = file.getName();
        if (!file.isDirectory() || fileNameString.startsWith(".")) {
            return FileType.UNKNOWN;
        }
        return FileType.SUBDIR;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MapPathItem item = (MapPathItem) this.arrayList.get(i);
        if (item.fileType == FileType.SUBDIR.Value()) {
            k.d(this.aMn, item.path + File.separator + item.name + File.separator, item.name);
        }
    }
}
