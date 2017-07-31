package com.huluxia.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.n;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AppInfoDialog */
public class a extends Dialog {
    private OnItemClickListener Pz = new 2(this);
    private Handler Vo = null;
    private a bwd = null;
    private List<b> bwe = null;
    OnClickListener bwf = new 1(this);
    private BaseAdapter bwg = new 3(this);
    private Context mContext = null;

    /* compiled from: AppInfoDialog */
    public interface a {
        void a(b bVar);

        boolean a(PackageInfo packageInfo);

        Intent be(Context context);

        boolean et(String str);

        void rb();
    }

    public a(Context context, a callback) {
        super(context, n.theme_dialog_normal);
        this.mContext = context;
        this.bwd = callback;
        this.bwe = new ArrayList();
        Intent intent = callback.be(context);
        if (intent == null) {
            bE(context);
        }
        if (intent != null) {
            h(context, intent);
        }
        show();
    }

    public a(Context context, a callback, Handler handler) {
        super(context, n.theme_dialog_normal);
        this.mContext = context;
        this.bwd = callback;
        this.Vo = handler;
        this.bwe = new ArrayList();
        Intent intent = callback.be(context);
        if (intent == null) {
            bE(context);
        }
        if (intent != null) {
            h(context, intent);
        }
        show();
    }

    private void h(Context context, Intent intent) {
        PackageManager manager = context.getPackageManager();
        for (ResolveInfo info : manager.queryIntentActivities(intent, 0)) {
            if (this.bwd.et(info.activityInfo.packageName)) {
                b appInfo = new b();
                appInfo.bwk = info.activityInfo.name;
                appInfo.bwj = info.activityInfo.packageName;
                appInfo.bwi = info.loadIcon(manager);
                appInfo.bwl = info.loadLabel(manager).toString();
                this.bwe.add(appInfo);
            }
        }
    }

    private void bE(Context context) {
        PackageManager packMgr = context.getPackageManager();
        for (PackageInfo info : packMgr.getInstalledPackages(0)) {
            if (info.versionName != null && this.bwd.a(info)) {
                b appInfo = new b();
                appInfo.bwk = info.applicationInfo.className;
                appInfo.bwj = info.packageName;
                appInfo.bwi = info.applicationInfo.loadIcon(packMgr);
                appInfo.bwl = info.applicationInfo.loadLabel(packMgr).toString();
                this.bwe.add(appInfo);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.dialog_appinfo);
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        LayoutParams lp = getWindow().getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (display.getWidth() * 8) / 10;
        lp.height = (display.getHeight() * 6) / 10;
        getWindow().setAttributes(lp);
        findViewById(g.DlgAppinfoCloseButton).setOnClickListener(this.bwf);
        GridView gridView = (GridView) findViewById(g.DlgAppinfoGridView);
        gridView.setAdapter(this.bwg);
        gridView.setOnItemClickListener(this.Pz);
    }
}
