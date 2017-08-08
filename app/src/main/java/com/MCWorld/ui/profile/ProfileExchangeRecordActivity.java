package com.MCWorld.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.profile.ExchangeRecord;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.h;
import com.MCWorld.module.profile.b;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.itemadapter.profile.ExchangeRecordItemAdapter;
import com.MCWorld.utils.aa;
import com.MCWorld.utils.aa.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class ProfileExchangeRecordActivity extends HTBaseActivity implements OnItemClickListener {
    private static final int PAGE_SIZE = 20;
    private aa aHb;
    private PullToRefreshListView bgl;
    private ExchangeRecordItemAdapter bgm;
    private b bgn = new b();
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ProfileExchangeRecordActivity bgo;

        {
            this.bgo = this$0;
        }

        @MessageHandler(message = 553)
        public void onRecvExchangeRecord(boolean succ, b record, String msg) {
            if (succ) {
                this.bgo.aHb.onLoadComplete();
                if (record.start > 20) {
                    this.bgo.bgn.start = record.start;
                    this.bgo.bgn.more = record.more;
                    this.bgo.bgm.n(record.userCashList);
                } else {
                    this.bgo.bgn = record;
                    this.bgo.bgm.setData(record.userCashList);
                }
            } else {
                this.bgo.aHb.KN();
                t.n(this.bgo, msg);
            }
            this.bgo.bgl.onRefreshComplete();
            this.bgo.aHb.onLoadComplete();
            this.bgo.cs(false);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_profile_exchange_record);
        Fo();
        EP();
        EventNotifyCenter.add(h.class, this.mCallback);
        if (this.bgn == null || this.bgn.userCashList.size() == 0) {
            reload();
        }
    }

    private void EP() {
        this.aIs.setVisibility(8);
        this.aIR.setVisibility(0);
        ((ImageButton) findViewById(g.sys_header_right_img)).setVisibility(8);
        ej("兑换记录");
    }

    private void Fo() {
        this.bgl = (PullToRefreshListView) findViewById(g.list);
        ((ListView) this.bgl.getRefreshableView()).setSelector(d.transparent);
        this.bgl.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ ProfileExchangeRecordActivity bgo;

            {
                this.bgo = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bgo.reload();
            }
        });
        this.bgm = new ExchangeRecordItemAdapter(this);
        this.bgl.setAdapter(this.bgm);
        this.bgl.setOnItemClickListener(this);
        this.aHb = new aa((ListView) this.bgl.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ ProfileExchangeRecordActivity bgo;

            {
                this.bgo = this$0;
            }

            public void onLoadData() {
                this.bgo.Fc();
            }

            public boolean shouldLoadData() {
                if (this.bgo.bgn == null) {
                    this.bgo.aHb.onLoadComplete();
                    return false;
                } else if (this.bgo.bgn.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.bgl.setOnScrollListener(this.aHb);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void reload() {
        cs(true);
        this.bgn.start = 20;
        com.MCWorld.module.profile.g.Eb().as(0, 20);
    }

    private void Fc() {
        com.MCWorld.module.profile.g.Eb().as(this.bgn.start, 20);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExchangeRecord record = (ExchangeRecord) parent.getAdapter().getItem(position);
        if (record != null) {
            t.a((Context) this, record);
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.bgm != null) {
            k setter = new j(this.bgl);
            setter.a(this.bgm);
            builder.a(setter);
        }
        builder.aY(16908290, c.backgroundDefault);
    }
}
