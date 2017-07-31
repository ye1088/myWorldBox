package com.huluxia.ui.bbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.http.bbs.category.d;
import com.huluxia.ui.base.HTBaseTableActivity;
import com.huluxia.ui.itemadapter.category.CategoryVoteItemAdapter;
import com.huluxia.widget.pulltorefresh.PullToRefreshListView;
import com.simple.colorful.a.a;

public class CategoryVoteActivity extends HTBaseTableActivity {
    private static final int PAGE_SIZE = 20;
    private CategoryVoteItemAdapter aJY = null;
    private d aJZ = new d();
    private VoteTitle aKa;
    private BroadcastReceiver aKb = new BroadcastReceiver(this) {
        final /* synthetic */ CategoryVoteActivity aKc;

        {
            this.aKc = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            if (this.aKc.aJh != null) {
                this.aKc.aJh.PT();
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_topic_list);
        com.huluxia.service.i.c(this.aKb);
        ej(getResources().getString(m.vote_cate));
        this.aKa = new VoteTitle(this);
        this.aJh = (PullToRefreshListView) findViewById(g.listViewData);
        this.aJh.addHeaderView(this.aKa);
        this.aJY = new CategoryVoteItemAdapter(this, this.arrayList);
        super.a(g.listViewData, this.aJY, true);
        this.aJZ.a(this);
        this.aJh.PT();
    }

    public void cn(boolean isLoading) {
        ek("正在投票,请稍候");
        cs(isLoading);
    }

    public void a(AdapterView<?> adapterView, View view, int position, long id) {
    }

    public void reload() {
        this.aJZ.aK("0");
        this.aJZ.setCount(20);
        this.aJZ.bb(0);
        this.aJZ.execute();
    }

    public void EY() {
        this.aJZ.execute();
    }

    protected void a(a builder) {
        super.a(builder);
        builder.aY(g.container, c.backgroundDefault).j(this.aKa.findViewById(g.topic_top), c.listSelector).i(this.aKa.findViewById(g.title), c.backgroundDim).i(this.aKa.findViewById(g.split_title), c.splitColorDim).a((TextView) this.aKa.findViewById(g.title), 16842808);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.aJY != null) {
            this.aJY.notifyDataSetChanged();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.aKb != null) {
            com.huluxia.service.i.unregisterReceiver(this.aKb);
            this.aKb = null;
        }
    }
}
