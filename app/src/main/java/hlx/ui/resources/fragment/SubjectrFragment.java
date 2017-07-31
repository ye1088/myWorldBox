package hlx.ui.resources.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsNetwork;
import com.huluxia.module.n;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingLayout;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.utils.c;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import hlx.module.resources.d;
import hlx.ui.itemadapter.resource.SubjectListAdapter;

public class SubjectrFragment extends BaseThemeFragment {
    private static final String TAG = "SubjectrFragment";
    private int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private BaseLoadingLayout aIy;
    private int axr;
    private hlx.module.resources.c bQo;
    private String bcC = "";
    private String cgb;
    private SubjectListAdapter cgq;
    private d cgr;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ SubjectrFragment cgs;

        {
            this.cgs = this$0;
        }

        @MessageHandler(message = 2817)
        public void onRecvMapTopic(boolean succ, int requestCode, d subjectList, String msg) {
            this.cgs.aEq.onRefreshComplete();
            this.cgs.aEV.onLoadComplete();
            if (requestCode != this.cgs.mRequestCode) {
                return;
            }
            String noNet;
            Context activity;
            if (succ && subjectList != null) {
                if (subjectList.start > this.cgs.PAGE_SIZE) {
                    this.cgs.cgr.start = subjectList.start;
                    this.cgs.cgr.more = subjectList.more;
                    this.cgs.cgq.c(subjectList.topList, false);
                } else if (subjectList.status == 0) {
                    if (this.cgs.cgq.getData() == null || this.cgs.cgq.getData().size() == 0) {
                        this.cgs.aIy.FB();
                    } else {
                        noNet = this.cgs.getActivity().getResources().getString(R.string.no_network);
                        activity = this.cgs.getActivity();
                        if (UtilsNetwork.isNetworkConnected(this.cgs.getActivity())) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(activity, noNet);
                    }
                    HLog.error("SubjectrFragment.onRecvMapTopic", "info.status" + subjectList.status + "info.msg" + subjectList.msg, new Object[0]);
                    return;
                } else {
                    this.cgs.cgr = subjectList;
                    this.cgs.cgq.c(this.cgs.cgr.topList, true);
                }
                this.cgs.aIy.FC();
            } else if (this.cgs.cgq.getData() == null || this.cgs.cgq.getData().size() == 0) {
                this.cgs.aIy.FB();
            } else {
                noNet = this.cgs.getActivity().getResources().getString(R.string.no_network);
                activity = this.cgs.getActivity();
                if (UtilsNetwork.isNetworkConnected(this.cgs.getActivity())) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(activity, noNet);
            }
        }
    };
    private int mRequestCode;

    public static SubjectrFragment r(int resType, String resTypeName) {
        Bundle bundle = new Bundle();
        bundle.putInt("resType", resType);
        bundle.putString("resTypeName", resTypeName);
        SubjectrFragment subjectrFragment = new SubjectrFragment();
        subjectrFragment.setArguments(bundle);
        return subjectrFragment;
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        EventNotifyCenter.add(n.class, this.mCallback);
        Bundle bundle = getArguments();
        this.cgb = bundle.getString("resTypeName");
        this.axr = bundle.getInt("resType");
        this.bQo = new hlx.module.resources.c(this.axr, this.bcC);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_resource_download, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(R.id.loading_layout);
        UZ();
        e(view);
        this.aIy.Fy();
        reload();
        return view;
    }

    private void UZ() {
        this.aIy.Fy();
        this.aIy.setRetryClickListener(new 1(this));
    }

    protected void a(a builder) {
        super.a(builder);
        if (this.cgq != null) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.cgq);
            builder.a(setter);
        }
    }

    void e(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.fragment_resource_listview);
        this.cgq = new SubjectListAdapter(getActivity(), this.axr);
        this.aEq.setAdapter(this.cgq);
        this.aEq.setOnRefreshListener(new 2(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 3(this));
        this.aEq.setOnScrollListener(this.aEV);
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void reload() {
        this.bQo.ah(this.mRequestCode, 0, this.PAGE_SIZE);
    }

    protected void al(int requestCode, int start, int pageSize) {
        this.bQo.ah(requestCode, start, pageSize);
    }
}
