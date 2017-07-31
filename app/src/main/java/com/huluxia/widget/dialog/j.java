package com.huluxia.widget.dialog;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.huluxia.data.map.h;
import com.huluxia.framework.R;
import com.huluxia.utils.at;
import java.util.List;

/* compiled from: HPopupWindow */
public class j {
    private PopupWindow bxi;
    private List<h> bxj;
    private ListView bxk;
    private Activity bxl;
    private a bxm = null;
    private View view;

    /* compiled from: HPopupWindow */
    public interface a {
        void kz(int i);
    }

    public j(Activity act, a callback, List<h> texts) {
        this.bxl = act;
        this.bxm = callback;
        this.bxj = texts;
    }

    public void t(View parent) {
        a(parent, 0, 0);
    }

    public void a(View parent, int xOffset, int yOffset) {
        if (this.bxi == null) {
            this.view = ((LayoutInflater) this.bxl.getSystemService("layout_inflater")).inflate(R.layout.group_list, null);
            this.bxk = (ListView) this.view.findViewById(R.id.lvGroup);
            this.bxk.setAdapter(new GroupAdapter(this.bxl, this.bxj));
            this.bxi = new PopupWindow(this.view, at.dipToPx(this.bxl, 150), -2);
        }
        this.bxi.setFocusable(true);
        this.bxi.setOutsideTouchable(true);
        this.bxi.setBackgroundDrawable(new BitmapDrawable());
        this.bxi.showAsDropDown(parent, xOffset, yOffset);
        LayoutParams layoutParams = this.bxl.getWindow().getAttributes();
        layoutParams.alpha = 0.8f;
        this.bxl.getWindow().setAttributes(layoutParams);
        this.bxk.setOnItemClickListener(new 1(this));
        this.bxi.setOnDismissListener(new 2(this, layoutParams));
    }
}
