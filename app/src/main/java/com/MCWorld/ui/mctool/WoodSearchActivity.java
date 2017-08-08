package com.MCWorld.ui.mctool;

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
import com.MCWorld.data.map.f;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.module.ac;
import com.MCWorld.module.n;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.bbs.ResourceSearchEmptyTitle;
import com.MCWorld.ui.itemadapter.wood.WoodDownAdapter;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.at;
import com.MCWorld.utils.c;
import com.MCWorld.utils.c.a;

public class WoodSearchActivity extends HTBaseActivity implements OnClickListener {
    private static final int PAGE_SIZE = 20;
    private c aEV;
    private PullToRefreshListView aEq;
    private f aGH;
    private Activity aMn;
    private boolean baV = true;
    private ResourceSearchEmptyTitle bbi;
    private LinearLayout bbj;
    private WoodDownAdapter bcA;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ WoodSearchActivity bcB;

        {
            this.bcB = this$0;
        }

        @MessageHandler(message = 1796)
        public void onRecvRankingData(boolean succ, f info) {
            HLog.debug(this.bcB.aMn, "onRecvRankingData info = " + info + ", succ = " + succ, new Object[0]);
            this.bcB.aEq.onRefreshComplete();
            this.bcB.aEV.onLoadComplete();
            this.bcB.bbj.removeAllViews();
            if (succ && info != null) {
                if (info.start > 20) {
                    this.bcB.aGH.start = info.start;
                    this.bcB.aGH.more = info.more;
                    this.bcB.aGH.mapList.addAll(info.mapList);
                } else {
                    this.bcB.aGH = info;
                    if (UtilsFunction.empty(this.bcB.aGH.mapList)) {
                        this.bcB.bbj.addView(this.bcB.bbi);
                    }
                }
                this.bcB.bcA.a(this.bcB.aGH.mapList, true);
            } else if (info != null) {
                t.n(this.bcB.aMn, ab.n(info.code, info.msg));
            } else {
                t.n(this.bcB.aMn, "数据请求失败，请下拉刷新重试");
            }
        }

        @MessageHandler(message = 768)
        public void onUnzip(long id) {
            if (this.bcB.bcA != null) {
                this.bcB.bcA.notifyDataSetChanged();
            }
        }
    };
    private CallbackHandler mDownloadCallback = new CallbackHandler(this) {
        final /* synthetic */ WoodSearchActivity bcB;

        {
            this.bcB = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            HLog.debug(this, "onProgress info = " + progressInfo + ", url = " + url, new Object[0]);
            if (this.bcB.bcA != null) {
                this.bcB.bcA.a(url, progressInfo);
            }
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (this.bcB.bcA != null && true == this.bcB.baV) {
                this.bcB.bcA.eu(url);
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            HLog.debug(this, "recv download cancel url = " + url, new Object[0]);
            if (this.bcB.bcA != null) {
                this.bcB.bcA.ew(url);
            }
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            if (this.bcB.bcA != null) {
                this.bcB.bcA.ex(url);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.layout_map_rank);
        this.aMn = this;
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(BaseEvent.class, this.mDownloadCallback);
        this.aIY.setHint("输入材质名称/关键字");
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
        this.bcA.HA();
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
            this.bcA.HA();
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
            final /* synthetic */ WoodSearchActivity bcB;

            {
                this.bcB = this$0;
            }

            public void afterTextChanged(Editable s) {
                if (this.aEo.length() > 1) {
                    this.bcB.aJa.setClickable(true);
                    this.bcB.aIZ.setVisibility(0);
                } else if (this.aEo.length() > 0) {
                    this.bcB.aJa.setClickable(true);
                    this.bcB.aIZ.setVisibility(0);
                } else {
                    this.bcB.aJa.setClickable(false);
                    this.bcB.aIZ.setVisibility(4);
                    this.bcB.bcA.HA();
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
        this.aEq.setAdapter(this.bcA);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ WoodSearchActivity bcB;

            {
                this.bcB = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bcB.f(0, 20, true);
            }
        });
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new a(this) {
            final /* synthetic */ WoodSearchActivity bcB;

            {
                this.bcB = this$0;
            }

            public void onLoadData() {
                if (this.bcB.aGH != null) {
                    this.bcB.f(this.bcB.aGH.start, 20, false);
                }
            }

            public boolean shouldLoadData() {
                if (this.bcB.aGH == null) {
                    this.bcB.aEV.onLoadComplete();
                    return false;
                } else if (this.bcB.aGH.more > 0) {
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
        this.bcA = new WoodDownAdapter(this.aMn);
    }

    protected void e(int start, int count, String key) {
        ac.DT().d(start, count, key);
        r.ck().K_umengEvent(r.a.iq);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
