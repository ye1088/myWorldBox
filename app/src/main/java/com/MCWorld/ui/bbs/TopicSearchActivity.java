package com.MCWorld.ui.bbs;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.title.TitleBar;
import com.MCWorld.module.h;
import com.MCWorld.module.topic.b;
import com.MCWorld.module.topic.k;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.ui.itemadapter.topic.Topic2GItemAdapter;
import com.MCWorld.ui.itemadapter.topic.TopicWifiItemAdapter;
import com.MCWorld.utils.aa;
import com.MCWorld.utils.aa.a;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.at;
import com.MCWorld.utils.ba;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class TopicSearchActivity extends HTBaseThemeActivity {
    private static final int PAGE_SIZE = 20;
    protected aa aHb;
    private ImageView aIZ;
    private TitleBar aIw;
    private TopicCategory aJO;
    private long aMV;
    private BaseAdapter aPG = null;
    private b aPI = new b();
    protected PullToRefreshListView aQB;
    private TopicSearchActivity aQC;
    private ImageButton aQD;
    private ImageButton aQE;
    private EditText aQF;
    private BaseLoadingLayout aQG;
    private OnClickListener aQH = new OnClickListener(this) {
        final /* synthetic */ TopicSearchActivity aQI;

        {
            this.aQI = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.ImageButtonLeft) {
                this.aQI.finish();
            } else if (id == g.imgClear) {
                this.aQI.clear();
            } else if (id == g.imgSearchBtn) {
                this.aQI.Hm();
            }
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ TopicSearchActivity aQI;

        {
            this.aQI = this$0;
        }

        @MessageHandler(message = 624)
        public void onRecvTopicList(boolean succ, String start, b info) {
            this.aQI.aQB.onRefreshComplete();
            if (succ && this.aQI.aPG != null && info != null && info.isSucc()) {
                this.aQI.aQG.setVisibility(8);
                this.aQI.aHb.onLoadComplete();
                this.aQI.aPI.start = info.start;
                this.aQI.aPI.more = info.more;
                if (start == null || start.equals("0")) {
                    this.aQI.aPI.posts.clear();
                    this.aQI.aPI.posts.addAll(info.posts);
                } else {
                    this.aQI.aPI.posts.addAll(info.posts);
                }
                if (UtilsFunction.empty(info.posts)) {
                    t.n(this.aQI.aQC, "没有搜索到数据，请尝试其他关键字搜索");
                }
                this.aQI.aPG.notifyDataSetChanged();
            } else if (info != null) {
                t.n(this.aQI.aQC, ab.n(info.code, info.msg));
            } else if (this.aQI.aPI == null || UtilsFunction.empty(this.aQI.aPI.posts)) {
                this.aQI.aQG.setVisibility(0);
                this.aQI.aQG.FB();
            } else {
                t.n(this.aQI.aQC, "数据请求失败，请下拉刷新重试");
            }
        }
    };
    private String tC;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aQC = this;
        setContentView(i.activity_resource_search);
        EventNotifyCenter.add(h.class, this.mCallback);
        FN();
        this.aJO = (TopicCategory) getIntent().getSerializableExtra("category");
        this.aMV = this.aJO == null ? 0 : this.aJO.getCategoryID();
        this.aQB = (PullToRefreshListView) findViewById(g.list);
        this.aPG = ba.a((Context) this, (ArrayList) this.aPI.posts, true);
        this.aQB.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ TopicSearchActivity aQI;

            {
                this.aQI = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                k.Ej().a(this.aQI.aMV, this.aQI.tC, "0", 20);
            }
        });
        this.aQB.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ TopicSearchActivity aQI;

            {
                this.aQI = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopicItem data = parent.getAdapter().getItem(position);
                if (data != null && (data instanceof TopicItem)) {
                    TopicItem topidData = data;
                    topidData.setCategoryName(this.aQI.aJO.getTitle());
                    t.a(this.aQI.aQC, topidData, 0);
                }
            }
        });
        this.aQB.setAdapter(this.aPG);
        this.aHb = new aa((ListView) this.aQB.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ TopicSearchActivity aQI;

            {
                this.aQI = this$0;
            }

            public void onLoadData() {
                if (!UtilsFunction.empty(this.aQI.tC)) {
                    String start = "0";
                    if (!(this.aQI.aPI == null || this.aQI.aPI.start == null)) {
                        start = this.aQI.aPI.start;
                    }
                    k.Ej().a(this.aQI.aMV, this.aQI.tC, start, 20);
                }
            }

            public boolean shouldLoadData() {
                if (UtilsFunction.empty(this.aQI.tC)) {
                    this.aQI.aHb.onLoadComplete();
                    return false;
                } else if (this.aQI.aPI == null) {
                    this.aQI.aHb.onLoadComplete();
                    return false;
                } else if (this.aQI.aPI.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aQB.setOnScrollListener(this.aHb);
        this.aQG = (BaseLoadingLayout) findViewById(g.base_loading_layout);
        this.aQG.setVisibility(8);
        this.aQG.setRetryClickListener(new BaseLoadingLayout.b(this) {
            final /* synthetic */ TopicSearchActivity aQI;

            {
                this.aQI = this$0;
            }

            public void onRetryClick(View view) {
                if (UtilsFunction.empty(this.aQI.tC) || this.aQI.tC.length() < 2) {
                    this.aQI.aQG.FB();
                } else {
                    k.Ej().a(this.aQI.aMV, this.aQI.tC, "0", 20);
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void FN() {
        this.aIw = (TitleBar) findViewById(g.title_bar);
        this.aIw.setLeftLayout(i.home_left_btn2);
        this.aIw.setRightLayout(i.home_searchbar2);
        this.aIw.findViewById(g.header_title).setVisibility(8);
        this.aQE = (ImageButton) this.aIw.findViewById(g.imgSearchBtn);
        this.aQE.setVisibility(0);
        this.aQE.setOnClickListener(this.aQH);
        this.aQD = (ImageButton) this.aIw.findViewById(g.ImageButtonLeft);
        this.aQD.setVisibility(0);
        this.aQD.setImageDrawable(d.o(this, c.drawableTitleBack));
        this.aQD.setOnClickListener(this.aQH);
        this.aIZ = (ImageView) findViewById(g.imgClear);
        this.aIZ.setOnClickListener(this.aQH);
        this.aQF = (EditText) this.aIw.findViewById(g.edtSearch);
        this.aQF.setHint("输入帖子名称/关键字");
        this.aIZ = (ImageView) findViewById(g.imgClear);
        this.aIZ.setOnClickListener(this.aQH);
        this.aQF.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ TopicSearchActivity aQI;

            {
                this.aQI = this$0;
            }

            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (key.length() > 1) {
                    this.aQI.aIZ.setVisibility(0);
                } else if (key.length() > 0) {
                    this.aQI.aIZ.setVisibility(0);
                } else {
                    this.aQI.aIZ.setVisibility(4);
                    this.aQI.tC = "";
                    if (this.aQI.aPG instanceof Topic2GItemAdapter) {
                        ((Topic2GItemAdapter) this.aQI.aPG).clear();
                    } else if (this.aQI.aPG instanceof TopicWifiItemAdapter) {
                        ((TopicWifiItemAdapter) this.aQI.aPG).clear();
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        this.aQF.setOnEditorActionListener(new OnEditorActionListener(this) {
            final /* synthetic */ TopicSearchActivity aQI;

            {
                this.aQI = this$0;
            }

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 3) {
                    return false;
                }
                this.aQI.Hm();
                return true;
            }
        });
    }

    public void clear() {
        this.aQF.getEditableText().clear();
        this.aQF.getEditableText().clearSpans();
        this.aQF.setText("");
        if (this.aPG instanceof Topic2GItemAdapter) {
            ((Topic2GItemAdapter) this.aPG).clear();
        } else if (this.aPG instanceof TopicWifiItemAdapter) {
            ((TopicWifiItemAdapter) this.aPG).clear();
        }
    }

    private void Hm() {
        CharSequence key = this.aQF.getText().toString().trim();
        if (!UtilsFunction.empty(key)) {
            if (key.length() < 2) {
                t.show_toast(this, "搜索条件必须大于两个字符");
                return;
            }
            this.tC = key;
            at.hideInputMethod(this.aQF);
            k.Ej().a(this.aMV, this.tC, "0", 20);
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aPG != null && (this.aPG instanceof com.simple.colorful.b)) {
            com.simple.colorful.setter.k setter = new j((ViewGroup) this.aQB.getRefreshableView());
            setter.a((com.simple.colorful.b) this.aPG);
            builder.a(setter);
        }
        builder.aY(16908290, c.backgroundDefault).c(this.aQE, c.drawableTitleSearch).c(this.aQD, c.drawableTitleBack).j(this.aQE, c.backgroundTitleBarButton).j(this.aQD, c.backgroundTitleBarButton).aY(g.title_bar, c.backgroundTitleBar).aZ(g.search_back, c.drawableTitleBack).j(this.aQF, c.backgroundSearchView);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aPG.notifyDataSetChanged();
    }
}
