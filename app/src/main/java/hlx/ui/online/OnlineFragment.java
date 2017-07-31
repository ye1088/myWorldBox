package hlx.ui.online;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.k;
import com.huluxia.mconline.gameloc.http.g;
import com.huluxia.mconline.gameloc.http.h;
import com.huluxia.module.n;
import com.huluxia.module.o;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.c;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.module.resources.a;
import java.util.ArrayList;
import java.util.List;

public class OnlineFragment extends BaseLoadingFragment implements OnClickListener {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "OnlineFragment";
    private static final String ccL = "GUEST_ROOM_DATA";
    protected Handler Vo = new Handler(this) {
        final /* synthetic */ OnlineFragment cde;

        {
            this.cde = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.cde.Vh();
                    this.cde.Vo.sendMessageDelayed(this.cde.Vo.obtainMessage(1), (long) this.cde.cdd);
                    return;
                default:
                    return;
            }
        }
    };
    private c aET;
    private PullToRefreshListView aEq;
    private TextView aIg;
    private ArrayList<a> aQR = new ArrayList();
    private h ccM;
    private OnlineAdapter ccN;
    private TextView ccO;
    private TextView ccP;
    private TextView ccQ;
    private CategoryAdapter ccR;
    private RelativeLayout ccS;
    private RelativeLayout ccT;
    private PopupWindow ccU;
    private PopupWindow ccV;
    private VersionAdapter ccW;
    private GridView ccX;
    private PullToRefreshListView ccY;
    private View ccZ;
    private Button cda;
    private MsgtipReciver cdb;
    private ClearMsgReciver cdc;
    private int cdd = 20000;
    private TextView eN;
    private CallbackHandler mCallback = new 9(this);
    OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ OnlineFragment cde;

        {
            this.cde = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ic_create_room:
                    k.aa(this.cde.mContext);
                    return;
                default:
                    return;
            }
        }
    };
    private Activity mContext;

    protected class ClearMsgReciver extends BroadcastReceiver {
        final /* synthetic */ OnlineFragment cde;

        protected ClearMsgReciver(OnlineFragment this$0) {
            this.cde = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.cde.Fs();
        }
    }

    protected class MsgtipReciver extends BroadcastReceiver {
        final /* synthetic */ OnlineFragment cde;

        protected MsgtipReciver(OnlineFragment this$0) {
            this.cde = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.cde.Fr();
        }
    }

    public static OnlineFragment Ve() {
        return new OnlineFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(n.class, this.mCallback);
        this.mContext = getActivity();
        com.huluxia.mconline.utils.a.aN(this.mContext);
        this.cdb = new MsgtipReciver(this);
        this.cdc = new ClearMsgReciver(this);
        i.e(this.cdb);
        i.f(this.cdc);
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online, container, false);
        P(view);
        a(view, savedInstanceState);
        this.cda = (Button) view.findViewById(R.id.ic_create_room);
        this.cda.setOnClickListener(this.mClickListener);
        reload();
        this.Vo.sendMessageDelayed(this.Vo.obtainMessage(1), (long) this.cdd);
        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ccL, this.ccM);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.cdb != null) {
            i.unregisterReceiver(this.cdb);
            this.cdb = null;
        }
        if (this.cdc != null) {
            i.unregisterReceiver(this.cdc);
            this.cdc = null;
        }
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void EX() {
        super.EX();
        reload();
    }

    private void P(View view) {
        this.ccO = (TextView) view.findViewById(R.id.msg_banner);
        this.ccZ = view.findViewById(R.id.online_header);
        this.ccP = (TextView) view.findViewById(R.id.all_category);
        this.ccQ = (TextView) view.findViewById(R.id.all_version);
        this.ccS = (RelativeLayout) view.findViewById(R.id.rly_all_version);
        this.ccT = (RelativeLayout) view.findViewById(R.id.rly_all_category);
        this.ccS.setOnClickListener(this);
        this.ccT.setOnClickListener(this);
        Vf();
        Vg();
        this.ccZ.setVisibility(8);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rly_all_category:
                this.ccX.setBackgroundDrawable(d.o(this.mContext, R.attr.home_common_bg));
                a(this.ccV, this.ccU, this.ccP);
                return;
            case R.id.rly_all_version:
                a(this.ccU, this.ccV, this.ccQ);
                return;
            default:
                return;
        }
    }

    private void Vf() {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.gridview, null, false);
        this.ccX = (GridView) view.findViewById(R.id.grid_view);
        View transparent = view.findViewById(R.id.transparent_area);
        this.ccR = new CategoryAdapter(this.mContext);
        this.ccX.setAdapter(this.ccR);
        this.ccV = new PopupWindow(view);
        b(this.ccV);
        transparent.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onClick(View v) {
                if (this.cde.ccV != null && this.cde.ccV.isShowing()) {
                    this.cde.ccV.dismiss();
                }
            }
        });
        this.ccV.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onDismiss() {
                this.cde.ccP.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.cde.mContext, R.attr.down), null);
            }
        });
    }

    private void a(PopupWindow popupWindow, PopupWindow otherWindow, TextView icon) {
        if (popupWindow != null && otherWindow != null) {
            if (otherWindow.isShowing()) {
                otherWindow.dismiss();
            }
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.showAsDropDown(this.ccZ);
            }
            icon.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.mContext, popupWindow.isShowing() ? R.attr.up : R.attr.down), null);
        }
    }

    private void Vg() {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.listview, null, false);
        this.ccY = (PullToRefreshListView) view.findViewById(R.id.list_view);
        View transparent = view.findViewById(R.id.transparent_area);
        this.ccW = new VersionAdapter(this.mContext);
        this.ccY.setPullToRefreshEnabled(false);
        this.ccY.setAdapter(this.ccW);
        this.ccU = new PopupWindow(view);
        b(this.ccU);
        transparent.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onClick(View v) {
                if (this.cde.ccU != null && this.cde.ccU.isShowing()) {
                    this.cde.ccU.dismiss();
                }
            }
        });
        this.ccU.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onDismiss() {
                this.cde.ccQ.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.cde.mContext, R.attr.down), null);
            }
        });
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

    private void a(View view, Bundle savedInstanceState) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.listview);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.cde.reload();
            }
        });
        this.ccN = new OnlineAdapter(this.mContext);
        this.aEq.setAdapter(this.ccN);
        this.aET = new c((ListView) this.aEq.getRefreshableView());
        this.aET.a(new c.a(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onLoadData() {
                if (this.cde.ccM != null) {
                    this.cde.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.cde.ccM == null) {
                    this.cde.aET.onLoadComplete();
                    return false;
                } else if (this.cde.ccM.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aET);
        if (savedInstanceState == null) {
            com.huluxia.mconline.module.a.Bl().c(0, 20);
            Fy();
            return;
        }
        this.ccM = (h) savedInstanceState.getParcelable(ccL);
        if (this.ccM != null && this.ccM.room_infos != null) {
            this.ccN.c(this.ccM.room_infos, true);
        }
    }

    private void Fc() {
        com.huluxia.mconline.module.a.Bl().c(this.ccM == null ? 0 : this.ccM.start, 20);
    }

    private void reload() {
        o.DI();
        com.huluxia.mconline.module.a.Bl().c(0, 20);
        com.huluxia.mconline.module.a.Bl().Bm();
    }

    protected void a(TitleBar titleBar) {
        titleBar.setLeftLayout(R.layout.home_left_btn);
        titleBar.setRightLayout(R.layout.home_right_btn);
        this.eN = (TextView) this.aIw.findViewById(R.id.header_title);
        this.eN.setText("联机");
        this.aIg = (TextView) titleBar.findViewById(R.id.tv_msg);
        titleBar.findViewById(R.id.img_msg).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ OnlineFragment cde;

            {
                this.cde = this$0;
            }

            public void onClick(View v) {
                t.a(this.cde.getActivity(), HTApplication.bM());
            }
        });
    }

    public void setCategory(ArrayList<a> category) {
        this.aQR.clear();
        this.aQR.addAll(category);
        this.ccR.a(category, true);
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void Vh() {
        List<g> refreshList = new ArrayList();
        refreshList.addAll(this.ccN.Vd());
        HLog.verbose(TAG, "refreshData size " + refreshList.size(), new Object[0]);
        com.huluxia.mconline.module.a.Bl().H(refreshList);
    }

    protected void Fr() {
        MsgCounts msgCounts = HTApplication.bM();
        long allCount = msgCounts == null ? 0 : msgCounts.getAll();
        if (allCount > 0) {
            this.aIg.setVisibility(0);
            if (allCount > 99) {
                this.aIg.setText("99+");
                return;
            } else {
                this.aIg.setText(String.valueOf(msgCounts.getAll()));
                return;
            }
        }
        this.aIg.setVisibility(8);
    }

    protected void Fs() {
        this.aIg.setVisibility(8);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.ccN != null) {
            j setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.ccN);
            builder.a(setter);
        }
        if (this.ccW != null) {
            setter = new j((ViewGroup) this.ccY.getRefreshableView());
            setter.a(this.ccW);
            builder.a(setter);
        }
        if (this.ccR != null) {
            setter = new j(this.ccX);
            setter.a(this.ccR);
            builder.a(setter);
        }
        builder.a(this.eN, 16842809).aY(R.id.ll_all, R.attr.itemBackground).ba(R.id.all_category, R.attr.mapName).ba(R.id.all_version, R.attr.mapName).aY(R.id.vertical_dividing_line, R.attr.splitZoneCategory).ab(R.id.all_category, R.attr.down, 2).ab(R.id.all_version, R.attr.down, 2).aY(R.id.dividing_line, R.attr.dividingLine).aY(R.id.online_header, R.attr.label).aZ(R.id.root_view, R.attr.itemBackground).ba(R.id.ic_create_room, 16842809).aZ(R.id.ic_create_room, R.attr.bg_online_create);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.ccN != null) {
            this.ccN.notifyDataSetChanged();
        }
        if (this.ccR != null) {
            this.ccR.notifyDataSetChanged();
        }
        if (this.ccW != null) {
            this.ccW.notifyDataSetChanged();
        }
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
