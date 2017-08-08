package com.MCWorld.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AppShareDialog */
public class b extends Dialog {
    private OnItemClickListener Pz = new OnItemClickListener(this) {
        final /* synthetic */ b bwn;

        {
            this.bwn = this$0;
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
            this.bwn.bwm.a((a) this.bwn.bwg.getItem(pos));
            this.bwn.dismiss();
        }
    };
    private Handler Vo = null;
    private List<a> bwe = null;
    OnClickListener bwf = new OnClickListener(this) {
        final /* synthetic */ b bwn;

        {
            this.bwn = this$0;
        }

        public void onClick(View v) {
            this.bwn.dismiss();
            if (this.bwn.Vo != null) {
                this.bwn.Vo.sendMessage(this.bwn.Vo.obtainMessage(1));
            }
        }
    };
    private BaseAdapter bwg = new BaseAdapter(this) {
        final /* synthetic */ b bwn;

        {
            this.bwn = this$0;
        }

        public long getItemId(int pos) {
            return 0;
        }

        public Object getItem(int pos) {
            return this.bwn.bwe.get(pos);
        }

        public int getCount() {
            return this.bwn.bwe.size();
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(this.bwn.mContext).inflate(R.layout.item_gridview_plugin, null);
            }
            a obj = (a) getItem(pos);
            if (obj == null) {
                return null;
            }
            ((ImageView) convertView.findViewById(R.id.ChildPluginItemImage)).setImageDrawable(obj.bwi);
            ((TextView) convertView.findViewById(R.id.ChildPluginItemName)).setText(obj.bwl);
            convertView.findViewById(R.id.ChildPluginItemRunning).setVisibility(8);
            return convertView;
        }
    };
    private b bwm = null;
    private Context mContext = null;

    /* compiled from: AppShareDialog */
    public static class a {
        public Drawable bwi = null;
        public String bwj = null;
        public String bwk = null;
        public String bwl = null;
    }

    /* compiled from: AppShareDialog */
    public interface b {
        void a(a aVar);

        boolean a(PackageInfo packageInfo);

        Intent be(Context context);

        boolean et(String str);

        void rb();
    }

    public b(Context context, b callback) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.bwm = callback;
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

    public b(Context context, b callback, Handler handler) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.bwm = callback;
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
            if (this.bwm.et(info.activityInfo.name)) {
                a appInfo = new a();
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
            if (info.versionName != null && this.bwm.a(info)) {
                a appInfo = new a();
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
        setContentView(R.layout.floor_dialog_share);
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        LayoutParams lp = getWindow().getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (display.getWidth() * 8) / 10;
        lp.height = (display.getHeight() * 6) / 10;
        getWindow().setAttributes(lp);
        findViewById(R.id.DlgAppShareCloseButton).setOnClickListener(this.bwf);
        GridView gridView = (GridView) findViewById(R.id.DlgAppShareGridView);
        gridView.setAdapter(this.bwg);
        gridView.setOnItemClickListener(this.Pz);
    }
}
