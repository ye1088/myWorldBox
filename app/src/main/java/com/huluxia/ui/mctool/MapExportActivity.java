package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.framework.R;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.map.ExportMapItemAdapter;
import com.huluxia.utils.UtilsFile;
import com.huluxia.widget.Constants.FileType;
import com.huluxia.widget.h;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapExportActivity extends HTBaseActivity implements OnClickListener, OnItemClickListener {
    private Activity aMn;
    private ListView aZh;
    private ArrayList<Object> arrayList = new ArrayList();
    private TextView baJ;
    private TextView baK;
    private Button baL;
    private String bat;
    private ExportMapItemAdapter bbo = null;
    private String bbp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_select);
        this.bbp = getIntent().getStringExtra("mappath");
        this.bat = getIntent().getStringExtra("mapname");
        ej("导出地图");
        this.aMn = this;
        this.baJ = (TextView) findViewById(R.id.tv_tip);
        this.baK = (TextView) findViewById(R.id.tv_path);
        this.baL = (Button) findViewById(R.id.btn_export);
        this.baL.setVisibility(0);
        this.baL.setText("保存");
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.bbo = new ExportMapItemAdapter(this.aMn, this.arrayList);
        this.aZh.setAdapter(this.bbo);
        this.aZh.setOnItemClickListener(this);
        this.arrayList.addAll(eF(UtilsFile.getSdCardPath()));
        this.bbo.notifyDataSetChanged();
        this.baL.setOnClickListener(this);
        this.baJ.setText("请选择‘" + this.bat + ".zip’的保存路径");
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    public void onClick(View v) {
        String zipPath = this.baK.getText().toString() + File.separator + this.bat + ".zip";
        h.NV().g(String.valueOf(zipPath.hashCode()), zipPath, this.bbp, this.bat);
        t.o(this, "导出地图成功!");
        r.ck().j("export_map", this.bat);
    }

    private List<MapPathItem> eF(String filePath) {
        this.baK.setText(filePath);
        List<MapPathItem> items = new ArrayList();
        List<MapPathItem> tempItems = new ArrayList();
        File f = new File(filePath);
        File[] files = f.listFiles();
        if (!filePath.equals(UtilsFile.getSdCardPath())) {
            MapPathItem parentItem = new MapPathItem("上一层文件夹", f.getParent());
            parentItem.fileType = FileType.PARENTDIR.Value();
            items.add(parentItem);
        }
        for (File file : files) {
            FileType type = x(file);
            if (type == FileType.SUBDIR) {
                MapPathItem item = new MapPathItem(file.getName(), f.getPath());
                item.fileType = type.Value();
                tempItems.add(item);
            }
        }
        if (tempItems.size() > 1) {
            Collections.sort(tempItems);
            items.addAll(tempItems);
        } else if (tempItems.size() == 1) {
            items.addAll(tempItems);
        }
        return items;
    }

    public FileType x(File file) {
        String fileNameString = file.getName();
        String endNameString = fileNameString.substring(fileNameString.lastIndexOf(".") + 1, fileNameString.length()).toLowerCase();
        if (!file.isDirectory() || fileNameString.startsWith(".")) {
            return FileType.UNKNOWN;
        }
        return FileType.SUBDIR;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MapPathItem item = (MapPathItem) this.arrayList.get(i);
        List<MapPathItem> items;
        if (item.fileType == FileType.PARENTDIR.Value()) {
            items = eF(item.path);
            this.arrayList.clear();
            this.arrayList.addAll(items);
            this.bbo.notifyDataSetChanged();
        } else if (item.fileType == FileType.SUBDIR.Value()) {
            items = eF(item.path + File.separator + item.name + File.separator);
            this.arrayList.clear();
            this.arrayList.addAll(items);
            this.bbo.notifyDataSetChanged();
        }
    }
}
