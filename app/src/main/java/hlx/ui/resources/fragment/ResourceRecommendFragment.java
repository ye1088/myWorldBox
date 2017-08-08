package hlx.ui.resources.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
import hlx.data.localstore.a;

public class ResourceRecommendFragment extends PagerFragment {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "ResourceRecommendFragment";
    private c aEV;
    private PullToRefreshListView aEq;
    private DownAdapter aGG;
    private f aGH;
    CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ ResourceRecommendFragment cgl;

        {
            this.cgl = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            if (this.cgl.baU) {
                this.cgl.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            this.cgl.aGG.notifyDataSetChanged();
            if (this.cgl.baU && true == this.cgl.baV) {
                this.cgl.aGG.cK(false);
                this.cgl.aGG.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            if (this.cgl.baU) {
                this.cgl.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.cgl.baU) {
                this.cgl.aGG.notifyDataSetChanged();
            }
        }
    };
    CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ ResourceRecommendFragment cgl;

        {
            this.cgl = this$0;
        }

        @MessageHandler(message = 256)
        public void onWorkPrepare(String url) {
            if (this.cgl.baU) {
                this.cgl.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onWorkWait(String url) {
            if (this.cgl.baU) {
                this.cgl.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 262)
        public void onUnzipComplete(String url) {
            if (this.cgl.baU) {
                this.cgl.aGG.notifyDataSetChanged();
            }
        }
    };
    private BaseLoadingLayout aIy;
    private int axr;
    private hlx.module.resources.c bQo;
    private boolean baU;
    private boolean baV = true;
    private String bcC = "";
    private String[] beK;
    private LinearLayout cbB;
    private View cbw;
    private HorizontalScrollView cbx;
    private HorizontalScrollView cby;
    private RadioGroup cbz;
    private String cgb;
    OnCheckedChangeListener cgi = new 6(this);
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ResourceRecommendFragment cgl;

        {
            this.cgl = this$0;
        }

        @MessageHandler(message = 3073)
        public void onRecvMapListInfo(boolean succ, int tag, f info) {
            this.cgl.aEq.onRefreshComplete();
            this.cgl.aEV.onLoadComplete();
            if (tag != this.cgl.mRequestCode) {
                return;
            }
            String noNet;
            Context activity;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cgl.aGH != null) {
                        HLog.info(ResourceRecommendFragment.TAG, "开始加载更多", new Object[0]);
                        this.cgl.aGH.start = info.start;
                        this.cgl.aGH.more = info.more;
                        this.cgl.aGG.a(info.mapList, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cgl.aGG.getData() == null || this.cgl.aGG.getData().size() == 0) {
                        this.cgl.aIy.FB();
                    } else {
                        noNet = this.cgl.getActivity().getResources().getString(R.string.no_network);
                        activity = this.cgl.getActivity();
                        if (UtilsNetwork.isNetworkConnected(this.cgl.getActivity())) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(activity, noNet);
                    }
                    HLog.error("ResourceRecommendFragment.onRecvMapListInfo", "info.status" + info.status + "info.msg" + info.msg, new Object[0]);
                    return;
                } else {
                    this.cgl.aGH = info;
                    this.cgl.aGG.a(this.cgl.aGH.mapList, true);
                }
                this.cgl.aIy.FC();
            } else if (this.cgl.aGG.getData() == null || this.cgl.aGG.getData().size() == 0) {
                this.cgl.aIy.FB();
            } else {
                noNet = this.cgl.getActivity().getResources().getString(R.string.no_network);
                activity = this.cgl.getActivity();
                if (UtilsNetwork.isNetworkConnected(this.cgl.getActivity())) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(activity, noNet);
            }
        }
    };
    private int mRequestCode;

    public static ResourceRecommendFragment q(int resType, String resTypeName) {
        Bundle bundle = new Bundle();
        bundle.putInt("resType", resType);
        bundle.putString("resTypeName", resTypeName);
        ResourceRecommendFragment recommendFragment = new ResourceRecommendFragment();
        recommendFragment.setArguments(bundle);
        return recommendFragment;
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_resource_download, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(R.id.loading_layout);
        this.cbw = LayoutInflater.from(getActivity()).inflate(R.layout.title_map_download, null, false);
        UZ();
        e(view);
        d(view);
        VT();
        this.aIy.Fy();
        reload();
        return view;
    }

    private void d(View view) {
        int i;
        int i2 = 0;
        this.cbx = (HorizontalScrollView) view.findViewById(R.id.all_tag_scroll_view);
        this.cby = (HorizontalScrollView) view.findViewById(R.id.version_select);
        this.cbz = (RadioGroup) view.findViewById(R.id.version_container);
        this.cbB = (LinearLayout) view.findViewById(R.id.tag_container);
        View dividingLine = view.findViewById(R.id.dividing_line);
        if (this.axr == 1) {
            i = 8;
        } else {
            i = 0;
        }
        dividingLine.setVisibility(i);
        HorizontalScrollView horizontalScrollView = this.cby;
        if (this.axr == 1) {
            i2 = 8;
        }
        horizontalScrollView.setVisibility(i2);
        this.cbx.setVisibility(8);
    }

    void e(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.fragment_resource_listview);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.cbw);
        this.aEq.setAdapter(this.aGG);
        this.aEq.setOnRefreshListener(new 1(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 2(this));
        this.aEq.setOnScrollListener(this.aEV);
        this.cbw.getViewTreeObserver().addOnGlobalLayoutListener(new 3(this));
    }

    private void UZ() {
        this.aIy.Fy();
        this.aIy.setRetryClickListener(new 4(this));
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

    protected void VT() {
        this.beK = new String[]{a.bJr, a.bJs, a.bJt, "0.13", "0.14", a.bJy};
        this.cbz.setOnCheckedChangeListener(this.cgi);
        for (int i = 0; i < this.beK.length; i++) {
            RadioButton radio = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.view_map_cate_radio, this.cbz, false);
            radio.setText(this.beK[i]);
            radio.setId(i);
            this.cbz.addView(radio);
        }
    }

    protected void reload() {
        this.bQo.ae(this.mRequestCode, 0, 20);
    }

    protected void al(int requestCode, int start, int pageSize) {
        this.bQo.ae(requestCode, start, pageSize);
    }

    private void hN(String versionCode) {
        this.bQo.hs(versionCode);
        this.aGG.HA();
        this.aGH = null;
        this.aIy.Fy();
        reload();
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
