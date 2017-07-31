package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.map.f;
import com.huluxia.framework.BaseHttpEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.i;
import com.huluxia.module.n;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.itemadapter.js.JsDownAdapter;
import com.huluxia.utils.c;
import com.huluxia.utils.c.a;

public class JsListActivity extends HTBaseActivity {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private f aGH;
    private Activity aMn;
    private JsDownAdapter bbg;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ JsListActivity bbh;

        {
            this.bbh = this$0;
        }

        @MessageHandler(message = 1280)
        public void onRecvJsNew(boolean succ, f info) {
            HLog.debug(this, "onRecvJsNew info = " + info + ", succ = " + succ, new Object[0]);
            this.bbh.aEq.onRefreshComplete();
            this.bbh.aEV.onLoadComplete();
            if (info == null || !succ) {
                HLog.error(this, "onRecvRankingData info is NULL ", new Object[0]);
            } else if (this.bbh.bbg != null && info.isSucc()) {
                if (info.start > 20) {
                    this.bbh.aGH.start = info.start;
                    this.bbh.aGH.more = info.more;
                    this.bbh.aGH.mapList.addAll(info.mapList);
                } else {
                    this.bbh.aGH = info;
                }
                this.bbh.bbg.a(this.bbh.aGH.mapList, true);
            }
        }

        @MessageHandler(message = 769)
        public void onUnzip(long id) {
            if (this.bbh.bbg != null) {
                this.bbh.bbg.notifyDataSetChanged();
            }
        }
    };
    private CallbackHandler mDownloadCallback = new CallbackHandler(this) {
        final /* synthetic */ JsListActivity bbh;

        {
            this.bbh = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            HLog.debug(this, "onProgress info = " + progressInfo + ", url = " + url, new Object[0]);
            if (this.bbh.bbg != null) {
                this.bbh.bbg.a(url, progressInfo);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (this.bbh.bbg != null) {
                this.bbh.bbg.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            HLog.debug(this, "recv download cancel url = " + url, new Object[0]);
            if (this.bbh.bbg != null) {
                this.bbh.bbg.ew(url);
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.bbh.bbg != null) {
                this.bbh.bbg.ex(url);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.layout_map_rank);
        this.aMn = this;
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(BaseHttpEvent.class, this.mDownloadCallback);
        init();
    }

    private void init() {
        ej("ＪＳ下载");
        this.aEq = (PullToRefreshListView) findViewById(R.id.listview);
        this.bbg = new JsDownAdapter(this.aMn);
        this.aEq.setAdapter(this.bbg);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ JsListActivity bbh;

            {
                this.bbh = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                i.DB().aj(0, 20);
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new a(this) {
            final /* synthetic */ JsListActivity bbh;

            {
                this.bbh = this$0;
            }

            public void onLoadData() {
                if (this.bbh.aGH != null) {
                    i.DB().aj(this.bbh.aGH.start, 20);
                }
            }

            public boolean shouldLoadData() {
                if (this.bbh.aGH == null) {
                    this.bbh.aEV.onLoadComplete();
                    return false;
                } else if (this.bbh.aGH.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
        this.aEq.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ JsListActivity bbh;

            {
                this.bbh = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });
        i.DB().aj(0, 20);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.mDownloadCallback);
    }
}
