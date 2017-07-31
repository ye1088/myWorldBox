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
import com.huluxia.http.profile.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseTableActivity;
import com.huluxia.utils.ba;
import com.huluxia.widget.pulltorefresh.PullToRefreshListView;

public class ProfileFavoriteListActivity extends HTBaseTableActivity {
    private h bgF = new h();
    private long userid = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_topic_list);
        this.userid = getIntent().getLongExtra(FriendListActivity.EXTRA_USER_ID, 0);
        Log.i("ProfileFavoriteListActivity", Long.toString(this.userid));
        this.aJh = (PullToRefreshListView) findViewById(g.listViewData);
        ej(getResources().getString(this.userid == j.ep().getUserid() ? m.my_favorite : m.his_favorite));
        super.a(g.listViewData, ba.c((Context) this, this.arrayList));
        this.bgF.w(this.userid);
        this.bgF.a(this);
        this.aJh.PT();
    }

    public void a(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        TopicItem data = (TopicItem) this.arrayList.get(position);
        if (data.getCategory() != null) {
            data.setCategoryName(data.getCategory().getTitle());
        }
        t.a((Context) this, data);
    }

    public void reload() {
        this.bgF.aK("0");
        this.bgF.setCount(20);
        this.bgF.execute();
    }

    public void EY() {
        this.bgF.execute();
    }
}
