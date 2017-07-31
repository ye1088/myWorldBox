package com.huluxia.widget.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.huluxia.framework.R;
import com.huluxia.service.i;
import com.huluxia.ui.itemadapter.map.MapSelectItemAdapter;
import com.huluxia.utils.at;
import java.util.ArrayList;

/* compiled from: ListDialog */
public class m {
    private Builder bxt = null;
    private Context mContext = null;
    private LayoutInflater mInflater = null;

    public m(Context context) {
        this.bxt = new Builder(context);
        this.bxt.setInverseBackgroundForced(true);
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    private static int u(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredHeight();
    }

    public void q(ArrayList<Object> arrayList) {
        int dialogheight;
        View layout = this.mInflater.inflate(R.layout.dialog_list, null);
        int height = u(layout.findViewById(R.id.tv_confirm));
        int dp200 = at.dipToPx(this.mContext, 400);
        int scr_h = (int) (((double) at.getScreenPixHeight(this.mContext)) * 0.8d);
        final AlertDialog dialog = this.bxt.create();
        MapSelectItemAdapter creditItemAdapter = new MapSelectItemAdapter(this.mContext, arrayList);
        ViewGroup listView = (ListView) layout.findViewById(R.id.listViewCredit);
        listView.setAdapter(creditItemAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ m bxu;

            public void onItemClick(AdapterView<?> adapterView, View adpterView, int arg2, long arg3) {
                i.e((String) adpterView.getTag(), true);
                dialog.dismiss();
            }
        });
        creditItemAdapter.notifyDataSetChanged();
        int totalHeight = 0;
        ListAdapter adapter = listView.getAdapter();
        int len = adapter.getCount();
        for (int i = 0; i < len; i++) {
            int itemH = u(adapter.getView(i, listView.getChildAt(i), listView));
            totalHeight += itemH;
            Log.i("item height", Integer.toString(itemH));
        }
        totalHeight += listView.getDividerHeight() * (listView.getCount() - 1);
        LayoutParams params = listView.getLayoutParams();
        if (totalHeight + height > (scr_h / 3) * 2) {
            params.height = dp200;
            dialogheight = dp200 + height;
        } else {
            params.height = totalHeight;
            dialogheight = totalHeight + height;
        }
        dialog.setView(layout, 0, 0, 0, 0);
        dialog.show();
        listView.setLayoutParams(params);
        listView.requestLayout();
        WindowManager.LayoutParams dialogparams = dialog.getWindow().getAttributes();
        dialogparams.height = dialogheight;
        dialog.getWindow().setAttributes(dialogparams);
        layout.findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ m bxu;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }
}
