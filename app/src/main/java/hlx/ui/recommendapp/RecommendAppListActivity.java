package hlx.ui.recommendapp;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsNetwork;
import com.huluxia.module.n;
import com.huluxia.module.q;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.utils.c;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import hlx.ui.recommendapp.a.a;
import java.util.Iterator;

public class RecommendAppListActivity extends HTBaseLoadingActivity {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ RecommendAppListActivity cfm;

        {
            this.cfm = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            HLog.debug(this, "onProgress info = " + progressInfo + ", url = " + url, new Object[0]);
            if (this.cfm.cfl != null) {
                this.cfm.cfl.a(url, progressInfo);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (this.cfm.cfl != null) {
                this.cfm.cfl.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            HLog.debug(this, "recv download cancel url = " + url, new Object[0]);
            if (this.cfm.cfl != null) {
                this.cfm.cfl.ew(url);
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.cfm.cfl != null) {
                this.cfm.cfl.ex(url);
            }
        }
    };
    private CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ RecommendAppListActivity cfm;

        {
            this.cfm = this$0;
        }

        @MessageHandler(message = 256)
        public void onWorkPrepare(String url) {
            if (this.cfm.cfl != null) {
                this.cfm.cfl.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onWorkWait(String url) {
            if (this.cfm.cfl != null) {
                this.cfm.cfl.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 263)
        public void onDownloadComplete(String url) {
            if (this.cfm.cfl != null) {
                this.cfm.cfl.notifyDataSetChanged();
            }
            if (url != null) {
                Iterator it = this.cfm.cfk.adList.iterator();
                while (it.hasNext()) {
                    a tmpItem = (a) it.next();
                    if (url.equals(tmpItem.appdownurl)) {
                        r.ck().j(hlx.data.tongji.a.bON, String.valueOf(tmpItem.id));
                        q.aH((long) tmpItem.id);
                        return;
                    }
                }
            }
        }

        @MessageHandler(message = 265)
        public void onInstallApk(String url, String apkPath, String signature) {
            if (url != null) {
                Iterator it = this.cfm.cfk.adList.iterator();
                while (it.hasNext()) {
                    a tmpItem = (a) it.next();
                    if (url.equals(tmpItem.appdownurl)) {
                        hlx.ui.recommendapp.statisticsrecord.a.VI().a(tmpItem);
                        r.ck().j(hlx.data.tongji.a.bOO, String.valueOf(tmpItem.id));
                        q.aI((long) tmpItem.id);
                        return;
                    }
                }
            }
        }
    };
    private CallbackHandler cec = new CallbackHandler(this) {
        final /* synthetic */ RecommendAppListActivity cfm;

        {
            this.cfm = this$0;
        }

        @MessageHandler(message = 302)
        public void onRecvListInfo(boolean succ, int tag, a info) {
            this.cfm.aEq.onRefreshComplete();
            this.cfm.aEV.onLoadComplete();
            if (tag != this.cfm.mRequestCode) {
                return;
            }
            String noNet;
            Context context;
            if (succ && info != null) {
                if (info.start > 20) {
                    if (this.cfm.cfk != null) {
                        this.cfm.cfk.start = info.start;
                        this.cfm.cfk.more = info.more;
                        this.cfm.cfl.c(info.adList, false);
                    } else {
                        return;
                    }
                } else if (info.status == 0) {
                    if (this.cfm.cfl.getData() == null || this.cfm.cfl.getData().size() == 0) {
                        this.cfm.FB();
                    } else {
                        noNet = this.cfm.getResources().getString(R.string.no_network);
                        context = this.cfm;
                        if (UtilsNetwork.isNetworkConnected(this.cfm)) {
                            noNet = "数据请求失败，请下拉刷新重试";
                        }
                        t.n(context, noNet);
                    }
                    HLog.error("RecommendAppListActivity.onRecvListInfo", info.status + "" + info.msg, new Object[0]);
                    return;
                } else {
                    this.cfm.cfk = info;
                    this.cfm.cfl.c(this.cfm.cfk.adList, true);
                }
                this.cfm.FC();
            } else if (this.cfm.cfl.getData() == null || this.cfm.cfl.getData().size() == 0) {
                this.cfm.FB();
            } else {
                noNet = this.cfm.getResources().getString(R.string.no_network);
                context = this.cfm;
                if (UtilsNetwork.isNetworkConnected(this.cfm)) {
                    noNet = "数据请求失败，请下拉刷新重试";
                }
                t.n(context, noNet);
            }
        }
    };
    private a cfk;
    private RecommendAppAdapter cfl;
    private Context mContext;
    private int mRequestCode;

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.cfl != null) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.cfl);
            builder.a(setter);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.aty_home_app_recommend);
        EventNotifyCenter.add(n.class, this.cec);
        EventNotifyCenter.add(BaseEvent.class, this.aGM);
        EventNotifyCenter.add(com.huluxia.controller.c.class, this.aGO);
        Sw();
        VG();
        Fa();
        Fy();
        reload();
    }

    private void Sw() {
        this.mContext = this;
        this.cfl = new RecommendAppAdapter(this);
        this.mRequestCode = 0;
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void VG() {
        ej(this.mContext.getString(R.string.recommend_app_title));
    }

    private void Fa() {
        this.aEq = (PullToRefreshListView) findViewById(R.id.listviewAppRecommend);
        this.aEq.setAdapter(this.cfl);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ RecommendAppListActivity cfm;

            {
                this.cfm = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.cfm.reload();
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new c.a(this) {
            final /* synthetic */ RecommendAppListActivity cfm;

            {
                this.cfm = this$0;
            }

            public void onLoadData() {
                if (this.cfm.cfk != null) {
                    this.cfm.Fc();
                }
            }

            public boolean shouldLoadData() {
                if (this.cfm.cfk == null) {
                    this.cfm.aEV.onLoadComplete();
                    return false;
                } else if (this.cfm.cfk.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void reload() {
        q.S(this.mRequestCode, 0, 20);
    }

    private void Fc() {
        q.S(this.mRequestCode, this.cfk.start, 20);
    }

    protected void EX() {
        super.EX();
        Fy();
        reload();
    }

    protected void onResume() {
        super.onResume();
        if (this.cfl != null) {
            this.cfl.notifyDataSetChanged();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.cec);
        EventNotifyCenter.remove(this.aGM);
        EventNotifyCenter.remove(this.aGO);
    }
}
