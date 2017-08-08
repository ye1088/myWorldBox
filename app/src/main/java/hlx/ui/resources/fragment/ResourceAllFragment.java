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
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.module.n;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.base.BaseThemeFragment;
import com.MCWorld.ui.itemadapter.js.JsDownAdapter;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.itemadapter.skin.SkinDownAdapter;
import com.MCWorld.ui.itemadapter.wood.WoodDownAdapter;
import com.MCWorld.utils.c;
import com.MCWorld.widget.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.util.ArrayList;
import java.util.Collection;

public class ResourceAllFragment extends BaseThemeFragment {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "ResourceAllFragment";
    public static final int TYPE_ALL = 1;
    public static final int cbC = 2;
    private c aEV;
    private PullToRefreshListView aEq;
    private DownAdapter aGG;
    private f aGH;
    CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ ResourceAllFragment cgj;

        {
            this.cgj = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            if (this.cgj.baU) {
                this.cgj.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            this.cgj.aGG.notifyDataSetChanged();
            if (this.cgj.baU && true == this.cgj.baV) {
                this.cgj.aGG.cK(false);
                this.cgj.aGG.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            if (this.cgj.baU) {
                this.cgj.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.cgj.baU) {
                this.cgj.aGG.notifyDataSetChanged();
            }
        }
    };
    CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ ResourceAllFragment cgj;

        {
            this.cgj = this$0;
        }

        @MessageHandler(message = 256)
        public void onWorkPrepare(String url) {
            if (this.cgj.baU) {
                this.cgj.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onWorkWait(String url) {
            if (this.cgj.baU) {
                this.cgj.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 262)
        public void onUnzipComplete(String url) {
            if (this.cgj.baU) {
                this.cgj.aGG.notifyDataSetChanged();
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
    public int cbD;
    private long cbE;
    private View cbw;
    private HorizontalScrollView cbx;
    private HorizontalScrollView cby;
    private RadioGroup cbz;
    private a cfZ;
    private ArrayList<hlx.module.resources.a> cga;
    private String cgb;
    private CompoundButton cgc;
    private RadioButton cgd;
    private View cge;
    private View cgf;
    private ArrayList<CompoundButton> cgg;
    a.a cgh = new 6(this);
    OnCheckedChangeListener cgi = new 7(this);
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ResourceAllFragment cgj;

        {
            this.cgj = this$0;
        }

        @MessageHandler(message = 2816)
        public void onRecvResCate(boolean succ, ArrayList<hlx.module.resources.a> cateList, String msg) {
            if (!succ) {
                this.cgj.aIy.FB();
            } else if (UtilsFunction.empty((Collection) cateList)) {
                this.cgj.aIy.FB();
            } else {
                this.cgj.cga = cateList;
                this.cgj.Vf();
                this.cgj.reload();
            }
        }

        @MessageHandler(message = 3072)
        public void onRecvMapListInfo(boolean succ, int tag, f info) {
            this.cgj.aEq.onRefreshComplete();
            this.cgj.aEV.onLoadComplete();
            if (tag != this.cgj.mRequestCode) {
                return;
            }
            String noNet;
            Context activity;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cgj.aGH != null) {
                        this.cgj.aGH.start = info.start;
                        this.cgj.aGH.more = info.more;
                        this.cgj.aGG.a(info.mapList, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cgj.aGG.getData() == null || this.cgj.aGG.getData().size() == 0) {
                        this.cgj.aIy.FB();
                    } else {
                        noNet = this.cgj.getActivity().getResources().getString(R.string.no_network);
                        activity = this.cgj.getActivity();
                        if (UtilsNetwork.isNetworkConnected(this.cgj.getActivity())) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(activity, noNet);
                    }
                    HLog.error("ResourceAllFragment.onRecvMapListInfo", info.msg + "", new Object[0]);
                    return;
                } else {
                    this.cgj.aGH = info;
                    this.cgj.aGG.a(this.cgj.aGH.mapList, true);
                }
                this.cgj.aIy.FC();
            } else if (this.cgj.aGG.getData() == null || this.cgj.aGG.getData().size() == 0) {
                this.cgj.aIy.FB();
            } else {
                noNet = this.cgj.getActivity().getResources().getString(R.string.no_network);
                activity = this.cgj.getActivity();
                if (UtilsNetwork.isNetworkConnected(this.cgj.getActivity())) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(activity, noNet);
            }
        }
    };
    private int mRequestCode = -1;

    public static ResourceAllFragment o(int resType, String resTypeName) {
        Bundle bundle = new Bundle();
        bundle.putInt("resType", resType);
        bundle.putString("resTypeName", resTypeName);
        ResourceAllFragment allFragment = new ResourceAllFragment();
        allFragment.setArguments(bundle);
        return allFragment;
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aGG != null) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aGG);
            builder.a(setter);
        }
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
        this.aGG = nO(this.axr);
        this.bQo = new hlx.module.resources.c(this.axr, this.bcC);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_resource_download, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(R.id.loading_layout);
        this.cbw = LayoutInflater.from(getActivity()).inflate(R.layout.title_map_download, null, false);
        VS();
        UZ();
        e(view);
        d(view);
        VT();
        VU();
        return view;
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

    public void VS() {
        this.aIy.Fy();
        this.bQo.DK();
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
        if (this.cbD == 2) {
            this.bQo.y(this.mRequestCode, (int) this.cbE, 0, 20);
        } else {
            this.bQo.af(this.mRequestCode, 0, 20);
        }
    }

    protected void d(View view) {
        this.cbx = (HorizontalScrollView) view.findViewById(R.id.all_tag_scroll_view);
        this.cby = (HorizontalScrollView) view.findViewById(R.id.version_select);
        this.cbz = (RadioGroup) view.findViewById(R.id.version_container);
        this.cbB = (LinearLayout) view.findViewById(R.id.tag_container);
        this.cgc = (CompoundButton) view.findViewById(R.id.all_tag);
        this.cgd = (RadioButton) view.findViewById(R.id.all_version);
        this.cge = view.findViewById(R.id.dividing_line);
        this.cgf = view.findViewById(R.id.title_root);
        this.cby.setVisibility(this.axr == 1 ? 8 : 4);
    }

    protected void al(int requestCode, int start, int pageSize) {
        if (this.cbD == 2) {
            this.bQo.y(this.mRequestCode, (int) this.cbE, start, pageSize);
        } else {
            this.bQo.af(requestCode, start, pageSize);
        }
    }

    public void UZ() {
        this.aIy.setRetryClickListener(new 4(this));
    }

    private void Vf() {
        int i = 0;
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        if (!UtilsFunction.empty(this.cga) && this.cbB.getChildCount() < 2) {
            this.cgg = new ArrayList();
            for (int i2 = 0; i2 < this.cga.size(); i2++) {
                hlx.module.resources.a item = (hlx.module.resources.a) this.cga.get(i2);
                CompoundButton radio = (CompoundButton) mInflater.inflate(R.layout.view_map_cate_radio, this.cbB, false);
                radio.setTag(this.cga.get(i2));
                radio.setText(item.catename);
                radio.setId(O(radio));
                this.cbB.addView(radio);
                this.cfZ.a(radio);
            }
            HorizontalScrollView horizontalScrollView = this.cby;
            if (this.axr == 1) {
                i = 8;
            }
            horizontalScrollView.setVisibility(i);
        }
    }

    @SuppressLint({"NewApi"})
    private static int O(View view) {
        if (VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        return view.hashCode();
    }

    protected void VT() {
        this.beK = new String[]{hlx.data.localstore.a.bJr, hlx.data.localstore.a.bJs, hlx.data.localstore.a.bJt, "0.13", "0.14", hlx.data.localstore.a.bJy};
        this.cbz.setOnCheckedChangeListener(this.cgi);
        for (int i = 0; i < this.beK.length; i++) {
            RadioButton radio = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.view_map_cate_radio, this.cbz, false);
            radio.setText(this.beK[i]);
            radio.setId(i);
            this.cbz.addView(radio);
        }
    }

    public void VU() {
        this.cfZ = new a(this.cgh);
        this.cfZ.a(this.cgc);
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
