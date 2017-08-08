package hlx.ui.resources.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.z;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.data.server.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.module.n;
import com.MCWorld.module.o;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.itemadapter.server.ServerListAdapter;
import com.MCWorld.utils.c;

public class ServerRecommendFragment extends Fragment {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private BaseLoadingLayout aIy;
    private ServerListAdapter bcs;
    private a bct;
    CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ServerRecommendFragment cgp;

        {
            this.cgp = this$0;
        }

        @MessageHandler(message = 3078)
        public void onRecvMapListInfo(boolean succ, int tag, a info) {
            this.cgp.aEq.onRefreshComplete();
            this.cgp.aEV.onLoadComplete();
            if (tag != this.cgp.mRequestCode) {
                return;
            }
            String noNet;
            Context activity;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cgp.bct != null) {
                        this.cgp.bct.start = info.start;
                        this.cgp.bct.more = info.more;
                        this.cgp.bcs.a(info.serverList, false, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cgp.bcs.getData() == null || this.cgp.bcs.getData().size() == 0) {
                        this.cgp.aIy.FB();
                    } else {
                        noNet = this.cgp.getActivity().getResources().getString(R.string.no_network);
                        activity = this.cgp.getActivity();
                        if (UtilsNetwork.isNetworkConnected(this.cgp.getActivity())) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(activity, noNet);
                    }
                    HLog.error("ServerRecommendFragment.onRecvMapListInfo", "info.status" + info.status + "info.msg" + info.msg, new Object[0]);
                    return;
                } else {
                    this.cgp.bct = info;
                    this.cgp.bcs.a(this.cgp.bct.serverList, true, false);
                }
                this.cgp.aIy.FC();
            } else if (this.cgp.bcs.getData() == null || this.cgp.bcs.getData().size() == 0) {
                this.cgp.aIy.FB();
            } else {
                noNet = this.cgp.getActivity().getResources().getString(R.string.no_network);
                activity = this.cgp.getActivity();
                if (UtilsNetwork.isNetworkConnected(this.cgp.getActivity())) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(activity, noNet);
            }
        }
    };
    private int mRequestCode;

    public static ServerRecommendFragment VY() {
        return new ServerRecommendFragment();
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        EventNotifyCenter.add(n.class, this.mCallback);
        this.bcs = new ServerListAdapter(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_server_list, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(R.id.loading_layout);
        UZ();
        e(view);
        this.aIy.Fy();
        reload();
        return view;
    }

    private void reload() {
        o.O(this.mRequestCode, 0, 20);
    }

    private void e(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.fragment_resource_listview);
        this.bcs = new ServerListAdapter(getActivity());
        this.aEq.setAdapter(this.bcs);
        this.aEq.setOnRefreshListener(new 1(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 2(this));
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void Fc() {
        o.O(this.mRequestCode, this.bct.start, 20);
    }

    private void UZ() {
        this.aIy.Fy();
        this.aIy.setRetryClickListener(new 3(this));
    }
}
