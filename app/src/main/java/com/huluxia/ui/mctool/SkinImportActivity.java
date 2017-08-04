package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.map.MapPathItem;
import com.huluxia.data.skin.SkinItem;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.n;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.map.ImportMapItemAdapter;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ah;
import com.huluxia.utils.ai;
import com.huluxia.utils.aq;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.FileType;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkinImportActivity extends HTBaseActivity implements OnClickListener, OnItemClickListener {
    private static final String TAG = "SkinImportActivity";
    private String SCRIPTS_DIR = j.cU(true);
    private String aKs = "png";
    private Activity aMn;
    private ListView aZh;
    private ArrayList<Object> arrayList = new ArrayList();
    private TextView baJ;
    private TextView baK;
    private ImportMapItemAdapter bbb = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_select);
        ej("导入皮肤");
        this.aMn = this;
        this.baJ = (TextView) findViewById(R.id.tv_tip);
        this.baK = (TextView) findViewById(R.id.tv_path);
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.bbb = new ImportMapItemAdapter(this.aMn, this.arrayList);
        this.aZh.setAdapter(this.bbb);
        this.aZh.setOnItemClickListener(this);
        this.arrayList.addAll(eF(UtilsFile.getSdCardPath()));
        this.bbb.notifyDataSetChanged();
        this.baJ.setText("请选择要导入的皮肤png文件");
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
        if (!filePath.equals(UtilsFile.getSdCardPath())) {
            MapPathItem parentItem = new MapPathItem("上一层文件夹", f.getParent());
            parentItem.fileType = FileType.PARENTDIR.Value();
            items.add(parentItem);
        }
        for (File file : files) {
            FileType type = x(file);
            if (type == FileType.SUBDIR || type == FileType.PNG) {
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
        if (!endNameString.equalsIgnoreCase(this.aKs) || fileNameString.startsWith(".")) {
            return FileType.UNKNOWN;
        }
        return FileType.PNG;
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
            } else if (item.fileType == FileType.PNG.Value()) {
                Y(item.name, item.path + File.separator + item.name);
            }
        }
    }

    private void Y(String name, String filePath) {
        String newPath = this.SCRIPTS_DIR + name;
        UtilsFile.copyFile(filePath, newPath);
        String temp = name.toLowerCase();
        String end = "." + this.aKs;
        if (temp.endsWith(end)) {
            temp = temp.substring(0, temp.indexOf(end));
        }
        t.download_toast(this.aMn, "成功导入 " + name);
        n(temp, newPath, String.valueOf(System.currentTimeMillis()));
        HLog.verbose(TAG, "DTPrint EVENT_IMPORT_LOC_SKIN filePath is " + filePath, new Object[0]);
        EventNotifyCenter.notifyEvent(n.class, n.axd, Boolean.valueOf(false), filePath, null);
        finish();
    }

    private void n(String name, String path, String date) {
        SkinItem item = new SkinItem(name, path, date, 1, aq.MC());
        ai.b(item);
        ah.KZ().gb(item.path);
    }
}
