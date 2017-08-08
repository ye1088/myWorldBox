package com.MCWorld.mconline.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;
import com.MCWorld.data.map.b;
import com.MCWorld.framework.R;
import com.MCWorld.mconline.activity.RoomCreateActivity;
import com.MCWorld.mconline.activity.adapter.MapListItemAdapter;
import com.MCWorld.mconline.activity.adapter.MapListItemAdapter$a;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MapListDialog */
public class a extends Dialog implements MapListItemAdapter$a {
    private static final String TAG = "MapListDialog";
    private String akM;
    private a akU;
    private MapListItemAdapter akV = null;
    private ArrayList<Object> akW = new ArrayList();
    private List<b> ako;
    private Activity mContext = null;
    private ListView mListView;

    public a(Activity context, List<b> inputFileItemList, String inputChooseMapDir) {
        super(context, d.RD());
        this.mContext = context;
        this.akU = this;
        this.akM = inputChooseMapDir;
        this.ako = inputFileItemList;
        setCanceledOnTouchOutside(false);
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    public void dismiss() {
        super.dismiss();
    }

    public void G(List<b> data) {
        if (data == null || data.size() == 0) {
            this.mListView.setVisibility(8);
            return;
        }
        this.mListView.setVisibility(0);
        this.akW.clear();
        this.akW.addAll(data);
        this.akV.notifyDataSetChanged();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mconline_dialog_map_list);
        this.akV = new MapListItemAdapter(getContext(), this.akW, this.akM);
        this.akV.a(this);
        this.mListView = (ListView) findViewById(R.id.lvMCOnlineMapList);
        this.mListView.setAdapter(this.akV);
        G(this.ako);
    }

    public void showDialog() {
    }

    private void AQ() {
        if (this.mContext != null && !this.mContext.isFinishing()) {
            this.akU.dismiss();
        }
    }

    public void g(b item) {
        RoomCreateActivity.f(item);
        AQ();
    }
}
