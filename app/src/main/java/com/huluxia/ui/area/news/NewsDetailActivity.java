package com.huluxia.ui.area.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.ImeUtil;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.widget.KeyboardResizeLayout;
import com.huluxia.framework.base.widget.KeyboardResizeLayout.OnKeyboardShowListener;
import com.huluxia.module.h;
import com.huluxia.module.news.c;
import com.huluxia.module.news.d;
import com.huluxia.module.news.e;
import com.huluxia.module.news.i;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.itemadapter.news.NewsCommentItemAdapter;
import com.simple.colorful.a.a;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class NewsDetailActivity extends HTBaseLoadingActivity {
    private static final String TAG = "NewsDetailActivity";
    private static final String aGY = "RESOURCE_DATA";
    public static final String aGZ = "NEWS_ITEM";
    private static final int aHa = 100;
    private PullToRefreshListView aEq;
    private NewsCommentItemAdapter aHc;
    private EditText aHd;
    private c aHe;
    private e aHf = new e();
    private KeyboardResizeLayout aHg;
    private boolean aHh;
    private boolean aHi = false;
    private d aHj;
    private View aHk;
    private TextWatcher aHl = new TextWatcher(this) {
        private CharSequence aEo;
        final /* synthetic */ NewsDetailActivity aHz;
        private int selectionEnd;
        private int selectionStart;

        {
            this.aHz = this$0;
        }

        public void afterTextChanged(Editable s) {
            this.selectionStart = this.aHz.aHd.getSelectionStart();
            this.selectionEnd = this.aHz.aHd.getSelectionEnd();
            if (this.aEo.length() > 100) {
                s.delete(this.selectionStart - 1, this.selectionEnd);
                int tempSelection = this.selectionStart;
                this.aHz.aHd.setTextKeepState(s);
                this.aHz.aHd.setText(s);
                this.aHz.aHd.setSelection(100);
                t.n(this.aHz, String.format("内容不能大于%d个字符", new Object[]{Integer.valueOf(100)}));
            }
        }

        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            this.aEo = s;
        }

        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    };
    private NewsDetailHeader aHn;
    private NewsDetailFooter aHo;
    private LinearLayout aHp;
    private TextView aHq;
    private ImageView aHr;
    private View aHs;
    private String aHt;
    private String aHu;
    private HashMap<String, String> aHv;
    private HashMap<String, String> aHw;
    private OnClickListener aHx = new OnClickListener(this) {
        final /* synthetic */ NewsDetailActivity aHz;

        {
            this.aHz = this$0;
        }

        public void onClick(View v) {
            if (j.ep().ey()) {
                Boolean favorite = (Boolean) v.getTag();
                this.aHz.aHr.setEnabled(false);
                if (favorite == null || !favorite.booleanValue()) {
                    i.Ea().b(this.aHz.aHe.infoId, true);
                    return;
                } else {
                    i.Ea().b(this.aHz.aHe.infoId, false);
                    return;
                }
            }
            t.an(this.aHz);
        }
    };
    private BroadcastReceiver aHy = new BroadcastReceiver(this) {
        final /* synthetic */ NewsDetailActivity aHz;

        {
            this.aHz = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.aHz.aHr.setEnabled(false);
            i.Ea().aQ(this.aHz.aHe.infoId);
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ NewsDetailActivity aHz;

        {
            this.aHz = this$0;
        }

        @MessageHandler(message = 1025)
        public void onRecvNewsDetailComment(boolean succ, e info) {
            this.aHz.aEq.onRefreshComplete();
            if (succ && this.aHz.aHc != null) {
                this.aHz.FC();
                this.aHz.aHf = info;
                this.aHz.aHc.c(this.aHz.aHf.list, true);
                if (this.aHz.aHo == null) {
                    this.aHz.aHo = new NewsDetailFooter(this.aHz, this.aHz.aHe);
                }
                if (this.aHz.aHe.cmtCount > 3) {
                    this.aHz.aHp.addView(this.aHz.aHo, new LayoutParams(-1, -2));
                } else {
                    this.aHz.aHp.removeAllViews();
                }
            } else if (this.aHz.getCurrentPage() == 0) {
                this.aHz.FB();
            } else {
                t.n(this.aHz, (info != null ? info.msg : "数据请求失败") + ",请下拉刷新重试");
            }
        }

        @MessageHandler(message = 1029)
        public void onRecvCheckNewsLike(boolean succ, boolean isFavorte, String msg) {
            this.aHz.aHr.setEnabled(true);
            if (succ) {
                this.aHz.co(isFavorte);
            }
        }

        @MessageHandler(message = 1028)
        public void onRecvLikeNews(boolean succ, boolean favor, String msg) {
            this.aHz.aHr.setEnabled(true);
            if (succ) {
                this.aHz.co(favor);
                t.download_toast(this.aHz, favor ? "收藏成功" : "取消收藏成功");
                return;
            }
            t.n(this.aHz, msg);
        }

        @MessageHandler(message = 1030)
        public void onRecvWebRefresh(boolean succ) {
            this.aHz.aEq.onRefreshComplete();
        }

        @MessageHandler(message = 1027)
        public void onRecvCommentCreate(boolean succ, String msg, String context) {
            if (context.equals(NewsDetailActivity.TAG)) {
                this.aHz.aHk.setEnabled(true);
                this.aHz.cs(false);
                if (succ) {
                    t.o(this.aHz, msg);
                } else {
                    t.n(this.aHz, "评论失败！");
                }
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ NewsDetailActivity aHz;

        {
            this.aHz = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.et_comment) {
                this.aHz.aHd.setFocusable(true);
                this.aHz.aHd.requestFocus();
            } else if (id == g.comment_counts) {
                t.b(this.aHz, this.aHz.aHe);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(b.i.activity_news_detial);
        EventNotifyCenter.add(h.class, this.mCallback);
        com.huluxia.service.i.c(this.aHy);
        if (savedInstanceState == null) {
            this.aHe = (c) getIntent().getParcelableExtra("NEWS_ITEM");
            if (this.aHe != null) {
                Fy();
                i.Ea().aP(this.aHe.infoId);
            } else {
                return;
            }
        }
        this.aHe = (c) savedInstanceState.getParcelable("newsItem");
        this.aHf = (e) savedInstanceState.getParcelable(aGY);
        Fj();
        EP();
        initView();
        this.aHn.setNews(this.aHe);
    }

    protected void onPause() {
        super.onPause();
        if (this.aHn != null) {
            this.aHn.pause();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.aHn != null) {
            this.aHn.resume();
        }
    }

    private boolean Fi() {
        String detail = this.aHd.getText() == null ? "" : this.aHd.getText().toString();
        if (detail.trim().length() < 5) {
            t.n(this, "内容不能少于5个字符");
            return false;
        }
        this.aHk.setEnabled(false);
        ek("正在提交");
        cs(true);
        i.Ea().a(this.aHe.infoId, this.aHi ? this.aHj.commentID : 0, detail, TAG);
        ImeUtil.hideIME(this, this.aHd);
        return true;
    }

    private void EP() {
        this.aIs.setVisibility(8);
        ej("");
    }

    private void Fj() {
        if (this.aHe != null && !UtilsFunction.empty(this.aHe.uri)) {
            try {
                JSONObject uriObj = new JSONObject(this.aHe.uri.replaceFirst("template=", ""));
                this.aHt = uriObj.getString("templateId");
                this.aHu = uriObj.getString("title");
                this.aHv = new HashMap((Map) Json.getGson().fromJson(uriObj.getJSONObject("params").toString(), new TypeToken<Map<String, String>>(this) {
                    final /* synthetic */ NewsDetailActivity aHz;

                    {
                        this.aHz = this$0;
                    }
                }.getType()));
                this.aHw = new HashMap((Map) Json.getGson().fromJson(uriObj.getJSONObject("extras").toString(), new TypeToken<Map<String, String>>(this) {
                    final /* synthetic */ NewsDetailActivity aHz;

                    {
                        this.aHz = this$0;
                    }
                }.getType()));
            } catch (Exception e) {
                HLog.error(TAG, "parse news err " + e, new Object[0]);
            }
        }
    }

    private void initView() {
        this.aHg = (KeyboardResizeLayout) findViewById(g.root);
        this.aHg.addOnKeyboardShowListener(new OnKeyboardShowListener(this) {
            final /* synthetic */ NewsDetailActivity aHz;

            {
                this.aHz = this$0;
            }

            public void onShow(boolean show) {
                this.aHz.aHh = show;
                if (this.aHz.aHh) {
                    this.aHz.aHs.setVisibility(4);
                    this.aHz.aHk.setVisibility(0);
                    this.aHz.aHr.setVisibility(8);
                    return;
                }
                this.aHz.aHs.setVisibility(0);
                this.aHz.aHk.setVisibility(4);
                this.aHz.aHd.setHint(this.aHz.getResources().getString(m.comment_hint1));
                this.aHz.aHi = false;
                this.aHz.aHr.setVisibility(0);
            }
        });
        this.aHs = findViewById(g.favor_container);
        this.aHk = findViewById(g.send_btn);
        this.aHk.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ NewsDetailActivity aHz;

            {
                this.aHz = this$0;
            }

            public void onClick(View v) {
                if (!j.ep().ey()) {
                    t.an(this.aHz);
                } else if (this.aHz.Fi()) {
                    this.aHz.aHd.setText("");
                }
            }
        });
        this.aEq = (PullToRefreshListView) findViewById(g.comment_list);
        this.aHn = new NewsDetailHeader(this);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.aHn);
        this.aHc = new NewsCommentItemAdapter(this, this.aHf.list, true);
        this.aEq.setAdapter(this.aHc);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ NewsDetailActivity aHz;

            {
                this.aHz = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aHz.aHn.refresh();
                i.Ea().aP(this.aHz.aHe.infoId);
            }
        });
        this.aEq.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ NewsDetailActivity aHz;

            {
                this.aHz = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                d item = (d) parent.getAdapter().getItem(position);
                if (item != null) {
                    this.aHz.aHj = item;
                    this.aHz.aHi = true;
                    this.aHz.aHd.setHint("回复：" + item.user.nick);
                    this.aHz.aHd.requestFocus();
                    ImeUtil.showIME(this.aHz, this.aHz.aHd);
                }
            }
        });
        this.aHp = new LinearLayout(this);
        ((ListView) this.aEq.getRefreshableView()).addFooterView(this.aHp);
        this.aHd = (EditText) findViewById(g.et_comment);
        this.aHd.addTextChangedListener(this.aHl);
        this.aHq = (TextView) findViewById(g.comment_counts);
        this.aHq.setText(this.aHe.cmtCount + "");
        this.aHq.setOnClickListener(this.mClickListener);
        this.aHr = (ImageView) findViewById(g.iv_news_favor);
        this.aHr.setTag(Boolean.valueOf(false));
        this.aHr.setOnClickListener(this.aHx);
        if (j.ep().ey()) {
            this.aHr.setEnabled(false);
            i.Ea().aQ(this.aHe.infoId);
        }
    }

    public void co(boolean favorite) {
        this.aHr.setTag(Boolean.valueOf(favorite));
        if (favorite) {
            this.aHr.setImageResource(com.simple.colorful.d.r(this, b.c.drawableNewsFavorChecked));
        } else {
            this.aHr.setImageResource(com.simple.colorful.d.r(this, b.c.drawableNewsFavor));
        }
    }

    protected void EX() {
        super.EX();
        i.Ea().aP(this.aHe.infoId);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(aGY, this.aHf);
        outState.putParcelable("newsItem", this.aHe);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.aHn != null) {
            this.aHn.recycle();
        }
        if (this.aHd != null) {
            this.aHd.removeTextChangedListener(this.aHl);
        }
        com.huluxia.service.i.unregisterReceiver(this.aHy);
        EventNotifyCenter.remove(this.mCallback);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() != 0 || !this.aHh) {
            return super.onTouchEvent(event);
        }
        this.aHd.clearFocus();
        ImeUtil.hideIME(this, this.aHd);
        return true;
    }

    protected void a(a builder) {
        super.a(builder);
        builder.i(this.aHn, b.c.backgroundDefault);
    }
}
