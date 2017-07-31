package com.huluxia.ui.maptool;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.huluxia.framework.R;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.j;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/* compiled from: MapListDialog */
public class a extends Dialog {
    private OnItemClickListener Pz = new 1(this);
    private c aZH;
    private List<a> aZI;
    private ListView aZJ = null;
    private b aZK;
    List<Map<String, Object>> list;
    private Activity mActivity;

    /* compiled from: MapListDialog */
    public interface c {
        void m(String str, String str2, String str3);
    }

    public a(Activity activity, c cb) {
        super(activity, R.style.theme_dialog_normal);
        this.mActivity = activity;
        this.aZH = cb;
        this.aZI = new ArrayList();
        Ij();
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localmap_dialog_list);
        this.aZJ = (ListView) findViewById(R.id.mapConfigMapListView);
        this.aZK = new b(this, this.mActivity);
        this.aZJ.setAdapter(this.aZK);
        this.aZJ.setOnItemClickListener(this.Pz);
    }

    public void dismiss() {
        super.dismiss();
    }

    private void Ij() {
        SimpleDateFormat sdf = new SimpleDateFormat(hlx.data.localstore.a.bKc);
        File[] files = new File(UtilsFile.Kq()).listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory() && UtilsFile.isExist(file.getPath() + File.separator + "level.dat")) {
                    String date = null;
                    if (null == null) {
                        date = sdf.format(new Date(file.lastModified()));
                    }
                    long size = j.getFileSizes(file);
                    this.aZI.add(new a(this, file.getName(), date, String.valueOf(size % 1048576 == 0 ? size / 1048576 : (size / 1048576) + 1) + "MB"));
                }
            }
        }
    }
}
