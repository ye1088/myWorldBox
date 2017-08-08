package com.MCWorld.ui.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.TableList;
import com.MCWorld.http.base.d;
import com.MCWorld.t;
import com.MCWorld.widget.pulltorefresh.PullToRefreshListView;
import com.MCWorld.widget.pulltorefresh.PullToRefreshListView.Mode;
import com.MCWorld.widget.pulltorefresh.PullToRefreshListView.b;
import com.MCWorld.widget.pulltorefresh.PullToRefreshListView.c;
import java.util.ArrayList;

public abstract class HTBaseTableActivity extends HTBaseActivity {
    protected PullToRefreshListView aJh;
    protected BaseAdapter aJi;
    Runnable aJj = new Runnable(this) {
        final /* synthetic */ HTBaseTableActivity aJk;

        {
            this.aJk = this$0;
        }

        public void run() {
            int index;
            int last = this.aJk.aJh.getLastVisiblePosition();
            int headerCount = this.aJk.aJh.getHeaderViewsCount();
            if (headerCount > 1) {
                index = last - headerCount;
            } else {
                index = last;
            }
            View bottom = this.aJk.aJh.getChildAt(index);
            if (bottom == null || bottom.getBottom() > this.aJk.aJh.getHeight()) {
                this.aJk.aJh.dq(false);
            } else {
                this.aJk.aJh.dq(true);
            }
        }
    };
    protected ArrayList<Object> arrayList = new ArrayList();

    public abstract void EY();

    public abstract void a(AdapterView<?> adapterView, View view, int i, long j);

    public abstract void reload();

    protected void a(int resid, BaseAdapter _tableAdapter) {
        a(resid, _tableAdapter, true);
    }

    protected void a(int resid, BaseAdapter _tableAdapter, boolean enableLoadMore) {
        this.aJi = _tableAdapter;
        this.aJh = (PullToRefreshListView) findViewById(resid);
        this.aJh.setAdapter(this.aJi);
        this.aJh.setOnRefreshListener(new b(this) {
            final /* synthetic */ HTBaseTableActivity aJk;

            {
                this.aJk = this$0;
            }

            public void onRefresh() {
                this.aJk.reload();
            }
        });
        if (enableLoadMore) {
            this.aJh.setOnRefreshListener(new c(this) {
                final /* synthetic */ HTBaseTableActivity aJk;

                {
                    this.aJk = this$0;
                }

                public void FK() {
                    this.aJk.EY();
                }

                public void FL() {
                    this.aJk.reload();
                }
            });
        }
        this.aJh.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ HTBaseTableActivity aJk;

            {
                this.aJk = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int fristCount = this.aJk.aJh.getHeaderViewsCount();
                if (position < fristCount + this.aJk.aJi.getCount() && position >= fristCount) {
                    this.aJk.a(parent, view, position - fristCount, id);
                }
            }
        });
    }

    public void a(d response) {
    }

    public void b(d response) {
        if (response.fe() == 0) {
            t.n(this, getResources().getString(m.load_failed_please_retry));
            if (this.aJh != null) {
                this.aJh.onRefreshComplete();
            }
        }
    }

    public void c(d response) {
        if (response.fe() == 0) {
            TableList tableList = (TableList) response.getData();
            if (tableList != null) {
                boolean hasMore = tableList.getHasMore();
                if (this.aJh.getCurrentMode() == Mode.PULL_FROM_START) {
                    this.arrayList.clear();
                    this.aJh.onRefreshComplete();
                }
                this.aJh.setHasMore(hasMore);
                this.arrayList.addAll(tableList);
                this.aJi.notifyDataSetChanged();
                this.aJh.post(this.aJj);
            }
        }
    }
}
