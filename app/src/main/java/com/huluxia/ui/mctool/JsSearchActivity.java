package com.huluxia.ui.mctool;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.map.f;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.module.i;
import com.huluxia.module.n;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.ui.bbs.ResourceSearchEmptyTitle;
import com.huluxia.ui.itemadapter.js.JsDownAdapter;
import com.huluxia.utils.ab;
import com.huluxia.utils.at;
import com.huluxia.utils.c;
import com.huluxia.utils.c.a;

public class JsSearchActivity extends HTBaseActivity implements OnClickListener {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private f aGH;
    private Activity aMn;
    private boolean baV = true;
    private JsDownAdapter bbg;
    private ResourceSearchEmptyTitle bbi;
    private LinearLayout bbj;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ JsSearchActivity bbk;

        {
            this.bbk = this$0;
        }

        @MessageHandler(message = 1284)
        public void onRecvRankingData(boolean succ, f info) {
            HLog.debug(this.bbk.aMn, "onRecvRankingData info = " + info + ", succ = " + succ, new Object[0]);
            this.bbk.aEq.onRefreshComplete();
            this.bbk.aEV.onLoadComplete();
            this.bbk.bbj.removeAllViews();
            if (succ && info != null) {
                if (info.start > 20) {
                    this.bbk.aGH.start = info.start;
                    this.bbk.aGH.more = info.more;
                    this.bbk.aGH.mapList.addAll(info.mapList);
                } else {
                    this.bbk.aGH = info;
                    if (UtilsFunction.empty(this.bbk.aGH.mapList)) {
                        this.bbk.bbj.addView(this.bbk.bbi);
                    }
                }
                this.bbk.bbg.a(this.bbk.aGH.mapList, true);
            } else if (info != null) {
                t.n(this.bbk.aMn, ab.n(info.code, info.msg));
            } else {
                t.n(this.bbk.aMn, "数据请求失败，请下拉刷新重试");
            }
        }

        @MessageHandler(message = 768)
        public void onUnzip(long id) {
            if (this.bbk.bbg != null) {
                this.bbk.bbg.notifyDataSetChanged();
            }
        }
    };
    private CallbackHandler mDownloadCallback = new CallbackHandler(this) {
        final /* synthetic */ JsSearchActivity bbk;

        {
            this.bbk = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            HLog.debug(this, "onProgress info = " + progressInfo + ", url = " + url, new Object[0]);
            if (this.bbk.bbg != null) {
                this.bbk.bbg.a(url, progressInfo);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (this.bbk.bbg != null && true == this.bbk.baV) {
                this.bbk.bbg.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            HLog.debug(this, "recv download cancel url = " + url, new Object[0]);
            if (this.bbk.bbg != null) {
                this.bbk.bbg.ew(url);
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.bbk.bbg != null) {
                this.bbk.bbg.ex(url);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.layout_map_rank);
        this.aMn = this;
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(BaseEvent.class, this.mDownloadCallback);
        this.aIY.setHint("输入JS名称/关键字");
        Iy();
        initView();
        Fa();
    }

    protected void onStart() {
        super.onStart();
        this.baV = true;
    }

    protected void onStop() {
        super.onStop();
        this.baV = false;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.mDownloadCallback);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id != R.id.sys_header_right_img) {
            if (id == R.id.imgClear) {
                clear();
            } else if (id == R.id.imgSearch) {
                f(0, 20, true);
            }
        }
    }

    public void clear() {
        this.aIY.getEditableText().clear();
        this.aIY.getEditableText().clearSpans();
        this.aIY.setText("");
        this.bbg.HA();
        if (this.aGH != null) {
            this.aGH.more = 0;
        }
    }

    protected void f(int start, int count, boolean isClear) {
        String key = this.aIY.getText().toString().trim();
        if (key.length() < 2) {
            t.show_toast(this, "搜索条件必须大于两个字符");
            return;
        }
        if (isClear) {
            this.bbg.HA();
            if (this.aGH != null) {
                this.aGH.more = 0;
            }
        }
        e(start, count, key);
    }

    protected void initView() {
        ct(true);
        this.aIZ.setOnClickListener(this);
        this.aJa.setOnClickListener(this);
        this.aJa.setClickable(false);
        this.aIY.addTextChangedListener(new TextWatcher(this) {
            private CharSequence aEo;
            final /* synthetic */ JsSearchActivity bbk;

            {
                this.bbk = this$0;
            }

            public void afterTextChanged(Editable s) {
                if (this.aEo.length() > 1) {
                    this.bbk.aJa.setClickable(true);
                    this.bbk.aIZ.setVisibility(0);
                } else if (this.aEo.length() > 0) {
                    this.bbk.aJa.setClickable(true);
                    this.bbk.aIZ.setVisibility(0);
                } else {
                    this.bbk.aJa.setClickable(false);
                    this.bbk.aIZ.setVisibility(4);
                    this.bbk.bbg.HA();
                }
            }

            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                this.aEo = s;
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        this.aIY.requestFocus();
        at.showInputMethod(this.aIY, 200);
    }

    protected void Fa() {
        this.aEq = (PullToRefreshListView) findViewById(R.id.listview);
        this.bbj = new LinearLayout(this);
        this.bbj.setOrientation(1);
        this.bbi = new ResourceSearchEmptyTitle(this);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.bbj);
        this.aEq.setAdapter(this.bbg);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ JsSearchActivity bbk;

            {
                this.bbk = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bbk.f(0, 20, true);
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new a(this) {
            final /* synthetic */ JsSearchActivity bbk;

            {
                this.bbk = this$0;
            }

            public void onLoadData() {
                if (this.bbk.aGH != null) {
                    this.bbk.f(this.bbk.aGH.start, 20, false);
                }
            }

            public boolean shouldLoadData() {
                if (this.bbk.aGH == null) {
                    this.bbk.aEV.onLoadComplete();
                    return false;
                } else if (this.bbk.aGH.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
        this.aEq.setOnItemClickListener(null);
    }

    protected void Iy() {
        this.bbg = new JsDownAdapter(this.aMn);
    }

    protected void e(int start, int count, String key) {
        i.DB().a(start, count, key);
        r.ck().K(r.a.ip);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
