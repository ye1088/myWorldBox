package com.MCWorld.ui.mctool;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.MCWorld.data.map.MapPathItem;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.itemadapter.map.ImportMapItemAdapter;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.FileType;
import com.MCWorld.widget.dialog.g;
import com.MCWorld.widget.dialog.q;
import com.MCWorld.widget.h;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapImportActivity extends HTBaseActivity implements OnClickListener, OnItemClickListener {
    private static final String TAG = "MapImportActivity";
    private BroadcastReceiver aDe = new b();
    private Activity aMn;
    private ListView aZh;
    private ArrayList<Object> arrayList = new ArrayList();
    private TextView baJ;
    private TextView baK;
    private ImportMapItemAdapter bbb = null;
    private String bbq;
    private q bbr = null;
    private String mFileName;

    private class a implements com.MCWorld.widget.dialog.g.a {
        final /* synthetic */ MapImportActivity bbs;

        private a(MapImportActivity mapImportActivity) {
            this.bbs = mapImportActivity;
        }

        public void ra() {
        }

        public void rb() {
            if (!this.bbs.aMn.isFinishing()) {
                this.bbs.bbq = null;
                this.bbs.mFileName = null;
            }
        }

        public void rc() {
        }

        public void rd() {
            if (this.bbs.bbq != null && this.bbs.mFileName != null && !this.bbs.aMn.isFinishing()) {
                this.bbs.bbr = new q(this.bbs.aMn);
                this.bbs.bbr.setTitle("请稍候…");
                this.bbs.bbr.setCancelable(false);
                i.i(this.bbs.aDe);
                h.NV().t(String.valueOf(this.bbs.bbq.hashCode()), this.bbs.bbq, j.Kq());
            }
        }
    }

    private class b extends BroadcastReceiver {
        final /* synthetic */ MapImportActivity bbs;

        private b(MapImportActivity mapImportActivity) {
            this.bbs = mapImportActivity;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (this.bbs.bbr != null) {
                this.bbs.bbr.dismiss();
            }
            if ((taskId != null || status == 1) && taskId.equals(String.valueOf(this.bbs.bbq.hashCode()))) {
                String filePath = j.Kq() + this.bbs.mFileName.substring(0, this.bbs.mFileName.lastIndexOf("."));
                EventNotifyCenter.notifyEvent(n.class, n.axa, Boolean.valueOf(false), filePath, null, this.bbs.bbq);
                this.bbs.bbq = null;
                this.bbs.mFileName = null;
                i.unregisterReceiver(this.bbs.aDe);
                this.bbs.finish();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_select);
        ej("导入地图");
        this.aMn = this;
        this.baJ = (TextView) findViewById(R.id.tv_tip);
        this.baK = (TextView) findViewById(R.id.tv_path);
        this.aZh = (ListView) findViewById(R.id.listViewData);
        this.bbb = new ImportMapItemAdapter(this.aMn, this.arrayList);
        this.aZh.setAdapter(this.bbb);
        this.aZh.setOnItemClickListener(this);
        this.arrayList.addAll(eF(UtilsFile.getSdCardPath()));
        this.bbb.notifyDataSetChanged();
        this.baJ.setText("请选择要导入的地图zip文件");
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
        if (files != null) {
            for (File file : files) {
                FileType type = x(file);
                if (type == FileType.SUBDIR || type == FileType.ZIP) {
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
        }
        return items;
    }

    public FileType x(File file) {
        String fileNameString = file.getName();
        String endNameString = fileNameString.substring(fileNameString.lastIndexOf(".") + 1, fileNameString.length()).toLowerCase();
        if (file.isDirectory() && !fileNameString.startsWith(".")) {
            return FileType.SUBDIR;
        }
        if (!endNameString.equals("zip") || fileNameString.startsWith(".")) {
            return FileType.UNKNOWN;
        }
        return FileType.ZIP;
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
                String subPath = item.path + File.separator + item.name + File.separator;
                if (eG(subPath)) {
                    items = eF(subPath);
                    this.arrayList.clear();
                    this.arrayList.addAll(items);
                    this.bbb.notifyDataSetChanged();
                }
            } else if (item.fileType == FileType.ZIP.Value()) {
                this.bbq = item.path + File.separator + item.name;
                this.mFileName = item.name;
                g dia = new g(this.aMn, new a());
                dia.az(null, "确定解压 " + item.name + "吗");
                dia.u(hlx.data.localstore.a.bKB_bt_cancel, null, hlx.data.localstore.a.bKC_bt_ok);
            }
        }
    }

    private boolean eG(String path) {
        return new File(path).isDirectory();
    }
}
