package hlx.ui.resources.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.data.map.f;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.framework.base.widget.pager.PagerFragment;
import com.MCWorld.module.n;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.itemadapter.js.JsDownAdapter;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.itemadapter.skin.SkinDownAdapter;
import com.MCWorld.ui.itemadapter.wood.WoodDownAdapter;
import com.MCWorld.utils.c;

public class ResourceRankingFragment extends PagerFragment {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "ResourceRankingFragment";
    private c aEV;
    private PullToRefreshListView aEq;
    private DownAdapter aGG;
    private f aGH;
    CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ ResourceRankingFragment cgk;

        {
            this.cgk = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            if (this.cgk.baU) {
                this.cgk.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            this.cgk.aGG.notifyDataSetChanged();
            if (this.cgk.baU && true == this.cgk.baV) {
                this.cgk.aGG.cK(false);
                this.cgk.aGG.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            if (this.cgk.baU) {
                this.cgk.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.cgk.baU) {
                this.cgk.aGG.notifyDataSetChanged();
            }
        }
    };
    CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ ResourceRankingFragment cgk;

        {
            this.cgk = this$0;
        }

        @MessageHandler(message = 256)
        public void onWorkPrepare(String url) {
            if (this.cgk.baU) {
                this.cgk.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onWorkWait(String url) {
            if (this.cgk.baU) {
                this.cgk.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 262)
        public void onUnzipComplete(String url) {
            if (this.cgk.baU) {
                this.cgk.aGG.notifyDataSetChanged();
            }
        }
    };
    private BaseLoadingLayout aIy;
    private int axr;
    private hlx.module.resources.c bQo;
    private boolean baU;
    private boolean baV = true;
    private String bcC = "";
    private String cgb;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ResourceRankingFragment cgk;

        {
            this.cgk = this$0;
        }

        @MessageHandler(message = 3075)
        public void onRecvMapListInfo(boolean succ, int tag, f info) {
            this.cgk.aEq.onRefreshComplete();
            this.cgk.aEV.onLoadComplete();
            if (tag != this.cgk.mRequestCode) {
                return;
            }
            String noNet;
            Context activity;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cgk.aGH != null) {
                        this.cgk.aGH.start = info.start;
                        this.cgk.aGH.more = info.more;
                        this.cgk.aGG.a(info.mapList, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cgk.aGG.getData() == null || this.cgk.aGG.getData().size() == 0) {
                        this.cgk.aIy.FB();
                    } else {
                        noNet = this.cgk.getActivity().getResources().getString(R.string.no_network);
                        activity = this.cgk.getActivity();
                        if (UtilsNetwork.isNetworkConnected(this.cgk.getActivity())) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(activity, noNet);
                    }
                    HLog.error("ResourceRankingFragment.onRecvMapListInfo", "info.status" + info.status + "info.msg" + info.msg, new Object[0]);
                    return;
                } else {
                    this.cgk.aGH = info;
                    this.cgk.aGG.a(this.cgk.aGH.mapList, true);
                }
                this.cgk.aIy.FC();
            } else if (this.cgk.aGG.getData() == null || this.cgk.aGG.getData().size() == 0) {
                this.cgk.aIy.FB();
            } else {
                noNet = this.cgk.getActivity().getResources().getString(R.string.no_network);
                activity = this.cgk.getActivity();
                if (UtilsNetwork.isNetworkConnected(this.cgk.getActivity())) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(activity, noNet);
            }
        }
    };
    private int mRequestCode;

    public static ResourceRankingFragment p(int resType, String resTypeName) {
        Bundle bundle = new Bundle();
        bundle.putInt("resType", resType);
        bundle.putString("resTypeName", resTypeName);
        ResourceRankingFragment rankingFragment = new ResourceRankingFragment();
        rankingFragment.setArguments(bundle);
        return rankingFragment;
    }

    public void onSelected(int position) {
        this.baU = true;
        this.aGG.notifyDataSetChanged();
    }

    public void onUnSelected(int position) {
        super.onUnSelected(position);
        this.baU = false;
    }

    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(BaseEvent.class, this.aGM);
        EventNotifyCenter.add(com.MCWorld.controller.c.class, this.aGO);
        Bundle bundle = getArguments();
        this.axr = bundle.getInt("resType");
        this.cgb = bundle.getString("resTypeName");
        this.bQo = new hlx.module.resources.c(this.axr, this.bcC);
        this.aGG = nO(this.axr);
        this.aGG.cJ(true);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_resource_download, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(R.id.loading_layout);
        UZ();
        e(view);
        this.aIy.Fy();
        reload();
        return view;
    }

    private void e(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.fragment_resource_listview);
        this.aEq.setAdapter(this.aGG);
        this.aEq.setOnRefreshListener(new 1(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 2(this));
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void UZ() {
        this.aIy.Fy();
        this.aIy.setRetryClickListener(new 3(this));
    }

    private DownAdapter nO(int resType) {
        switch (resType) {
            case 2:
                return new JsDownAdapter(getActivity());
            case 3:
                return new SkinDownAdapter(getActivity());
            case 4:
                return new WoodDownAdapter(getActivity());
            default:
                return new DownAdapter(getActivity());
        }
    }

    protected void reload() {
        this.bQo.ag(this.mRequestCode, 0, 20);
    }

    protected void al(int requestCode, int start, int pageSize) {
        this.bQo.ag(requestCode, start, pageSize);
    }

    public void onStart() {
        super.onStart();
        this.baV = true;
    }

    public void onStop() {
        super.onStop();
        this.baV = false;
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aGM);
        EventNotifyCenter.remove(this.aGO);
    }
}
