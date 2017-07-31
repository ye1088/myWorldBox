package com.huluxia.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.http.profile.k;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseTableActivity;
import com.huluxia.utils.ba;
import com.huluxia.widget.pulltorefresh.PullToRefreshListView;

public class ProfileTopicListActivity extends HTBaseTableActivity {
    private k bhj = new k();
    private long userid = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_topic_list);
        this.userid = getIntent().getLongExtra(FriendListActivity.EXTRA_USER_ID, 0);
        Log.i("ProfileTopicListActivity", Long.toString(this.userid));
        this.aJh = (PullToRefreshListView) findViewById(g.listViewData);
        ej(getResources().getString(this.userid == j.ep().getUserid() ? m.my_topics : m.his_topics));
        super.a(g.listViewData, ba.a((Context) this, true, this.arrayList));
        this.bhj.w(this.userid);
        this.bhj.a(this);
        this.aJh.PT();
    }

    public void a(AdapterView<?> adapterView, View view, int position, long id) {
        TopicItem data = (TopicItem) this.arrayList.get(position);
        if (data.getCategory() != null) {
            data.setCategoryName(data.getCategory().getTitle());
        }
        t.a((Context) this, data);
    }

    public void reload() {
        this.bhj.aK("0");
        this.bhj.setCount(20);
        this.bhj.execute();
    }

    public void EY() {
        this.bhj.execute();
    }
}
