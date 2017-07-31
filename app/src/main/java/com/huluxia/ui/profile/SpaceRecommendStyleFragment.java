package com.huluxia.ui.profile;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase$OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.widget.pager.PagerFragment;
import com.huluxia.module.h;
import com.huluxia.module.profile.i;
import com.huluxia.ui.base.BaseLoadingLayout;
import com.huluxia.ui.base.BaseThemeFragment;
import com.huluxia.ui.itemadapter.profile.SpaceRecommendStyleAdapter;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.j;

public class SpaceRecommendStyleFragment extends BaseThemeFragment {
    private static final int PAGE_SIZE = 20;
    private static final String aSd = "ARG_FROM_HOME";
    private static final String bhu = "ARG_PROFILE_INFO";
    private BaseLoadingLayout aIy;
    private ProfileInfo aKG;
    private PullToRefreshGridView bhv;
    private SpaceRecommendStyleAdapter bhw;
    private i bhx;
    private Boolean bhy;
    private CallbackHandler mCallback = new 4(this);

    public static PagerFragment a(ProfileInfo profileInfo, boolean fromHome) {
        Bundle args = new Bundle();
        args.putParcelable(bhu, profileInfo);
        args.putBoolean(aSd, fromHome);
        SpaceRecommendStyleFragment fragment = new SpaceRecommendStyleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            this.aKG = (ProfileInfo) getArguments().get(bhu);
            this.bhy = (Boolean) getArguments().get(aSd);
        }
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        View view = inflater.inflate(b.i.fragment_space_style_recommend, container, false);
        this.aIy = (BaseLoadingLayout) view.findViewById(g.loading_layout);
        this.aIy.setRetryClickListener(new BaseLoadingLayout.b(this) {
            final /* synthetic */ SpaceRecommendStyleFragment bhz;

            {
                this.bhz = this$0;
            }

            public void onRetryClick(View view) {
                this.bhz.reload();
            }
        });
        o(view);
        EventNotifyCenter.add(h.class, this.mCallback);
        this.aIy.Fy();
        reload();
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void o(View view) {
        this.bhv = (PullToRefreshGridView) view.findViewById(g.grid);
        this.bhw = new SpaceRecommendStyleAdapter(getActivity(), this.bhy);
        if (!(this.aKG == null || this.aKG.space == null)) {
            this.bhw.ax(this.aKG.space.id, this.aKG.model);
        }
        this.bhv.setAdapter(this.bhw);
        ((GridView) this.bhv.getRefreshableView()).setSelector(d.transparent);
        ((GridView) this.bhv.getRefreshableView()).setNumColumns(3);
        ((GridView) this.bhv.getRefreshableView()).setStretchMode(2);
        this.bhv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ SpaceRecommendStyleFragment bhz;

            {
                this.bhz = this$0;
            }

            public void onGlobalLayout() {
                this.bhz.bhw.setImageHeight((int) (((double) ((UtilsScreen.getScreenWidth(this.bhz.getActivity()) / 3) - UtilsScreen.dipToPx(this.bhz.getActivity(), 12))) * 1.5d));
                if (VERSION.SDK_INT >= 16) {
                    this.bhz.bhv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    this.bhz.bhv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        this.bhv.setOnRefreshListener(new PullToRefreshBase$OnRefreshListener2<GridView>(this) {
            final /* synthetic */ SpaceRecommendStyleFragment bhz;

            {
                this.bhz = this$0;
            }

            public void onPullDownToRefresh(PullToRefreshBase<GridView> pullToRefreshBase) {
                this.bhz.reload();
            }

            public void onPullUpToRefresh(PullToRefreshBase<GridView> pullToRefreshBase) {
                if (this.bhz.bhx != null && this.bhz.bhx.more > 0) {
                    this.bhz.Fc();
                }
            }
        });
    }

    private void reload() {
        com.huluxia.module.profile.g.Eb().at(0, 20);
    }

    private void Fc() {
        com.huluxia.module.profile.g.Eb().at(this.bhx == null ? 0 : this.bhx.start, 20);
    }

    protected void a(a builder) {
        super.a(builder);
        j setter = new j((ViewGroup) this.bhv.getRefreshableView());
        setter.a(this.bhw);
        builder.a(setter).aY(16908290, c.backgroundDefault);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.bhw.notifyDataSetChanged();
    }
}
