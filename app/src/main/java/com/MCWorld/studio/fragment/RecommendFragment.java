package com.MCWorld.studio.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.data.j;
import com.MCWorld.data.studio.c;
import com.MCWorld.data.studio.g;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.widget.title.TitleBar;
import com.MCWorld.http.base.d;
import com.MCWorld.http.base.f;
import com.MCWorld.http.studio.a;
import com.MCWorld.module.h;
import com.MCWorld.module.y;
import com.MCWorld.module.z;
import com.MCWorld.studio.adapter.RecommendAdapter;
import com.MCWorld.studio.widget.BannerGalleryHeader;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingFragment;
import com.MCWorld.widget.banner.BannerGallery;
import com.MCWorld.widget.dialog.i;
import com.simple.colorful.b;
import com.simple.colorful.setter.k;
import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends BaseLoadingFragment implements f {
    private final String TAG = "StudioFragment";
    protected PullToRefreshListView aEq;
    protected g aGA;
    protected a aGB = new a();
    private int aGC = 1;
    protected TextView aGd;
    private OnClickListener aGg = new 1(this);
    protected Button aGn;
    protected LinearLayout aGw;
    protected BannerGalleryHeader aGx;
    protected BannerGallery aGy;
    protected RecommendAdapter aGz;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ RecommendFragment aGD;

        {
            this.aGD = this$0;
        }

        @MessageHandler(message = 801)
        public void recvStudioRecommendInfo(int tag, boolean succ, g info) {
            this.aGD.aEq.onRefreshComplete();
            if (tag != this.aGD.mType) {
                return;
            }
            if (succ && info != null) {
                this.aGD.aGA = info;
                this.aGD.aGz.c(info.list, true);
                this.aGD.FC();
            } else if (this.aGD.aGA == null) {
                this.aGD.FB();
            }
        }

        @MessageHandler(message = 793)
        public void recvStudioCarouselList(int tag, boolean succ, c info) {
            if (tag != this.aGD.mType) {
                return;
            }
            if (succ) {
                this.aGD.a(info);
                this.aGD.FC();
                return;
            }
            this.aGD.a(null);
        }

        @MessageHandler(message = 776)
        public void onRecvStudioId(boolean succ, y info, long userId, Object ctx) {
            if (!j.ep().ey() || j.ep().getUserid() != userId) {
                this.aGD.aGn.setVisibility(0);
            } else if (!succ || info == null) {
                this.aGD.aGn.setVisibility(8);
            } else if (info.getSid() != 0) {
                this.aGD.aGn.setVisibility(8);
            } else {
                this.aGD.aGn.setVisibility(0);
            }
        }
    };
    private Context mContext;
    protected int mType = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        EventNotifyCenter.add(h.class, this.aky);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studio_common, container, false);
        d(view);
        EQ();
        EZ();
        Fy();
        return view;
    }

    protected void a(TitleBar titleBar) {
        super.a(titleBar);
        titleBar.setLeftLayout(R.layout.include_header);
        titleBar.findViewById(R.id.fl_msg).setVisibility(8);
        this.aGd = (TextView) titleBar.findViewById(R.id.sys_header_back);
        this.aGn = (Button) titleBar.findViewById(R.id.sys_header_right);
        this.aGd.setText("工作室");
        this.aGn.setVisibility(8);
        this.aGn.setText("创建");
        this.aGd.setOnClickListener(this.aGg);
        this.aGn.setOnClickListener(this.aGg);
    }

    private void d(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.lv_list_view);
        this.aGw = new LinearLayout(this.mContext);
    }

    private void EQ() {
        this.aGw.setOrientation(1);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.aGw);
        this.aGz = new RecommendAdapter(this.mContext);
        this.aEq.setAdapter(this.aGz);
        this.aEq.setOnRefreshListener(new 2(this));
        this.aGB.bb(this.aGC);
        this.aGB.a(this);
    }

    private void EZ() {
        z.DO();
        z.jr(this.mType);
        z.DO();
        z.jp(this.mType);
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    private void reload() {
        EZ();
    }

    public void onResume() {
        super.onResume();
        z.DO();
        z.b(j.ep().getUserid(), null);
    }

    public void a(d response) {
    }

    public void b(d response) {
        if (this.aGC == response.fe()) {
            t.n(this.mContext, "网络异常");
        }
    }

    public void c(d response) {
        if (this.aGC != response.fe()) {
            return;
        }
        if (this.aGB.fW()) {
            t.a(this.mContext, null, true);
            return;
        }
        i dia = new i((Activity) this.mContext, null);
        dia.az(getString(R.string.dialog_title_nick_change_comfirm), this.aGB.getMsg());
        dia.gK(getString(R.string.btn_confirm));
        dia.showDialog();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void a(c bannerInfo) {
        if (bannerInfo == null || bannerInfo.list.isEmpty()) {
            this.aGw.removeAllViews();
            return;
        }
        if (this.aGx == null) {
            this.aGx = new BannerGalleryHeader(this.mContext);
            this.aGy = this.aGx.getBannerGallery();
        }
        if (-1 == this.aGw.indexOfChild(this.aGx)) {
            this.aGw.addView(this.aGx, new LayoutParams(-1, -2));
        }
        List<c.a> banners = bannerInfo.list;
        List<com.MCWorld.widget.banner.a> info = new ArrayList();
        info.addAll(banners);
        this.aGy.setIndicatorVisible(false);
        this.aGy.getGallery().setLoader(new 3(this));
        this.aGy.getGallery().setInterval(3000);
        this.aGy.setData(info);
        this.aGy.setOnItemClickListener(new 4(this));
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aGz != null && (this.aGz instanceof b)) {
            k setter = new com.simple.colorful.setter.j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aGz);
            builder.a(setter);
        }
        builder.a(this.aGd, R.attr.backText).a(this.aGd, R.attr.back, 1).j(this.aGd, R.attr.backgroundTitleBarButton).a(this.aGn, R.attr.backText).j(this.aGn, R.attr.backgroundTitleBarButton).aY(R.id.root_view, R.attr.backgroundDefault);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
