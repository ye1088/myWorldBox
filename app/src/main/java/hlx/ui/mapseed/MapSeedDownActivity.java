package hlx.ui.mapseed;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.module.n;
import com.MCWorld.module.u;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.base.BaseLoadingLayout.a;
import com.MCWorld.ui.base.BaseLoadingLayout.b;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.utils.c;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.util.ArrayList;
import java.util.Collection;

public class MapSeedDownActivity extends HTBaseLoadingActivity implements a {
    private static final int PAGE_SIZE = 20;
    public static final int TYPE_ALL = 0;
    public static final int cbC = 1;
    private c aEV;
    private PullToRefreshListView aEq;
    private BaseLoadingLayout aIy;
    private RadioButton cbA;
    private LinearLayout cbB;
    public int cbD = 0;
    private long cbE;
    com.MCWorld.widget.a cbF = new com.MCWorld.widget.a(new com.MCWorld.widget.a.a(this) {
        final /* synthetic */ MapSeedDownActivity cbG;

        {
            this.cbG = this$0;
        }

        public void a(CompoundButton button, boolean chedked) {
            int id = button.getId();
            if (chedked) {
                this.cbG.mRequestCode = id;
                switch (id) {
                    case R.id.all_tag:
                        this.cbG.cbD = 0;
                        break;
                    default:
                        this.cbG.cbD = 1;
                        this.cbG.cbE = ((hlx.module.resources.a) button.getTag()).cateid;
                        break;
                }
                this.cbG.cbx.setVisibility(0);
                this.cbG.cbu.HA();
                this.cbG.cbt = null;
                this.cbG.aIy.Fy();
                this.cbG.reload();
            }
        }
    });
    private b cbt;
    private SeedAdapter cbu;
    private ArrayList<hlx.module.resources.a> cbv;
    private View cbw;
    private HorizontalScrollView cbx;
    private HorizontalScrollView cby;
    private RadioGroup cbz;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ MapSeedDownActivity cbG;

        {
            this.cbG = this$0;
        }

        @MessageHandler(message = 301)
        public void onRecvResCate(boolean succ, ArrayList<hlx.module.resources.a> cateList, String msg) {
            if (!succ) {
                this.cbG.aIy.FB();
            } else if (UtilsFunction.empty((Collection) cateList)) {
                this.cbG.aIy.FB();
            } else {
                this.cbG.cbv = cateList;
                this.cbG.Vb();
                this.cbG.reload();
            }
        }

        @MessageHandler(message = 298)
        public void onRecvListInfo(boolean succ, int tag, b info) {
            this.cbG.aEq.onRefreshComplete();
            this.cbG.aEV.onLoadComplete();
            if (tag != this.cbG.mRequestCode) {
                return;
            }
            String noNet;
            Context context;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cbG.cbt != null) {
                        this.cbG.cbt.start = info.start;
                        this.cbG.cbt.more = info.more;
                        this.cbG.cbu.c(info.seedList, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cbG.cbu.getData() == null || this.cbG.cbu.getData().size() == 0) {
                        this.cbG.aIy.FB();
                    } else {
                        noNet = this.cbG.getResources().getString(R.string.no_network);
                        context = this.cbG;
                        if (UtilsNetwork.isNetworkConnected(this.cbG)) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(context, noNet);
                    }
                    HLog.error("ServerAllFragment.onRecvMapListInfo", "info.status" + info.status + "info.msg" + info.msg, new Object[0]);
                    return;
                } else {
                    this.cbG.cbt = info;
                    this.cbG.cbu.c(this.cbG.cbt.seedList, true);
                }
                this.cbG.aIy.FC();
            } else if (this.cbG.cbu.getData() == null || this.cbG.cbu.getData().size() == 0) {
                this.cbG.aIy.FB();
            } else {
                noNet = this.cbG.getResources().getString(R.string.no_network);
                context = this.cbG;
                if (UtilsNetwork.isNetworkConnected(this.cbG)) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(context, noNet);
            }
        }
    };
    private Context mContext;
    private int mRequestCode;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_seed_download);
        EventNotifyCenter.add(n.class, this.mCallback);
        ej("地图种子");
        Sw();
        this.cbw = LayoutInflater.from(this).inflate(R.layout.title_map_download, null, false);
        this.aIy = (BaseLoadingLayout) findViewById(R.id.loading_layout);
        d(this.cbw);
        Fa();
        UZ();
        Va();
    }

    public void UZ() {
        this.aIy.setRetryClickListener(new b(this) {
            final /* synthetic */ MapSeedDownActivity cbG;

            {
                this.cbG = this$0;
            }

            public void onRetryClick(View view) {
                this.cbG.Va();
            }
        });
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.cbu != null) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.cbu);
            builder.a(setter);
        }
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void Sw() {
        this.mContext = this;
        this.cbu = new SeedAdapter(this);
        this.mRequestCode = 0;
    }

    private void d(View view) {
        this.cbx = (HorizontalScrollView) view.findViewById(R.id.all_tag_scroll_view);
        this.cby = (HorizontalScrollView) view.findViewById(R.id.version_select);
        this.cbz = (RadioGroup) view.findViewById(R.id.version_container);
        this.cbA = (RadioButton) view.findViewById(R.id.all_tag);
        this.cbB = (LinearLayout) view.findViewById(R.id.tag_container);
        this.cby.setVisibility(8);
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

    private void Fa() {
        this.aEq = (PullToRefreshListView) findViewById(R.id.listview);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.cbw);
        this.aEq.setAdapter(this.cbu);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ MapSeedDownActivity cbG;

            {
                this.cbG = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.cbG.reload();
            }
        });
        this.aIy.setLoadingListener(this);
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new c.a(this) {
            final /* synthetic */ MapSeedDownActivity cbG;

            {
                this.cbG = this$0;
            }

            public void onLoadData() {
                if (this.cbG.cbt != null) {
                    this.cbG.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.cbG.cbt == null) {
                    this.cbG.aEV.onLoadComplete();
                    return false;
                } else if (this.cbG.cbt.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
        this.cbw.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ MapSeedDownActivity cbG;

            {
                this.cbG = this$0;
            }

            @SuppressLint({"NewApi"})
            public void onGlobalLayout() {
                this.cbG.aIy.setPadding(0, this.cbG.cbw.getHeight(), 0, 0);
                if (VERSION.SDK_INT >= 16) {
                    this.cbG.aIy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    this.cbG.aIy.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    private void Va() {
        this.aIy.Fy();
        u.DK();
    }

    private void reload() {
        if (this.cbD == 1) {
            u.b(this.mRequestCode, this.cbE, 0, 20);
        } else if (this.cbD == 0) {
            u.T(this.mRequestCode, 0, 20);
        }
    }

    private void Fc() {
        if (this.cbD == 1) {
            u.b(this.mRequestCode, this.cbE, this.cbt.start, 20);
        } else if (this.cbD == 0) {
            u.T(this.mRequestCode, this.cbt.start, 20);
        }
    }

    private void Vb() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        this.cbF.a(this.cbA);
        if (!UtilsFunction.empty(this.cbv) && this.cbB.getChildCount() < 2) {
            for (int i = 0; i < this.cbv.size(); i++) {
                hlx.module.resources.a item = (hlx.module.resources.a) this.cbv.get(i);
                CompoundButton radio = (CompoundButton) mInflater.inflate(R.layout.view_map_cate_radio, null);
                radio.setTag(this.cbv.get(i));
                radio.setText(item.catename);
                radio.setHeight(UtilsScreen.dipToPx(this, 23));
                this.cbB.addView(radio);
                radio.setId(O(radio));
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

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }
}
