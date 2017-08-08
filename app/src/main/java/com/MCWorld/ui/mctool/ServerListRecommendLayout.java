package com.MCWorld.ui.mctool;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.n;
import com.MCWorld.module.v;
import com.MCWorld.r;
import com.MCWorld.r.a;
import com.MCWorld.ui.itemadapter.server.ServerListAdapter;
import com.MCWorld.utils.c;
import java.util.List;

public class ServerListRecommendLayout extends LinearLayout {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private OnClickListener aJH = new OnClickListener(this) {
        final /* synthetic */ ServerListRecommendLayout bcv;

        {
            this.bcv = this$0;
        }

        public void onClick(View v) {
            r.ck().K_umengEvent(this.bcv.bcu == 0 ? a.iJ : a.iK);
            ((ServerListActivity) this.bcv.context).kU(this.bcv.bcu == 0 ? 1 : 0);
        }
    };
    private ServerListAdapter bcs;
    private com.MCWorld.data.server.a bct;
    private int bcu;
    private Context context;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ServerListRecommendLayout bcv;

        {
            this.bcv = this$0;
        }

        @MessageHandler(message = 260)
        public void onRecvServerInfoData(boolean succ, com.MCWorld.data.server.a info) {
            boolean z = false;
            HLog.debug(this.bcv, "onRecvServerInfoData info = " + info + ", succ = " + succ, new Object[0]);
            this.bcv.aEq.onRefreshComplete();
            this.bcv.aEV.onLoadComplete();
            if (info == null || !succ) {
                HLog.error(this.bcv, "onRecvServerInfoData info is NULL ", new Object[0]);
            } else if (this.bcv.bcs != null && info.isSucc()) {
                if (info.start > 20) {
                    this.bcv.bct.start = info.start;
                    this.bcv.bct.more = info.more;
                    this.bcv.bct.serverList.addAll(info.serverList);
                } else {
                    this.bcv.bct = info;
                }
                ServerListAdapter f = this.bcv.bcs;
                List list = this.bcv.bct.serverList;
                if (this.bcv.bcu == 0) {
                    z = true;
                }
                f.a(list, true, z);
            }
        }
    };

    public ServerListRecommendLayout(Context context) {
        super(context);
        init(context);
    }

    public ServerListRecommendLayout(Context context, int tab_tag) {
        super(context);
        this.context = context;
        this.bcu = tab_tag;
        init(context);
    }

    public ServerListRecommendLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(19)
    public ServerListRecommendLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void a(Context context, ListView listview) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_server_header, null, false);
        listview.addHeaderView(v);
        View item1 = v.findViewById(R.id.item1);
        item1.setTag(Integer.valueOf(R.string.ResMapCate));
        item1.setOnClickListener(this.aJH);
        TextView item2 = (TextView) v.findViewById(R.id.item2);
        item2.setTag(Integer.valueOf(R.string.ResMapRank));
        item2.setOnClickListener(this.aJH);
        IK();
    }

    void IK() {
        TextView v1 = (TextView) ((ListView) this.aEq.getRefreshableView()).findViewById(R.id.item1);
        TextView v2 = (TextView) ((ListView) this.aEq.getRefreshableView()).findViewById(R.id.item2);
        if (this.bcu == 0) {
            v1.setEnabled(false);
            v1.setTextColor(Color.parseColor("#40ac3c"));
            v2.setEnabled(true);
            v2.setTextColor(Color.parseColor("#666666"));
            return;
        }
        v1.setEnabled(true);
        v1.setTextColor(Color.parseColor("#666666"));
        v2.setEnabled(false);
        v2.setTextColor(Color.parseColor("#40ac3c"));
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_server_list, this);
        this.aEq = (PullToRefreshListView) findViewById(R.id.listview);
        this.bcs = new ServerListAdapter((Activity) context);
        a(context, (ListView) this.aEq.getRefreshableView());
        this.aEq.setAdapter(this.bcs);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ ServerListRecommendLayout bcv;

            {
                this.bcv = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (this.bcv.bcu == 0) {
                    int i;
                    v DL = v.DL();
                    if (this.bcv.bct != null) {
                        i = this.bcv.bct.start;
                    } else {
                        i = 0;
                    }
                    DL.am(i, 20);
                } else {
                    v.DL().an(this.bcv.bct != null ? this.bcv.bct.start : 0, 20);
                }
                EventNotifyCenter.notifyEvent(n.class, 272, new Object[0]);
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new c.a(this) {
            final /* synthetic */ ServerListRecommendLayout bcv;

            {
                this.bcv = this$0;
            }

            public void onLoadData() {
                if (this.bcv.bct != null) {
                    if (this.bcv.bcu == 0) {
                        v.DL().am(this.bcv.bct.start, 20);
                    } else {
                        v.DL().an(this.bcv.bct.start, 20);
                    }
                    EventNotifyCenter.notifyEvent(n.class, 272, new Object[0]);
                }
            }

            public boolean shouldLoadData() {
                if (this.bcv.bct == null) {
                    this.bcv.aEV.onLoadComplete();
                    return false;
                } else if (this.bcv.bct.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
        this.aEq.setOnItemClickListener(null);
    }

    public void ce(boolean init) {
        if (init) {
            EventNotifyCenter.add(n.class, this.mCallback);
        } else {
            EventNotifyCenter.remove(this.mCallback);
        }
    }

    public void IC() {
        if (this.bct == null) {
            this.aEq.setRefreshing();
        }
    }

    public void IL() {
        if (this.bct == null) {
            this.aEq.setRefreshing();
        }
    }
}
