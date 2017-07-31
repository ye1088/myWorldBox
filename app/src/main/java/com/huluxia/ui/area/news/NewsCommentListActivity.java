package com.huluxia.ui.area.news;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.j;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.ImeUtil;
import com.huluxia.framework.base.widget.KeyboardResizeLayout;
import com.huluxia.framework.base.widget.KeyboardResizeLayout.OnKeyboardShowListener;
import com.huluxia.module.h;
import com.huluxia.module.news.c;
import com.huluxia.module.news.d;
import com.huluxia.module.news.e;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.itemadapter.news.NewsCommentItemAdapter;
import com.huluxia.utils.aa;
import com.huluxia.utils.aa.a;
import com.huluxia.utils.at;

public class NewsCommentListActivity extends HTBaseLoadingActivity {
    private static final int PAGE_SIZE = 20;
    private static final String aGY = "RESOURCE_DATA";
    public static final String aGZ = "NEWS_ITEM";
    private static final int aHa = 100;
    private PullToRefreshListView aEq;
    private aa aHb;
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
        final /* synthetic */ NewsCommentListActivity aHm;
        private int selectionEnd;
        private int selectionStart;

        {
            this.aHm = this$0;
        }

        public void afterTextChanged(Editable s) {
            this.selectionStart = this.aHm.aHd.getSelectionStart();
            this.selectionEnd = this.aHm.aHd.getSelectionEnd();
            if (this.aEo.length() > 100) {
                s.delete(this.selectionStart - 1, this.selectionEnd);
                int tempSelection = this.selectionStart;
                this.aHm.aHd.setTextKeepState(s);
                this.aHm.aHd.setText(s);
                this.aHm.aHd.setSelection(100);
                t.n(this.aHm, String.format("内容不能大于%d个字符", new Object[]{Integer.valueOf(100)}));
            }
        }

        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            this.aEo = s;
        }

        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ NewsCommentListActivity aHm;

        {
            this.aHm = this$0;
        }

        @MessageHandler(message = 1026)
        public void onRecvNewsCommentList(boolean succ, e info) {
            this.aHm.aEq.onRefreshComplete();
            if (!succ || this.aHm.aHc == null) {
                this.aHm.aHb.KN();
                if (this.aHm.getCurrentPage() == 0) {
                    this.aHm.FB();
                    return;
                } else {
                    t.n(this.aHm.mContext, this.aHm.getResources().getString(m.loading_failed_please_retry));
                    return;
                }
            }
            this.aHm.aHb.onLoadComplete();
            if (this.aHm.getCurrentPage() == 0) {
                this.aHm.FC();
            }
            if (info.start > 20) {
                this.aHm.aHf.start = info.start;
                this.aHm.aHf.more = info.more;
                this.aHm.aHf.list.addAll(info.list);
            } else {
                this.aHm.aHf = info;
            }
            this.aHm.aHc.c(this.aHm.aHf.list, true);
        }

        @MessageHandler(message = 1027)
        public void onRecvCommentCreate(boolean succ, String msg, String context) {
            if (context.equals("NewsCommentListActivity")) {
                this.aHm.aHk.setEnabled(true);
                this.aHm.cs(false);
                if (succ) {
                    this.aHm.aEq.setRefreshing();
                    t.o(this.aHm, msg);
                    return;
                }
                t.n(this.aHm, "评论失败！");
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ NewsCommentListActivity aHm;

        {
            this.aHm = this$0;
        }

        public void onClick(View v) {
            if (v.getId() != g.et_comment) {
            }
        }
    };
    private Activity mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_news_comment);
        this.aHk = findViewById(g.send_btn);
        this.aHk.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ NewsCommentListActivity aHm;

            {
                this.aHm = this$0;
            }

            public void onClick(View v) {
                if (j.ep().ey()) {
                    this.aHm.Fh();
                } else {
                    t.an(this.aHm);
                }
            }
        });
        this.aHg = (KeyboardResizeLayout) findViewById(g.root);
        this.aHg.addOnKeyboardShowListener(new OnKeyboardShowListener(this) {
            final /* synthetic */ NewsCommentListActivity aHm;

            {
                this.aHm = this$0;
            }

            public void onShow(boolean show) {
                this.aHm.aHh = show;
                if (!this.aHm.aHh) {
                    this.aHm.aHd.clearFocus();
                    this.aHm.aHd.setHint(this.aHm.getResources().getString(m.comment_hint1));
                    this.aHm.aHi = false;
                }
            }
        });
        this.mContext = this;
        EventNotifyCenter.add(h.class, this.mCallback);
        this.aHe = (c) getIntent().getParcelableExtra("NEWS_ITEM");
        EP();
        initView();
        if (savedInstanceState != null) {
            this.aHf = (e) savedInstanceState.getParcelable(aGY);
            this.aHc.c(this.aHf.list, true);
        } else if (this.aHe != null) {
            com.huluxia.module.news.i.Ea().d(0, this.aHe.infoId);
            Fy();
        }
    }

    private void Fh() {
        String content = this.aHd.getText() == null ? "" : this.aHd.getText().toString();
        if (content.trim().length() < 5) {
            t.n(this, "内容不能少于5个字符");
            return;
        }
        this.aHk.setEnabled(false);
        ek("正在提交");
        cs(true);
        com.huluxia.module.news.i.Ea().a(this.aHe.infoId, this.aHi ? this.aHj.commentID : 0, content, "NewsCommentListActivity");
        this.aHd.setText("");
        at.hideInputMethod(this.aHd);
    }

    private void EP() {
        this.aIs.setVisibility(8);
        ej("评论");
    }

    private void initView() {
        this.aHd = (EditText) findViewById(g.et_comment);
        this.aHd.addTextChangedListener(this.aHl);
        this.aEq = (PullToRefreshListView) findViewById(g.comment_list);
        this.aHc = new NewsCommentItemAdapter(this.mContext, this.aHf.list, false);
        this.aEq.setAdapter(this.aHc);
        this.aEq.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ NewsCommentListActivity aHm;

            {
                this.aHm = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                d item = (d) parent.getAdapter().getItem(position);
                if (item != null) {
                    this.aHm.aHj = item;
                    this.aHm.aHi = true;
                    this.aHm.aHd.setHint("回复：" + item.user.nick);
                    this.aHm.aHd.requestFocus();
                    ImeUtil.showIME(this.aHm.mContext, this.aHm.aHd);
                }
            }
        });
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ NewsCommentListActivity aHm;

            {
                this.aHm = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                com.huluxia.module.news.i.Ea().d(0, this.aHm.aHe.infoId);
            }
        });
        this.aHb = new aa((ListView) this.aEq.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ NewsCommentListActivity aHm;

            {
                this.aHm = this$0;
            }

            public void onLoadData() {
                com.huluxia.module.news.i.Ea().d(this.aHm.aHf == null ? 0 : this.aHm.aHf.start, this.aHm.aHe.infoId);
            }

            public boolean shouldLoadData() {
                if (this.aHm.aHf == null) {
                    this.aHm.aHb.onLoadComplete();
                    return false;
                } else if (this.aHm.aHf.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEq.setOnScrollListener(this.aHb);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(aGY, this.aHf);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        if (this.aHd != null) {
            this.aHd.removeTextChangedListener(this.aHl);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() != 0 || !this.aHh) {
            return super.onTouchEvent(event);
        }
        this.aHd.clearFocus();
        ImeUtil.hideIME(this, this.aHd);
        return true;
    }
}
