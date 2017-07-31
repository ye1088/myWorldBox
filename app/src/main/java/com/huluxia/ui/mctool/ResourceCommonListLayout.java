package com.huluxia.ui.mctool;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.map.f;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.n;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.utils.c;

public class ResourceCommonListLayout extends LinearLayout {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private DownAdapter aGG;
    private f aGH;
    private CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ ResourceCommonListLayout bbK;

        {
            this.bbK = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            HLog.debug(this, "onProgress info = " + progressInfo + ", url = " + url, new Object[0]);
            if (this.bbK.aGG != null) {
                this.bbK.aGG.a(url, progressInfo);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (this.bbK.aGG != null && 4 == hlx.data.localstore.a.bLG) {
                this.bbK.aGG.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            HLog.debug(this, "recv download cancel url = " + url, new Object[0]);
            if (this.bbK.aGG != null) {
                this.bbK.aGG.ew(url);
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.bbK.aGG != null) {
                this.bbK.aGG.ex(url);
            }
        }
    };
    private CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ ResourceCommonListLayout bbK;

        {
            this.bbK = this$0;
        }

        @MessageHandler(message = 256)
        public void onWorkPrepare(String url) {
            if (this.bbK.aGG != null) {
                this.bbK.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 257)
        public void onWorkWait(String url) {
            if (this.bbK.aGG != null) {
                this.bbK.aGG.notifyDataSetChanged();
            }
        }

        @MessageHandler(message = 262)
        public void onUnzipComplete(String url) {
            if (this.bbK.aGG != null) {
                this.bbK.aGG.notifyDataSetChanged();
            }
        }
    };
    private a bbH;
    int bbI;
    int bbJ;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ResourceCommonListLayout bbK;

        {
            this.bbK = this$0;
        }

        @MessageHandler(message = 289)
        public void onRecvMapListInfo(boolean succ, int src_tag, f info) {
            if (src_tag == this.bbK.bbI) {
                this.bbK.a(succ, info);
            }
        }

        @MessageHandler(message = 3077)
        public void onRefresh() {
            this.bbK.bbH.Z(this.bbK.bbI, 0, 20);
        }
    };

    public interface a {
        void Z(int i, int i2, int i3);
    }

    public ResourceCommonListLayout(Context context, DownAdapter da, a rp) {
        super(context);
        this.aGG = da;
        this.bbH = rp;
        this.bbI = hashCode();
        init(context);
    }

    public ResourceCommonListLayout(Context context, AttributeSet attrs, DownAdapter da, a rp) {
        super(context, attrs);
        this.aGG = da;
        this.bbH = rp;
        init(context);
    }

    public int getTadId() {
        return this.bbI;
    }

    @TargetApi(19)
    public ResourceCommonListLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_map_rank, this);
        this.aEq = (PullToRefreshListView) findViewById(R.id.listview);
        this.aEq.setAdapter(this.aGG);
        this.aEq.setOnRefreshListener(new 1(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 2(this));
        this.aEq.setOnScrollListener(this.aEV);
        this.aEq.setOnItemClickListener(null);
        this.bbH.Z(this.bbI, 0, 20);
    }

    public void ce(boolean init) {
        if (init) {
            EventNotifyCenter.add(n.class, this.mCallback);
            EventNotifyCenter.add(BaseEvent.class, this.aGM);
            EventNotifyCenter.add(com.huluxia.controller.c.class, this.aGO);
            return;
        }
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aGM);
        EventNotifyCenter.remove(this.aGO);
    }

    public void IC() {
        if (this.aGH == null) {
            this.aEq.setRefreshing();
        }
    }

    public void a(boolean succ, f info) {
        HLog.debug(this, "onNewDataReceived info = " + info + ", succ = " + succ, new Object[0]);
        this.aEq.onRefreshComplete();
        this.aEV.onLoadComplete();
        if (info == null || !succ) {
            EventNotifyCenter.notifyEvent(n.class, 3076, Boolean.valueOf(false));
            HLog.error(this, "onRecvSkinNew info is NULL ", new Object[0]);
        } else if (this.aGG == null || !info.isSucc()) {
            EventNotifyCenter.notifyEvent(n.class, 3076, Boolean.valueOf(false));
        } else {
            if (info.start > 20) {
                this.aGH.start = info.start;
                this.aGH.more = info.more;
                this.aGH.mapList.addAll(info.mapList);
            } else {
                this.aGH = info;
            }
            this.aGG.a(this.aGH.mapList, true);
            EventNotifyCenter.notifyEvent(n.class, 3076, Boolean.valueOf(true));
        }
    }
}
