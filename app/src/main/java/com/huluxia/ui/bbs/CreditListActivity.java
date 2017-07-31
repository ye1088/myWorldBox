package com.huluxia.ui.bbs;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.i;
import com.huluxia.data.topic.ScoreItem;
import com.huluxia.http.bbs.topic.g;
import com.huluxia.ui.base.HTBaseTableActivity;
import com.huluxia.ui.itemadapter.topic.CreditRecordItemAdapter;
import com.huluxia.widget.pulltorefresh.PullToRefreshListView;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class CreditListActivity extends HTBaseTableActivity {
    private static final int PAGE_SIZE = 20;
    private CreditRecordItemAdapter aKm = null;
    private boolean aKn;
    private g aKo = new g();
    private long ss;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_topic_list);
        FN();
        if (savedInstanceState == null) {
            this.ss = getIntent().getLongExtra("post_id", 0);
            this.aKn = getIntent().getBooleanExtra("isTopic", false);
        } else {
            this.ss = savedInstanceState.getLong("post_id");
            this.aKn = savedInstanceState.getBoolean("isTopic");
        }
        this.aJh = (PullToRefreshListView) findViewById(b.g.listViewData);
        this.aKm = new CreditRecordItemAdapter(this, this.arrayList);
        super.a(b.g.listViewData, this.aKm, true);
        this.aKo.x(this.ss);
        this.aKo.I(this.aKn);
        this.aKo.aK("0");
        this.aKo.setCount(20);
        this.aKo.bb(0);
        this.aKo.a(this);
        this.aJh.PT();
    }

    private void FN() {
        ej("打赏列表");
        this.aIs.setVisibility(8);
    }

    public void a(AdapterView<?> parent, View view, int position, long id) {
        ScoreItem data = (ScoreItem) parent.getAdapter().getItem(position);
    }

    public void reload() {
        this.aKo.aK("0");
        this.aKo.setCount(20);
        this.aKo.execute();
    }

    public void EY() {
        this.aKo.execute();
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong("post_id", this.ss);
        outState.putBoolean("isTopic", this.aKn);
    }

    protected void a(a builder) {
        super.a(builder);
        if (this.aKm != null) {
            k setter = new j(this.aJh);
            setter.a(this.aKm);
            builder.a(setter);
        }
        builder.aY(b.g.container, c.backgroundDefault);
    }
}
