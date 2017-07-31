package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.js.JsItem;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.i;
import com.huluxia.module.n;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.map.ImportMapItemAdapter;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ai;
import com.huluxia.utils.aq;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.FileType;
import hlx.data.localstore.a;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsImportActivity extends HTBaseActivity implements OnClickListener, OnItemClickListener {
    private String SCRIPTS_DIR = j.cT(true);
    private Activity aMn;
    private ListView aZh;
    private ArrayList<Object> arrayList = new ArrayList();
    private TextView baJ;
    private TextView baK;
    private ImportMapItemAdapter bbb = null;
    private String bbe;
    private String bbf;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_select);
        ej("导入ＪＳ");
        this.aMn = this;
        this.baJ = (TextView) findViewById(R.id.tv_tip);
        this.baK = (TextView) findViewById(R.id.tv_path);
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.bbb = new ImportMapItemAdapter(this.aMn, this.arrayList);
        this.aZh.setAdapter(this.bbb);
        this.aZh.setOnItemClickListener(this);
        this.arrayList.addAll(eF(UtilsFile.CU()));
        this.bbb.notifyDataSetChanged();
        this.baJ.setText("请选择要导入的js文件");
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    public void onClick(View v) {
    }

    private List<MapPathItem> eF(String filePath) {
        this.baK.setText(filePath);
        List<MapPathItem> items = new ArrayList();
        List<MapPathItem> tempItems = new ArrayList();
        File f = new File(filePath);
        File[] files = f.listFiles();
        if (!filePath.equals(UtilsFile.CU())) {
            MapPathItem parentItem = new MapPathItem("上一层文件夹", f.getParent());
            parentItem.fileType = FileType.PARENTDIR.Value();
            items.add(parentItem);
        }
        for (File file : files) {
            FileType type = x(file);
            if (type == FileType.SUBDIR || type == FileType.JS) {
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
        if (file.isDirectory() && !fileNameString.startsWith(".")) {
            return FileType.SUBDIR;
        }
        if (!endNameString.equalsIgnoreCase("js") || fileNameString.startsWith(".")) {
            return FileType.UNKNOWN;
        }
        return FileType.JS;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (!this.aMn.isFinishing()) {
            MapPathItem item = (MapPathItem) this.arrayList.get(i);
            List<MapPathItem> items;
            if (item.fileType == FileType.PARENTDIR.Value()) {
                items = eF(item.path);
                this.arrayList.clear();
                this.arrayList.addAll(items);
                this.bbb.notifyDataSetChanged();
            } else if (item.fileType == FileType.SUBDIR.Value()) {
                items = eF(item.path + File.separator + item.name + File.separator);
                this.arrayList.clear();
                this.arrayList.addAll(items);
                this.bbb.notifyDataSetChanged();
            } else if (item.fileType == FileType.JS.Value()) {
                String filePath = item.path + File.separator + item.name;
                this.bbe = filePath;
                this.bbf = item.name;
                X(item.name, filePath);
                EventNotifyCenter.notifyEvent(n.class, n.axb, Boolean.valueOf(false), filePath, null);
            }
        }
    }

    private void X(String name, String filePath) {
        String newPath = this.SCRIPTS_DIR + name;
        if (UtilsFile.copyFile(filePath, newPath)) {
            String temp = name.toLowerCase();
            if (temp.endsWith(a.bJY)) {
                temp = temp.substring(0, temp.indexOf(a.bJY));
            }
            t.l(this.aMn, "成功导入 " + name);
            n(temp, newPath, String.valueOf(System.currentTimeMillis()));
        } else {
            t.l(this.aMn, "导入 " + name + " 失败");
        }
        finish();
    }

    private void n(String name, String path, String date) {
        ai.b(new JsItem(name, path, date, 1, aq.MC()));
        i.DB().L(j.getFileMD5(path), name);
    }
}
