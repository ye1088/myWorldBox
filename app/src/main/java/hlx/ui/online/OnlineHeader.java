package hlx.ui.online;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.framework.R;
import com.simple.colorful.c;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.module.resources.a;
import java.util.ArrayList;

public class OnlineHeader extends LinearLayout implements OnClickListener, c {
    private PullToRefreshListView aEq;
    private ArrayList<a> aQR = new ArrayList();
    private GridView bdT;
    private TextView ccP;
    private TextView ccQ;
    private CategoryAdapter ccR;
    private RelativeLayout ccS;
    private RelativeLayout ccT;
    private PopupWindow ccU;
    private PopupWindow ccV;
    private VersionAdapter ccW;
    PullToRefreshListView cdf;
    private boolean cdg = true;
    private Activity mContext;

    public OnlineHeader(Activity context, PullToRefreshListView listView) {
        super(context);
        this.mContext = context;
        init();
        this.cdf = listView;
    }

    private void init() {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.onlien_header, this, false);
        this.ccP = (TextView) view.findViewById(R.id.all_category);
        this.ccQ = (TextView) view.findViewById(R.id.all_version);
        this.ccS = (RelativeLayout) view.findViewById(R.id.rly_all_version);
        this.ccT = (RelativeLayout) view.findViewById(R.id.rly_all_category);
        this.ccS.setOnClickListener(this);
        this.ccT.setOnClickListener(this);
        addView(view);
        IW();
        Fa();
    }

    private void Fa() {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.listview, this, false);
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.list_view);
        View transparent = view.findViewById(R.id.transparent_area);
        this.ccW = new VersionAdapter(this.mContext);
        this.aEq.setPullToRefreshEnabled(false);
        this.aEq.setAdapter(this.ccW);
        this.ccU = new PopupWindow(view);
        b(this.ccU);
        transparent.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ OnlineHeader cdh;

            {
                this.cdh = this$0;
            }

            public void onClick(View v) {
                if (this.cdh.ccU != null && this.cdh.ccU.isShowing()) {
                    this.cdh.ccU.dismiss();
                }
            }
        });
        this.ccU.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ OnlineHeader cdh;

            {
                this.cdh = this$0;
            }

            public void onDismiss() {
                this.cdh.ccQ.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.cdh.mContext, R.attr.down), null);
            }
        });
    }

    private void IW() {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.gridview, this, false);
        this.bdT = (GridView) view.findViewById(R.id.grid_view);
        View transparent = view.findViewById(R.id.transparent_area);
        this.ccR = new CategoryAdapter(this.mContext);
        this.bdT.setAdapter(this.ccR);
        this.ccV = new PopupWindow(view);
        b(this.ccV);
        transparent.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ OnlineHeader cdh;

            {
                this.cdh = this$0;
            }

            public void onClick(View v) {
                if (this.cdh.ccV != null && this.cdh.ccV.isShowing()) {
                    this.cdh.ccV.dismiss();
                }
            }
        });
        this.ccV.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ OnlineHeader cdh;

            {
                this.cdh = this$0;
            }

            public void onDismiss() {
                this.cdh.ccP.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.cdh.mContext, R.attr.down), null);
            }
        });
    }

    public boolean getScoll() {
        return this.cdg;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rly_all_category:
                this.bdT.setBackgroundDrawable(d.o(this.mContext, R.attr.home_common_bg));
                a(this.ccV, this.ccU, this.ccP);
                return;
            case R.id.rly_all_version:
                a(this.ccU, this.ccV, this.ccQ);
                return;
            default:
                return;
        }
    }

    private void a(PopupWindow popupWindow, PopupWindow otherWindow, TextView icon) {
        if (popupWindow != null && otherWindow != null) {
            if (otherWindow.isShowing()) {
                otherWindow.dismiss();
            }
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.showAsDropDown(this);
                this.cdg = false;
            }
            icon.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.mContext, popupWindow.isShowing() ? R.attr.up : R.attr.down), null);
        }
    }

    private void b(PopupWindow popupWindow) {
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-2);
        popupWindow.setAnimationStyle(R.style.popupAnim);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setClippingEnabled(false);
    }

    public void setCategory(ArrayList<a> category) {
        this.aQR.clear();
        this.aQR.addAll(category);
        this.ccR.a(category, true);
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        if (this.ccW != null) {
            j setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.ccW);
            builder.a(setter);
        }
        if (this.ccR != null) {
            setter = new j(this.bdT);
            setter.a(this.ccR);
            builder.a(setter);
        }
        builder.aY(R.id.ll_all, R.attr.itemBackground).ba(R.id.all_category, R.attr.mapName).ba(R.id.all_version, R.attr.mapName).aY(R.id.vertical_dividing_line, R.attr.splitZoneCategory).ab(R.id.all_category, R.attr.down, 2).ab(R.id.all_version, R.attr.down, 2).aY(R.id.dividing_line, R.attr.dividingLine);
        return builder;
    }

    public void FG() {
        if (this.ccR != null) {
            this.ccR.notifyDataSetChanged();
        }
        if (this.ccW != null) {
            this.ccW.notifyDataSetChanged();
        }
    }
}
