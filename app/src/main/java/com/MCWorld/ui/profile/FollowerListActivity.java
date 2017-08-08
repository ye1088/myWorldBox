package com.MCWorld.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.h;
import com.MCWorld.module.profile.d;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.ui.itemadapter.profile.FriendItemAdapter;
import com.MCWorld.utils.aa;
import com.MCWorld.utils.aa.a;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class FollowerListActivity extends HTBaseLoadingActivity {
    private final int PAGE_SIZE = 20;
    private PullToRefreshListView aEq;
    protected aa aHb;
    private Activity aMn;
    private d beY = null;
    private FriendItemAdapter beZ;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ FollowerListActivity bfa;

        {
            this.bfa = this$0;
        }

        @MessageHandler(message = 663)
        public void onRecvList(boolean succ, d info, int laststart, Context context) {
            if (context == this.bfa.aMn) {
                this.bfa.aEq.onRefreshComplete();
                this.bfa.cs(false);
                if (succ) {
                    this.bfa.aHb.onLoadComplete();
                    if (laststart > 20) {
                        this.bfa.beY.start = info.start;
                        this.bfa.beY.more = info.more;
                        this.bfa.beZ.c(info.friendships, false);
                    } else {
                        this.bfa.beY = info;
                        if (UtilsFunction.empty(info.friendships)) {
                            this.bfa.findViewById(g.rly_show_no_fans).setVisibility(0);
                        } else {
                            this.bfa.findViewById(g.rly_show_no_fans).setVisibility(8);
                        }
                        this.bfa.beZ.c(info.friendships, true);
                    }
                    this.bfa.FC();
                } else if (this.bfa.getCurrentPage() == 0) {
                    this.bfa.FB();
                } else {
                    this.bfa.aHb.KN();
                    t.n(this.bfa.aMn, info == null ? this.bfa.getResources().getString(m.loading_failed_please_retry) : info.msg);
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
        ej(getResources().getString(m.my_fans_list));
    }

    private void Fo() {
        this.aEq = (PullToRefreshListView) findViewById(g.list);
        this.beZ = new FriendItemAdapter(this);
        this.aEq.setAdapter(this.beZ);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ FollowerListActivity bfa;

            {
                this.bfa = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bfa.reload();
            }
        });
        this.aHb = new aa((ListView) this.aEq.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ FollowerListActivity bfa;

            {
                this.bfa = this$0;
            }

            public void onLoadData() {
                this.bfa.Fc();
            }

            public boolean shouldLoadData() {
                if (this.bfa.beY == null) {
                    this.bfa.aHb.onLoadComplete();
                    return false;
                } else if (this.bfa.beY.more > 0) {
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
        com.MCWorld.module.profile.g.Eb().a(0, 20, this.userid, this.aMn);
    }

    private void Fc() {
        int start;
        int i = 0;
        if (this.beY != null) {
            start = this.beY.start;
        } else {
            start = 0;
        }
        com.MCWorld.module.profile.g Eb = com.MCWorld.module.profile.g.Eb();
        if (this.beY != null) {
            i = start;
        }
        Eb.a(i, 20, this.userid, this.aMn);
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
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.beZ);
            builder.a(setter);
        }
        builder.aY(g.container, c.backgroundDefault);
    }
}
