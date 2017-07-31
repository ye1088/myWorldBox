package hlx.ui.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.n;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.ad;
import com.huluxia.utils.c;
import com.huluxia.utils.c.a;
import com.simple.colorful.setter.j;
import hlx.module.news.b;
import hlx.module.news.d;
import hlx.ui.itemadapter.news.NewsAdapter;

public class NewsPageFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private c aET;
    private PullToRefreshListView aEq;
    private TextView aIg;
    private OnClickListener aIh = new OnClickListener(this) {
        final /* synthetic */ NewsPageFragment ccg;

        {
            this.ccg = this$0;
        }

        public void onClick(View v) {
            if (v.getId() == R.id.img_msg) {
                t.a(this.ccg.getActivity(), HTApplication.bM());
            }
        }
    };
    private NewsImageTitle cca;
    private NewsAdapter ccb;
    private hlx.module.news.c ccc;
    private MsgTipReceiver ccd;
    private ClearMsgReceiver cce;
    private TextView ccf;
    private CallbackHandler mCallback;
    private View view;

    protected class ClearMsgReceiver extends BroadcastReceiver {
        final /* synthetic */ NewsPageFragment ccg;

        protected ClearMsgReceiver(NewsPageFragment this$0) {
            this.ccg = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.ccg.Fs();
        }
    }

    protected class MsgTipReceiver extends BroadcastReceiver {
        final /* synthetic */ NewsPageFragment ccg;

        protected MsgTipReceiver(NewsPageFragment this$0) {
            this.ccg = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.ccg.Fr();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mCallback = new a(this);
        EventNotifyCenter.add(n.class, this.mCallback);
        this.ccd = new MsgTipReceiver(this);
        this.cce = new ClearMsgReceiver(this);
        this.view = inflater.inflate(R.layout.fragment_news, container, false);
        this.ccf = (TextView) this.view.findViewById(R.id.tvNewNetErrorTips);
        j(this.view);
        Fq();
        Fy();
        reload();
        return this.view;
    }

    protected void EX() {
        super.EX();
        reload();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (this.aEq != null && this.cca != null) {
                this.cca.startAnimation();
            }
        } else if (this.aEq != null && this.cca != null) {
            this.cca.Gi();
        }
    }

    private void Fp() {
        if (this.ccd != null) {
            i.unregisterReceiver(this.ccd);
        }
        if (this.cce != null) {
            i.unregisterReceiver(this.cce);
        }
    }

    private void Fq() {
        i.e(this.ccd);
        i.f(this.cce);
    }

    public void onDestroy() {
        super.onDestroy();
        Fp();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void a(b info) {
        if (this.cca == null) {
            this.cca = new NewsImageTitle(getActivity());
            ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.cca);
        }
        this.cca.setData(info);
    }

    protected void a(TitleBar titleBar) {
        titleBar.setLeftLayout(R.layout.home_left_btn);
        titleBar.setRightLayout(R.layout.home_right_btn);
        this.aIg = (TextView) titleBar.findViewById(R.id.tv_msg);
        titleBar.findViewById(R.id.img_msg).setOnClickListener(this.aIh);
        ((TextView) this.aIw.findViewById(R.id.header_title)).setText("资讯");
    }

    private void j(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.list);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ NewsPageFragment ccg;

            {
                this.ccg = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.ccg.reload();
            }
        });
        ((ListView) this.aEq.getRefreshableView()).setSelector(R.drawable.transparent);
        this.ccb = new NewsAdapter(getActivity());
        this.cca = new NewsImageTitle(getActivity());
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.cca);
        this.aEq.setAdapter(this.ccb);
        this.aET = new c((ListView) this.aEq.getRefreshableView());
        this.aET.a(new a(this) {
            final /* synthetic */ NewsPageFragment ccg;

            {
                this.ccg = this$0;
            }

            public void onLoadData() {
                if (this.ccg.ccc != null) {
                    this.ccg.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.ccg.ccc == null) {
                    this.ccg.aET.onLoadComplete();
                    return false;
                } else if (this.ccg.ccc.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aET);
        this.aEq.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ NewsPageFragment ccg;

            {
                this.ccg = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hlx.module.news.c.a news = (hlx.module.news.c.a) parent.getAdapter().getItem(position);
                if (news == null) {
                    return;
                }
                if (news.type == 0) {
                    t.a(this.ccg.getActivity(), news.postid, false);
                    r.ck().j(hlx.data.tongji.a.bOw, news.title != null ? news.title : "null");
                } else if (news.type == 1 && !ad.empty(news.newsurl)) {
                    t.q(this.ccg.getActivity(), news.newsurl);
                    r.ck().j(hlx.data.tongji.a.bOx, news.title != null ? news.title : "null");
                }
            }
        });
    }

    private void Fc() {
        d.ar(this.ccc == null ? 0 : this.ccc.start, 20);
    }

    private void reload() {
        d.ar(0, 20);
        d.TS();
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

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.ccb != null) {
            j setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.ccb);
            builder.a(setter);
        }
        builder.i(this.view, R.attr.backgroundDefault).a((TextView) FD().findViewById(R.id.header_title), 16842809).c((ImageView) FD().findViewById(R.id.sys_header_left_img), R.attr.drawableTitleLogo).j(FD().findViewById(R.id.img_msg), R.attr.backgroundTitleBarButton).c((ImageView) FD().findViewById(R.id.img_msg), R.attr.drawableTitleMsg).a((TextView) FD().findViewById(R.id.header_title), R.attr.drawableTitleLogo, 1).a(this.cca);
    }
}
