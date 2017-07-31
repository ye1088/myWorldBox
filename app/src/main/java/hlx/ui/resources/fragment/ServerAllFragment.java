package hlx.ui.resources.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsNetwork;
import com.huluxia.module.n;
import com.huluxia.module.o;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingLayout;
import com.huluxia.ui.base.BaseLoadingLayout.a;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.ui.itemadapter.server.ServerListAdapter;
import com.huluxia.ui.mctool.e;
import com.huluxia.utils.c;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import hlx.module.resources.f;
import java.util.ArrayList;
import java.util.Collection;

public class ServerAllFragment extends BaseThemeFragment implements a {
    private static final int PAGE_SIZE = 20;
    public static final int TYPE_ALL = 0;
    public static final int cbC = 1;
    private c aEV;
    private PullToRefreshListView aEq;
    private BaseLoadingLayout aIy;
    private ServerListAdapter bcs;
    private com.huluxia.data.server.a bct;
    private LinearLayout cbB;
    public int cbD = 0;
    private long cbE;
    com.huluxia.widget.a cbF = new com.huluxia.widget.a(new 6(this));
    private View cbw;
    private HorizontalScrollView cbx;
    private HorizontalScrollView cby;
    private RadioGroup cbz;
    private ArrayList<f> cga;
    private e cgm;
    private CompoundButton cgn;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ServerAllFragment cgo;

        {
            this.cgo = this$0;
        }

        @MessageHandler(message = 3076)
        public void onRecvResCate(boolean succ, ArrayList<f> cateList, String msg) {
            if (!succ) {
                this.cgo.aIy.FB();
            } else if (UtilsFunction.empty((Collection) cateList)) {
                this.cgo.aIy.FB();
            } else {
                this.cgo.cga = cateList;
                this.cgo.VX();
                this.cgo.reload();
            }
        }

        @MessageHandler(message = 3077)
        public void onRecvMapListInfo(boolean succ, int tag, com.huluxia.data.server.a info) {
            this.cgo.aEq.onRefreshComplete();
            this.cgo.aEV.onLoadComplete();
            if (tag != this.cgo.mRequestCode) {
                return;
            }
            String noNet;
            Context activity;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cgo.bct != null) {
                        this.cgo.bct.start = info.start;
                        this.cgo.bct.more = info.more;
                        this.cgo.bcs.a(info.serverList, false, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cgo.bcs.getData() == null || this.cgo.bcs.getData().size() == 0) {
                        this.cgo.aIy.FB();
                    } else {
                        noNet = this.cgo.getActivity().getResources().getString(R.string.no_network);
                        activity = this.cgo.getActivity();
                        if (UtilsNetwork.isNetworkConnected(this.cgo.getActivity())) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(activity, noNet);
                    }
                    HLog.error("ServerAllFragment.onRecvMapListInfo", "info.status" + info.status + "info.msg" + info.msg, new Object[0]);
                    return;
                } else {
                    this.cgo.bct = info;
                    this.cgo.bcs.a(this.cgo.bct.serverList, true, false);
                }
                this.cgo.aIy.FC();
            } else if (this.cgo.bcs.getData() == null || this.cgo.bcs.getData().size() == 0) {
                this.cgo.aIy.FB();
            } else {
                noNet = this.cgo.getActivity().getResources().getString(R.string.no_network);
                activity = this.cgo.getActivity();
                if (UtilsNetwork.isNetworkConnected(this.cgo.getActivity())) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(activity, noNet);
            }
        }
    };
    private int mRequestCode;

    public static ServerAllFragment VV() {
        return new ServerAllFragment();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.bcs != null) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.bcs);
            builder.a(setter);
        }
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        EventNotifyCenter.add(n.class, this.mCallback);
        this.bcs = new ServerListAdapter(getActivity());
        this.cgm = new e();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_server_list, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(R.id.loading_layout);
        this.cbw = LayoutInflater.from(getActivity()).inflate(R.layout.title_map_download, null, false);
        UZ();
        e(view);
        R(view);
        VW();
        return view;
    }

    private void VW() {
        this.aIy.Fy();
        this.cgm.DK();
    }

    private void R(View view) {
        this.cbx = (HorizontalScrollView) view.findViewById(R.id.all_tag_scroll_view);
        this.cby = (HorizontalScrollView) view.findViewById(R.id.version_select);
        this.cbz = (RadioGroup) view.findViewById(R.id.version_container);
        this.cgn = (RadioButton) view.findViewById(R.id.all_tag);
        this.cbB = (LinearLayout) view.findViewById(R.id.tag_container);
        this.cby.setVisibility(8);
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
    }

    public void showLoading() {
        this.aEq.setPullToRefreshEnabled(false);
    }

    public void FH() {
        this.aEq.setPullToRefreshEnabled(false);
    }

    public void FA() {
        this.aEq.setPullToRefreshEnabled(true);
    }

    private void e(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.fragment_resource_listview);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.cbw);
        this.bcs = new ServerListAdapter(getActivity());
        this.aEq.setAdapter(this.bcs);
        this.aEq.setOnRefreshListener(new 1(this));
        this.aIy.setLoadingListener(this);
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 2(this));
        this.aEq.setOnScrollListener(this.aEV);
        this.cbw.getViewTreeObserver().addOnGlobalLayoutListener(new 3(this));
    }

    private void Fc() {
        if (this.cbD == 1) {
            this.cgm.y(this.mRequestCode, (int) this.cbE, this.bct.start, 20);
        } else {
            o.N(this.mRequestCode, this.bct.start, 20);
        }
    }

    public void UZ() {
        this.aIy.setRetryClickListener(new 4(this));
    }

    private void reload() {
        if (this.cbD == 1) {
            this.cgm.y(this.mRequestCode, (int) this.cbE, 0, 20);
        } else if (this.cbD == 0) {
            o.N(this.mRequestCode, 0, 20);
        }
    }

    private void VX() {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        this.cbF.a(this.cgn);
        if (!UtilsFunction.empty(this.cga) && this.cbB.getChildCount() < 2) {
            for (int i = 0; i < this.cga.size(); i++) {
                f item = (f) this.cga.get(i);
                CompoundButton radio = (CompoundButton) mInflater.inflate(R.layout.view_map_cate_radio, this.cbB, false);
                radio.setTag(this.cga.get(i));
                radio.setText(item.categoryName);
                radio.setId(O(radio));
                this.cbB.addView(radio);
                this.cbF.a(radio);
            }
        }
    }

    @SuppressLint({"NewApi"})
    private static int O(View view) {
        if (VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        return view.hashCode();
    }
}
