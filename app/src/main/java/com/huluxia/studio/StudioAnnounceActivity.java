package com.huluxia.studio;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.studio.b;
import com.huluxia.data.studio.b.a;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.k;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.module.z;
import com.huluxia.studio.adapter.AnnouncementAdapter;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.utils.c;
import com.simple.colorful.d;

public class StudioAnnounceActivity extends HTBaseLoadingActivity {
    public static final String aDV = "USER_ROLE";
    public static final String aDW = "STUDIO_ID";
    private static final int aEQ = 20;
    private String TAG = "StudioAnnounceActivity";
    private int aDX;
    private int aDY;
    protected AnnouncementAdapter aER;
    protected b aES;
    protected c aET;
    protected PullToRefreshListView aEq;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ StudioAnnounceActivity aEU;

        {
            this.aEU = this$0;
        }

        @MessageHandler(message = 806)
        public void recvStudioAnnounceInfo(int tag, boolean succ, b info) {
            this.aEU.aEq.onRefreshComplete();
            this.aEU.aET.onLoadComplete();
            if (tag != this.aEU.aDX) {
                return;
            }
            if (info == null || !succ) {
                if (info != null) {
                    t.n(this.aEU.mContext, info.msg);
                }
                if (this.aEU.aES == null) {
                    this.aEU.FB();
                    return;
                }
                return;
            }
            if (info.start <= 20) {
                this.aEU.aES = info;
                if (UtilsFunction.empty(this.aEU.aES.list)) {
                    this.aEU.findViewById(R.id.rly_show_no_announce).setVisibility(0);
                } else {
                    this.aEU.findViewById(R.id.rly_show_no_announce).setVisibility(8);
                }
            } else if (this.aEU.aES != null) {
                this.aEU.aES.start = info.start;
                this.aEU.aES.more = info.more;
                this.aEU.aES.list.addAll(info.list);
            } else {
                return;
            }
            this.aEU.aER.c(this.aEU.aES.list, true);
            this.aEU.FC();
        }

        @MessageHandler(message = 808)
        public void deleteAnnounceDetailInList(int tag, a item) {
            if (tag == this.aEU.aDX && this.aEU.aER != null) {
                this.aEU.aER.a(item);
            }
        }

        @MessageHandler(message = 805)
        public void recvIssueResult(int tag, boolean succ, w info) {
            if (tag == this.aEU.aDX && succ) {
                this.aEU.reload();
            }
        }
    };
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_studio_announce);
        this.mContext = this;
        EventNotifyCenter.add(h.class, this.aky);
        if (savedInstanceState != null) {
            this.aDX = savedInstanceState.getInt("STUDIO_ID", 0);
            this.aDY = savedInstanceState.getInt(aDV, 3);
        } else {
            this.aDX = getIntent().getIntExtra("STUDIO_ID", 0);
            this.aDY = getIntent().getIntExtra(aDV, 3);
        }
        initView();
        EP();
        EQ();
        EZ();
        Fy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("STUDIO_ID", this.aDX);
        outState.putInt(aDV, this.aDY);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void initView() {
        this.aEq = (PullToRefreshListView) findViewById(R.id.lv_announce);
        this.aET = new c((ListView) this.aEq.getRefreshableView());
    }

    private void EP() {
        ej("工作室公告");
        this.aIs.setVisibility(8);
        if (1 == this.aDY || 2 == this.aDY) {
            this.aIQ.setVisibility(0);
        } else {
            this.aIQ.setVisibility(8);
        }
        this.aIQ.setImageResource(d.r(this.mContext, R.attr.studio_write_announce));
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ StudioAnnounceActivity aEU;

            {
                this.aEU = this$0;
            }

            public void onClick(View v) {
                k.c(this.aEU.mContext, this.aEU.aDX);
            }
        });
    }

    private void EQ() {
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ StudioAnnounceActivity aEU;

            {
                this.aEU = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aEU.reload();
            }
        });
        this.aET.a(new c.a(this) {
            final /* synthetic */ StudioAnnounceActivity aEU;

            {
                this.aEU = this$0;
            }

            public void onLoadData() {
                if (this.aEU.aES != null) {
                    this.aEU.EY();
                }
            }

            public boolean shouldLoadData() {
                if (this.aEU.aES == null) {
                    this.aEU.aET.onLoadComplete();
                    return false;
                } else if (this.aEU.aES.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aET);
        this.aER = new AnnouncementAdapter(this.mContext, this.aDX, this.aDY);
        this.aEq.setAdapter(this.aER);
    }

    private void EZ() {
        reload();
    }

    private void reload() {
        z.DO().Y(this.aDX, 0, 20);
    }

    private void EY() {
        z.DO().Y(this.aDX, this.aES.start, 20);
    }

    protected void EX() {
        super.EX();
        reload();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(16908290, R.attr.game_option_bg).bc(R.id.iv_temp, R.attr.studio_announce_no_msg).ba(R.id.tv_no_announce, 16843282);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aIQ.setImageResource(d.r(this.mContext, R.attr.studio_write_announce));
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
