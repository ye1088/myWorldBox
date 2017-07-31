package com.huluxia.ui.profile;

import android.os.Bundle;
import android.support.annotation.y;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.profile.d;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.module.z;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.itemadapter.profile.AuditAdapter;
import com.huluxia.ui.itemadapter.profile.AuditAdapter.AuditReslut;
import com.huluxia.utils.ah;
import com.huluxia.utils.c;
import com.huluxia.utils.c.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.util.ArrayList;

public class StudioAuditActivity extends HTBaseLoadingActivity implements OnClickListener {
    public static final String aDW = "STUDIO_ID";
    private int PAGE_SIZE = 20;
    private int aDX;
    private c aEV;
    private PullToRefreshListView aEq;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ StudioAuditActivity bid;

        {
            this.bid = this$0;
        }

        @MessageHandler(message = 786)
        public void onRecvStudioInfo(boolean succ, d info, int studioId) {
            int i = 8;
            if (this.bid.aDX == studioId) {
                this.bid.aEq.onRefreshComplete();
                this.bid.aEV.onLoadComplete();
                if (!succ || info == null) {
                    if (info != null) {
                        t.n(this.bid.bic, info.msg);
                    }
                    if (this.bid.bib == null || this.bid.bib.getList().size() == 0) {
                        this.bid.FB();
                        return;
                    }
                    return;
                }
                this.bid.FC();
                if (info.start > this.bid.PAGE_SIZE) {
                    this.bid.bib.start = info.start;
                    this.bid.bib.more = info.more;
                    this.bid.bib.getList().addAll(info.getList());
                    this.bid.bia.c(info.getList(), false);
                    return;
                }
                int i2;
                View j = this.bid.mEmptyView;
                if (info.getList().size() == 0) {
                    i2 = 0;
                } else {
                    i2 = 8;
                }
                j.setVisibility(i2);
                Button k = this.bid.aIT;
                if (info.getList().size() != 0) {
                    i = 0;
                }
                k.setVisibility(i);
                this.bid.bib = info;
                this.bid.bia.c(info.getList(), true);
            }
        }

        @MessageHandler(message = 789)
        public void onRecvAuditResult(boolean succ, w info, int position, int opt, int sid) {
            if (this.bid.aDX == sid) {
                this.bid.cn(false);
                if (!succ || info == null) {
                    if (info == null || info.code != 104 || info.msg.equals(this.bid.getString(m.account_lock))) {
                        this.bid.bia.notifyDataSetChanged();
                    } else {
                        this.bid.bia.aw(position, AuditReslut.EXPIRED.getResult());
                    }
                    t.n(this.bid.bic, info != null ? info.msg : this.bid.getString(m.str_network_not_capable));
                    return;
                }
                this.bid.bia.aw(position, opt);
                t.o(this.bid.bic, this.bid.getString(m.submit_data_sucess));
            }
        }

        @MessageHandler(message = 788)
        public void clearAudit(boolean succ, w info) {
            this.bid.cn(false);
            if (!succ || info == null) {
                t.n(this.bid.bic, info != null ? info.msg : this.bid.getString(m.str_network_not_capable));
                return;
            }
            this.bid.mEmptyView.setVisibility(0);
            this.bid.aIT.setVisibility(8);
            t.o(this.bid.bic, this.bid.getString(m.clear_record_sucess));
            this.bid.bia.HA();
            ah.KZ().lm(0);
        }
    };
    private CommonMenuDialog bgQ;
    private AuditAdapter bia;
    private d bib;
    private StudioAuditActivity bic;
    private View mEmptyView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aDX = getIntent().getIntExtra("STUDIO_ID", 0);
        this.bic = this;
        setContentView(i.activity_audit);
        EventNotifyCenter.add(h.class, this.aky);
        this.mEmptyView = findViewById(g.empty);
        Fd();
        Fa();
        Fy();
        reload();
    }

    @y
    private void Fa() {
        this.aEq = (PullToRefreshListView) findViewById(g.listview);
        this.bia = new AuditAdapter(this);
        this.aEq.setAdapter(this.bia);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ StudioAuditActivity bid;

            {
                this.bid = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bid.reload();
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new a(this) {
            final /* synthetic */ StudioAuditActivity bid;

            {
                this.bid = this$0;
            }

            public void onLoadData() {
                if (this.bid.bib != null) {
                    this.bid.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.bid.bib == null) {
                    this.bid.aEV.onLoadComplete();
                    return false;
                } else if (this.bid.bib.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void Fc() {
        z.DO();
        z.U(this.bib.start, this.PAGE_SIZE, this.aDX);
    }

    private void reload() {
        z.DO();
        z.U(0, this.PAGE_SIZE, this.aDX);
    }

    protected void EX() {
        super.EX();
        reload();
    }

    private void Fd() {
        ej(getString(m.audit));
        this.aIs.setVisibility(8);
        this.aIT.setText(m.clear);
        this.aIT.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == g.sys_header_right) {
            JL();
        }
    }

    public void cn(boolean show) {
        ek(getString(m.submit_data));
        cs(show);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void JL() {
        ArrayList<Object> list = new ArrayList();
        list.add(new ResMenuItem(getString(m.clear_record), 0, b.d.locmgr_focus_btn_color));
        this.bgQ = new CommonMenuDialog(this, list, new CommonMenuDialogListener(this) {
            final /* synthetic */ StudioAuditActivity bid;

            {
                this.bid = this$0;
            }

            public void pressMenuById(int inIndex, Object inItem) {
                if (inIndex == 0) {
                    z.DO();
                    z.jq(this.bid.aDX);
                    this.bid.cn(true);
                }
                this.bid.bgQ.dismissDialog();
            }
        }, com.simple.colorful.d.RB(), 1);
        this.bgQ.showMenu(null, null);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.bia != null) {
            this.bia.notifyDataSetChanged();
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (!(this.bia == null || this.aEq == null)) {
            k setter = new j(this.aEq);
            setter.a(this.bia);
            builder.a(setter);
        }
        builder.aY(g.view, b.c.backgroundDefault).i(this.aIU, b.c.backgroundTitleBar).a(this.aIR, b.c.back, 1).a(this.aIR, b.c.backText).a(this.aIT, b.c.backText);
    }
}
