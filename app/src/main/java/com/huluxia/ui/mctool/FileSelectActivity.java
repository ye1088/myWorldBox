package com.huluxia.ui.mctool;

import android.app.Activity;
import android.content.Intent;
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
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.map.FileSelectItemAdapter;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.FileType;
import hlx.data.localstore.a;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSelectActivity extends HTBaseActivity implements OnClickListener, OnItemClickListener {
    private static final String baH = ".js.zip";
    private Activity aMn;
    private ListView aZh;
    private ArrayList<Object> arrayList = new ArrayList();
    private int axr = 1;
    private FileSelectItemAdapter baI = null;
    private TextView baJ;
    private TextView baK;
    private Button baL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_map_select);
        Im();
        Ip();
        Iq();
        It();
        String path = UtilsFile.getSdCardPath();
        if (this.axr == 1) {
            String mapPath = j.Kq();
            if (mapPath != null) {
                File file = new File(mapPath);
                if (file != null && file.exists()) {
                    path = mapPath;
                }
            }
        } else if (!(this.axr == 2 || this.axr == 4 || this.axr != 3)) {
        }
        this.arrayList.addAll(eF(path));
        this.baI.notifyDataSetChanged();
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void Im() {
        this.aMn = this;
        this.axr = getIntent().getIntExtra("ResType", 1);
        this.baI = new FileSelectItemAdapter(this.aMn, this.arrayList, this.axr);
    }

    private void Ip() {
        this.baJ = (TextView) findViewById(R.id.tv_tip);
        this.baK = (TextView) findViewById(R.id.tv_path);
        this.baL = (Button) findViewById(R.id.btn_export);
        this.aZh = (ListView) findViewById(R.id.listViewData);
    }

    private void Iq() {
        switch (this.axr) {
            case 1:
                ej(this.aMn.getString(R.string.choose_map));
                this.baJ.setText(this.aMn.getString(R.string.choose_map_folder_tips));
                break;
            case 2:
                ej(this.aMn.getString(R.string.choose_js));
                this.baJ.setText(this.aMn.getString(R.string.choose_js_file_tips));
                break;
            case 3:
                ej(this.aMn.getString(R.string.choose_skin));
                this.baJ.setText(this.aMn.getString(R.string.choose_skin_file_tips));
                break;
            case 4:
                ej(this.aMn.getString(R.string.choose_wood));
                this.baJ.setText(this.aMn.getString(R.string.choose_wood_file_tips));
                break;
        }
        this.baL.setText(this.aMn.getString(R.string.comfirm));
        this.baL.setVisibility(0);
        this.aZh.setAdapter(this.baI);
    }

    private void It() {
        this.baL.setOnClickListener(this);
        this.aZh.setOnItemClickListener(this);
    }

    public void onClick(View v) {
        String _path = this.baI.getPath();
        if (_path == null) {
            switch (this.axr) {
                case 1:
                    t.show_toast(this.aMn, this.aMn.getString(R.string.please_choose_map_folder));
                    return;
                case 2:
                    t.show_toast(this.aMn, this.aMn.getString(R.string.please_choose_js_file));
                    return;
                case 3:
                    t.show_toast(this.aMn, this.aMn.getString(R.string.please_choose_skin_file));
                    return;
                case 4:
                    t.show_toast(this.aMn, this.aMn.getString(R.string.please_choose_wood_file));
                    return;
                default:
                    return;
            }
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("filepath", _path);
        intent.putExtras(bundle);
        setResult(-1, intent);
        finish();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MapPathItem item = (MapPathItem) this.arrayList.get(i);
        List<MapPathItem> items;
        if (item.fileType == FileType.PARENTDIR.Value()) {
            items = eF(item.path);
            this.arrayList.clear();
            this.arrayList.addAll(items);
            this.baI.notifyDataSetChanged();
        } else if (item.fileType == FileType.SUBDIR.Value()) {
            items = eF(item.path + File.separator + item.name + File.separator);
            this.arrayList.clear();
            this.arrayList.addAll(items);
            this.baI.notifyDataSetChanged();
        }
    }

    private List<MapPathItem> eF(String filePath) {
        this.baK.setText(filePath);
        List<MapPathItem> items = new ArrayList();
        List<MapPathItem> tempFolderItems = new ArrayList();
        List<MapPathItem> tempItems = new ArrayList();
        File f = new File(filePath);
        File[] files = f.listFiles();
        a(filePath, items, f);
        for (File file : files) {
            FileType type = x(file);
            MapPathItem item;
            if (type == FileType.SUBDIR) {
                item = new MapPathItem(file.getName(), f.getPath());
                item.fileType = type.Value();
                if (this.axr != 1) {
                    tempFolderItems.add(item);
                } else if (j.eS(item.name)) {
                    tempFolderItems.add(item);
                }
            } else if (this.axr == 2 && type == FileType.JS) {
                item = new MapPathItem(file.getName(), f.getPath());
                item.fileType = type.Value();
                tempItems.add(item);
            } else if (this.axr == 4 && type == FileType.ZIP) {
                item = new MapPathItem(file.getName(), f.getPath());
                item.fileType = type.Value();
                tempItems.add(item);
            } else if (this.axr == 3 && type == FileType.PNG) {
                item = new MapPathItem(file.getName(), f.getPath());
                item.fileType = type.Value();
                tempItems.add(item);
            }
        }
        if (tempItems.size() > 1) {
            Collections.sort(tempItems);
        }
        items.addAll(tempItems);
        if (tempFolderItems.size() > 1) {
            Collections.sort(tempFolderItems);
            items.addAll(tempFolderItems);
        } else if (tempFolderItems.size() == 1) {
            items.addAll(tempFolderItems);
        }
        return items;
    }

    private void a(String filePath, List<MapPathItem> items, File currentItem) {
        if (!filePath.equals(UtilsFile.getSdCardPath())) {
            String filePath1 = filePath + File.separator;
            MapPathItem parentItem;
            switch (this.axr) {
                case 1:
                    if (!filePath.equals(j.Kq()) && !filePath1.equals(j.Kq())) {
                        parentItem = new MapPathItem(this.aMn.getString(R.string.return_parent_folder), currentItem.getParent());
                        parentItem.fileType = FileType.PARENTDIR.Value();
                        items.add(parentItem);
                        return;
                    }
                    return;
                case 2:
                case 3:
                case 4:
                    parentItem = new MapPathItem(this.aMn.getString(R.string.return_parent_folder), currentItem.getParent());
                    parentItem.fileType = FileType.PARENTDIR.Value();
                    items.add(parentItem);
                    return;
                default:
                    return;
            }
        }
    }

    public FileType x(File file) {
        String fileNameString = file.getName();
        FileType _fileType = FileType.UNKNOWN;
        if (file.isDirectory()) {
            if (fileNameString.startsWith(".")) {
                return _fileType;
            }
            return FileType.SUBDIR;
        } else if (fileNameString.toLowerCase().endsWith(a.bJY)) {
            return FileType.JS;
        } else {
            if (fileNameString.toLowerCase().endsWith(".zip")) {
                return FileType.ZIP;
            }
            if (fileNameString.toLowerCase().endsWith(a.bKa)) {
                return FileType.PNG;
            }
            return _fileType;
        }
    }
}
