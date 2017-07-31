package com.huluxia.ui.area.news;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.h;
import com.huluxia.module.news.k;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.ui.itemadapter.news.NewsListAdapter;
import com.huluxia.utils.aa;
import com.huluxia.utils.al;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.j;

public class ResourceNewsFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private static final String aIc = "RESOURCE_NEWS_DATA";
    private PullToRefreshListView aEq;
    private k aHZ = new k();
    private aa aHb;
    private NewsListAdapter aId;
    private MsgTipReceiver aIe;
    private ClearMsgReceiver aIf;
    private TextView aIg;
    private OnClickListener aIh = new 5(this);
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ResourceNewsFragment aIi;

        {
            this.aIi = this$0;
        }

        @MessageHandler(message = 1024)
        public void onRecvNewsList(boolean succ, k info) {
            this.aIi.aEq.onRefreshComplete();
            if (!succ || this.aIi.aId == null) {
                this.aIi.aHb.KN();
                if (this.aIi.getCurrentPage() == 0) {
                    this.aIi.FB();
                    return;
                } else {
                    t.n(this.aIi.getActivity(), (info != null ? info.msg : "数据请求失败") + ",请下拉刷新重试");
                    return;
                }
            }
            this.aIi.FC();
            this.aIi.aHb.onLoadComplete();
            if (info.start > 20) {
                this.aIi.aHZ.start = info.start;
                this.aIi.aHZ.more = info.more;
                this.aIi.aHZ.list.addAll(info.list);
            } else {
                this.aIi.aHZ = info;
            }
            this.aIi.aId.a(this.aIi.aHZ.list, true);
            this.aIi.aId.notifyDataSetChanged();
        }
    };
    private Activity mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(h.class, this.mCallback);
        this.aIe = new MsgTipReceiver(this);
        this.aIf = new ClearMsgReceiver(this);
        Fq();
        this.mContext = getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(i.include_resource_game_recommend, container, false);
        e(view);
        view.findViewById(g.tv_load).setVisibility(8);
        if (savedInstanceState == null) {
            Fy();
            com.huluxia.module.news.i.Ea().ar(0, 20);
        } else {
            this.aHZ = (k) savedInstanceState.getParcelable(aIc);
            this.aId.notifyDataSetChanged();
        }
        return view;
    }

    protected void a(TitleBar titleBar) {
        titleBar.setLeftLayout(i.home_left_btn);
        titleBar.setRightLayout(i.home_right_btn);
        this.aIg = (TextView) titleBar.findViewById(g.tv_msg);
        titleBar.findViewById(g.img_msg).setOnClickListener(this.aIh);
        ((TextView) this.aIw.findViewById(g.header_title)).setText("资讯");
    }

    private void e(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(g.game_listview);
        this.aId = al.b(this.mContext, this.aHZ.list);
        this.aEq.setAdapter(this.aId);
        ((ListView) this.aEq.getRefreshableView()).setSelector(getResources().getDrawable(d.transparent));
        this.aEq.setOnRefreshListener(new 1(this));
        this.aHb = new aa((ListView) this.aEq.getRefreshableView());
        this.aHb.a(new 2(this));
        this.aHb.setParentOnScrollListener(new 3(this));
        this.aEq.setOnScrollListener(this.aHb);
    }

    protected void EX() {
        super.EX();
        com.huluxia.module.news.i.Ea().ar(0, 20);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(aIc, this.aHZ);
    }

    public void onDestroy() {
        super.onDestroy();
        Fp();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void Fp() {
        if (this.aIe != null) {
            com.huluxia.service.i.unregisterReceiver(this.aIe);
        }
        if (this.aIf != null) {
            com.huluxia.service.i.unregisterReceiver(this.aIf);
        }
    }

    private void Fq() {
        com.huluxia.service.i.e(this.aIe);
        com.huluxia.service.i.f(this.aIf);
    }

    protected void Fr() {
        MsgCounts msgCounts = HTApplication.bM();
        long allCount = msgCounts == null ? 0 : msgCounts.getAll();
        if (allCount > 0) {
            this.aIg.setVisibility(0);
            if (allCount > 99) {
                this.aIg.setText("99+");
                return;
            } else {
                this.aIg.setText(String.valueOf(msgCounts.getAll()));
                return;
            }
        }
        this.aIg.setVisibility(8);
    }

    protected void Fs() {
        this.aIg.setVisibility(8);
    }

    protected void a(a builder) {
        super.a(builder);
        if (this.aId != null) {
            com.simple.colorful.setter.k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aId);
            builder.a(setter);
        }
        builder.aY(g.fragment_content, c.backgroundDefault).a((TextView) FD().findViewById(g.header_title), 16842809).j(FD().findViewById(g.img_msg), c.backgroundTitleBarButton).c((ImageView) FD().findViewById(g.img_msg), c.drawableTitleMsg).a((TextView) FD().findViewById(g.header_title), c.drawableTitleLogo, 1);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.aId != null) {
            this.aId.notifyDataSetChanged();
        }
    }
}
