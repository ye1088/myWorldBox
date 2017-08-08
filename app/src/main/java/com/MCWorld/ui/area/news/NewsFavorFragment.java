package com.MCWorld.ui.area.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.module.h;
import com.MCWorld.module.news.c;
import com.MCWorld.module.news.k;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingFragment;
import com.MCWorld.ui.itemadapter.news.NewsDefaultItemAdapter;
import com.MCWorld.utils.aa;
import com.MCWorld.utils.aa.a;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;

public class NewsFavorFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private static final String aHW = "ARG_USER_ID";
    private PullToRefreshListView aHX;
    private NewsDefaultItemAdapter aHY;
    private k aHZ = new k();
    private aa aHb;
    private View aIa;
    private CallbackHandler mCallback = new 4(this);
    private long pM;

    public static NewsFavorFragment bj(long userId) {
        NewsFavorFragment fragment = new NewsFavorFragment();
        Bundle args = new Bundle();
        args.putLong(aHW, userId);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(h.class, this.mCallback);
        if (getArguments() != null) {
            this.pM = getArguments().getLong(aHW);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.aIa = inflater.inflate(i.include_default_pulllist, container, false);
        Fo();
        Fy();
        reload();
        cq(false);
        return this.aIa;
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void Fo() {
        this.aHX = (PullToRefreshListView) this.aIa.findViewById(g.list);
        this.aHY = new NewsDefaultItemAdapter(getActivity(), this.aHZ.list);
        this.aHX.setAdapter(this.aHY);
        this.aHX.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ NewsFavorFragment aIb;

            {
                this.aIb = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aIb.reload();
            }
        });
        this.aHX.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ NewsFavorFragment aIb;

            {
                this.aIb = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                c item = (c) parent.getAdapter().getItem(position);
                if (item != null) {
                    t.a(this.aIb.getActivity(), item);
                }
            }
        });
        this.aHb = new aa((ListView) this.aHX.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ NewsFavorFragment aIb;

            {
                this.aIb = this$0;
            }

            public void onLoadData() {
                this.aIb.Fc();
            }

            public boolean shouldLoadData() {
                if (this.aIb.aHZ == null) {
                    this.aIb.aHb.onLoadComplete();
                    return false;
                } else if (this.aIb.aHZ.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aHX.setOnScrollListener(this.aHb);
    }

    private void Fc() {
        com.MCWorld.module.news.i.Ea().e(this.aHZ.start, this.pM);
    }

    private void reload() {
        com.MCWorld.module.news.i.Ea().e(0, this.pM);
    }

    protected void EX() {
        super.EX();
        reload();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aHY != null && (this.aHY instanceof b)) {
            j setter = new j((ViewGroup) this.aHX.getRefreshableView());
            setter.a(this.aHY);
            builder.a(setter);
        }
    }
}
