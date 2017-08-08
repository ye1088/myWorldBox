package com.MCWorld.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.j;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.http.profile.h;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseTableActivity;
import com.MCWorld.utils.ba;
import com.MCWorld.widget.pulltorefresh.PullToRefreshListView;

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
