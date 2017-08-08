package com.MCWorld.ui.bbs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.category.Daren;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.topic.h;
import com.MCWorld.module.topic.k;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.ui.itemadapter.category.DarenItemAdapter;
import com.MCWorld.utils.aa;
import com.MCWorld.utils.aa.a;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;

public class CategoryDarenActivity extends HTBaseLoadingActivity implements OnItemClickListener {
    public static final int PAGE_SIZE = 50;
    private PullToRefreshListView aEq;
    protected aa aHb;
    private DarenRankingTitle aJJ;
    private DarenItemAdapter aJK;
    private h aJL = new h();
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ CategoryDarenActivity aJM;

        {
            this.aJM = this$0;
        }

        @MessageHandler(message = 608)
        public void onRecvDarenList(boolean succ, h info) {
            this.aJM.aEq.onRefreshComplete();
            this.aJM.cs(false);
            if (succ) {
                this.aJM.aHb.onLoadComplete();
                this.aJM.aJL.start = info.start;
                this.aJM.aJL.more = info.more;
                if (info.start > 50) {
                    this.aJM.aJK.c(info.daren, false);
                } else if (UtilsFunction.empty(info.daren)) {
                    this.aJM.FO();
                } else {
                    this.aJM.aJK.c(info.daren, true);
                    this.aJM.aJJ.setUserRanking(info.rank);
                }
                this.aJM.FC();
            } else if (this.aJM.getCurrentPage() == 0) {
                this.aJM.FB();
            } else {
                this.aJM.aHb.KN();
                t.n(this.aJM, info == null ? this.aJM.getResources().getString(m.loading_failed_please_retry) : info.msg);
            }
        }
    };
    private long sm = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.include_default_pulllist);
        this.sm = getIntent().getLongExtra("cat_id", 0);
        FN();
        Fo();
        EventNotifyCenter.add(com.MCWorld.module.h.class, this.mCallback);
        Fy();
        reload();
    }

    private void Fo() {
        this.aEq = (PullToRefreshListView) findViewById(g.list);
        this.aJJ = new DarenRankingTitle(this);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.aJJ);
        this.aJK = new DarenItemAdapter(this, this.aJL.daren);
        this.aEq.setAdapter(this.aJK);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ CategoryDarenActivity aJM;

            {
                this.aJM = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aJM.reload();
            }
        });
        this.aHb = new aa((ListView) this.aEq.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ CategoryDarenActivity aJM;

            {
                this.aJM = this$0;
            }

            public void onLoadData() {
                this.aJM.EY();
            }

            public boolean shouldLoadData() {
                if (this.aJM.aJL == null || UtilsFunction.empty(this.aJM.aJL.daren)) {
                    this.aJM.aHb.onLoadComplete();
                    return false;
                } else if (this.aJM.aJL.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aHb);
        this.aEq.setOnItemClickListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void FN() {
        ej(getResources().getString(m.daren));
        this.aIs.setVisibility(8);
        this.aIT.setVisibility(0);
        this.aIT.setText(getResources().getString(m.introduction));
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CategoryDarenActivity aJM;

            {
                this.aJM = this$0;
            }

            public void onClick(View v) {
                t.al(this.aJM);
            }
        });
    }

    public void reload() {
        k.Ej().c(this.sm, 0, 50);
    }

    public void EY() {
        k.Ej().c(this.sm, this.aJL.start, 50);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Daren data = (Daren) parent.getAdapter().getItem(position);
        if (data != null) {
            t.a((Context) this, data.getDaren().getUserID(), data.getDaren());
        }
    }

    private void FO() {
        final Dialog dialog = new Dialog(this, d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_one, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText("本版新开，暂时没有统计。\n客官下周再来吧~");
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CategoryDarenActivity aJM;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.aJM.finish();
            }
        });
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aJK != null) {
            com.simple.colorful.setter.k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aJK);
            builder.a(setter);
        }
        builder.aY(16908290, c.backgroundDefault).a(this.aJJ);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.aJK != null) {
            this.aJK.notifyDataSetChanged();
        }
    }

    protected void EX() {
        super.EX();
        reload();
    }
}
