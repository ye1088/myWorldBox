package com.MCWorld.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import java.util.List;

/* compiled from: MenuDialog */
public class n extends Dialog implements OnItemClickListener {
    private ListView aZh;
    private MenuItemArrayAdapter bxv;
    private a bxw;
    private View bxx;
    private int checked = 1;
    private Context context;
    private Object tag;
    private String title;

    /* compiled from: MenuDialog */
    public interface a {
        void a(o oVar);
    }

    public n(Context context, String title) {
        super(context, 16973840);
        this.context = context;
        this.title = title;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void show() {
        getWindow().setGravity(17);
        super.show();
    }

    public void aL(int backColor, int textColor) {
        ((ImageView) this.bxx.findViewById(g.bottom_img_view)).setVisibility(8);
        ((TextView) this.bxx.findViewById(g.dialog_menu_title)).setTextColor(this.context.getResources().getColor(textColor));
        TextView promptView = (TextView) this.bxx.findViewById(g.dialog_menu_prompt);
        promptView.setTextColor(this.context.getResources().getColor(textColor));
        promptView.setVisibility(0);
        this.bxx.findViewById(g.layout_title).setBackgroundResource(backColor);
    }

    public void setMenuItems(List<o> items) {
        this.bxv = new MenuItemArrayAdapter(this.context, i.listitem_dialog, items);
        View layout = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(i.dialog_menu, (ViewGroup) findViewById(g.dialog_layout_root));
        setContentView(layout);
        this.bxx = layout;
        ((TextView) layout.findViewById(g.dialog_menu_title)).setText(this.title);
        this.aZh = (ListView) layout.findViewById(g.listview);
        this.aZh.setAdapter(this.bxv);
        this.aZh.setOnItemClickListener(this);
        layout.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ n bxy;

            {
                this.bxy = this$0;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.bxy.dismiss();
                return false;
            }
        });
    }

    public void setTag(Object obj) {
        this.tag = obj;
    }

    public Object getTag() {
        return this.tag;
    }

    public ListView getListView() {
        return this.aZh;
    }

    public int On() {
        return this.checked;
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        o m = (o) this.bxv.getItem(position);
        this.checked = this.bxv.On();
        if (this.bxw != null) {
            this.bxw.a(m);
        }
    }

    public a Or() {
        return this.bxw;
    }

    public void a(a onMenuItemClick) {
        this.bxw = onMenuItemClick;
    }
}
