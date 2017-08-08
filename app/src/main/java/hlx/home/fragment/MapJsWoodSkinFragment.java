package hlx.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.data.map.f;
import com.MCWorld.data.server.a;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.n;
import com.MCWorld.module.o;
import com.MCWorld.module.q;
import com.MCWorld.module.u;
import com.MCWorld.ui.itemadapter.js.JsDownAdapter;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.itemadapter.server.ServerListAdapter;
import com.MCWorld.ui.itemadapter.skin.SkinDownAdapter;
import com.MCWorld.ui.itemadapter.wood.WoodDownAdapter;
import com.MCWorld.utils.c;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import hlx.ui.mapseed.SeedAdapter;
import hlx.ui.mapseed.b;
import hlx.ui.recommendapp.RecommendAppAdapter;

public class MapJsWoodSkinFragment extends ScrollableFragment {
    private static final int PAGE_SIZE = 20;
    private static String TAG = "MapJsWoodSkinFragment";
    private static final String aGI = "RES_TYPE";
    private static final String aGK = "DATA";
    private static final String bQv = "SEED_DATA";
    private static final String bQw = "APP_DATA";
    private static final String bQx = "SERVER_DATA";
    private c aET;
    private PullToRefreshListView aEq;
    private boolean aGE;
    private boolean aGF = false;
    private DownAdapter aGG;
    private f aGH;
    CallbackHandler aGM = new 3(this);
    CallbackHandler aGO = new 4(this);
    private int axr = 1;
    private boolean bQn = false;
    private hlx.module.resources.c bQo;
    private ServerListAdapter bQp;
    private a bQq;
    private SeedAdapter bQr;
    private b bQs;
    private RecommendAppAdapter bQt;
    private hlx.ui.recommendapp.a bQu;
    private String bcC = "";
    private CallbackHandler mCallback = new 2(this);
    private Activity mContext;

    public static MapJsWoodSkinFragment mF(int resourceType) {
        MapJsWoodSkinFragment fragment = new MapJsWoodSkinFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("RES_TYPE", resourceType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(BaseEvent.class, this.aGM);
        EventNotifyCenter.add(com.MCWorld.controller.c.class, this.aGO);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tap_strip, container, false);
        if (savedInstanceState != null) {
            this.axr = savedInstanceState.getInt("RES_TYPE");
        } else {
            this.axr = getArguments().getInt("RES_TYPE");
        }
        d(view);
        RK();
        d(savedInstanceState);
        if (this.bQn) {
            EZ();
        }
        return view;
    }

    public void onSelected(int position) {
        super.onSelected(position);
        this.aGE = true;
    }

    public void onUnSelected(int position) {
        super.onUnSelected(position);
        this.aGE = false;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("RES_TYPE", this.axr);
        switch (this.axr) {
            case 5:
                outState.putParcelable(bQx, this.bQq);
                return;
            case 6:
                outState.putParcelable(bQv, this.bQs);
                return;
            case 7:
                outState.putParcelable(bQw, this.bQu);
                return;
            default:
                outState.putParcelable(aGK, this.aGH);
                return;
        }
    }

