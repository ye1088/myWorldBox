package com.huluxia.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.module.h;
import com.huluxia.module.profile.d;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.itemadapter.profile.FriendItemAdapter;
import com.huluxia.utils.aa;
import com.huluxia.utils.aa.a;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class FollowingListActivity extends HTBaseLoadingActivity {
    private final int PAGE_SIZE = 20;
    private PullToRefreshListView aEq;
    protected aa aHb;
    private Activity aMn;
    private d beY = null;
    private FriendItemAdapter beZ;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ FollowingListActivity bfb;

        {
            this.bfb = this$0;
        }

        @MessageHandler(message = 664)
        public void onRecvList(boolean succ, d info, int laststart, Context context) {
            if (context == this.bfb.aMn) {
                this.bfb.aEq.onRefreshComplete();
                this.bfb.cs(false);
                if (succ) {
                    this.bfb.aHb.onLoadComplete();
                    if (laststart > 20) {
                        this.bfb.beY.start = info.start;
                        this.bfb.beY.more = info.more;
                        this.bfb.beZ.c(info.friendships, false);
                    } else {
                        this.bfb.beY = info;
                        if (UtilsFunction.empty(info.friendships)) {
                            this.bfb.findViewById(g.rly_show_no_attention).setVisibility(0);
                        } else {
                            this.bfb.findViewById(g.rly_show_no_attention).setVisibility(8);
                        }
                        this.bfb.beZ.c(info.friendships, true);
                    }
                    this.bfb.FC();
                } else if (this.bfb.getCurrentPage() == 0) {
                    this.bfb.FB();
                } else {
                    this.bfb.aHb.KN();
                    t.n(this.bfb.aMn, info == null ? this.bfb.getResources().getString(m.loading_failed_please_retry) : info.msg);
                }
            }
        }
    };
    private long userid = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aMn = this;
        setContentView(i.include_default_pulllist);
        this.userid = getIntent().getLongExtra(FriendListActivity.EXTRA_USER_ID, 0);
        FN();
        Fo();
        EventNotifyCenter.add(h.class, this.mCallback);
        Fy();
        reload();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void FN() {
        ej(getResources().getString(m.my_idol_list));
    }

    private void Fo() {
        this.aEq = (PullToRefreshListView) findViewById(g.list);
        this.beZ = new FriendItemAdapter(this);
        this.aEq.setAdapter(this.beZ);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ FollowingListActivity bfb;

            {
                this.bfb = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bfb.reload();
            }
        });
        this.aHb = new aa((ListView) this.aEq.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ FollowingListActivity bfb;

            {
                this.bfb = this$0;
            }

            public void onLoadData() {
                this.bfb.Fc();
            }

            public boolean shouldLoadData() {
                if (this.bfb.beY == null) {
                    this.bfb.aHb.onLoadComplete();
                    return false;
                } else if (this.bfb.beY.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aHb);
        this.aEq.setOnItemClickListener(null);
    }

    private void reload() {
        com.huluxia.module.profile.g.Eb().b(0, 20, this.userid, this.aMn);
    }

    private void Fc() {
        int start;
        int i = 0;
        if (this.beY != null) {
            start = this.beY.start;
        } else {
            start = 0;
        }
        com.huluxia.module.profile.g Eb = com.huluxia.module.profile.g.Eb();
        if (this.beY != null) {
            i = start;
        }
        Eb.b(i, 20, this.userid, this.aMn);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.beZ != null) {
            this.beZ.notifyDataSetChanged();
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.beZ != null && (this.beZ instanceof b)) {
            k setter = new j(this.aEq);
            setter.a(this.beZ);
            builder.a(setter);
        }
        builder.aY(g.container, c.backgroundDefault);
    }
}
