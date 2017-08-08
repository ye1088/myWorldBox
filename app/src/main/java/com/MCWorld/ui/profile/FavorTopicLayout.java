package com.MCWorld.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.TableList;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.http.base.d;
import com.MCWorld.http.base.f;
import com.MCWorld.http.profile.h;
import com.MCWorld.t;
import com.MCWorld.utils.aa;
import com.MCWorld.utils.aa.a;
import com.MCWorld.utils.ba;

public class FavorTopicLayout extends LinearLayout implements f {
    private PullToRefreshListView aHX;
    protected aa aHb;
    private h beU = new h();
    private BaseAdapter beV;
    private TableList beW;
    private long userid = 0;

    public FavorTopicLayout(Context context, long userid) {
        super(context);
        LayoutInflater.from(context).inflate(i.include_default_pulllist, this, true);
        this.userid = userid;
        init();
    }

    private void init() {
        this.beW = new TableList();
        this.aHX = (PullToRefreshListView) findViewById(g.list);
        this.beV = ba.c(getContext(), this.beW);
        this.aHX.setAdapter(this.beV);
        this.aHX.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ FavorTopicLayout beX;

            {
                this.beX = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.beX.reload();
            }
        });
        this.aHb = new aa((ListView) this.aHX.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ FavorTopicLayout beX;

            {
                this.beX = this$0;
            }

            public void onLoadData() {
                this.beX.EY();
            }

            public boolean shouldLoadData() {
                if (this.beX.beW != null) {
                    return this.beX.beW.isHasMore();
                }
                this.beX.aHb.onLoadComplete();
                return false;
            }
        });
        this.aHX.setOnScrollListener(this.aHb);
        this.aHX.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ FavorTopicLayout beX;

            {
                this.beX = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopicItem data = (TopicItem) parent.getAdapter().getItem(position);
                if (data.getCategory() != null) {
                    data.setCategoryName(data.getCategory().getTitle());
                }
                t.a(this.beX.getContext(), data, 0);
            }
        });
        this.beU = new h();
        this.beU.w(this.userid);
        this.beU.a(this);
        this.aHX.setRefreshing(true);
    }

    private void reload() {
        this.beU.aK("0");
        this.beU.setCount(20);
        this.beU.execute();
    }

    public void EY() {
        this.beU.execute();
    }

    public void a(d response) {
    }

    public void b(d response) {
        t.n(getContext(), "访问错误");
        if (this.aHX != null) {
            this.aHX.onRefreshComplete();
        }
        this.aHb.KN();
    }

    public void c(d response) {
        if (response.fe() == 0) {
            this.aHb.onLoadComplete();
            TableList tableList = (TableList) response.getData();
            if (tableList != null) {
                if (this.beW == null) {
                    this.beW = new TableList();
                }
                this.beW.setStart(tableList.getStart());
                this.beW.setHasMore(tableList.getHasMore());
                this.beW.setExtData(tableList.getExtData());
                if (this.aHX != null && this.aHX.isRefreshing()) {
                    this.beW.clear();
                }
                this.beW.addAll(tableList);
                this.beV.notifyDataSetChanged();
            } else {
                return;
            }
        }
        if (this.aHX.isRefreshing()) {
            this.aHX.onRefreshComplete();
        }
    }
}