    private void d(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.lv_tap_strip);
    }

    private void RK() {
        this.aET = new c((ListView) this.aEq.getRefreshableView());
        this.aET.a(new c.a(this) {
            final /* synthetic */ MapJsWoodSkinFragment bQy;

            {
                this.bQy = this$0;
            }

            public void onLoadData() {
                switch (this.bQy.axr) {
                    case 5:
                        if (this.bQy.bQq != null) {
                            this.bQy.Fc();
                            return;
                        }
                        return;
                    case 6:
                        if (this.bQy.bQs != null) {
                            this.bQy.Fc();
                            return;
                        }
                        return;
                    case 7:
                        if (this.bQy.bQu != null) {
                            this.bQy.Fc();
                            return;
                        }
                        return;
                    default:
                        if (this.bQy.aGH != null) {
                            this.bQy.Fc();
                            return;
                        }
                        return;
                }
            }

            public boolean shouldLoadData() {
                switch (this.bQy.axr) {
                    case 5:
                        if (this.bQy.bQq != null) {
                            if (this.bQy.bQq.more <= 0) {
                                return false;
                            }
                            return true;
                        }
                        break;
                    case 6:
                        if (this.bQy.bQs != null) {
                            if (this.bQy.bQs.more <= 0) {
                                return false;
                            }
                            return true;
                        }
                        break;
                    case 7:
                        if (this.bQy.bQu != null) {
                            if (this.bQy.bQu.more <= 0) {
                                return false;
                            }
                            return true;
                        }
                        break;
                    default:
                        if (this.bQy.aGH != null) {
                            if (this.bQy.aGH.more > 0) {
                                return true;
                            }
                            return false;
                        }
                        break;
                }
                this.bQy.aET.onLoadComplete();
                return false;
            }
        });
        this.aEq.setOnScrollListener(this.aET);
    }

    private void d(Bundle savedInstanceState) {
        this.bQn = false;
        switch (this.axr) {
            case 5:
                if (savedInstanceState != null) {
                    this.bQq = (a) savedInstanceState.getParcelable(bQx);
                }
                this.bQp = new ServerListAdapter(this.mContext);
                if (this.bQq == null || this.bQq.serverList == null) {
                    this.bQn = true;
                } else {
                    this.bQp.a(this.bQq.serverList, true, true);
                }
                this.aEq.setAdapter(this.bQp);
                return;
            case 6:
                if (savedInstanceState != null) {
                    this.bQs = (b) savedInstanceState.getParcelable(bQv);
                }
                this.bQr = new SeedAdapter(this.mContext);
                if (this.bQs == null || this.bQs.seedList == null) {
                    this.bQn = true;
                } else {
                    this.bQr.c(this.bQs.seedList, true);
                }
                this.aEq.setAdapter(this.bQr);
                return;
            case 7:
                if (savedInstanceState != null) {
                    this.bQu = (hlx.ui.recommendapp.a) savedInstanceState.getParcelable(bQw);
                }
                this.bQt = new RecommendAppAdapter(this.mContext);
                if (this.bQu == null || this.bQu.adList == null) {
                    this.bQn = true;
                } else {
                    this.bQt.c(this.bQu.adList, true);
                }
                this.aEq.setAdapter(this.bQt);
                return;
            default:
                if (savedInstanceState != null) {
                    this.aGH = (f) savedInstanceState.getParcelable(aGK);
                }
                this.bQo = new hlx.module.resources.c(this.axr, this.bcC);
                this.aGG = Fe();
                if (this.aGH == null || this.aGH.mapList == null) {
                    this.bQn = true;
                } else {
                    this.aGG.a(this.aGH.mapList, true);
                }
                this.aEq.setAdapter(this.aGG);
                return;
        }
    }

    public void EZ() {
        reload();
    }

    public void onStart() {
        super.onStart();
        this.aGF = true;
    }

    public void onStop() {
        super.onStop();
        this.aGF = false;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aGM);
        EventNotifyCenter.remove(this.aGO);
    }

    private DownAdapter Fe() {
        switch (this.axr) {
            case 2:
                return new JsDownAdapter(this.mContext);
            case 3:
                return new SkinDownAdapter(this.mContext);
            case 4:
                return new WoodDownAdapter(this.mContext);
            default:
                return new DownAdapter(this.mContext);
        }
    }

    private void Fc() {
        switch (this.axr) {
            case 5:
                o.O(this.axr, this.bQq.start, 20);
                return;
            case 6:
                u.T(this.axr, this.bQs.start, 20);
                return;
            case 7:
                q.S(this.axr, this.bQu.start, 20);
                return;
            default:
                this.bQo.ae(this.axr, this.aGH.start, 20);
                return;
        }
    }

    public f Iw() {
        return this.aGH;
    }

    public b RQ() {
        return this.bQs;
    }

    public a RR() {
        return this.bQq;
    }

    public hlx.ui.recommendapp.a RS() {
        return this.bQu;
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        j setter;
        switch (this.axr) {
            case 5:
                if (this.bQp != null && (this.bQp instanceof com.simple.colorful.b)) {
                    setter = new j((ViewGroup) this.aEq.getRefreshableView());
                    setter.a(this.bQp);
                    builder.a(setter);
                    break;
                }
            case 6:
                if (this.bQr != null && (this.bQr instanceof com.simple.colorful.b)) {
                    setter = new j((ViewGroup) this.aEq.getRefreshableView());
                    setter.a(this.bQr);
                    builder.a(setter);
                    break;
                }
            case 7:
                if (this.bQt != null && (this.bQt instanceof com.simple.colorful.b)) {
                    setter = new j((ViewGroup) this.aEq.getRefreshableView());
                    setter.a(this.bQt);
                    builder.a(setter);
                    break;
                }
            default:
                if (this.aGG != null && (this.aGG instanceof com.simple.colorful.b)) {
                    setter = new j((ViewGroup) this.aEq.getRefreshableView());
                    setter.a(this.aGG);
                    builder.a(setter);
                    break;
                }
        }
        builder.aY(R.id.container, R.attr.backgroundDefault);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void kj(int themeId) {
        switch (this.axr) {
            case 5:
                if (this.bQp != null) {
                    this.bQp.setDayMode(d.isDayMode());
                    return;
                }
                return;
            case 6:
                if (this.bQr != null) {
                    this.bQr.setDayMode(d.isDayMode());
                    return;
                }
                return;
            case 7:
                if (this.bQt != null) {
                    this.bQt.setDayMode(d.isDayMode());
                    return;
                }
                return;
            default:
                if (this.aGG != null) {
                    this.aGG.setDayMode(d.isDayMode());
                    return;
                }
                return;
        }
    }

    private void reload() {
        switch (this.axr) {
            case 5:
                o.O(this.axr, 0, 20);
                return;
            case 6:
                u.T(this.axr, 0, 20);
                return;
            case 7:
                q.S(this.axr, 0, 20);
                return;
            default:
                this.bQo.ae(this.axr, 0, 20);
                return;
        }
    }

    private void Ff() {
        switch (this.axr) {
            case 1:
            case 2:
            case 3:
            case 4:
                if (this.aGG != null) {
                    this.aGG.notifyDataSetChanged();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean canScrollVertically(int direction) {
        int scrollY = 0;
        boolean canScroll = false;
        if (!(this.aEq == null || this.aEq.getRefreshableView() == null)) {
            View firstChild = ((ListView) this.aEq.getRefreshableView()).getChildAt(0);
            if (firstChild != null) {
                scrollY = firstChild.getTop();
            }
            canScroll = ViewCompat.canScrollVertically(this.aEq.getRefreshableView(), direction);
        }
        if (canScroll || scrollY < 0) {
            return true;
        }
        return false;
    }

    public void f(int y, long duration) {
        if (this.aEq != null && this.aEq.getRefreshableView() != null) {
            ((ListView) this.aEq.getRefreshableView()).smoothScrollBy(y, (int) duration);
        }
    }
}
