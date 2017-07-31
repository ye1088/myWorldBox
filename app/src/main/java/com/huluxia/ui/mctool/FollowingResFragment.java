package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.map.f;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.h;
import com.huluxia.module.z;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.ui.itemadapter.js.JsDownAdapter;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.skin.SkinDownAdapter;
import com.huluxia.ui.itemadapter.wood.WoodDownAdapter;
import com.huluxia.utils.c;
import com.huluxia.utils.c.a;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;

public class FollowingResFragment extends BaseLoadingFragment {
    private static final String aGI = "RES_TYPE";
    private final int PAGE_SIZE = 20;
    private String TAG = "MapJsWoodSkinFragment";
    private c aEV;
    private PullToRefreshListView aEq;
    private DownAdapter aGG;
    private f aGH;
    private CallbackHandler aGM = new 4(this);
    private CallbackHandler aGO = new 5(this);
    private int axr = 1;
    private View baT;
    private boolean baU;
    private boolean baV = false;
    private CallbackHandler mCallback = new 3(this);
    private Activity mContext;

    public static FollowingResFragment kQ(int resourceType) {
        FollowingResFragment fragment = new FollowingResFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("RES_TYPE", resourceType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        if (savedInstanceState != null) {
            this.axr = savedInstanceState.getInt("RES_TYPE");
        } else {
            this.axr = getArguments().getInt("RES_TYPE");
        }
        EventNotifyCenter.add(h.class, this.mCallback);
        EventNotifyCenter.add(BaseEvent.class, this.aGM);
        EventNotifyCenter.add(com.huluxia.controller.c.class, this.aGO);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tap_strip, container, false);
        d(view);
        EQ();
        EZ();
        Fy();
        return view;
    }

    protected void a(TitleBar titleBar) {
        titleBar.setVisibility(8);
    }

    public void onSelected(int position) {
        super.onSelected(position);
        this.baU = true;
    }

    public void onUnSelected(int position) {
        super.onUnSelected(position);
        this.baU = false;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("RES_TYPE", this.axr);
    }

    private void d(View view) {
        this.baT = view.findViewById(R.id.ll_no_resource);
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.lv_tap_strip);
    }

    private void EQ() {
        this.aGG = Fe();
        this.aEq.setAdapter(this.aGG);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ FollowingResFragment baW;

            {
                this.baW = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.baW.reload();
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new a(this) {
            final /* synthetic */ FollowingResFragment baW;

            {
                this.baW = this$0;
            }

            public void onLoadData() {
                if (this.baW.aGH != null) {
                    this.baW.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.baW.aGH == null) {
                    this.baW.aEV.onLoadComplete();
                    return false;
                } else if (this.baW.aGH.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
    }

    public void EZ() {
        z.DO();
        z.X(this.axr, 0, 20);
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    public void onStart() {
        super.onStart();
        this.baV = true;
    }

    public void onStop() {
        super.onStop();
        this.baV = false;
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
        z.DO();
        z.X(this.axr, this.aGH.start, 20);
    }

    public f Iw() {
        return this.aGH;
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aGG != null && (this.aGG instanceof b)) {
            j setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aGG);
            builder.a(setter);
        }
        builder.aY(R.id.container, R.attr.backgroundDefault).bc(R.id.iv_no_resource, R.attr.ic_atten_no_resourc).ba(R.id.tv_no_resource_upline_tip, 16843282).ba(R.id.tv_no_resource_downline_tip, 16843282);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void kj(int themeId) {
        if (this.aGG != null) {
            this.aGG.setDayMode(d.isDayMode());
        }
    }

    private void reload() {
        EZ();
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
}
